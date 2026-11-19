
package modelo;

/**
 *
 * @author Jurbano
 */
public class MonstruoNormal extends Carta {

    public MonstruoNormal(String nombre, int ataque, int defensa, String posicion) {
        super(nombre, ataque, defensa, posicion);
    }

    @Override
    public int calcularPoderAtaque() {
        return ataque;
    }

    @Override
    public String obtenerTipo() {
        return "Normal";
    }

    @Override
    public String obtenerDescripcion() {
        return nombre + " [Normal | ATQ:" + ataque + " DEF:" + defensa + " | " + posicion + "]";
    }
}
