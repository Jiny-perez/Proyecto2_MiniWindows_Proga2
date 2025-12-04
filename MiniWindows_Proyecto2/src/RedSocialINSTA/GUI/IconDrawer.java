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

    private static ImageIcon createIcon(int size, IconPainter painter) {
        BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        
        painter.paint(g2d, size);
        g2d.dispose();
        
        return new ImageIcon(image);
    }

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

    public static ImageIcon createHomeFilledIcon(int size) {
        return createIcon(size, (g, s) -> {
            g.setColor(ICON_COLOR);
            
            int margin = s / 6;
            
            int[] xPoints = {s/2, s - margin, margin};
            int[] yPoints = {margin, margin + s/3, margin + s/3};
            g.fillPolygon(xPoints, yPoints, 3);
            
            g.fillRect(margin + s/6, margin + s/3, s - 2*margin - s/3, s - margin - s/3);
            
            g.setColor(Color.WHITE);
            int doorWidth = s/5;
            int doorHeight = s/3;
            g.fillRect(s/2 - doorWidth/2, s - margin - doorHeight, doorWidth, doorHeight);
        });
    }
    
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

    public static ImageIcon createMessageIcon(int size) {
        return createIcon(size, (g, s) -> {
            g.setColor(ICON_COLOR);
            g.setStroke(new BasicStroke(2.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            
            int margin = (int)(s * 0.05);
            int rectWidth = (int)(s * 0.85);
            int rectHeight = (int)(s * 0.6);
            int cornerRadius = s / 4;
            
            g.drawRoundRect(margin, margin, rectWidth, rectHeight, cornerRadius, cornerRadius);
            
            int tailBaseY = margin + rectHeight;
            int tailBaseX1 = margin + rectWidth / 5;
            int tailTipX = margin + rectWidth / 8;
            int tailTipY = (int)(s * 0.85);
            int tailBaseX2 = margin + (int)(rectWidth * 0.38);
            
            g.drawLine(tailBaseX1, tailBaseY, tailTipX, tailTipY);
            g.drawLine(tailTipX, tailTipY, tailBaseX2, tailBaseY);
        });
    }
    
    public static ImageIcon createNotificationIcon(int size) {
        return createIcon(size, (g, s) -> {
            g.setColor(ICON_COLOR);
            g.setStroke(new BasicStroke(2.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            
            int bellWidth = (int)(s * 0.65);
            int bellHeight = (int)(s * 0.6);
            int centerX = s / 2;
            int topY = (int)(s * 0.08);
            
            g.drawArc(centerX - bellWidth/2, topY, bellWidth, bellHeight, 0, -180);
            
            int bottomY = topY + bellHeight/2;
            g.drawLine(centerX - bellWidth/2, bottomY, centerX + bellWidth/2, bottomY);
            
            int handleWidth = (int)(s * 0.2);
            int handleY = topY - (int)(s * 0.1);
            g.drawArc(centerX - handleWidth/2, handleY, handleWidth, (int)(s * 0.12), 0, 180);
            
            int clapperSize = (int)(s * 0.18);
            int clapperY = bottomY + (int)(s * 0.03);
            g.fillOval(centerX - clapperSize/2, clapperY, clapperSize, clapperSize);
        });
    }
    
    public static ImageIcon createPlusIcon(int size) {
        return createIcon(size, (g, s) -> {
            g.setColor(ICON_COLOR);
            g.setStroke(new BasicStroke(2.5f));
            
            int margin = s / 4;
            
            g.drawLine(margin, s/2, s - margin, s/2);
            
            g.drawLine(s/2, margin, s/2, s - margin);
        });
    }
    
    public static ImageIcon createProfileIcon(int size) {
        return createIcon(size, (g, s) -> {
            g.setColor(ICON_COLOR);
            g.setStroke(new BasicStroke(2.0f));
            
            int margin = s / 6;
            
            int headSize = s / 3;
            g.drawOval(s/2 - headSize/2, margin, headSize, headSize);
            
            int bodyWidth = (int)(s * 0.65);
            int bodyHeight = (int)(s * 0.4);
            g.drawArc(s/2 - bodyWidth/2, margin + headSize + margin/2, 
                     bodyWidth, bodyHeight, 0, -180);
        });
    }
    
    public static ImageIcon createLogoutIcon(int size) {
        return createIcon(size, (g, s) -> {
            g.setColor(ICON_COLOR);
            g.setStroke(new BasicStroke(2.0f));
            
            int margin = s / 5;
            
            g.drawRoundRect(margin, margin, (int)(s * 0.5), s - 2*margin, s/8, s/8);
            
            int arrowStartX = (int)(s * 0.4);
            int arrowEndX = s - margin;
            int arrowY = s / 2;
            
            g.drawLine(arrowStartX, arrowY, arrowEndX, arrowY);
            
            int arrowSize = s / 6;
            g.drawLine(arrowEndX, arrowY, arrowEndX - arrowSize, arrowY - arrowSize);
            g.drawLine(arrowEndX, arrowY, arrowEndX - arrowSize, arrowY + arrowSize);
        });
    }
    
    public static ImageIcon createHeartOutlineIcon(int size) {
        return createIcon(size, (g, s) -> {
            g.setColor(Color.BLACK);
            g.setStroke(new BasicStroke(2.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            
            Path2D heart = createHeartPath(s);
            g.draw(heart);
        });
    }
    
    public static ImageIcon createHeartFilledIcon(int size) {
        return createIcon(size, (g, s) -> {
            g.setColor(Color.BLACK);  // Negro en lugar de rojo
            
            Path2D heart = createHeartPath(s);
            g.fill(heart);
        });
    }
    
    private static Path2D createHeartPath(int size) {
        Path2D heart = new Path2D.Float();
        
        float cx = size / 2f;
        float cy = size * 0.45f;
        float w = size * 0.8f;
        float h = size * 0.75f;
        
        heart.moveTo(cx, cy + h * 0.5f);
        
        heart.curveTo(
            cx - w * 0.15f, cy + h * 0.25f,
            cx - w * 0.45f, cy + h * 0.05f,
            cx - w * 0.35f, cy - h * 0.15f
        );
        
        heart.curveTo(
            cx - w * 0.5f, cy - h * 0.35f,
            cx - w * 0.2f, cy - h * 0.35f,
            cx, cy - h * 0.05f
        );
        
        heart.curveTo(
            cx + w * 0.2f, cy - h * 0.35f,
            cx + w * 0.5f, cy - h * 0.35f,
            cx + w * 0.35f, cy - h * 0.15f
        );
        
        heart.curveTo(
            cx + w * 0.45f, cy + h * 0.05f,
            cx + w * 0.15f, cy + h * 0.25f,
            cx, cy + h * 0.5f
        );
        
        heart.closePath();
        return heart;
    }
    
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
    
    public static ImageIcon createEyeOpenIcon(int size) {
        return createIcon(size, (g, s) -> {
            g.setColor(new Color(100, 100, 100));
            g.setStroke(new BasicStroke(2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            
            int centerX = s / 2;
            int centerY = s / 2;
            
            int eyeWidth = (int)(s * 0.8);
            int eyeHeight = (int)(s * 0.45);
            g.drawArc(centerX - eyeWidth/2, centerY - eyeHeight/2, eyeWidth, eyeHeight, 0, 180);
            g.drawArc(centerX - eyeWidth/2, centerY - eyeHeight/2, eyeWidth, eyeHeight, 180, 180);
            
            int pupilaSize = (int)(s * 0.35);
            g.fillOval(centerX - pupilaSize/2, centerY - pupilaSize/2, pupilaSize, pupilaSize);
            
            g.setColor(Color.WHITE);
            int destelloSize = (int)(s * 0.1);
            g.fillOval(centerX - pupilaSize/4, centerY - pupilaSize/4, destelloSize, destelloSize);
        });
    }
    
    public static ImageIcon createEyeClosedIcon(int size) {
        return createIcon(size, (g, s) -> {
            g.setColor(new Color(100, 100, 100));
            g.setStroke(new BasicStroke(2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            
            int centerX = s / 2;
            int centerY = s / 2;
            
            int eyeWidth = (int)(s * 0.8);
            int eyeHeight = (int)(s * 0.45);
            g.drawArc(centerX - eyeWidth/2, centerY - eyeHeight/2, eyeWidth, eyeHeight, 0, 180);
            g.drawArc(centerX - eyeWidth/2, centerY - eyeHeight/2, eyeWidth, eyeHeight, 180, 180);
            
            int pupilaSize = (int)(s * 0.35);
            g.fillOval(centerX - pupilaSize/2, centerY - pupilaSize/2, pupilaSize, pupilaSize);
            
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
    
    public static ImageIcon createDeleteIcon(int size) {
        BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        
        g.setColor(new Color(237, 73, 86));
        g.setStroke(new BasicStroke(2.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        
        int margin = (int)(size * 0.25);
        int x1 = margin;
        int y1 = margin;
        int x2 = size - margin;
        int y2 = size - margin;
        
        g.drawLine(x1, y1, x2, y2);
        
        g.drawLine(x2, y1, x1, y2);
        
        g.dispose();
        return new ImageIcon(image);
    }
    
    @FunctionalInterface
    private interface IconPainter {
        void paint(Graphics2D g, int size);
    }
}