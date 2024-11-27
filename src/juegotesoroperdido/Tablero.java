package juegotesoroperdido;

/**
 *
 * @author Miguelito
 */
import java.util.Random;

public class Tablero {
    private static final int SIZE = 20;
    private static final int MINAS = 6;
    private char[][] tablero;
    private boolean[][] minas;
    private int tesoroX, tesoroY;
    private int inicioX, inicioY; // Coordenadas iniciales del jugador
    private boolean juegoTerminado = false;

    public Tablero() {
        tablero = new char[SIZE][SIZE];
        minas = new boolean[SIZE][SIZE];
    }

    public void inicializarTablero() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                tablero[i][j] = '.';
            }
        }
    }

    public void ubicarJugador(Jugador jugador) {
        Random rand = new Random();
        int x = rand.nextInt(SIZE);
        int y = rand.nextInt(SIZE);

        jugador.setX(x);
        jugador.setY(y);

        inicioX = x; // Guardar la posición inicial
        inicioY = y;

        tablero[x][y] = 'J'; // Marcar al jugador en el tablero
    }

    public void ubicarTesoro(Jugador jugador) {
        Random rand = new Random();
        boolean posicionValida = false;

        while (posicionValida==false) {
            int vertical = rand.nextInt(9) + 1;
            int horizontal = 10 - vertical;
            boolean empiezaVertical = rand.nextBoolean();

            if (empiezaVertical) {
                if (jugador.getX() + vertical < SIZE && jugador.getY() + horizontal < SIZE) {
                    tesoroX = jugador.getX() + vertical;
                    tesoroY = jugador.getY() + horizontal;
                    posicionValida = true;
                }
            } 
        }

        tablero[tesoroX][tesoroY] = 'T'; // Revelar el tesoro para depuración
    }

    public void ubicarMinas() {
        Random rand = new Random();
        int minasUbicadas = 0;
        while (minasUbicadas < MINAS) {
            int minaX = rand.nextInt(SIZE);
            int minaY = rand.nextInt(SIZE);

            if ((Math.abs(minaX - tesoroX) > 1) && (Math.abs(minaY - tesoroY)>1) && tablero[minaX][minaY] == '.') {
                minas[minaX][minaY] = true;
                tablero[minaX][minaY] = 'M'; // Revelar las minas para depuración
                minasUbicadas++;
            }
        }
    }

    private void limpiarRastros() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (tablero[i][j] == '*') {
                    tablero[i][j] = '.'; // Reemplaza el rastro por una celda vacía
                }
            }
        }
    }

    public void mostrarTablero() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                System.out.print(tablero[i][j] + " ");
            }
            System.out.println();
        }
    }

    public void moverJugador(Jugador jugador, String direccion) {
        tablero[jugador.getX()][jugador.getY()] = '*'; // Marca el rastro del jugador
        int prevX = jugador.getX();
        int prevY = jugador.getY();
      
        switch (direccion) {
            case "w": if (jugador.getX() > 0)
                jugador.setX(jugador.getX() - 1); break;
            case "s": if (jugador.getX() < SIZE - 1) 
                jugador.setX(jugador.getX() + 1); break;
            case "a": if (jugador.getY() > 0)
                jugador.setY(jugador.getY() - 1); break;
            case "d": if (jugador.getY() < SIZE - 1)
                jugador.setY(jugador.getY() + 1); break;
            case"o":
                System.out.println("Juego terminado");
                juegoTerminado = true;break;
            default: System.out.println("Movimiento no valido"); return;
        }

        if (tablero[jugador.getX()][jugador.getY()] == '*') {
            tablero[prevX][prevY] = '.'; // Limpia el rastro si retrocede
        }
       

        tablero[jugador.getX()][jugador.getY()] = 'J';
    }

    private boolean validarL() {
        int contFilas = 0, contColumnas = 0;
        for (int i = SIZE - 1; i >= 0; i--) {
            int contFila = 0, contColumna = 0;
            for (int j = 0; j < SIZE; j++) {
                if (tablero[i][j] == '*') contFila++;
                if (tablero[j][i] == '*') contColumna++;
            }
            if (contFila > 1) contFilas++;
            if (contColumna > 1) contColumnas++;
        }
        return contFilas == 1 && contColumnas == 1;
    }

    public void verificarEstadoJuego(Jugador jugador, Resultado resultado) {
        if (minas[jugador.getX()][jugador.getY()]) {
            jugador.perderVida();
            System.out.println("Pisaste una mina. Vidas restantes: " + jugador.getVidas());

            if (jugador.getVidas() > 0) {
                limpiarRastros(); // Limpia todos los asteriscos del tablero
                tablero[jugador.getX()][jugador.getY()] = '.'; // Limpia la celda actual
                jugador.setX(inicioX);
                jugador.setY(inicioY);
                tablero[inicioX][inicioY] = 'J'; // Reaparece en la posición inicial
                //System.out.println("Has regresado a tu posición inicial.");
            } else {
                System.out.println("Te haz quedado sin vidas");
                juegoTerminado = true;
            }
        } else if (validarL()==true && tablero[tesoroX][tesoroY]=='*' ) {
                        System.out.println("Tesoro encontrado.");
            juegoTerminado = true;
            resultado.setLlegadaTesoro(true);
        }
    }

    public boolean isJuegoTerminado() {
        return juegoTerminado;
    }
}
