/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package RedSocialINSTA.Logica;

import RedSocialINSTA.Modelo.Notificacion;
import RedSocialINSTA.Modelo.Notificacion.TipoNotificacion;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 *
 * @author najma
 */
public class GestorNotificaciones {
    
    private static final String ARCHIVO_NOTIFICACIONES = "notificaciones_insta.dat";
    private ArrayList<Notificacion> notificaciones;
    
    public GestorNotificaciones() {
        cargarNotificaciones();
    }
    
    private void cargarNotificaciones() {
        File archivo = new File(ARCHIVO_NOTIFICACIONES);
        
        if (archivo.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
                notificaciones = (ArrayList<Notificacion>) ois.readObject();
            } catch (Exception e) {
                System.err.println("Error al cargar notificaciones: " + e.getMessage());
                notificaciones = new ArrayList<>();
            }
        } else {
            notificaciones = new ArrayList<>();
        }
    }
    
    public void guardarNotificaciones() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARCHIVO_NOTIFICACIONES))) {
            oos.writeObject(notificaciones);
        } catch (Exception e) {
            System.err.println("Error al guardar notificaciones: " + e.getMessage());
        }
    }
    
    /**
     * Crea una notificación de like
     */
    public void crearNotificacionLike(String usernameOrigen, String usernameDestino, String idPublicacion) {
        // No crear notificación si el usuario se da like a sí mismo
        if (usernameOrigen.equals(usernameDestino)) {
            return;
        }
        
        // Verificar si ya existe una notificación de like de este usuario en esta publicación
        for (Notificacion notif : notificaciones) {
            if (notif.getTipo() == TipoNotificacion.LIKE &&
                notif.getUsernameOrigen().equals(usernameOrigen) &&
                notif.getUsernameDestino().equals(usernameDestino) &&
                notif.getIdPublicacion().equals(idPublicacion)) {
                // Ya existe, no crear duplicado
                return;
            }
        }
        
        Notificacion notif = new Notificacion(TipoNotificacion.LIKE, usernameOrigen, usernameDestino, idPublicacion, null);
        notificaciones.add(notif);
        guardarNotificaciones();
    }
    
    /**
     * Elimina una notificación de like (cuando se quita el like)
     */
    public void eliminarNotificacionLike(String usernameOrigen, String usernameDestino, String idPublicacion) {
        notificaciones.removeIf(notif -> 
            notif.getTipo() == TipoNotificacion.LIKE &&
            notif.getUsernameOrigen().equals(usernameOrigen) &&
            notif.getUsernameDestino().equals(usernameDestino) &&
            notif.getIdPublicacion().equals(idPublicacion)
        );
        guardarNotificaciones();
    }
    
    /**
     * Crea una notificación de comentario
     */
    public void crearNotificacionComentario(String usernameOrigen, String usernameDestino, 
                                           String idPublicacion, String contenidoComentario) {
        // No crear notificación si el usuario comenta su propia publicación
        if (usernameOrigen.equals(usernameDestino)) {
            return;
        }
        
        Notificacion notif = new Notificacion(TipoNotificacion.COMENTARIO, usernameOrigen, 
                                              usernameDestino, idPublicacion, contenidoComentario);
        notificaciones.add(notif);
        guardarNotificaciones();
    }
    
    /**
     * Crea una notificación de mención
     */
    public void crearNotificacionMencion(String usernameOrigen, String usernameDestino, 
                                        String idPublicacion, String contenido) {
        // No crear notificación si el usuario se menciona a sí mismo
        if (usernameOrigen.equals(usernameDestino)) {
            return;
        }
        
        Notificacion notif = new Notificacion(TipoNotificacion.MENCION, usernameOrigen, 
                                              usernameDestino, idPublicacion, contenido);
        notificaciones.add(notif);
        guardarNotificaciones();
    }
    
    /**
     * Crea una notificación de nuevo seguidor
     */
    public void crearNotificacionSeguidor(String usernameOrigen, String usernameDestino) {
        // Verificar si ya existe
        for (Notificacion notif : notificaciones) {
            if (notif.getTipo() == TipoNotificacion.SEGUIDOR &&
                notif.getUsernameOrigen().equals(usernameOrigen) &&
                notif.getUsernameDestino().equals(usernameDestino)) {
                return; // Ya existe
            }
        }
        
        Notificacion notif = new Notificacion(TipoNotificacion.SEGUIDOR, usernameOrigen, usernameDestino);
        notificaciones.add(notif);
        guardarNotificaciones();
    }
    
    /**
     * Elimina una notificación de seguidor (cuando se deja de seguir)
     */
    public void eliminarNotificacionSeguidor(String usernameOrigen, String usernameDestino) {
        notificaciones.removeIf(notif -> 
            notif.getTipo() == TipoNotificacion.SEGUIDOR &&
            notif.getUsernameOrigen().equals(usernameOrigen) &&
            notif.getUsernameDestino().equals(usernameDestino)
        );
        guardarNotificaciones();
    }
    
    /**
     * Obtiene todas las notificaciones de un usuario (ordenadas por fecha, más recientes primero)
     */
    public ArrayList<Notificacion> obtenerNotificaciones(String username) {
        ArrayList<Notificacion> notificacionesUsuario = new ArrayList<>();
        
        for (Notificacion notif : notificaciones) {
            if (notif.getUsernameDestino().equals(username)) {
                notificacionesUsuario.add(notif);
            }
        }
        
        // Ordenar por fecha (más recientes primero)
        Collections.sort(notificacionesUsuario, new Comparator<Notificacion>() {
            @Override
            public int compare(Notificacion n1, Notificacion n2) {
                return n2.getFechaCreacion().compareTo(n1.getFechaCreacion());
            }
        });
        
        return notificacionesUsuario;
    }
    
    /**
     * Obtiene la cantidad de notificaciones no leídas de un usuario
     */
    public int contarNoLeidas(String username) {
        int count = 0;
        for (Notificacion notif : notificaciones) {
            if (notif.getUsernameDestino().equals(username) && !notif.isLeida()) {
                count++;
            }
        }
        return count;
    }
    
    /**
     * Marca todas las notificaciones de un usuario como leídas
     */
    public void marcarTodasComoLeidas(String username) {
        for (Notificacion notif : notificaciones) {
            if (notif.getUsernameDestino().equals(username)) {
                notif.setLeida(true);
            }
        }
        guardarNotificaciones();
    }
    
    /**
     * Limpia las notificaciones antiguas (más de 30 días)
     */
    public void limpiarNotificacionesAntiguas() {
        // Implementación futura si se desea
    }
}
