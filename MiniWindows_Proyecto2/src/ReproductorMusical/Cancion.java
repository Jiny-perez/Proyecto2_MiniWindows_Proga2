package ReproductorMusical;

import java.io.*;
import org.jaudiotagger.audio.*;

/**
 *
 * @author marye
 */
public class Cancion {

    private String titulo;
    private String artista;
    private String direccion;
    private long duracion;

    public Cancion(String titulo, String artista, String direccion) {
        this.titulo = titulo;
        this.artista = artista;
        this.direccion = direccion;
        this.duracion = calcularDuracionMP3();
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setArtista(String artista) {
        this.artista = artista;
    }

    public String getArtista() {
        return artista;
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

    public boolean isMP3(byte[] cabecera) {
        if (cabecera.length >= 3 && cabecera[0] == 'I' && cabecera[1] == 'D' && cabecera[2] == '3') {
            return true;
        }

        if (cabecera.length >= 2 && (cabecera[0] & 0xFF) == 0xFF && (cabecera[1] & 0xE0) == 0xE0) {
            return true;
        }
        return false;
    }

    public long calcularDuracionMP3() {
        try {
            File MP3 = new File(this.direccion);
            this.duracion = 0;

            AudioFile audioFile = AudioFileIO.read(MP3);
            AudioHeader audioHeader = audioFile.getAudioHeader();

            if (audioHeader != null) {
                return audioHeader.getTrackLength();
            }
        } catch (Exception e) {
            System.err.println("Error: No se logro leer la duración de: " + this.direccion);
            return 0;
        }
        return 0;
    }

    public long CalcularDuracionPorTamaño(long tamanioBytes) {
        double MegaBytes = tamanioBytes / (1024.0 * 1024.0);
        long duracionSegundos = (long) (tamanioBytes / 16000.0);

        duracionSegundos = (long) (duracionSegundos * 0.92);

        if (duracionSegundos < 10) {
            duracionSegundos = 60;
        }
        if (duracionSegundos > 3600) {
            duracionSegundos = 3600;
        }

        return duracionSegundos;
    }

    public String DuracionFormateada() {
        if (duracion <= 0) {
            return "0:00";
        }

        long minutos = duracion / 60;
        long segundos = duracion % 60;

        if (minutos >= 60) {
            long horas = minutos / 60;
            minutos = minutos % 60;
            return String.format("%d:%02d:%02d", horas, minutos, segundos);
        }

        return String.format("%d:%02d", minutos, segundos);
    }
    
     public String toString() {
        return titulo + " - " + artista + " (" + DuracionFormateada() + ")";
    }

}
