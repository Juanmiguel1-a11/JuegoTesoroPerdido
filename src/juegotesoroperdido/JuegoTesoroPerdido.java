package juegotesoroperdido;

/**
 *
 * @author Miguelito
 */
import java.time.Duration;
import java.time.Instant;
import java.util.Scanner;

public class JuegoTesoroPerdido {
    private static final int MAX_PARTIDAS = 100; // Máximo número de partidas registrables
    private static Resultado[] estadisticas = new Resultado[MAX_PARTIDAS];
    private static int partidasJugadas = 0;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean continuar = true;

        while (continuar==true) {
            System.out.println("1. Juego Nuevo");
            System.out.println("2. Estadisticas");
            System.out.println("3. Salir");
            System.out.print("Elige una opcion: ");

            try {
                int opcion = Integer.parseInt(sc.nextLine()); // Convertir la entrada a número

                if (opcion == 1) {
                    juegoNuevo(sc);
                } else if (opcion == 2) {
                    mostrarEstadisticas();
                } else if (opcion == 3) {
                    System.out.println("¡Gracias por jugar! Hasta pronto.");
                    continuar = false;
                } else {
                    System.out.println("Opción no valida. Intenta de nuevo.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada no valida. Por favor, ingresa un numero (1, 2 o 3).");
            }
        }
    }

    private static void juegoNuevo(Scanner sc) {
        
        System.out.println("Introduce tu nombre:");
        String nombre = sc.nextLine();

        Tablero tablero = new Tablero();
        Jugador jugador = new Jugador();
        Resultado resultado = new Resultado(nombre);

        tablero.inicializarTablero();
        tablero.ubicarJugador(jugador);
        tablero.ubicarTesoro(jugador);
        tablero.ubicarMinas();

        System.out.println("Bienvenido " + nombre + " al Juego del Tesoro Perdido");
        Instant inicio = Instant.now();

        while (tablero.isJuegoTerminado()==false) {
            tablero.mostrarTablero();
            System.out.println("Mueve con las teclas W (arriba), S (abajo), A (izquierda), D (derecha):");
            String movimiento = sc.nextLine().toLowerCase();
            tablero.moverJugador(jugador, movimiento);
            tablero.verificarEstadoJuego(jugador, resultado);
        }

        Instant fin = Instant.now();
        Duration tiempoJuego = Duration.between(inicio, fin);
        resultado.setTiempo(tiempoJuego);
        resultado.mostrarResultado();

        // Guardar el resultado en el arreglo
        estadisticas[partidasJugadas] = resultado;
        partidasJugadas++;
    }

    private static void mostrarEstadisticas() {
        System.out.println("--- Estadisticas ---");
        if (partidasJugadas == 0) {
            System.out.println("No hay estadísticas disponibles. Juega una partida primero.");
        } else {
            for (int i = 0; i < partidasJugadas; i++) {
                Resultado r = estadisticas[i];
                System.out.println("Partida " + (i + 1) + ":");
                System.out.println("Jugador: " + r.getNombre());
                System.out.println("Resultado: " + (r.isLlegadaTesoro() ? "Encontro el tesoro" : "No encontro el tesoro."));
                System.out.println("Tiempo jugado: " + r.getTiempo().toMinutesPart() + " minutos y " + r.getTiempo().toSecondsPart() + " segundos.");
                System.out.println("--------------------");
            }
        }
    }
}