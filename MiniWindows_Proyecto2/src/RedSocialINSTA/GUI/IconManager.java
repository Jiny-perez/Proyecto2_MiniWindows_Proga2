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
    
    public static final String LOGO = "instagram_logo.png";
    public static final String DEFAULT_AVATAR = "default_avatar.jpg";
    public static final String PLACEHOLDER_IMAGE = "placeholder_image.jpg";
    
    public static final String ICON_HOME = "icon_home.png";
    public static final String ICON_HOME_FILLED = "icon_home_filled.png";
    public static final String ICON_SEARCH = "icon_search.png";
    public static final String ICON_MESSAGES = "icon_messages.png";
    public static final String ICON_NOTIFICATIONS = "icon_notifications.png";
    public static final String ICON_CREATE = "icon_create.png";
    public static final String ICON_PROFILE = "icon_profile.png";
    public static final String ICON_LOGOUT = "icon_logout.png";
    
    public static final String ICON_HEART_OUTLINE = "icon_heart_outline.png";
    public static final String ICON_HEART_FILLED = "icon_heart_filled.png";
    public static final String ICON_COMMENT = "icon_comment.png";
    public static final String ICON_SHARE = "icon_share.png";
    
    public static ImageIcon getIcon(String nombreArchivo) {
        if (cache.containsKey(nombreArchivo)) {
            return cache.get(nombreArchivo);
        }
        
        try {
            URL url = IconManager.class.getResource(RUTA_IMAGENES + nombreArchivo);
            if (url != null) {
                ImageIcon icon = new ImageIcon(url);
                cache.put(nombreArchivo, icon);
                return icon;
            } else {
                System.err.println("No se encontró la imagen: " + nombreArchivo);
                return crearIconoPlaceholder(24, 24);
            }
        } catch (Exception e) {
            System.err.println("Error al cargar imagen " + nombreArchivo + ": " + e.getMessage());
            return crearIconoPlaceholder(24, 24);
        }
    }

    public static ImageIcon getIconScaled(String nombreArchivo, int ancho, int alto) {
        ImageIcon original = getIcon(nombreArchivo);
        if (original != null && original.getIconWidth() > 0) {
            Image img = original.getImage();
            Image imgScaled = img.getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
            return new ImageIcon(imgScaled);
        }
        return crearIconoPlaceholder(ancho, alto);
    }
    
    public static Image getImage(String nombreArchivo) {
        ImageIcon icon = getIcon(nombreArchivo);
        return icon != null ? icon.getImage() : null;
    }
    
    public static ImageIcon getLogo() {
        return getIcon(LOGO);
    }
    
    public static ImageIcon getLogoScaled(int ancho, int alto) {
        return getIconScaled(LOGO, ancho, alto);
    }
    
    public static ImageIcon getDefaultAvatar() {
        return getIcon(DEFAULT_AVATAR);
    }
    
    public static ImageIcon getDefaultAvatarScaled(int tamano) {
        return getIconScaled(DEFAULT_AVATAR, tamano, tamano);
    }
    
    public static ImageIcon getPlaceholderImage() {
        return getIcon(PLACEHOLDER_IMAGE);
    }
    
    public static void precargarIconos() {
        System.out.println("Precargando iconos...");
        
        getIcon(LOGO);
        
        getIcon(DEFAULT_AVATAR);
        getIcon(PLACEHOLDER_IMAGE);
        
        getIcon(ICON_HOME);
        getIcon(ICON_HOME_FILLED);
        getIcon(ICON_SEARCH);
        getIcon(ICON_MESSAGES);
        getIcon(ICON_NOTIFICATIONS);
        getIcon(ICON_CREATE);
        getIcon(ICON_PROFILE);
        getIcon(ICON_LOGOUT);
        
        getIcon(ICON_HEART_OUTLINE);
        getIcon(ICON_HEART_FILLED);
        getIcon(ICON_COMMENT);
        getIcon(ICON_SHARE);
        
        System.out.println("Iconos precargados: " + cache.size());
    }
    
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
    
    public static void limpiarCache() {
        cache.clear();
    }
    
    public static int getCacheSize() {
        return cache.size();
    }
    
    public static boolean existeIcono(String nombreArchivo) {
        URL url = IconManager.class.getResource(RUTA_IMAGENES + nombreArchivo);
        return url != null;
    }
}