/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package RedSocialINSTA.Modelo;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 *
 * @author najma
 */
public class Notificacion implements Serializable {
    
    public enum TipoNotificacion {
        LIKE,           // Alguien dio like a tu publicación
        COMENTARIO,     // Alguien comentó en tu publicación
        MENCION,        // Alguien te mencionó (@usuario)
        SEGUIDOR        // Alguien comenzó a seguirte
    }
    
    private TipoNotificacion tipo;
    private String usernameOrigen;      // Usuario que generó la notificación
    private String usernameDestino;     // Usuario que recibe la notificación
    private String idPublicacion;       // ID de la publicación relacionada (si aplica)
    private String contenido;           // Contenido adicional (comentario, etc.)
    private LocalDateTime fechaCreacion;
    private boolean leida;
    
    public Notificacion(TipoNotificacion tipo, String usernameOrigen, String usernameDestino) {
        this.tipo = tipo;
        this.usernameOrigen = usernameOrigen;
        this.usernameDestino = usernameDestino;
        this.fechaCreacion = LocalDateTime.now();
        this.leida = false;
    }
    
    public Notificacion(TipoNotificacion tipo, String usernameOrigen, String usernameDestino, 
                       String idPublicacion, String contenido) {
        this(tipo, usernameOrigen, usernameDestino);
        this.idPublicacion = idPublicacion;
        this.contenido = contenido;
    }
    
    // Getters
    public TipoNotificacion getTipo() { return tipo; }
    public String getUsernameOrigen() { return usernameOrigen; }
    public String getUsernameDestino() { return usernameDestino; }
    public String getIdPublicacion() { return idPublicacion; }
    public String getContenido() { return contenido; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public boolean isLeida() { return leida; }
    
    // Setters
    public void setLeida(boolean leida) { this.leida = leida; }
    
    /**
     * Obtiene el mensaje de la notificación según su tipo
     */
    public String getMensaje() {
        switch (tipo) {
            case LIKE:
                return "@" + usernameOrigen + " le gustó tu publicación";
            case COMENTARIO:
                return "@" + usernameOrigen + " comentó en tu publicación";
            case MENCION:
                return "@" + usernameOrigen + " te mencionó en una publicación";
            case SEGUIDOR:
                return "@" + usernameOrigen + " comenzó a seguirte";
            default:
                return "@" + usernameOrigen + " interactuó contigo";
        }
    }
    
    /**
     * Obtiene el icono/emoji según el tipo de notificación
     */
    public String getIcono() {
        switch (tipo) {
            case LIKE:
                return "❤️";
            case COMENTARIO:
                return "💬";
            case MENCION:
                return "@";
            case SEGUIDOR:
                return "👤";
            default:
                return "📬";
        }
    }
    
    /**
     * Calcula el tiempo transcurrido desde la notificación
     */
    public String getTiempoTranscurrido() {
        LocalDateTime ahora = LocalDateTime.now();
        
        long segundos = ChronoUnit.SECONDS.between(fechaCreacion, ahora);
        if (segundos < 60) {
            return "Ahora";
        }
        
        long minutos = ChronoUnit.MINUTES.between(fechaCreacion, ahora);
        if (minutos < 60) {
            return minutos + "m";
        }
        
        long horas = ChronoUnit.HOURS.between(fechaCreacion, ahora);
        if (horas < 24) {
            return horas + "h";
        }
        
        long dias = ChronoUnit.DAYS.between(fechaCreacion, ahora);
        if (dias < 7) {
            return dias + "d";
        }
        
        long semanas = ChronoUnit.WEEKS.between(fechaCreacion, ahora);
        if (semanas < 4) {
            return semanas + "sem";
        }
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM");
        return fechaCreacion.format(formatter);
    }
}