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
            g.setStroke(new BasicStroke(2.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            
            int margin = s / 8;
            int baseY = (int)(s * 0.75);
            
            int[] xPoints = {s/2, s - margin, margin};
            int[] yPoints = {margin, margin + s/3, margin + s/3};
            g.drawPolygon(xPoints, yPoints, 3);
            
            g.drawLine(margin, margin + s/3, margin, baseY);
            g.drawLine(s - margin, margin + s/3, s - margin, baseY);
            g.drawLine(margin, baseY, s - margin, baseY);
            
            int doorWidth = s/4;
            int doorHeight = (int)(s * 0.35);
            int doorX = s/2 - doorWidth/2;
            int doorY = baseY - doorHeight;
            g.drawRect(doorX, doorY, doorWidth, doorHeight);
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
            g.setStroke(new BasicStroke(2.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            
            int circleSize = (int)(s * 0.55);
            int margin = (int)(s * 0.12);
            
            g.drawOval(margin, margin, circleSize, circleSize);
            
            int centerX = margin + circleSize/2;
            int centerY = margin + circleSize/2;
            double angle = Math.toRadians(45);
            int startX = (int)(centerX + circleSize/2 * Math.cos(angle));
            int startY = (int)(centerY + circleSize/2 * Math.sin(angle));
            int endX = s - margin;
            int endY = s - margin;
            
            g.drawLine(startX, startY, endX, endY);
        });
    }
    
    /**
     * Icono de Mensaje/Chat
     */
    public static ImageIcon createMessageIcon(int size) {
        return createIcon(size, (g, s) -> {
            g.setColor(ICON_COLOR);
            g.setStroke(new BasicStroke(2.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            
            // Bocadillo mucho más grande
            int margin = (int)(s * 0.05); // Margen muy pequeño
            int rectWidth = (int)(s * 0.85);
            int rectHeight = (int)(s * 0.6);
            int cornerRadius = s / 4;
            
            // Rectángulo redondeado principal
            g.drawRoundRect(margin, margin, rectWidth, rectHeight, cornerRadius, cornerRadius);
            
            // Cola del bocadillo (más visible)
            int tailBaseY = margin + rectHeight;
            int tailBaseX1 = margin + rectWidth / 5;
            int tailTipX = margin + rectWidth / 8;
            int tailTipY = (int)(s * 0.85);
            int tailBaseX2 = margin + (int)(rectWidth * 0.38);
            
            g.drawLine(tailBaseX1, tailBaseY, tailTipX, tailTipY);
            g.drawLine(tailTipX, tailTipY, tailBaseX2, tailBaseY);
        });
    }
    
    /**
     * Icono de Campana/Notificación
     */
    public static ImageIcon createNotificationIcon(int size) {
        return createIcon(size, (g, s) -> {
            g.setColor(ICON_COLOR);
            g.setStroke(new BasicStroke(2.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            
            // Campana mucho más grande
            int bellWidth = (int)(s * 0.65);
            int bellHeight = (int)(s * 0.6);
            int centerX = s / 2;
            int topY = (int)(s * 0.08); // Margen superior pequeño
            
            // Cuerpo de la campana (arco)
            g.drawArc(centerX - bellWidth/2, topY, bellWidth, bellHeight, 0, -180);
            
            // Línea inferior de la campana
            int bottomY = topY + bellHeight/2;
            g.drawLine(centerX - bellWidth/2, bottomY, centerX + bellWidth/2, bottomY);
            
            // Asa superior de la campana
            int handleWidth = (int)(s * 0.2);
            int handleY = topY - (int)(s * 0.1);
            g.drawArc(centerX - handleWidth/2, handleY, handleWidth, (int)(s * 0.12), 0, 180);
            
            // Badajo (bolita inferior)
            int clapperSize = (int)(s * 0.18);
            int clapperY = bottomY + (int)(s * 0.03);
            g.fillOval(centerX - clapperSize/2, clapperY, clapperSize, clapperSize);
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
     * Icono de Corazón Outline (sin rellenar) - NEGRO
     */
    public static ImageIcon createHeartOutlineIcon(int size) {
        return createIcon(size, (g, s) -> {
            g.setColor(Color.BLACK);  // Negro en lugar de gris
            g.setStroke(new BasicStroke(2.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            
            Path2D heart = createHeartPath(s);
            g.draw(heart);
        });
    }
    
    /**
     * Icono de Corazón Relleno (like activo) - NEGRO
     */
    public static ImageIcon createHeartFilledIcon(int size) {
        return createIcon(size, (g, s) -> {
            g.setColor(Color.BLACK);  // Negro en lugar de rojo
            
            Path2D heart = createHeartPath(s);
            g.fill(heart);
        });
    }
    
    /**
     * Path del corazón reutilizable
     */
    private static Path2D createHeartPath(int size) {
        Path2D heart = new Path2D.Float();
        
        // Centro y dimensiones mejoradas
        float cx = size / 2f;
        float cy = size * 0.45f; // Centrado mejor verticalmente
        float w = size * 0.8f;   // Más ancho
        float h = size * 0.75f;  // Más alto
        
        // Punto inferior (punta del corazón)
        heart.moveTo(cx, cy + h * 0.5f);
        
        // Lado izquierdo: de la punta al lóbulo superior izquierdo
        heart.curveTo(
            cx - w * 0.15f, cy + h * 0.25f,  // Control 1: curvatura inferior
            cx - w * 0.45f, cy + h * 0.05f,   // Control 2: aproximación al lóbulo
            cx - w * 0.35f, cy - h * 0.15f    // Punto: parte superior del lóbulo izquierdo
        );
        
        // Lóbulo superior izquierdo (redondeado)
        heart.curveTo(
            cx - w * 0.5f, cy - h * 0.35f,    // Control 1: curva exterior del lóbulo
            cx - w * 0.2f, cy - h * 0.35f,    // Control 2: curva interior del lóbulo
            cx, cy - h * 0.05f                // Punto: centro superior entre lóbulos
        );
        
        // Lóbulo superior derecho (redondeado)
        heart.curveTo(
            cx + w * 0.2f, cy - h * 0.35f,    // Control 1: curva interior del lóbulo
            cx + w * 0.5f, cy - h * 0.35f,    // Control 2: curva exterior del lóbulo
            cx + w * 0.35f, cy - h * 0.15f    // Punto: parte superior del lóbulo derecho
        );
        
        // Lado derecho: del lóbulo superior derecho a la punta
        heart.curveTo(
            cx + w * 0.45f, cy + h * 0.05f,   // Control 1: aproximación al lóbulo
            cx + w * 0.15f, cy + h * 0.25f,   // Control 2: curvatura inferior
            cx, cy + h * 0.5f                 // Punto: punta del corazón
        );
        
        heart.closePath();
        return heart;
    }
    
    /**
     * Icono de Comentario
     */
    public static ImageIcon createCommentIcon(int size) {
        return createIcon(size, (g, s) -> {
            g.setColor(ICON_COLOR);
            g.setStroke(new BasicStroke(2.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            
            int margin = (int)(s * 0.15);
            int rectWidth = s - 2 * margin;
            int rectHeight = (int)(s * 0.6);
            
            g.drawRoundRect(margin, margin, rectWidth, rectHeight, s/5, s/5);
            
            int tailX1 = margin + rectWidth/4;
            int tailY1 = margin + rectHeight;
            int tailX2 = margin + rectWidth/5;
            int tailY2 = s - margin;
            int tailX3 = margin + rectWidth/2;
            int tailY3 = margin + rectHeight;
            
            g.drawLine(tailX1, tailY1, tailX2, tailY2);
            g.drawLine(tailX2, tailY2, tailX3, tailY3);
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
    
    /**
     * Icono de Ojo Abierto (contraseña visible)
     */
    public static ImageIcon createEyeOpenIcon(int size) {
        return createIcon(size, (g, s) -> {
            g.setColor(new Color(100, 100, 100));
            g.setStroke(new BasicStroke(2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            
            int centerX = s / 2;
            int centerY = s / 2;
            
            // Contorno del ojo (elipse)
            int eyeWidth = (int)(s * 0.8);
            int eyeHeight = (int)(s * 0.45);
            g.drawArc(centerX - eyeWidth/2, centerY - eyeHeight/2, eyeWidth, eyeHeight, 0, 180);
            g.drawArc(centerX - eyeWidth/2, centerY - eyeHeight/2, eyeWidth, eyeHeight, 180, 180);
            
            // Pupila (círculo relleno)
            int pupilaSize = (int)(s * 0.35);
            g.fillOval(centerX - pupilaSize/2, centerY - pupilaSize/2, pupilaSize, pupilaSize);
            
            // Destello en la pupila (punto blanco)
            g.setColor(Color.WHITE);
            int destelloSize = (int)(s * 0.1);
            g.fillOval(centerX - pupilaSize/4, centerY - pupilaSize/4, destelloSize, destelloSize);
        });
    }
    
    /**
     * Icono de Ojo Cerrado/Tachado (contraseña oculta)
     */
    public static ImageIcon createEyeClosedIcon(int size) {
        return createIcon(size, (g, s) -> {
            g.setColor(new Color(100, 100, 100));
            g.setStroke(new BasicStroke(2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            
            int centerX = s / 2;
            int centerY = s / 2;
            
            // Contorno del ojo (elipse)
            int eyeWidth = (int)(s * 0.8);
            int eyeHeight = (int)(s * 0.45);
            g.drawArc(centerX - eyeWidth/2, centerY - eyeHeight/2, eyeWidth, eyeHeight, 0, 180);
            g.drawArc(centerX - eyeWidth/2, centerY - eyeHeight/2, eyeWidth, eyeHeight, 180, 180);
            
            // Pupila (círculo relleno)
            int pupilaSize = (int)(s * 0.35);
            g.fillOval(centerX - pupilaSize/2, centerY - pupilaSize/2, pupilaSize, pupilaSize);
            
            // Línea diagonal que tacha el ojo
            int lineMargin = (int)(s * 0.15);
            g.drawLine(lineMargin, s - lineMargin, s - lineMargin, lineMargin);
        });
    }
    
    public static ImageIcon createInstagramLogo(int width, int height) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        
        String text = "Instagram";
        Font baseFont = new Font("Brush Script MT", Font.ITALIC, 100);
        g2d.setFont(baseFont);
        FontMetrics baseFm = g2d.getFontMetrics();
        
        float naturalWidth = baseFm.stringWidth(text);
        float naturalHeight = baseFm.getHeight();
        float naturalRatio = naturalWidth / naturalHeight;
        
        float targetRatio = (float)width / height;
        
        int finalFontSize;
        if (targetRatio > naturalRatio) {
            finalFontSize = (int)(height * 0.75);
        } else {
            finalFontSize = (int)(width / naturalRatio * 0.75);
        }
        
        Font finalFont = new Font("Brush Script MT", Font.ITALIC, finalFontSize);
        g2d.setFont(finalFont);
        g2d.setColor(new Color(38, 38, 38));
        
        FontMetrics fm = g2d.getFontMetrics();
        int textWidth = fm.stringWidth(text);
        int textHeight = fm.getHeight();
        
        int x = (width - textWidth) / 2;
        int y = (height - textHeight) / 2 + fm.getAscent();
        
        g2d.drawString(text, x, y);
        g2d.dispose();
        
        return new ImageIcon(image);
    }
    
    @FunctionalInterface
    private interface IconPainter {
        void paint(Graphics2D g, int size);
    }
}