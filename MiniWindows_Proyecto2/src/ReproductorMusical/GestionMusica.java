package ReproductorMusical;

import Modelo.ArchivoVirtual;
import Sistema.MiniWindowsClass;

import java.io.*;

/**
 *
 * @author marye
 */
public class GestionMusica {

    private RandomAccessFile file;
    private File archivoReal;

    public GestionMusica() {
        try {
            inicializarArchivoMusica();
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public RandomAccessFile getArchivo() {
        try {
            if (file == null) {
                inicializarArchivoMusica();
            }
        } catch (IOException e) {
            System.out.println("Error al inicializar archivo: " + e.getMessage());
            file = null;
        }
        return file;
    }

    private File fileVirtualToReal(String direccion) {

        String dirVir = direccion.replace("/", File.separator).replace("\\", File.separator);

        String dirBase = System.getProperty("user.dir") + File.separator + "Z";
        File dir = new File(dirBase);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        if (dirVir.startsWith("Z:" + File.separator)) {
            dirVir = dirVir.substring(2);
            if (dirVir.startsWith(File.separator)) {
                dirVir = dirVir.substring(1);
            }
        }

        return new File(dir, dirVir);
    }

    private void inicializarArchivoMusica() throws IOException {

        MiniWindowsClass sistema = MiniWindowsClass.getInstance();

        if (sistema == null || sistema.getUsuarioActual() == null) {
            file = null;
            archivoReal = null;
            return;
        }

        String username = sistema.getUsuarioActual().getUsername();

        String dirVirtual = "Z:\\" + username + "\\Musica\\";

        // Convertir carpeta virtual → carpeta real
        File rutaMusicaReal = fileVirtualToReal(dirVirtual);

        // Asegurar que la carpeta existe
        if (!rutaMusicaReal.exists() && !rutaMusicaReal.mkdirs()) {
            throw new IOException("No se pudo crear el directorio: " + rutaMusicaReal.getAbsolutePath());
        }

        // Archivo final dentro de la carpeta Musica
        archivoReal = new File(rutaMusicaReal, "listaCanciones.mp3");

        // Crear si no existe
        File parent = archivoReal.getParentFile();
        if (parent != null && !parent.exists()) {
            if (!parent.mkdirs()) {
                throw new IOException("No se pudo crear el directorio: " + parent.getAbsolutePath());
            }
        }

        file = new RandomAccessFile(archivoReal, "rw");
    }

    public void agregarCancion(Cancion cancion) throws IOException {

        RandomAccessFile raf = getArchivo();

        if (raf == null) {
            throw new IOException("Archivo de lista de canciones no inicializado.");
        }

        raf.seek(raf.length());
        raf.writeUTF(cancion.getTitulo());
        raf.writeUTF(cancion.getDireccion());
        raf.getFD().sync();

    }

    public Cancion crearCancionDesdeArchivo(String rutaArchivo) {
        File archivo = new File(rutaArchivo);
        String nombreArchivo = archivo.getName();
        String titulo = nombreArchivo.replaceFirst("[.][^.]+$", "");
        return new Cancion(titulo, rutaArchivo);
    }

    public void GuardarListaCanciones(ListaCanciones lista) throws IOException {

        RandomAccessFile raf = getArchivo();

        if (raf == null) {
            throw new IOException("Archivo de lista de canciones no inicializado.");
        }

        raf.setLength(0);
        int totalCanciones = lista.tamanio();

        for (int i = 0; i < totalCanciones; i++) {
            Cancion cancion = lista.getCancion(i);
            if (cancion != null) {
                raf.writeUTF(cancion.getTitulo());
                raf.writeUTF(cancion.getDireccion());
            }
        }

        raf.getFD().sync();
    }
}
