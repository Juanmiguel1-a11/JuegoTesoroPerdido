package juegotesoroperdido;

/**
 *
 * @author Miguelito
 */
public class Jugador {
    private int x, y;
    private int vidas;

    public Jugador() {
        this.vidas = 3;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getVidas() {
        return vidas;
    }

    public void perderVida() {
        vidas--;
    }
}
