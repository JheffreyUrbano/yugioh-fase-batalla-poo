
package modelo;

/**
 *
 * @author Jurbano
 */
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MotorCombateTest {

    @Test
    public void testAtaqueVsAtaqueGanaAtacante() {
        Jugador j1 = new Jugador("Yugi", 8000);
        Jugador j2 = new Jugador("Kaiba", 8000);
        Carta magoOscuro = new MonstruoNormal("Mago Oscuro", 2500, 2100, "ATAQUE");
        Carta soldado = new MonstruoNormal("Soldado Piedra", 1300, 2000, "ATAQUE");
        j1.agregarCarta(magoOscuro);
        j2.agregarCarta(soldado);

        MotorCombate motor = new MotorCombate();
        motor.resolverCombate(j1, magoOscuro, j2, soldado);

        assertTrue(soldado.isDestruida());
        assertFalse(magoOscuro.isDestruida());
        assertEquals(6800, j2.getPuntosVida());
        assertEquals(8000, j1.getPuntosVida());
    }

    @Test
    public void testAtaqueVsAtaqueGanaDefensor() {
        Jugador j1 = new Jugador("Yugi", 8000);
        Jugador j2 = new Jugador("Kaiba", 8000);
        Carta kuriboh = new MonstruoNormal("Kuriboh", 300, 200, "ATAQUE");
        Carta dragon = new MonstruoNormal("Dragon Blanco", 3000, 2500, "ATAQUE");
        j1.agregarCarta(kuriboh);
        j2.agregarCarta(dragon);

        MotorCombate motor = new MotorCombate();
        motor.resolverCombate(j1, kuriboh, j2, dragon);

        assertTrue(kuriboh.isDestruida());
        assertFalse(dragon.isDestruida());
        assertEquals(5300, j1.getPuntosVida());
        assertEquals(8000, j2.getPuntosVida());
    }

    @Test
    public void testAtaqueVsAtaqueEmpate() {
        Jugador j1 = new Jugador("Yugi", 8000);
        Jugador j2 = new Jugador("Kaiba", 8000);
        Carta c1 = new MonstruoNormal("Mago A", 2500, 2100, "ATAQUE");
        Carta c2 = new MonstruoNormal("Mago B", 2500, 2100, "ATAQUE");

        MotorCombate motor = new MotorCombate();
        motor.resolverCombate(j1, c1, j2, c2);

        assertTrue(c1.isDestruida());
        assertTrue(c2.isDestruida());
        assertEquals(8000, j1.getPuntosVida());
        assertEquals(8000, j2.getPuntosVida());
    }

    @Test
    public void testAtaqueVsDefensaSuperaDefensa() {
        Jugador j1 = new Jugador("Yugi", 8000);
        Jugador j2 = new Jugador("Kaiba", 8000);
        Carta dragon = new MonstruoNormal("Dragon Blanco", 3000, 2500, "ATAQUE");
        Carta muro = new MonstruoNormal("Muro Laberinto", 0, 2000, "DEFENSA");

        MotorCombate motor = new MotorCombate();
        motor.resolverCombate(j1, dragon, j2, muro);

        assertTrue(muro.isDestruida());
        assertEquals(8000, j2.getPuntosVida());
    }

    @Test
    public void testAtaqueVsDefensaNoSuperaDefensa() {
        Jugador j1 = new Jugador("Yugi", 8000);
        Jugador j2 = new Jugador("Kaiba", 8000);
        Carta guerrero = new MonstruoNormal("Guerrero", 1500, 1200, "ATAQUE");
        Carta escudo = new MonstruoNormal("Escudo Gigante", 100, 2600, "DEFENSA");

        MotorCombate motor = new MotorCombate();
        motor.resolverCombate(j1, guerrero, j2, escudo);

        assertFalse(guerrero.isDestruida());
        assertFalse(escudo.isDestruida());
        assertEquals(6900, j1.getPuntosVida());
    }

    @Test
    public void testAtaqueDirectoConMonstruoEfecto() {
        Jugador j1 = new Jugador("Yugi", 8000);
        Jugador j2 = new Jugador("Kaiba", 8000);
        Carta efecto = new MonstruoEfecto("Chica Maga", 2000, 1700, "ATAQUE", 300, "Bono magico");
        j1.agregarCarta(efecto);

        MotorCombate motor = new MotorCombate();
        motor.resolverCombate(j1, efecto, j2);

        assertEquals(5700, j2.getPuntosVida());
    }

    @Test
    public void testCartaEnDefensaNoPuedeAtacar() {
        Jugador j1 = new Jugador("Yugi", 8000);
        Jugador j2 = new Jugador("Kaiba", 8000);
        Carta enDefensa = new MonstruoNormal("Muro", 1000, 2000, "DEFENSA");

        MotorCombate motor = new MotorCombate();
        assertThrows(IllegalArgumentException.class, () -> {
            motor.resolverCombate(j1, enDefensa, j2);
        });
    }

    @Test
    public void testPuntosVidaNoBajanDeCero() {
        Jugador j1 = new Jugador("Yugi", 1000);
        j1.recibirDanio(3000);
        assertEquals(0, j1.getPuntosVida());
    }
}
