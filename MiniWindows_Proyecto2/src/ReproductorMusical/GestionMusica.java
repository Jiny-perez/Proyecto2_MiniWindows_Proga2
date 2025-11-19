package ReproductorMusical;

import java.io.*;

/**
 *
 * @author marye
 */
public class GestionMusica {

    private RandomAccessFile file;
    private String direccion = "archivo/cancion.mp3";

    public GestionMusica() {
        try {
            File path = new File("archivo");
            if (!path.exists()) {
                path.mkdir();
            }
            file = new RandomAccessFile(direccion, "rw");
        } catch (IOException e) {
            System.out.println("Error : " + e.getMessage());
        }
    }

    public RandomAccessFile getArchivo() {
        return file;
    }

    public void AddSong(Cancion cancion) throws IOException {
        file.seek(file.length());
        file.writeUTF(cancion.getTitulo());
        file.writeUTF(cancion.getArtista());
        file.writeUTF(cancion.getDireccion());
    }
    
    public void cargarListaCanciones(ListaCanciones lista) throws IOException {
        file.seek(0);

        while (file.getFilePointer() < file.length()) {
            String titulo = file.readUTF();
            String artista = file.readUTF();
            String direccion = file.readUTF();

            Cancion cancion = new Cancion(titulo, artista, direccion);
            lista.addListaCanciones(cancion);
        }
    }
}
