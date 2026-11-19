
package main;

/**
 *
 * @author Jurbano
 */
import controlador.ControladorBatalla;
import java.util.ArrayList;
import modelo.Carta;
import modelo.Jugador;
import modelo.MotorCombate;
import vista.VentanaBatalla;

public class Main {
    private static ArrayList<Jugador> jugadores = new ArrayList<>();
    private static ArrayList<String> historialCombates = new ArrayList<>();

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            VentanaBatalla vista = new VentanaBatalla();
            MotorCombate motor = new MotorCombate();
            new ControladorBatalla(vista, motor, jugadores, historialCombates);

            vista.setTitle("Simulador Fase de Batalla - Yu-Gi-Oh!");
            vista.setLocationRelativeTo(null);
            vista.setVisible(true);
        });
    }

    public static String generarReporteJugadores(ArrayList<Jugador> listaJugadores) {
        StringBuilder sb = new StringBuilder();
        sb.append("=== REPORTE DE ESTADO DE JUGADORES ===\n\n");

        for (Jugador j : listaJugadores) {
            int activos = 0;
            int destruidos = 0;

            for (Carta c : j.getCampo()) {
                if (c.isDestruida()) {
                    destruidos++;
                } else {
                    activos++;
                }
            }

            sb.append("Jugador: ").append(j.getNombre()).append("\n");
            sb.append("  Puntos de Vida (LP): ").append(j.getPuntosVida()).append("\n");
            sb.append("  Monstruos activos: ").append(activos).append("\n");
            sb.append("  Monstruos destruidos: ").append(destruidos).append("\n\n");
        }

        return sb.toString();
    }

    public static String generarReporteCombates(ArrayList<String> historial) {
        StringBuilder sb = new StringBuilder();
        sb.append("=== REPORTE DE RESUMEN DE COMBATES ===\n\n");
        sb.append("Total de ataques registrados: ").append(historial.size()).append("\n");

        int ataquesDirectos = 0;
        for (String linea : historial) {
            if (linea.contains("DIRECTO")) {
                ataquesDirectos++;
            }
        }
        sb.append("Ataques directos realizados: ").append(ataquesDirectos).append("\n");
        sb.append("Combates entre monstruos: ").append(historial.size() - ataquesDirectos).append("\n\n");

        if (historial.isEmpty()) {
            sb.append("Aun no se han realizado combates.");
        } else {
            sb.append("Detalle:\n");
            for (String linea : historial) {
                sb.append("- ").append(linea).append("\n");
            }
        }

        return sb.toString();
    }
}