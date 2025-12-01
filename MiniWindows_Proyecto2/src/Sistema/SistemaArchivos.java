package Sistema;

import Modelo.ArchivoVirtual;
import Modelo.Usuario;
import Excepciones.ArchivoNoValidoException;
import Excepciones.PermisosDenegadosException;

import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Stack;

/**
 *
 * @author najma
 */
public class SistemaArchivos implements Serializable {

    // atributo constante
    private static final long serialVersionUID = 1L;

    // atributos 
    private ArchivoVirtual raiz;
    private ArchivoVirtual carpetaActual;
    private Stack<ArchivoVirtual> historialNavegacion;
    private Usuario usuarioActual;
    private static final String ARCHIVO_SISTEMA = "sistema_archivos.sop";

    // constructor
    public SistemaArchivos() {
        this.historialNavegacion = new Stack<>();
        inicializarSistema();
    }

    // inicializa el sistema con la unidad Z:\
    private void inicializarSistema() {
        this.raiz = new ArchivoVirtual("Z:", "");
        this.raiz.setRutaCompleta("Z:");
        this.carpetaActual = raiz;
    }

    // Métodos CMD
    // obtiene la ruta de la carpeta actual
    public String getRutaActual() {
        return carpetaActual.getRutaCompleta() + "\\";
    }

    // obtiene la carpeta actual
    public ArchivoVirtual getCarpetaActual() {
        return carpetaActual;
    }

    // mkdir - crear nueva carpeta
    public boolean crearCarpeta(String nombre) throws ArchivoNoValidoException {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new ArchivoNoValidoException("El nombre de la carpeta no puede estar vacío");
        }

        if (nombre.contains("\\") || nombre.contains("/") || nombre.contains(":") ||
            nombre.contains("*") || nombre.contains("?") || nombre.contains("\"") ||
            nombre.contains("<") || nombre.contains(">") || nombre.contains("|")) {
            throw new ArchivoNoValidoException(nombre, "contiene caracteres inválidos");
        }

        if (carpetaActual.existeHijo(nombre)) {
            throw new ArchivoNoValidoException(nombre, "ya existe una carpeta o archivo con ese nombre");
        }

        ArchivoVirtual nuevaCarpeta = new ArchivoVirtual(nombre, carpetaActual.getRutaCompleta());
        carpetaActual.agregarHijo(nuevaCarpeta);
        return true;
    }

    // rm - eliminar carpeta o archivo
    public boolean eliminar(String nombre) throws ArchivoNoValidoException {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new ArchivoNoValidoException("Debe especificar un nombre");
        }

        if (!carpetaActual.existeHijo(nombre)) {
            throw new ArchivoNoValidoException(nombre, "no existe");
        }

        return carpetaActual.eliminarHijo(nombre);
    }

    // cd - cambiar de carpeta
    public boolean cambiarDirectorio(String nombre) throws ArchivoNoValidoException {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new ArchivoNoValidoException("Debe especificar un nombre de carpeta");
        }

        ArchivoVirtual destino = carpetaActual.buscarHijo(nombre);

        if (destino == null) {
            throw new ArchivoNoValidoException(nombre, "no existe");
        }

        if (!destino.isEsCarpeta()) {
            throw new ArchivoNoValidoException(nombre, "no es una carpeta");
        }

        historialNavegacion.push(carpetaActual);
        carpetaActual = destino;
        return true;
    }

    /**
     * cd.. - Regresar a la carpeta padre
     */
    public boolean regresarCarpeta() {
        if (!historialNavegacion.isEmpty()) {
            carpetaActual = historialNavegacion.pop();
            return true;
        } else if (carpetaActual != raiz) {
            carpetaActual = raiz;
            return true;
        }
        return false;
    }

    /**
     * dir - Listar contenido de la carpeta actual
     */
    public ArrayList<ArchivoVirtual> listarContenido() {
        if (carpetaActual.getHijos() != null) {
            return new ArrayList<>(carpetaActual.getHijos());
        }
        return new ArrayList<>();
    }

    // Listar contenido como texto formateado (para el CMD)
    public String listarContenidoTexto() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n Directorio de ").append(carpetaActual.getRutaCompleta()).append("\n\n");

        ArrayList<ArchivoVirtual> contenido = listarContenido();

        if (contenido.isEmpty()) {
            sb.append("  (carpeta vacía)\n");
        } else {
            int carpetas = 0;
            int archivos = 0;
            long totalBytes = 0;

            for (ArchivoVirtual nodo : contenido) {
                Calendar cal = nodo.getFechaCreacion();
                String fecha = String.format("%02d/%02d/%04d  %02d:%02d",
                    cal.get(Calendar.DAY_OF_MONTH),
                    cal.get(Calendar.MONTH) + 1,
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.HOUR_OF_DAY),
                    cal.get(Calendar.MINUTE));

                if (nodo.isEsCarpeta()) {
                    sb.append(fecha).append("    <DIR>          ").append(nodo.getNombre()).append("\n");
                    carpetas++;
                } else {
                    sb.append(fecha).append("    ").append(String.format("%,15d", nodo.getTamanio()))
                      .append(" ").append(nodo.getNombre()).append("\n");
                    archivos++;
                    totalBytes += nodo.getTamanio();
                }
            }

            sb.append("\n");
            sb.append(String.format("%,16d archivo(s)  %,d bytes\n", archivos, totalBytes));
            sb.append(String.format("%,16d carpeta(s)\n", carpetas));
        }

        return sb.toString();
    }

    // Métodos gestión de usuarios
    // crear carpeta de usuario con subcarpetas
    public void crearCarpetaUsuario(String username) throws ArchivoNoValidoException {
        ArchivoVirtual anterior = carpetaActual;

        carpetaActual = raiz;

        crearCarpeta(username);
        cambiarDirectorio(username);

        crearCarpeta("Mis Documentos");
        crearCarpeta("Musica");
        crearCarpeta("Mis Imagenes");

        carpetaActual = anterior;
        historialNavegacion.clear();
    }

    // establecer el usuario actual y posicionarse en dicha carpeta
    public void establecerUsuario(Usuario usuario) throws ArchivoNoValidoException, PermisosDenegadosException {
        this.usuarioActual = usuario;

        if (usuario.esAdmin()) {
            carpetaActual = raiz;
        } else {
            carpetaActual = raiz;
            ArchivoVirtual carpetaUsuario = raiz.buscarHijo(usuario.getUsername());

            if (carpetaUsuario != null) {
                carpetaActual = carpetaUsuario;
            } else {
                throw new PermisosDenegadosException("No se encontró la carpeta del usuario");
            }
        }

        historialNavegacion.clear();
    }

    public Usuario getUsuarioActual() {
        return usuarioActual;
    }

    // Otros métodos
    public ArrayList<ArchivoVirtual> listarOrdenadoPorNombre(boolean ascendente) {
        ArrayList<ArchivoVirtual> lista = listarContenido();
        Collections.sort(lista, (a, b) -> {
            int resultado = a.getNombre().compareToIgnoreCase(b.getNombre());
            return ascendente ? resultado : -resultado;
        });
        return lista;
    }

    public ArrayList<ArchivoVirtual> listarOrdenadoPorFecha(boolean ascendente) {
        ArrayList<ArchivoVirtual> lista = listarContenido();
        Collections.sort(lista, (a, b) -> {
            int resultado = a.getFechaModificacion().compareTo(b.getFechaModificacion());
            return ascendente ? resultado : -resultado;
        });
        return lista;
    }

    public ArrayList<ArchivoVirtual> listarOrdenadoPorTamanio(boolean ascendente) {
        ArrayList<ArchivoVirtual> lista = listarContenido();
        Collections.sort(lista, (a, b) -> {
            int resultado = Long.compare(a.getTamanio(), b.getTamanio());
            return ascendente ? resultado : -resultado;
        });
        return lista;
    }

    public ArrayList<ArchivoVirtual> listarOrdenadoPorTipo(boolean ascendente) {
        ArrayList<ArchivoVirtual> lista = listarContenido();
        Collections.sort(lista, (a, b) -> {
            if (a.isEsCarpeta() && !b.isEsCarpeta()) return -1;
            if (!a.isEsCarpeta() && b.isEsCarpeta()) return 1;

            int resultado = a.getTipoArchivo().compareToIgnoreCase(b.getTipoArchivo());
            return ascendente ? resultado : -resultado;
        });
        return lista;
    }

    public boolean crearArchivo(String nombre, String tipo, String contenido) throws ArchivoNoValidoException {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new ArchivoNoValidoException("El nombre del archivo no puede estar vacío");
        }

        if (carpetaActual.existeHijo(nombre)) {
            throw new ArchivoNoValidoException(nombre, "ya existe");
        }

        long tamanio = contenido != null ? contenido.length() : 0;
        ArchivoVirtual nuevoArchivo = new ArchivoVirtual(nombre, carpetaActual.getRutaCompleta(), tipo, tamanio);
        nuevoArchivo.setContenido(contenido);
        carpetaActual.agregarHijo(nuevoArchivo);
        return true;
    }

    // obtener un archivo por su nombre (en carpetaActual)
    public ArchivoVirtual obtenerArchivo(String nombre) {
        return carpetaActual.buscarHijo(nombre);
    }

    // renombrar archivo o carpeta
    public boolean renombrar(String nombreActual, String nombreNuevo) throws ArchivoNoValidoException {
        ArchivoVirtual nodo = carpetaActual.buscarHijo(nombreActual);

        if (nodo == null) {
            throw new ArchivoNoValidoException(nombreActual, "no existe");
        }

        if (carpetaActual.existeHijo(nombreNuevo)) {
            throw new ArchivoNoValidoException(nombreNuevo, "ya existe un archivo o carpeta con ese nombre");
        }

        nodo.setNombre(nombreNuevo);
        return true;
    }

    // guardar el sistema de archivos en disco
    public void guardar() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARCHIVO_SISTEMA))) {
            oos.writeObject(this.raiz);
        } catch (IOException e) {
            System.err.println("Error al guardar el sistema de archivos: "+e.getMessage());
        }
    }

    // cargar el sistema de archivos desde disco
    public boolean cargar() {
        File archivo = new File(ARCHIVO_SISTEMA);
        if (!archivo.exists()) {
            return false;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ARCHIVO_SISTEMA))) {
            this.raiz = (ArchivoVirtual) ois.readObject();
            this.carpetaActual = this.raiz;
            this.historialNavegacion.clear();
            return true;
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error al cargar el sistema de archivos: "+e.getMessage());
            return false;
        }
    }

    // obtener la raíz del sistema
    public ArchivoVirtual getRaiz() {
        return raiz;
    }

    // navegar a una ruta específica desde la raíz
    public boolean navegarARuta(String ruta) throws ArchivoNoValidoException {
        if (ruta == null || ruta.isEmpty()) {
            return false;
        }

        ruta = ruta.replace("Z:", "").replace("Z:\\", "");

        if (ruta.isEmpty()) {
            carpetaActual = raiz;
            historialNavegacion.clear();
            return true;
        }

        String[] partes = ruta.split("\\\\");
        ArchivoVirtual actual = raiz;

        for (String parte : partes) {
            if (!parte.isEmpty()) {
                ArchivoVirtual siguiente = actual.buscarHijo(parte);
                if (siguiente == null || !siguiente.isEsCarpeta()) {
                    throw new ArchivoNoValidoException(parte, "no existe o no es una carpeta");
                }
                actual = siguiente;
            }
        }

        carpetaActual = actual;
        historialNavegacion.clear();
        return true;
    }

   
    private ArchivoVirtual buscarNodoPorRuta(String rutaVirtual) {
        if (rutaVirtual == null) return raiz;

        String r = rutaVirtual.replace("Z:", "").replace("Z:\\", "").replace("Z/", "");
        r = r.replace("/", File.separator).replace("\\", File.separator).trim();

        if (r.startsWith(File.separator)) r = r.substring(1);
        if (r.isEmpty()) return raiz;

        String[] partes = r.split(java.util.regex.Pattern.quote(File.separator));
        ArchivoVirtual actual = raiz;
        for (String parte : partes) {
            if (parte == null || parte.isEmpty()) continue;
            ArchivoVirtual siguiente = actual.buscarHijo(parte);
            if (siguiente == null) return null;
            actual = siguiente;
        }
        return actual;
    }

    
    public boolean crearArchivoEnRuta(String nombre, String tipo, String contenido, String rutaVirtualPadre) throws ArchivoNoValidoException {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new ArchivoNoValidoException("El nombre del archivo no puede estar vacío");
        }

        ArchivoVirtual padre = buscarNodoPorRuta(rutaVirtualPadre);
        if (padre == null) {
            throw new ArchivoNoValidoException(rutaVirtualPadre, "la ruta padre no existe");
        }
        if (!padre.isEsCarpeta()) {
            throw new ArchivoNoValidoException(rutaVirtualPadre, "la ruta padre no es una carpeta");
        }
        if (padre.existeHijo(nombre)) {
            throw new ArchivoNoValidoException(nombre, "ya existe");
        }

        long tamanio = contenido != null ? contenido.length() : 0;
        ArchivoVirtual nuevoArchivo = new ArchivoVirtual(nombre, padre.getRutaCompleta(), tipo, tamanio);
        nuevoArchivo.setContenido(contenido);
        padre.agregarHijo(nuevoArchivo);
        return true;
    }

  
    public boolean crearCarpetaEnRuta(String nombre, String rutaVirtualPadre) throws ArchivoNoValidoException {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new ArchivoNoValidoException("El nombre de la carpeta no puede estar vacío");
        }

        ArchivoVirtual padre = buscarNodoPorRuta(rutaVirtualPadre);
        if (padre == null) {
            throw new ArchivoNoValidoException(rutaVirtualPadre, "la ruta padre no existe");
        }
        if (!padre.isEsCarpeta()) {
            throw new ArchivoNoValidoException(rutaVirtualPadre, "la ruta padre no es una carpeta");
        }
        if (padre.existeHijo(nombre)) {
            throw new ArchivoNoValidoException(nombre, "ya existe una carpeta o archivo con ese nombre");
        }

        ArchivoVirtual nuevaCarpeta = new ArchivoVirtual(nombre, padre.getRutaCompleta());
        padre.agregarHijo(nuevaCarpeta);
        return true;
    }


    public ArchivoVirtual obtenerArchivoEnRuta(String nombre, String rutaVirtualPadre) {
        ArchivoVirtual padre = buscarNodoPorRuta(rutaVirtualPadre);
        if (padre == null) return null;
        return padre.buscarHijo(nombre);
    }


}