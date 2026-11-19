
package controlador;

/**
 *
 * @author Jurbano
 */
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import main.Main;
import modelo.ArchivoHistorial;
import modelo.Carta;
import modelo.Jugador;
import modelo.MonstruoEfecto;
import modelo.MonstruoNormal;
import modelo.MotorCombate;
import vista.VentanaBatalla;

public class ControladorBatalla {
    private VentanaBatalla vista;
    private MotorCombate motor;
    private ArchivoHistorial archivoHistorial;
    private ArrayList<Jugador> jugadores;
    private ArrayList<String> historialCombates;

    private ArrayList<Carta> atacantesDisponibles;
    private ArrayList<Carta> defensoresDisponibles;

    public ControladorBatalla(VentanaBatalla vista, MotorCombate motor,
            ArrayList<Jugador> jugadores, ArrayList<String> historialCombates) {
        this.vista = vista;
        this.motor = motor;
        this.archivoHistorial = new ArchivoHistorial();
        this.jugadores = jugadores;
        this.historialCombates = historialCombates;
        this.atacantesDisponibles = new ArrayList<>();
        this.defensoresDisponibles = new ArrayList<>();

        configurarValidacionesNumericas();
        configurarEventos();
        actualizarCamposEfecto();
        iniciarDuelo(false);
    }

    private void configurarEventos() {
        vista.getBtnIniciarDuelo().addActionListener(e -> iniciarDuelo(true));
        vista.getBtnAgregarCarta().addActionListener(e -> agregarCarta());
        vista.getBtnAtacar().addActionListener(e -> ejecutarAtaque());
        vista.getCmbTurno().addActionListener(e -> actualizarCombosCombate());
        vista.getCmbTipo().addActionListener(e -> actualizarCamposEfecto());
        vista.getBtnReporteJugadores().addActionListener(e -> mostrarReporteJugadores());
        vista.getBtnReporteCombates().addActionListener(e -> mostrarReporteCombates());
    }

    private void configurarValidacionesNumericas() {
        soloNumeros(vista.getTxtLpInicialJ1());
        soloNumeros(vista.getTxtLpInicialJ2());
        soloNumeros(vista.getTxtAtaque());
        soloNumeros(vista.getTxtDefensa());
        soloNumeros(vista.getTxtBono());
    }

    private void soloNumeros(JTextField campo) {
        campo.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c) && c != KeyEvent.VK_BACK_SPACE && c != KeyEvent.VK_DELETE) {
                    e.consume();
                }
            }
        });
    }

    private void actualizarCamposEfecto() {
        boolean esEfecto = "Efecto".equals(vista.getCmbTipo().getSelectedItem());
        vista.getTxtBono().setEnabled(esEfecto);
        vista.getTxtEfecto().setEnabled(esEfecto);
        if (!esEfecto) {
            vista.getTxtBono().setText("0");
            vista.getTxtEfecto().setText("");
        }
    }

    private void iniciarDuelo(boolean mostrarMensaje) {
        try {
            String nombreJ1 = vista.getTxtNombreJ1().getText().trim();
            String nombreJ2 = vista.getTxtNombreJ2().getText().trim();
            String textoLp1 = vista.getTxtLpInicialJ1().getText().trim();
            String textoLp2 = vista.getTxtLpInicialJ2().getText().trim();

            if (nombreJ1.isEmpty() || nombreJ2.isEmpty()) {
                JOptionPane.showMessageDialog(vista, "Ingresa el nombre de ambos jugadores.", "Validacion", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int lp1 = textoLp1.isEmpty() ? Jugador.LP_INICIAL : Integer.parseInt(textoLp1);
            int lp2 = textoLp2.isEmpty() ? Jugador.LP_INICIAL : Integer.parseInt(textoLp2);

            if (lp1 <= 0 || lp2 <= 0) {
                JOptionPane.showMessageDialog(vista, "Los LP iniciales deben ser mayores a 0.", "Validacion", JOptionPane.WARNING_MESSAGE);
                return;
            }

            jugadores.clear();
            jugadores.add(new Jugador(nombreJ1, lp1));
            jugadores.add(new Jugador(nombreJ2, lp2));
            
            cargarMonstruosPorDefecto();
            
            actualizarEtiquetasLP();
            actualizarCombosCombate();

            vista.getTxtHistorial().setText("--- NUEVO DUELO INICIADO ---\n"
                    + jugadores.get(0) + " vs " + jugadores.get(1) + "\n\n");

            if (mostrarMensaje) {
                JOptionPane.showMessageDialog(vista, "Duelo iniciado correctamente.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(vista, "Los LP iniciales deben ser numeros enteros validos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void agregarCarta() {
        try {
            String nombre = vista.getTxtNombreCarta().getText().trim();
            String textoAtq = vista.getTxtAtaque().getText().trim();
            String textoDef = vista.getTxtDefensa().getText().trim();

            if (nombre.isEmpty() || textoAtq.isEmpty() || textoDef.isEmpty()) {
                JOptionPane.showMessageDialog(vista, "Completa el nombre, ataque y defensa del monstruo.", "Validacion", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int atq = Integer.parseInt(textoAtq);
            int def = Integer.parseInt(textoDef);
            String posicion = (String) vista.getCmbPosicion().getSelectedItem();
            String tipo = (String) vista.getCmbTipo().getSelectedItem();

            Carta nuevaCarta;
            if ("Efecto".equals(tipo)) {
                String textoBono = vista.getTxtBono().getText().trim();
                int bono = textoBono.isEmpty() ? 0 : Integer.parseInt(textoBono);
                String efecto = vista.getTxtEfecto().getText().trim();
                if (efecto.isEmpty()) {
                    efecto = "Aumento de ataque";
                }
                nuevaCarta = new MonstruoEfecto(nombre, atq, def, posicion, bono, efecto);
            } else {
                nuevaCarta = new MonstruoNormal(nombre, atq, def, posicion);
            }

            int indiceJugador = vista.getCmbJugadorCarta().getSelectedIndex();
            Jugador duenio = jugadores.get(indiceJugador);
            duenio.agregarCarta(nuevaCarta);

            vista.getTxtHistorial().append("[" + duenio.getNombre() + "] invoco a: " + nuevaCarta.obtenerDescripcion() + "\n");
            limpiarFormularioCarta();
            actualizarCombosCombate();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(vista, "Ataque, defensa y bono deben ser numeros enteros.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void ejecutarAtaque() {
        if (jugadores.get(0).getPuntosVida() <= 0 || jugadores.get(1).getPuntosVida() <= 0) {
            JOptionPane.showMessageDialog(vista, "El duelo ya termino. Presiona Iniciar / Reiniciar Duelo.", "Duelo finalizado", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if (atacantesDisponibles.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "El jugador atacante no tiene monstruos activos en el campo.", "Validacion", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int turno = vista.getCmbTurno().getSelectedIndex();
            Jugador atacante = (turno == 0) ? jugadores.get(0) : jugadores.get(1);
            Jugador defensor = (turno == 0) ? jugadores.get(1) : jugadores.get(0);

            int idxAtac = vista.getCmbCartaAtacante().getSelectedIndex();
            Carta cartaAtac = atacantesDisponibles.get(idxAtac);

            String resultado;
            if (defensoresDisponibles.isEmpty()) {
                resultado = motor.resolverCombate(atacante, cartaAtac, defensor);
            } else {
                int idxDef = vista.getCmbCartaDefensora().getSelectedIndex();
                Carta cartaDef = defensoresDisponibles.get(idxDef);
                resultado = motor.resolverCombate(atacante, cartaAtac, defensor, cartaDef);
            }

            historialCombates.add(resultado);
            vista.getTxtHistorial().append(resultado + "\n");

            actualizarEtiquetasLP();
            actualizarCombosCombate();
            verificarGanador();

        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(vista, ex.getMessage(), "Regla de combate", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void actualizarEtiquetasLP() {
        vista.getLblLpJ1().setText("LP J1: " + jugadores.get(0).getPuntosVida());
        vista.getLblLpJ2().setText("LP J2: " + jugadores.get(1).getPuntosVida());
    }

    private void actualizarCombosCombate() {
        if (jugadores.size() < 2) {
            return;
        }

        int turno = vista.getCmbTurno().getSelectedIndex();
        Jugador atacante = (turno == 0) ? jugadores.get(0) : jugadores.get(1);
        Jugador defensor = (turno == 0) ? jugadores.get(1) : jugadores.get(0);

        atacantesDisponibles.clear();
        defensoresDisponibles.clear();
        vista.getCmbCartaAtacante().removeAllItems();
        vista.getCmbCartaDefensora().removeAllItems();

        for (Carta c : atacante.getCampo()) {
            if (!c.isDestruida()) {
                atacantesDisponibles.add(c);
                vista.getCmbCartaAtacante().addItem(c.obtenerDescripcion());
            }
        }

        for (Carta c : defensor.getCampo()) {
            if (!c.isDestruida()) {
                defensoresDisponibles.add(c);
                vista.getCmbCartaDefensora().addItem(c.obtenerDescripcion());
            }
        }

        if (defensoresDisponibles.isEmpty()) {
            vista.getCmbCartaDefensora().addItem("-- ATAQUE DIRECTO --");
        }
    }

    private void verificarGanador() {
        Jugador j1 = jugadores.get(0);
        Jugador j2 = jugadores.get(1);

        if (j1.getPuntosVida() == 0) {
            String msg = "FIN DEL DUELO: " + j2.getNombre() + " ha ganado el combate.";
            vista.getTxtHistorial().append(msg + "\n");
            JOptionPane.showMessageDialog(vista, msg, "Ganador", JOptionPane.INFORMATION_MESSAGE);
        } else if (j2.getPuntosVida() == 0) {
            String msg = "FIN DEL DUELO: " + j1.getNombre() + " ha ganado el combate.";
            vista.getTxtHistorial().append(msg + "\n");
            JOptionPane.showMessageDialog(vista, msg, "Ganador", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void mostrarReporteJugadores() {
        String reporte = Main.generarReporteJugadores(jugadores);
        JOptionPane.showMessageDialog(vista, reporte, "Reporte de Jugadores", JOptionPane.INFORMATION_MESSAGE);
    }

    private void mostrarReporteCombates() {
        ArrayList<String> registros = historialCombates.isEmpty()
                ? archivoHistorial.leerHistorial()
                : historialCombates;
        String reporte = Main.generarReporteCombates(registros);
        JOptionPane.showMessageDialog(vista, reporte, "Reporte de Combates", JOptionPane.INFORMATION_MESSAGE);
    }

    private void limpiarFormularioCarta() {
        vista.getTxtNombreCarta().setText("");
        vista.getTxtAtaque().setText("");
        vista.getTxtDefensa().setText("");
        vista.getTxtBono().setText("0");
        vista.getTxtEfecto().setText("");
    }
    
    private void cargarMonstruosPorDefecto() {
        Jugador j1 = jugadores.get(0);
        Jugador j2 = jugadores.get(1);

        j1.agregarCarta(new MonstruoNormal("Mago Oscuro", 2500, 2100, "ATAQUE"));
        j1.agregarCarta(new MonstruoEfecto("Chica Maga Oscura", 2000, 1700, "ATAQUE", 300, "Bono de Magos"));

        j2.agregarCarta(new MonstruoNormal("Dragon Blanco", 3000, 2500, "ATAQUE"));
        j2.agregarCarta(new MonstruoNormal("Soldado de Piedra", 1300, 2000, "DEFENSA"));
    }
    
}
