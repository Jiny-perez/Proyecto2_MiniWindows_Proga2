package ReproductorMusical;

import java.io.*;
import org.jaudiotagger.audio.*;

/**
 *
 * @author marye
 */
public class Cancion {

    private String titulo;
    private String direccion;
    private long duracion;

    public Cancion(String titulo, String direccion) {
        this.titulo = titulo;
        this.direccion = direccion;
        this.duracion = calcularDuracionMP3();
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getDireccion() {
        return direccion;
    }

    public long getDuracion() {
        return duracion;
    }

    private long calcularDuracionMP3() {
        try {
            File archivoMP3 = new File(this.direccion);
            if (!archivoMP3.exists()) {
                return 0;
            }

            AudioFile audioFile = AudioFileIO.read(archivoMP3);
            AudioHeader audioHeader = audioFile.getAudioHeader();

            if (audioHeader != null) {
                return audioHeader.getTrackLength();
            }
        } catch (Exception e) {
            System.err.println("Error al leer duración de: " + this.direccion);
        }
        return 0;
    }

    public String DuracionFormateada() {
        if (duracion <= 0) {
            return "0:00";
        }

        long minutos = duracion / 60;
        long segundos = duracion % 60;

        if (minutos >= 60) {
            long horas = minutos / 60;
            minutos %= 60;
            return String.format("%d:%02d:%02d", horas, minutos, segundos);
        }
        return String.format("%d:%02d", minutos, segundos);
    }

    public String toString() {
        return titulo + " (" + DuracionFormateada() + ")";
    }
}
