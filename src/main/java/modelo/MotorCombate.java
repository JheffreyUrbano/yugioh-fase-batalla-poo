
package modelo;

/**
 *
 * @author Jurbano
 */
public class MotorCombate {
    private static int contadorTurnos = 0;
    private ArchivoHistorial archivoHistorial;

    public MotorCombate() {
        this.archivoHistorial = new ArchivoHistorial();
    }

    // Combate monstruo contra monstruo
    public String resolverCombate(Jugador atacante, Carta cartaAtac, Jugador defensor, Carta cartaDef) {
        if (cartaAtac == null || cartaDef == null) {
            throw new IllegalArgumentException("Debe seleccionar ambas cartas para el combate.");
        }
        if (cartaAtac.isDestruida()) {
            throw new IllegalArgumentException("La carta atacante ya fue destruida.");
        }
        if (!cartaAtac.getPosicion().equalsIgnoreCase("ATAQUE")) {
            throw new IllegalArgumentException("Solo las cartas en posicion de ATAQUE pueden atacar.");
        }
        if (cartaDef.isDestruida()) {
            throw new IllegalArgumentException("La carta defensora ya fue destruida.");
        }

        contadorTurnos++;
        int poderAtac = cartaAtac.calcularPoderAtaque();
        int danio = 0;
        String resultado;

        if (cartaDef.getPosicion().equalsIgnoreCase("ATAQUE")) {
            int poderDef = cartaDef.calcularPoderAtaque();

            if (poderAtac > poderDef) {
                danio = poderAtac - poderDef;
                cartaDef.marcarDestruida();
                defensor.recibirDanio(danio);
                resultado = cartaDef.getNombre() + " fue destruido. " + defensor.getNombre() + " pierde " + danio + " LP.";
            } else if (poderAtac < poderDef) {
                danio = poderDef - poderAtac;
                cartaAtac.marcarDestruida();
                atacante.recibirDanio(danio);
                resultado = cartaAtac.getNombre() + " fue destruido. " + atacante.getNombre() + " pierde " + danio + " LP.";
            } else {
                cartaAtac.marcarDestruida();
                cartaDef.marcarDestruida();
                resultado = "Empate en ataque. Ambos monstruos fueron destruidos (0 LP de danio).";
            }
        } else {
            // Ataque vs Defensa
            int defDef = cartaDef.getDefensa();

            if (poderAtac > defDef) {
                cartaDef.marcarDestruida();
                resultado = cartaDef.getNombre() + " en defensa fue destruido (0 LP de danio).";
            } else if (poderAtac < defDef) {
                danio = defDef - poderAtac;
                atacante.recibirDanio(danio);
                resultado = cartaAtac.getNombre() + " no supero la defensa. " + atacante.getNombre() + " pierde " + danio + " LP.";
            } else {
                resultado = "Empate contra defensa. Ningun monstruo destruido (0 LP de danio).";
            }
        }

        String registro = "Turno " + contadorTurnos
                + " | Atacante: " + cartaAtac.getNombre() + " (" + atacante.getNombre() + ")"
                + " vs Defensor: " + cartaDef.getNombre() + " (" + defensor.getNombre() + ")"
                + " | Danio: " + danio
                + " | Resultado: " + resultado;

        archivoHistorial.guardarRegistro(registro);
        return registro;
    }

    // Sobrecarga para ataque directo (sin monstruo defensor)
    public String resolverCombate(Jugador atacante, Carta cartaAtac, Jugador defensor) {
        if (cartaAtac == null) {
            throw new IllegalArgumentException("Debe seleccionar una carta atacante.");
        }
        if (cartaAtac.isDestruida()) {
            throw new IllegalArgumentException("La carta atacante ya fue destruida.");
        }
        if (!cartaAtac.getPosicion().equalsIgnoreCase("ATAQUE")) {
            throw new IllegalArgumentException("Solo las cartas en posicion de ATAQUE pueden atacar.");
        }

        for (Carta c : defensor.getCampo()) {
            if (!c.isDestruida()) {
                throw new IllegalArgumentException("No se puede hacer ataque directo mientras el rival tenga monstruos activos.");
            }
        }

        contadorTurnos++;
        int danio = cartaAtac.calcularPoderAtaque();
        defensor.recibirDanio(danio);

        String resultado = "Ataque directo exitoso. " + defensor.getNombre() + " pierde " + danio + " LP.";
        String registro = "Turno " + contadorTurnos
                + " | Atacante: " + cartaAtac.getNombre() + " (" + atacante.getNombre() + ")"
                + " vs Defensor: DIRECTO (" + defensor.getNombre() + ")"
                + " | Daño: " + danio
                + " | Resultado: " + resultado;

        archivoHistorial.guardarRegistro(registro);
        return registro;
    }

    public static int totalTurnos() {
        return contadorTurnos;
    }
}