/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package RedSocialINSTA.GUI;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.HashMap;

/**
 *
 * @author najma
 */
public class IconManager {
    
    private static final String RUTA_IMAGENES = "/RedSocialINSTA/Imagenes/";
    private static HashMap<String, ImageIcon> cache = new HashMap<>();
    
    // Nombres de archivos
    public static final String LOGO = "instagram_logo.png";
    public static final String DEFAULT_AVATAR = "default_avatar.jpg";
    public static final String PLACEHOLDER_IMAGE = "placeholder_image.jpg";
    
    // Iconos del sidebar
    public static final String ICON_HOME = "icon_home.png";
    public static final String ICON_HOME_FILLED = "icon_home_filled.png";
    public static final String ICON_SEARCH = "icon_search.png";
    public static final String ICON_MESSAGES = "icon_messages.png";
    public static final String ICON_NOTIFICATIONS = "icon_notifications.png";
    public static final String ICON_CREATE = "icon_create.png";
    public static final String ICON_PROFILE = "icon_profile.png";
    public static final String ICON_LOGOUT = "icon_logout.png";
    
    // Iconos de acciones
    public static final String ICON_HEART_OUTLINE = "icon_heart_outline.png";
    public static final String ICON_HEART_FILLED = "icon_heart_filled.png";
    public static final String ICON_COMMENT = "icon_comment.png";
    public static final String ICON_SHARE = "icon_share.png";
    
    /**
     * Obtiene un icono del cache o lo carga si no existe
     * Si falla, usa IconDrawer para dibujar el icono
     */
    public static ImageIcon getIcon(String nombreArchivo) {
        if (cache.containsKey(nombreArchivo)) {
            return cache.get(nombreArchivo);
        }
        
        try {
            URL url = IconManager.class.getResource(RUTA_IMAGENES + nombreArchivo);
            if (url != null) {
                ImageIcon icon = new ImageIcon(url);
                // Verificar que la imagen se cargó correctamente
                if (icon.getIconWidth() > 0 && icon.getIconHeight() > 0) {
                    cache.put(nombreArchivo, icon);
                    return icon;
                }
            }
        } catch (Exception e) {
            System.err.println("Error al cargar imagen " + nombreArchivo + ": " + e.getMessage());
        }
        
        // Si falla, usar IconDrawer para dibujar el icono
        System.out.println("Usando IconDrawer para: " + nombreArchivo);
        ImageIcon drawnIcon = getDrawnIcon(nombreArchivo, 24);
        if (drawnIcon != null) {
            cache.put(nombreArchivo, drawnIcon);
            return drawnIcon;
        }
        
        return crearIconoPlaceholder(24, 24);
    }
    
    /**
     * Obtiene un icono dibujado según el nombre del archivo
     */
    private static ImageIcon getDrawnIcon(String nombreArchivo, int size) {
        switch (nombreArchivo) {
            case ICON_HOME:
                return IconDrawer.createHomeIcon(size);
            case ICON_HOME_FILLED:
                return IconDrawer.createHomeFilledIcon(size);
            case ICON_SEARCH:
                return IconDrawer.createSearchIcon(size);
            case ICON_MESSAGES:
                return IconDrawer.createMessageIcon(size);
            case ICON_NOTIFICATIONS:
                return IconDrawer.createNotificationIcon(size);
            case ICON_CREATE:
                return IconDrawer.createPlusIcon(size);
            case ICON_PROFILE:
                return IconDrawer.createProfileIcon(size);
            case ICON_LOGOUT:
                return IconDrawer.createLogoutIcon(size);
            case ICON_HEART_OUTLINE:
                return IconDrawer.createHeartOutlineIcon(size);
            case ICON_HEART_FILLED:
                return IconDrawer.createHeartFilledIcon(size);
            case ICON_COMMENT:
                return IconDrawer.createCommentIcon(size);
            case DEFAULT_AVATAR:
                return IconDrawer.createDefaultAvatar(size);
            default:
                return null;
        }
    }
    
    /**
     * Obtiene un icono escalado a un tamaño específico
     */
    public static ImageIcon getIconScaled(String nombreArchivo, int ancho, int alto) {
        // Primero intentar obtener icono dibujado directamente al tamaño correcto
        ImageIcon drawnIcon = getDrawnIcon(nombreArchivo, ancho);
        if (drawnIcon != null) {
            cache.put(nombreArchivo + "_" + ancho + "x" + alto, drawnIcon);
            return drawnIcon;
        }
        
        // Si no, intentar cargar desde archivo y escalar
        ImageIcon original = getIcon(nombreArchivo);
        if (original != null && original.getIconWidth() > 0) {
            Image img = original.getImage();
            Image imgScaled = img.getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
            return new ImageIcon(imgScaled);
        }
        
        return crearIconoPlaceholder(ancho, alto);
    }
    
    /**
     * Obtiene una imagen (no como icono) para uso en componentes
     */
    public static Image getImage(String nombreArchivo) {
        ImageIcon icon = getIcon(nombreArchivo);
        return icon != null ? icon.getImage() : null;
    }
    
    /**
     * Obtiene el logo de Instagram
     */
    public static ImageIcon getLogo() {
        return getIcon(LOGO);
    }
    
    /**
     * Obtiene el logo escalado
     */
    public static ImageIcon getLogoScaled(int ancho, int alto) {
        return getIconScaled(LOGO, ancho, alto);
    }
    
    /**
     * Obtiene avatar por defecto
     */
    public static ImageIcon getDefaultAvatar() {
        return getIcon(DEFAULT_AVATAR);
    }
    
    /**
     * Obtiene avatar por defecto escalado (circular)
     */
    public static ImageIcon getDefaultAvatarScaled(int tamano) {
        // Siempre usar IconDrawer para avatares, es más confiable
        return IconDrawer.createDefaultAvatar(tamano);
    }
    
    /**
     * Obtiene placeholder de imagen
     */
    public static ImageIcon getPlaceholderImage() {
        return getIcon(PLACEHOLDER_IMAGE);
    }
    
    /**
     * Pre-carga todos los iconos al inicio para mejor rendimiento
     */
    public static void precargarIconos() {
        System.out.println("Precargando iconos...");
        
        // Logo
        getIcon(LOGO);
        
        // Defaults
        getIcon(DEFAULT_AVATAR);
        getIcon(PLACEHOLDER_IMAGE);
        
        // Sidebar
        getIcon(ICON_HOME);
        getIcon(ICON_HOME_FILLED);
        getIcon(ICON_SEARCH);
        getIcon(ICON_MESSAGES);
        getIcon(ICON_NOTIFICATIONS);
        getIcon(ICON_CREATE);
        getIcon(ICON_PROFILE);
        getIcon(ICON_LOGOUT);
        
        // Actions
        getIcon(ICON_HEART_OUTLINE);
        getIcon(ICON_HEART_FILLED);
        getIcon(ICON_COMMENT);
        getIcon(ICON_SHARE);
        
        System.out.println("Iconos precargados: " + cache.size());
    }
    
    /**
     * Crea un icono placeholder en caso de error
     */
    private static ImageIcon crearIconoPlaceholder(int ancho, int alto) {
        Image img = new java.awt.image.BufferedImage(ancho, alto, java.awt.image.BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = (Graphics2D) img.getGraphics();
        
        g2d.setColor(new Color(200, 200, 200));
        g2d.fillRect(0, 0, ancho, alto);
        
        g2d.setColor(new Color(150, 150, 150));
        g2d.drawRect(0, 0, ancho - 1, alto - 1);
        
        g2d.dispose();
        
        return new ImageIcon(img);
    }
    
    /**
     * Limpia el cache de iconos
     */
    public static void limpiarCache() {
        cache.clear();
    }
    
    /**
     * Obtiene el tamaño del cache
     */
    public static int getCacheSize() {
        return cache.size();
    }
    
    /**
     * Verifica si un icono existe
     */
    public static boolean existeIcono(String nombreArchivo) {
        URL url = IconManager.class.getResource(RUTA_IMAGENES + nombreArchivo);
        return url != null;
    }
}