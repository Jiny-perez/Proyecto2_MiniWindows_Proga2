/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package RedSocialINSTA.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;

/**
 *
 * @author najma
 */
public class IconDrawer {
    
    private static final Color ICON_COLOR = new Color(38, 38, 38);
    private static final Color ICON_COLOR_LIGHT = new Color(142, 142, 142);
    private static final Color ACCENT_COLOR = new Color(0, 149, 246);
    private static final Color LIKE_COLOR = new Color(237, 73, 86);
    
    /**
     * Crea un ImageIcon dibujado con Graphics2D
     */
    private static ImageIcon createIcon(int size, IconPainter painter) {
        BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        
        // Antialiasing para suavidad
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        
        painter.paint(g2d, size);
        g2d.dispose();
        
        return new ImageIcon(image);
    }
    
    /**
     * Icono de Casa/Home
     */
    public static ImageIcon createHomeIcon(int size) {
        return createIcon(size, (g, s) -> {
            g.setColor(ICON_COLOR);
            g.setStroke(new BasicStroke(2.0f));
            
            int margin = s / 6;
            
            // Techo
            int[] xPoints = {s/2, s - margin, margin};
            int[] yPoints = {margin, margin + s/3, margin + s/3};
            g.drawPolygon(xPoints, yPoints, 3);
            
            // Casa
            g.drawRect(margin + s/6, margin + s/3, s - 2*margin - s/3, s - margin - s/3);
            
            // Puerta
            int doorWidth = s/5;
            int doorHeight = s/3;
            g.fillRect(s/2 - doorWidth/2, s - margin - doorHeight, doorWidth, doorHeight);
        });
    }
    
    /**
     * Icono de Casa/Home Relleno
     */
    public static ImageIcon createHomeFilledIcon(int size) {
        return createIcon(size, (g, s) -> {
            g.setColor(ICON_COLOR);
            
            int margin = s / 6;
            
            // Techo relleno
            int[] xPoints = {s/2, s - margin, margin};
            int[] yPoints = {margin, margin + s/3, margin + s/3};
            g.fillPolygon(xPoints, yPoints, 3);
            
            // Casa rellena
            g.fillRect(margin + s/6, margin + s/3, s - 2*margin - s/3, s - margin - s/3);
            
            // Puerta (más oscura)
            g.setColor(Color.WHITE);
            int doorWidth = s/5;
            int doorHeight = s/3;
            g.fillRect(s/2 - doorWidth/2, s - margin - doorHeight, doorWidth, doorHeight);
        });
    }
    
    /**
     * Icono de Lupa/Search
     */
    public static ImageIcon createSearchIcon(int size) {
        return createIcon(size, (g, s) -> {
            g.setColor(ICON_COLOR);
            g.setStroke(new BasicStroke(2.0f));
            
            int circleSize = s * 2 / 3;
            int margin = s / 6;
            
            // Círculo de lupa
            g.drawOval(margin, margin, circleSize, circleSize);
            
            // Mango
            int handleStartX = margin + circleSize - circleSize/5;
            int handleStartY = margin + circleSize - circleSize/5;
            g.drawLine(handleStartX, handleStartY, s - margin, s - margin);
        });
    }
    
    /**
     * Icono de Mensaje/Chat
     */
    public static ImageIcon createMessageIcon(int size) {
        return createIcon(size, (g, s) -> {
            g.setColor(ICON_COLOR);
            g.setStroke(new BasicStroke(2.0f));
            
            int margin = s / 6;
            int width = s - 2 * margin;
            int height = (int)(s * 0.6);
            
            // Bocadillo
            Path2D path = new Path2D.Float();
            path.moveTo(margin, margin);
            path.lineTo(s - margin, margin);
            path.lineTo(s - margin, margin + height);
            path.lineTo(s/2 + margin/2, margin + height);
            path.lineTo(s/2, s - margin);
            path.lineTo(s/2 - margin/2, margin + height);
            path.lineTo(margin, margin + height);
            path.closePath();
            
            g.draw(path);
        });
    }
    
    /**
     * Icono de Campana/Notificación
     */
    public static ImageIcon createNotificationIcon(int size) {
        return createIcon(size, (g, s) -> {
            g.setColor(ICON_COLOR);
            g.setStroke(new BasicStroke(2.0f));
            
            int margin = s / 5;
            int bellWidth = s - 2 * margin;
            int bellHeight = (int)(s * 0.55);
            
            // Campana
            Path2D bell = new Path2D.Float();
            bell.moveTo(s/2, margin);
            bell.curveTo(margin, margin + bellHeight/3, margin, margin + bellHeight, margin, margin + bellHeight);
            bell.lineTo(s - margin, margin + bellHeight);
            bell.curveTo(s - margin, margin + bellHeight, s - margin, margin + bellHeight/3, s/2, margin);
            
            g.draw(bell);
            
            // Línea horizontal inferior
            g.drawLine(margin, margin + bellHeight, s - margin, margin + bellHeight);
            
            // Badajo
            int clapperSize = s / 8;
            g.fillOval(s/2 - clapperSize/2, margin + bellHeight, clapperSize, clapperSize);
        });
    }
    
    /**
     * Icono de Plus/Crear
     */
    public static ImageIcon createPlusIcon(int size) {
        return createIcon(size, (g, s) -> {
            g.setColor(ICON_COLOR);
            g.setStroke(new BasicStroke(2.5f));
            
            int margin = s / 4;
            
            // Línea horizontal
            g.drawLine(margin, s/2, s - margin, s/2);
            
            // Línea vertical
            g.drawLine(s/2, margin, s/2, s - margin);
        });
    }
    
    /**
     * Icono de Persona/Perfil
     */
    public static ImageIcon createProfileIcon(int size) {
        return createIcon(size, (g, s) -> {
            g.setColor(ICON_COLOR);
            g.setStroke(new BasicStroke(2.0f));
            
            int margin = s / 6;
            
            // Cabeza (círculo)
            int headSize = s / 3;
            g.drawOval(s/2 - headSize/2, margin, headSize, headSize);
            
            // Cuerpo (semicírculo)
            int bodyWidth = (int)(s * 0.65);
            int bodyHeight = (int)(s * 0.4);
            g.drawArc(s/2 - bodyWidth/2, margin + headSize + margin/2, 
                     bodyWidth, bodyHeight, 0, -180);
        });
    }
    
    /**
     * Icono de Puerta/Logout
     */
    public static ImageIcon createLogoutIcon(int size) {
        return createIcon(size, (g, s) -> {
            g.setColor(ICON_COLOR);
            g.setStroke(new BasicStroke(2.0f));
            
            int margin = s / 5;
            
            // Marco de puerta
            g.drawRoundRect(margin, margin, (int)(s * 0.5), s - 2*margin, s/8, s/8);
            
            // Flecha saliendo
            int arrowStartX = (int)(s * 0.4);
            int arrowEndX = s - margin;
            int arrowY = s / 2;
            
            g.drawLine(arrowStartX, arrowY, arrowEndX, arrowY);
            
            // Punta de flecha
            int arrowSize = s / 6;
            g.drawLine(arrowEndX, arrowY, arrowEndX - arrowSize, arrowY - arrowSize);
            g.drawLine(arrowEndX, arrowY, arrowEndX - arrowSize, arrowY + arrowSize);
        });
    }
    
    /**
     * Icono de Corazón Outline (sin rellenar)
     */
    public static ImageIcon createHeartOutlineIcon(int size) {
        return createIcon(size, (g, s) -> {
            g.setColor(ICON_COLOR);
            g.setStroke(new BasicStroke(2.0f));
            
            Path2D heart = createHeartPath(s);
            g.draw(heart);
        });
    }
    
    /**
     * Icono de Corazón Relleno (like activo)
     */
    public static ImageIcon createHeartFilledIcon(int size) {
        return createIcon(size, (g, s) -> {
            g.setColor(LIKE_COLOR);
            
            Path2D heart = createHeartPath(s);
            g.fill(heart);
        });
    }
    
    /**
     * Path del corazón reutilizable
     */
    private static Path2D createHeartPath(int size) {
        Path2D heart = new Path2D.Float();
        
        int margin = size / 6;
        float cx = size / 2f;
        float cy = size / 2f;
        float w = size - 2 * margin;
        float h = size - 2 * margin;
        
        heart.moveTo(cx, cy + h/4);
        
        // Lado izquierdo
        heart.curveTo(cx - w/2, cy - h/4, 
                     cx - w/4, cy - h/2, 
                     cx, cy - h/8);
        
        // Lado derecho
        heart.curveTo(cx + w/4, cy - h/2, 
                     cx + w/2, cy - h/4, 
                     cx, cy + h/4);
        
        return heart;
    }
    
    /**
     * Icono de Comentario
     */
    public static ImageIcon createCommentIcon(int size) {
        return createIcon(size, (g, s) -> {
            g.setColor(ICON_COLOR);
            g.setStroke(new BasicStroke(2.0f));
            
            int margin = s / 6;
            
            // Bocadillo de diálogo
            g.drawRoundRect(margin, margin, s - 2*margin, (int)(s * 0.6), s/6, s/6);
            
            // Cola del bocadillo
            int[] xPoints = {margin + s/6, margin + s/4, margin + s/3};
            int[] yPoints = {(int)(margin + s * 0.6), s - margin, (int)(margin + s * 0.6)};
            g.drawPolyline(xPoints, yPoints, 3);
        });
    }
    
    /**
     * Avatar circular por defecto
     */
    public static ImageIcon createDefaultAvatar(int size) {
        return createIcon(size, (g, s) -> {
            g.setColor(new Color(200, 200, 200));
            g.fillOval(0, 0, s, s);
            
            g.setColor(Color.WHITE);
            
            int headSize = s / 3;
            g.fillOval(s/2 - headSize/2, s/4, headSize, headSize);
            
            int bodyWidth = (int)(s * 0.7);
            int bodyHeight = (int)(s * 0.5);
            g.fillArc(s/2 - bodyWidth/2, s/2, bodyWidth, bodyHeight, 0, -180);
        });
    }
    
    public static ImageIcon createInstagramLogo(int width, int height) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        
        Font font = new Font("Brush Script MT", Font.ITALIC, (int)(height * 0.8));
        g2d.setFont(font);
        g2d.setColor(new Color(38, 38, 38));
        
        FontMetrics fm = g2d.getFontMetrics();
        String text = "Instagram";
        int textWidth = fm.stringWidth(text);
        int x = (width - textWidth) / 2;
        int y = ((height - fm.getHeight()) / 2) + fm.getAscent();
        
        g2d.drawString(text, x, y);
        g2d.dispose();
        
        return new ImageIcon(image);
    }
    
    @FunctionalInterface
    private interface IconPainter {
        void paint(Graphics2D g, int size);
    }
}