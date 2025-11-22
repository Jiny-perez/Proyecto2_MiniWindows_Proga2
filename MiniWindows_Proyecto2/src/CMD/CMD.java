package CMD;

import java.io.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author marye
 */
public class CMD {

    private File carpetaActual;

    public CMD() {
        carpetaActual = new File(System.getProperty("user.dir"));
    }

    public String getPrompt() {
        return carpetaActual.getAbsolutePath() + "> ";
    }

    public String Ejecutar(String entrada) {
        if (entrada == null) {
            entrada = " ";
        }
        entrada = entrada.trim();
        if (entrada.isEmpty()) {
            return "";
        }
        String[] datos = entrada.split("\\s+", 2);
        String comando = datos[0].toLowerCase();
        String parametro = (datos.length > 1) ? datos[1] : "";
        switch (comando) {
            case "mkdir":
                return crearCarpeta(parametro);
            case "rm":
                return borrarCarpeta(parametro);
            case "cd":
                return CambiarCarpeta(parametro);
            case "..":
                return RegresarCarpeta();
            case "dir":
                return dir();
            case "date":
                return fechaActual();
            case "time":
                return horaActual();
            default:
                return "Comando no valido";
        }
    }

    private String crearCarpeta(String nombreCarpeta) {
        if (nombreCarpeta == null || nombreCarpeta.isBlank()) {
            return "Comando no valido";
        }
        File nueva = new File(carpetaActual, nombreCarpeta);
        if (nueva.exists()) {
            return "La carpeta \"" + nueva.getName() + "\" ya existe";
        }
        return nueva.mkdir() ? "Carpeta creada " + nueva.getName() : "No se pudo crear la carpeta.";
    }
    
     private boolean eliminarTodo(File f) {
        if (f.isDirectory()) {
            File[] hijos = f.listFiles();
            if (hijos != null) {
                for (File h : hijos) {
                    if (!eliminarTodo(h)) {
                        return false;
                    }
                }
            }
        }
        return f.delete();
    }

    private String borrarCarpeta(String nombreCarpeta) {
        if (nombreCarpeta == null || nombreCarpeta.isBlank()) {
            return "Comando no valido";
        }
        
        File objetivo = new File(carpetaActual, nombreCarpeta);
       
        if (!objetivo.exists()) {
            return "No existe";
        }
        
        return eliminarTodo(objetivo) ? "Eliminado: " + nombreCarpeta : "No se pudo eliminar";
    }

    private String CambiarCarpeta(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            return "Comando no valido";
        }
        File nuevaRuta = new File(nombre);
        if (!nuevaRuta.isAbsolute()) {
            nuevaRuta = new File(carpetaActual, nombre);
        }
        if (nuevaRuta.exists() && nuevaRuta.isDirectory()) {
            carpetaActual = nuevaRuta;
            return "";
        }
        return "Directorio no encontrado";
    }

    private String RegresarCarpeta() {
        File padre = carpetaActual.getParentFile();
        if (padre != null) {
            carpetaActual = padre;
            return "";
        }
        return "Ya estas en la raiz";
    }

    private String dir() {
        File[] lista = carpetaActual.listFiles();
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        StringBuilder salida = new StringBuilder();
        salida.append("\nDirectorio de: ").append(carpetaActual.getAbsolutePath()).append("\n\n");

        salida.append(String.format("%-19s  %-6s  %-10s  %s%n", "Modificacion", "Tipo", "Tamano", "Nombre"));
        salida.append("---------------------------------------------------------\n");
        if (lista != null) {
            for (File f : lista) {
                String fecha = formato.format(new Date(f.lastModified()));
                String tipo = f.isDirectory() ? "<DIR>" : "FILE";
                String tam = f.isDirectory() ? "-" : convertirTam(f.length());
                String nombre = f.getName();
                salida.append(String.format("%-19s  %-6s  %-10s  %s%n", fecha, tipo, tam, nombre));
            }
        }
        return salida.toString();
    }

    private String convertirTam(Long b) {
        if (b < 1024) {
            return b + " B";
        }
        double temp = b;
        String[] unidades = {"KB", "MB", "GB", "TB", "PB"};
        int pos = 0;
        temp /= 1024.0;
        while (temp >= 1024.0 && pos < unidades.length - 1) {
            temp /= 1024.0;
            pos++;
        }
        int t = (int) Math.round(temp * 10);
        int entero = t / 10;
        int decimal = t % 10;
        String numero = (decimal == 0) ? ("" + entero) : (entero + "." + decimal);
        return numero + " " + unidades[pos];
    }

    private String fechaActual() {
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }

    private String horaActual() {
        return new SimpleDateFormat("HH:mm:ss").format(new Date());
    }
}
