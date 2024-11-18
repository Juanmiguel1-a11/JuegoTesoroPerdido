package juegotesoroperdido;

/**
 *
 * @author Miguelito
 */
import java.util.Scanner;
import java.time.*;


public class JuegoTesoroPerdido {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean jugarDeNuevo = true;

        while (jugarDeNuevo== true) {
            System.out.println("Introduce tu nombre:");
            String nombre = sc.nextLine();

            Tablero tablero = new Tablero();
            Jugador jugador = new Jugador();
            Resultado resultado = new Resultado(nombre);

            tablero.inicializarTablero();
            tablero.ubicarJugador(jugador);
            tablero.ubicarTesoro(jugador);
            tablero.ubicarMinas();

            System.out.println("¡Bienvenido " + nombre + " al Juego del Tesoro Perdido!");
            Instant inicio = Instant.now();

            while (tablero.isJuegoTerminado()==false) {
                tablero.mostrarTablero();
                System.out.println("Mueve con las teclas W (arriba), S (abajo), A (izquierda), D (derecha)");
                char movimiento = sc.next().charAt(0);
                tablero.moverJugador(jugador, movimiento);
                tablero.verificarEstadoJuego(jugador, resultado);
            }

            Instant end = Instant.now();
            Duration tiempoJuego = Duration.between(inicio, end);
            resultado.setTiempo(tiempoJuego);
            resultado.mostrarResultado();

            System.out.println("¿Quieres jugar de nuevo? (S para sí, cualquier otra tecla para salir):");
            char respuesta = sc.next().charAt(0);
            jugarDeNuevo = (respuesta == 'S' || respuesta == 's');
            sc.nextLine(); // Captura el salto de línea restante
        }

        
        System.out.println("¡Gracias por jugar!");
    }
}
