
package juegotesoroperdido;

/**
 *
 * @author Miguelito
 */
import java.time.Duration;

public class Resultado {
    private String nombre;
    private boolean llegadaTesoro;
    private Duration tiempo;

    public Resultado(String nombre) {
        this.nombre = nombre;
        this.llegadaTesoro = false;
    }

    public void setLlegadaTesoro(boolean llegadaTesoro) {
        this.llegadaTesoro = llegadaTesoro;
    }

    public boolean isLlegadaTesoro() {
        return llegadaTesoro;
    }

    public void setTiempo(Duration tiempo) {
        this.tiempo = tiempo;
    }

    public Duration getTiempo() {
        return tiempo;
    }

    public String getNombre() {
        return nombre;
    }

    public void mostrarResultado() {
        System.out.println("Jugador: " + nombre);
        if (llegadaTesoro==true) {
            System.out.println("Has llegado al tesoro");
        } else {
            System.out.println("No lograste llegar al tesoro.");
        }
        System.out.println("Tiempo jugado: " + tiempo.toMinutesPart() + " minutos y " + tiempo.toSecondsPart() + " segundos.");
    }
}