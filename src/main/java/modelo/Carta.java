
package modelo;

/**
 *
 * @author Jurbano
 */

public abstract class Carta implements ICombate {
    protected String nombre;
    protected int ataque;
    protected int defensa;
    protected String posicion; // "ATAQUE" o "DEFENSA"
    protected boolean destruida;

    public Carta(String nombre, int ataque, int defensa, String posicion) {
        this.nombre = nombre;
        this.ataque = ataque;
        this.defensa = defensa;
        this.posicion = posicion.toUpperCase();
        this.destruida = false;
    }

    public abstract String obtenerDescripcion();

    public void cambiarPosicion(String nuevaPosicion) {
        this.posicion = nuevaPosicion.toUpperCase();
    }

    public void marcarDestruida() {
        this.destruida = true;
    }

    public String getNombre() {
        return nombre;
    }

    public int getAtaque() {
        return ataque;
    }

    public int getDefensa() {
        return defensa;
    }

    public String getPosicion() {
        return posicion;
    }

    public boolean isDestruida() {
        return destruida;
    }
}