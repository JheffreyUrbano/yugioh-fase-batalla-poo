
package modelo;

/**
 *
 * @author Jurbano
 */
public class MonstruoEfecto extends Carta {
    private int bonoAtaque;
    private String nombreEfecto;

    public MonstruoEfecto(String nombre, int ataque, int defensa, String posicion, int bonoAtaque, String nombreEfecto) {
        super(nombre, ataque, defensa, posicion);
        this.bonoAtaque = Math.max(0, bonoAtaque);
        this.nombreEfecto = nombreEfecto;
    }

    @Override
    public int calcularPoderAtaque() {
        return ataque + bonoAtaque;
    }

    @Override
    public String obtenerTipo() {
        return "Efecto";
    }

    @Override
    public String obtenerDescripcion() {
        return nombre + " [Efecto: " + nombreEfecto + " (+" + bonoAtaque + ") | ATQ:"
                + calcularPoderAtaque() + " DEF:" + defensa + " | " + posicion + "]";
    }

    public int getBonoAtaque() {
        return bonoAtaque;
    }

    public String getNombreEfecto() {
        return nombreEfecto;
    }
}
