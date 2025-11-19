/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
/**
 *
 * @author najma
 */
public class ArchivoVirtual implements Serializable {
    
    // atributo constante
    private static final long serialVersionUID = 1L;
    
    // atributos
    private String nombre;
    private boolean esCarpeta;
    private Calendar fechaCreacion;
    private Calendar fechaModificacion;
    private long tamanio;
    private String rutaCompleta;
    private ArrayList<ArchivoVirtual> hijos;
    private String contenido;
    private String tipoArchivo;
    
    // constructor para carpetas
    public ArchivoVirtual(String nombre, String rutaPadre) {
        this.nombre = nombre;
        this.esCarpeta = true;
        this.fechaCreacion = Calendar.getInstance();
        this.fechaModificacion = Calendar.getInstance();
        this.tamanio = 0;
        this.rutaCompleta = rutaPadre.endsWith("\\") ? rutaPadre + nombre : rutaPadre + "\\" + nombre;
        this.hijos = new ArrayList<>();
        this.tipoArchivo = "Carpetas";
    }
    
    // constructor para archivos 
    public ArchivoVirtual(String nombre, String rutaPadre, String tipoArchivo, long tamanio) {
        this.nombre = nombre;
        this.esCarpeta = false;
        this.fechaCreacion = Calendar.getInstance();
        this.fechaModificacion = Calendar.getInstance();
        this.tamanio = tamanio;
        this.rutaCompleta = rutaPadre.endsWith("\\") ? rutaPadre + nombre : rutaPadre + "\\" + nombre;
        this.hijos = null;
        this.tipoArchivo = tipoArchivo;
        this.contenido = "";
    }
    
    // métodos para el manejo de subcarpetas y archivos
    public void agregarHijo(ArchivoVirtual hijo) {
        if (esCarpeta && hijos != null) {
            hijos.add(hijo);
            this.fechaModificacion = Calendar.getInstance();
        }
    }
    
    public boolean eliminarHijo(String nombreHijo) {
        if (esCarpeta && hijos != null) {
            for (int i = 0; i < hijos.size(); i++) {
                if (hijos.get(i).getNombre().equalsIgnoreCase(nombreHijo)) {
                    hijos.remove(i);
                    this.fechaModificacion = Calendar.getInstance();
                    return true;
                }
            }
        }
        return false;
    }
    
    public ArchivoVirtual buscarHijo(String nombreHijo) {
        if (esCarpeta && hijos != null) {
            for(ArchivoVirtual hijo : hijos) {
                if (hijo.getNombre().equalsIgnoreCase(nombreHijo)) {
                    return hijo;
                }
            }
        }
        return null;
    }
    
    public boolean existeHijo(String nombreHijo) {
        return buscarHijo(nombreHijo) != null;
    }
    
    // getters 
    public String getNombre() {
        return nombre;
    }

    public boolean isEsCarpeta() {
        return esCarpeta;
    }

    public Calendar getFechaCreacion() {
        return fechaCreacion;
    }

    public Calendar getFechaModificacion() {
        return fechaModificacion;
    }

    public long getTamanio() {
        return tamanio;
    }

    public String getRutaCompleta() {
        return rutaCompleta;
    }

    public ArrayList<ArchivoVirtual> getHijos() {
        return hijos;
    }

    public String getContenido() {
        return contenido;
    }

    public String getTipoArchivo() {
        return tipoArchivo;
    }
    
    // setters
    public void setNombre(String nombre) {
        this.nombre = nombre;
        this.fechaModificacion = Calendar.getInstance();
    }
    
    public void setFechaModificacion(Calendar fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }
    
    public void setTamanio(long tamanio) {
        this.tamanio = tamanio;
    }
    
    public void setRutaCompleta(String rutaCompleta) {
        this.rutaCompleta = rutaCompleta;
    }
    
    public void setContenido(String contenido) {
        this.contenido = contenido;
        this.tamanio = contenido != null ? contenido.length() : 0;
        this.fechaModificacion = Calendar.getInstance();
    }
    
    public void setTipoArchivo(String tipoArchivo) {
        this.tipoArchivo = tipoArchivo;
    }
    
    @Override 
    public String toString() {
        if (esCarpeta) {
            return "[DIR] "+nombre; 
        } else {
            return nombre + " (" + tamanio + " bytes)";
        }
    }
}