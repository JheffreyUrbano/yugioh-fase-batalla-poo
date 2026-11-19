
package modelo;

/**
 *
 * @author Jurbano
 */
import java.util.ArrayList;

public class Jugador {
    public static final int LP_INICIAL = 8000;

    private String nombre;
    private int puntosVida;
    private ArrayList<Carta> campo;

    public Jugador(String nombre) {
        this(nombre, LP_INICIAL);
    }

    public Jugador(String nombre, int puntosVida) {
        this.nombre = nombre;
        this.puntosVida = Math.max(0, puntosVida);
        this.campo = new ArrayList<>();
    }

    public void recibirDanio(int danio) {
        if (danio > 0) {
            this.puntosVida -= danio;
            if (this.puntosVida < 0) {
                this.puntosVida = 0;
            }
        }
    }

    public void agregarCarta(Carta carta) {
        this.campo.add(carta);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPuntosVida() {
        return puntosVida;
    }

    public void setPuntosVida(int puntosVida) {
        this.puntosVida = Math.max(0, puntosVida);
    }

    public ArrayList<Carta> getCampo() {
        return campo;
    }

    public void setCampo(ArrayList<Carta> campo) {
        this.campo = campo;
    }

    @Override
    public String toString() {
        return nombre + " (LP: " + puntosVida + ")";
    }
}
