/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package VisorImagenes;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

/**
 *
 * @author najma
 */
public class VisorImagenes {
    
    private ArrayList<File> listaImagenes;
    private int indiceActual;
    private BufferedImage imagenOriginal;
    private BufferedImage imagenTransformada;
    
    public double zoomActual;
    private int rotacionActual;
    
    public VisorImagenes(File archivoInicial) {
        this.listaImagenes = new ArrayList<>();
        this.indiceActual = 0;
        this.zoomActual = 1.0;
        this.rotacionActual = 0;
        
        cargarListaImagenes(archivoInicial);
    }
      
    private void cargarListaImagenes(File archivoInicial) {
        File directorio = archivoInicial.getParentFile();
        
        if (directorio != null && directorio.exists()) {
            File[] archivos = directorio.listFiles();
            
            if (archivos != null) {
                for (File archivo : archivos) {
                    if (esImagen(archivo)) {
                        listaImagenes.add(archivo);
                        if (archivo.equals(archivoInicial)) {
                            indiceActual = listaImagenes.size() - 1;
                        }
                    }
                }
            }
        }
        
        if (listaImagenes.isEmpty() && esImagen(archivoInicial)) {
            listaImagenes.add(archivoInicial);
            indiceActual = 0;
        }
    }
    
    public boolean esImagen(File archivo) {
        if (archivo == null || !archivo.isFile()) {
            return false;
        }
        
        String nombre = archivo.getName().toLowerCase();
        return nombre.endsWith(".jpg") || nombre.endsWith(".jpeg") || 
               nombre.endsWith(".png") || nombre.endsWith(".gif") ||
               nombre.endsWith(".bmp") || nombre.endsWith(".webp");
    }
    
    public boolean cargarImagen(int indice) {
        if (indice < 0 || indice >= listaImagenes.size()) {
            return false;
        }
        
        File archivo = listaImagenes.get(indice);
        
        try {
            imagenOriginal = ImageIO.read(archivo);
            
            if (imagenOriginal == null) {
                return false;
            }
            
            zoomActual = 1.0;
            rotacionActual = 0;
            indiceActual = indice;
            
            actualizarImagenTransformada();
            return true;
            
        } catch (IOException e) {
            return false;
        }
    }  
    
    public void actualizarImagenTransformada() { 
        if (imagenOriginal == null) {
            return;
        }
        
        imagenTransformada = imagenOriginal;
        
        if (rotacionActual != 0) {
            imagenTransformada = rotarImagen(imagenTransformada, rotacionActual);
        }
        
        if (zoomActual != 1.0) {
            imagenTransformada = escalarImagen(imagenTransformada, zoomActual);
        }
    }
    
    private BufferedImage rotarImagen(BufferedImage imagen, int grados) {
        int ancho = imagen.getWidth();
        int alto = imagen.getHeight();
        
        int nuevoAncho = (grados == 90 || grados == 270) ? alto : ancho;
        int nuevoAlto = (grados == 90 || grados == 270) ? ancho : alto;
        
        BufferedImage rotada = new BufferedImage(nuevoAncho, nuevoAlto, imagen.getType());
        Graphics2D g2d = rotada.createGraphics();
        
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        
        AffineTransform transform = new AffineTransform();
        transform.translate(nuevoAncho / 2.0, nuevoAlto / 2.0);
        transform.rotate(Math.toRadians(grados));
        transform.translate(-ancho / 2.0, -alto / 2.0);
        
        g2d.setTransform(transform);
        g2d.drawImage(imagen, 0, 0, null);
        g2d.dispose();
        
        return rotada;
    }
    
    private BufferedImage escalarImagen(BufferedImage imagen, double escala) {
        int nuevoAncho = (int) (imagen.getWidth() * escala);
        int nuevoAlto = (int) (imagen.getHeight() * escala);
        
        if (nuevoAncho <= 0 || nuevoAlto <= 0) {
            return imagen;
        }
        
        BufferedImage escalada = new BufferedImage(nuevoAncho, nuevoAlto, imagen.getType());
        Graphics2D g2d = escalada.createGraphics();
        
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        
        g2d.drawImage(imagen, 0, 0, nuevoAncho, nuevoAlto, null);
        g2d.dispose();
        
        return escalada;
    }
    
    
    public boolean imagenAnterior() {
        if (indiceActual > 0) {
            return cargarImagen(indiceActual - 1);
        }
        return false;
    }
    
    public boolean imagenSiguiente() {
        if (indiceActual < listaImagenes.size() - 1) {
            return cargarImagen(indiceActual + 1);
        }
        return false;
    }
    
    
    public void zoomIn() {
        zoomActual = Math.min(zoomActual * 1.25, 5.0);
        actualizarImagenTransformada();
    }
    
    public void zoomOut() {
        zoomActual = Math.max(zoomActual / 1.25, 0.1);
        actualizarImagenTransformada();
    }
    
    public void tamañoReal() {
        zoomActual = 1.0;
        actualizarImagenTransformada();
    }
    
    public void ajustarPantalla(int anchoVentana, int altoVentana) {
        if (imagenOriginal == null) {
            return;
        }
        
        int anchoImagen = imagenOriginal.getWidth();
        int altoImagen = imagenOriginal.getHeight();
        
        if (rotacionActual == 90 || rotacionActual == 270) {
            int temp = anchoImagen;
            anchoImagen = altoImagen;
            altoImagen = temp;
        }
        
        double escalaAncho = (double) anchoVentana / anchoImagen;
        double escalaAlto = (double) altoVentana / altoImagen;
        
        zoomActual = Math.min(escalaAncho, escalaAlto);
        actualizarImagenTransformada();
    }
    
    
    public void rotarIzquierda() {
        rotacionActual = (rotacionActual - 90 + 360) % 360;
        actualizarImagenTransformada();
    }
    
    public void rotarDerecha() {
        rotacionActual = (rotacionActual + 90) % 360;
        actualizarImagenTransformada();
    }
    
    
    public BufferedImage getImagenTransformada() {
        return imagenTransformada;
    }
    
    public BufferedImage getImagenOriginal() {
        return imagenOriginal;
    }
    
    public File getArchivoActual() {
        if (indiceActual >= 0 && indiceActual < listaImagenes.size()) {
            return listaImagenes.get(indiceActual);
        }
        return null;
    }
    
    public int getIndiceActual() {
        return indiceActual;
    }
    
    public int getTotalImagenes() {
        return listaImagenes.size();
    }
    
    public double getZoomActual() {
        return zoomActual;
    }
    
    public int getRotacionActual() {
        return rotacionActual;
    }
    
    public boolean hayAnterior() {
        return indiceActual > 0;
    }
    
    public boolean haySiguiente() {
        return indiceActual < listaImagenes.size() - 1;
    }
    
    public int getAnchoOriginal() {
        return imagenOriginal != null ? imagenOriginal.getWidth() : 0;
    }
    
    public int getAltoOriginal() {
        return imagenOriginal != null ? imagenOriginal.getHeight() : 0;
    }
}