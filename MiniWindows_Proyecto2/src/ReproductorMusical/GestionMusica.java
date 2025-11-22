package ReproductorMusical;

import java.io.*;

/**
 *
 * @author marye
 */
public class GestionMusica {

    private RandomAccessFile file;
    private final String direccion = "archivo/cancion.mp3";

    public GestionMusica() {
        try {
            File path = new File("archivo");
            if (!path.exists()) {
                path.mkdir();
            }
            file = new RandomAccessFile(direccion, "rw");
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public RandomAccessFile getArchivo() {
        return file;
    }

    public void AddSong(Cancion cancion) throws IOException {
        file.seek(file.length());
        file.writeUTF(cancion.getTitulo());
        file.writeUTF(cancion.getDireccion());
    }

    public Cancion crearCancionDesdeArchivo(String rutaArchivo) {
        File archivo = new File(rutaArchivo);
        String nombreArchivo = archivo.getName();
        String titulo = nombreArchivo.replaceFirst("[.][^.]+$", "");
        return new Cancion(titulo, rutaArchivo);
    }

    public void GuardarListaCanciones(ListaCanciones lista) throws IOException {
        file.setLength(0);
        int totalCanciones = lista.tamanio();
        for (int i = 0; i < totalCanciones; i++) {
            Cancion cancion = lista.obtenerCancion(i);
            if (cancion != null) {
                AddSong(cancion);
            }
        }
    }
}
