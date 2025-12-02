/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package RedSocialINSTA.GUI;

import RedSocialINSTA.Logica.GestorINSTA;
import RedSocialINSTA.Logica.GestorINSTA.EstadisticasUsuario;
import RedSocialINSTA.Modelo.Publicacion;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.ArrayList;

/**
 *
 * @author najma
 */
public class PanelPerfil extends JPanel {
    
    private GestorINSTA gestorINSTA;
    private String usernameVisualizando;
    private VentanaINSTA ventanaPrincipal;
    
    private JPanel panelEstadisticas;
    private JPanel panelPublicaciones;
    private JButton btnSeguir;
    private JScrollPane scrollPane;
    
    private static final Color BACKGROUND_COLOR = new Color(250, 250, 250);
    private static final Color CARD_COLOR = Color.WHITE;
    private static final Color BORDER_COLOR = new Color(219, 219, 219);
    private static final Color TEXT_PRIMARY = new Color(38, 38, 38);
    private static final Color TEXT_SECONDARY = new Color(142, 142, 142);
    private static final Color ACCENT_COLOR = new Color(0, 149, 246);
    
    public PanelPerfil(GestorINSTA gestor, String username, VentanaINSTA ventana) {
        this.gestorINSTA = gestor;
        this.usernameVisualizando = username;
        this.ventanaPrincipal = ventana;
        
        initComponents();
    }
    
    private void initComponents() {
        setLayout(new BorderLayout());
        setBackground(BACKGROUND_COLOR);
        
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
        panelPrincipal.setBackground(BACKGROUND_COLOR);
        
        panelPrincipal.add(crearHeader());
        panelPrincipal.add(Box.createVerticalStrut(20));
        
        JSeparator separador = new JSeparator();
        separador.setForeground(BORDER_COLOR);
        separador.setMaximumSize(new Dimension(800, 1));
        panelPrincipal.add(separador);
        panelPrincipal.add(Box.createVerticalStrut(20));
        
        panelPublicaciones = new JPanel();
        panelPublicaciones.setLayout(new BoxLayout(panelPublicaciones, BoxLayout.Y_AXIS));
        panelPublicaciones.setBackground(BACKGROUND_COLOR);
        panelPublicaciones.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));
        
        panelPrincipal.add(panelPublicaciones);
        
        scrollPane = new JScrollPane(panelPrincipal);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        
        add(scrollPane, BorderLayout.CENTER);
    }
    
    private JPanel crearHeader() {
        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setBackground(CARD_COLOR);
        header.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        header.setMaximumSize(new Dimension(800, 300));
        header.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.LEFT, 30, 0));
        panelSuperior.setBackground(CARD_COLOR);
        panelSuperior.setMaximumSize(new Dimension(800, 160));
        
        JPanel fotoPerfil = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                g2d.setColor(new Color(200, 200, 200));
                g2d.fillOval(0, 0, 150, 150);
                
                g2d.setColor(Color.WHITE);
                g2d.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 70));
                g2d.drawString("👤", 40, 105);
            }
        };
        fotoPerfil.setPreferredSize(new Dimension(150, 150));
        fotoPerfil.setBackground(CARD_COLOR);
        
        JPanel panelInfo = new JPanel();
        panelInfo.setLayout(new BoxLayout(panelInfo, BoxLayout.Y_AXIS));
        panelInfo.setBackground(CARD_COLOR);
        
        JLabel lblUsername = new JLabel("@" + usernameVisualizando);
        lblUsername.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblUsername.setForeground(TEXT_PRIMARY);
        lblUsername.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        panelInfo.add(lblUsername);
        panelInfo.add(Box.createVerticalStrut(10));
        
        // Por ahora, solo mostrar el username como nombre también
        JLabel lblNombre = new JLabel("Usuario de Instagram");
        lblNombre.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblNombre.setForeground(TEXT_SECONDARY);
        lblNombre.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelInfo.add(lblNombre);
        panelInfo.add(Box.createVerticalStrut(20));
        
        if (!usernameVisualizando.equals(gestorINSTA.getUsernameActual())) {
            boolean estaSiguiendo = gestorINSTA.estaSiguiendo(usernameVisualizando);
            
            btnSeguir = new JButton(estaSiguiendo ? "Siguiendo" : "Seguir");
            btnSeguir.setFont(new Font("Segoe UI", Font.BOLD, 14));
            btnSeguir.setAlignmentX(Component.LEFT_ALIGNMENT);
            
            if (estaSiguiendo) {
                btnSeguir.setForeground(TEXT_PRIMARY);
                btnSeguir.setBackground(CARD_COLOR);
                btnSeguir.setBorder(new CompoundBorder(
                    new LineBorder(BORDER_COLOR, 1),
                    BorderFactory.createEmptyBorder(8, 24, 8, 24)
                ));
            } else {
                btnSeguir.setForeground(Color.WHITE);
                btnSeguir.setBackground(ACCENT_COLOR);
                btnSeguir.setBorder(BorderFactory.createEmptyBorder(8, 24, 8, 24));
            }
            
            btnSeguir.setFocusPainted(false);
            btnSeguir.setCursor(new Cursor(Cursor.HAND_CURSOR));
            btnSeguir.addActionListener(e -> toggleSeguir());
            
            panelInfo.add(btnSeguir);
        }
        
        panelSuperior.add(fotoPerfil);
        panelSuperior.add(panelInfo);
        
        header.add(panelSuperior);
        header.add(Box.createVerticalStrut(30));
        
        header.add(crearPanelEstadisticas());
        
        return header;
    }
    
    private JPanel crearPanelEstadisticas() {
        panelEstadisticas = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 0));
        panelEstadisticas.setBackground(CARD_COLOR);
        panelEstadisticas.setMaximumSize(new Dimension(600, 50));
        
        EstadisticasUsuario stats = gestorINSTA.obtenerEstadisticas(usernameVisualizando);
        
        panelEstadisticas.add(crearEstadistica(String.valueOf(stats.cantidadPublicaciones), "publicaciones"));
        panelEstadisticas.add(crearEstadistica(String.valueOf(stats.cantidadSeguidores), "seguidores"));
        panelEstadisticas.add(crearEstadistica(String.valueOf(stats.cantidadSiguiendo), "siguiendo"));
        
        return panelEstadisticas;
    }
    
    private JPanel crearEstadistica(String valor, String etiqueta) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(CARD_COLOR);
        
        JLabel lblValor = new JLabel(valor);
        lblValor.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblValor.setForeground(TEXT_PRIMARY);
        lblValor.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel lblEtiqueta = new JLabel(etiqueta);
        lblEtiqueta.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblEtiqueta.setForeground(TEXT_SECONDARY);
        lblEtiqueta.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        panel.add(lblValor);
        panel.add(lblEtiqueta);
        
        return panel;
    }
    
    private void toggleSeguir() {
        gestorINSTA.toggleSeguir(usernameVisualizando);
        
        boolean ahoraSiguiendo = gestorINSTA.estaSiguiendo(usernameVisualizando);
        btnSeguir.setText(ahoraSiguiendo ? "Siguiendo" : "Seguir");
        
        if (ahoraSiguiendo) {
            btnSeguir.setForeground(TEXT_PRIMARY);
            btnSeguir.setBackground(CARD_COLOR);
            btnSeguir.setBorder(new CompoundBorder(
                new LineBorder(BORDER_COLOR, 1),
                BorderFactory.createEmptyBorder(8, 24, 8, 24)
            ));
        } else {
            btnSeguir.setForeground(Color.WHITE);
            btnSeguir.setBackground(ACCENT_COLOR);
            btnSeguir.setBorder(BorderFactory.createEmptyBorder(8, 24, 8, 24));
        }
        
        actualizarEstadisticas();
    }
    
    private void actualizarEstadisticas() {
        panelEstadisticas.removeAll();
        
        EstadisticasUsuario stats = gestorINSTA.obtenerEstadisticas(usernameVisualizando);
        
        panelEstadisticas.add(crearEstadistica(String.valueOf(stats.cantidadPublicaciones), "publicaciones"));
        panelEstadisticas.add(crearEstadistica(String.valueOf(stats.cantidadSeguidores), "seguidores"));
        panelEstadisticas.add(crearEstadistica(String.valueOf(stats.cantidadSiguiendo), "siguiendo"));
        
        panelEstadisticas.revalidate();
        panelEstadisticas.repaint();
    }
    
    public void actualizarContenido() {
        panelPublicaciones.removeAll();
        
        ArrayList<Publicacion> publicaciones = gestorINSTA.obtenerPublicacionesDeUsuario(usernameVisualizando);
        
        if (publicaciones.isEmpty()) {
            mostrarMensajeVacio();
        } else {
            JLabel lblTitulo = new JLabel("Publicaciones");
            lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 16));
            lblTitulo.setForeground(TEXT_PRIMARY);
            lblTitulo.setAlignmentX(Component.LEFT_ALIGNMENT);
            lblTitulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 16, 0));
            
            panelPublicaciones.add(lblTitulo);
            
            for (Publicacion publicacion : publicaciones) {
                TarjetaPublicacion tarjeta = new TarjetaPublicacion(publicacion, gestorINSTA, ventanaPrincipal);
                panelPublicaciones.add(tarjeta);
                panelPublicaciones.add(Box.createVerticalStrut(15));
            }
        }
        
        actualizarEstadisticas();
        
        panelPublicaciones.revalidate();
        panelPublicaciones.repaint();
        
        SwingUtilities.invokeLater(() -> scrollPane.getVerticalScrollBar().setValue(0));
    }
    
    private void mostrarMensajeVacio() {
        JLabel lblMensaje = new JLabel("Aún no hay publicaciones");
        lblMensaje.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblMensaje.setForeground(TEXT_SECONDARY);
        lblMensaje.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblMensaje.setBorder(BorderFactory.createEmptyBorder(40, 0, 40, 0));
        
        panelPublicaciones.add(lblMensaje);
    }
}