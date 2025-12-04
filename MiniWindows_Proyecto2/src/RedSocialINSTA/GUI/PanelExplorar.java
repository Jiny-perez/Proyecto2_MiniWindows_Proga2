/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package RedSocialINSTA.GUI;

import RedSocialINSTA.Logica.GestorINSTA;
import RedSocialINSTA.Modelo.Publicacion;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.ArrayList;

/**
 *
 * @author najma
 */
public class PanelExplorar extends JPanel {
   
    private GestorINSTA gestorINSTA;
    private VentanaINSTA ventanaPrincipal;
    
    private JTextField txtBusqueda;
    private JPanel panelResultados;
    private JScrollPane scrollResultados;
    
    private static final Color BACKGROUND_COLOR = new Color(250, 250, 250);
    private static final Color CARD_COLOR = Color.WHITE;
    private static final Color BORDER_COLOR = new Color(219, 219, 219);
    private static final Color TEXT_PRIMARY = new Color(38, 38, 38);
    private static final Color TEXT_SECONDARY = new Color(142, 142, 142);
    private static final Color ACCENT_COLOR = new Color(0, 149, 246);
    
    public PanelExplorar(GestorINSTA gestor, VentanaINSTA ventana) {
        this.gestorINSTA = gestor;
        this.ventanaPrincipal = ventana;
        
        initComponents();
    }
    
    private void initComponents() {
        setLayout(new BorderLayout());
        setBackground(BACKGROUND_COLOR);
        
        // Panel de búsqueda
        JPanel panelBusqueda = crearPanelBusqueda();
        add(panelBusqueda, BorderLayout.NORTH);
        
        // Panel de resultados
        panelResultados = new JPanel();
        panelResultados.setLayout(new BoxLayout(panelResultados, BoxLayout.Y_AXIS));
        panelResultados.setBackground(BACKGROUND_COLOR);
        panelResultados.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        scrollResultados = new JScrollPane(panelResultados);
        scrollResultados.setBorder(null);
        scrollResultados.getVerticalScrollBar().setUnitIncrement(16);
        
        add(scrollResultados, BorderLayout.CENTER);
        
        // Mostrar contenido inicial
        mostrarSugerencias();
    }
    
    private JPanel crearPanelBusqueda() {
        JPanel panel = new JPanel(new BorderLayout(10, 0));
        panel.setBackground(CARD_COLOR);
        panel.setBorder(new CompoundBorder(
            new MatteBorder(0, 0, 1, 0, BORDER_COLOR),
            BorderFactory.createEmptyBorder(16, 20, 16, 20)
        ));
        
        JLabel lblTitulo = new JLabel("Explora Instagram");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitulo.setForeground(TEXT_PRIMARY);
        panel.add(lblTitulo, BorderLayout.NORTH);
        
        JPanel panelCentro = new JPanel(new BorderLayout(10, 0));
        panelCentro.setBackground(CARD_COLOR);
        panelCentro.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        
        txtBusqueda = new JTextField();
        txtBusqueda.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtBusqueda.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(BORDER_COLOR, 1),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        
        txtBusqueda.addActionListener(e -> buscar());
        
        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnBuscar.setForeground(Color.WHITE);
        btnBuscar.setBackground(ACCENT_COLOR);
        btnBuscar.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        btnBuscar.setFocusPainted(false);
        btnBuscar.setOpaque(true);
        btnBuscar.setBorderPainted(false);
        btnBuscar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnBuscar.addActionListener(e -> buscar());
        
        panelCentro.add(txtBusqueda, BorderLayout.CENTER);
        panelCentro.add(btnBuscar, BorderLayout.EAST);
        
        JLabel lblDescripcion = new JLabel("Busca usuarios por username, encuentra publicaciones por hashtag (#), o busca contenido específico.");
        lblDescripcion.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblDescripcion.setForeground(TEXT_SECONDARY);
        panelCentro.add(lblDescripcion, BorderLayout.SOUTH);
        
        panel.add(panelCentro, BorderLayout.CENTER);
        
        return panel;
    }
    
    private void buscar() {
        String termino = txtBusqueda.getText().trim();
        
        if (termino.isEmpty()) {
            mostrarSugerencias();
            return;
        }
        
        panelResultados.removeAll();
        
        if (termino.startsWith("@")) {
            buscarUsuarios(termino.substring(1));
        } else if (termino.startsWith("#")) {
            buscarHashtags(termino.substring(1));
        } else {
            buscarContenido(termino);
        }
        
        panelResultados.revalidate();
        panelResultados.repaint();
    }
    
    private void buscarUsuarios(String termino) {
        // Por ahora, mostrar mensaje de que esta función requiere integración
        mostrarMensajeVacio("Búsqueda de usuarios disponible cuando se integre con Mini-Windows");
    }
    
    private void buscarHashtags(String hashtag) {
        // Limpiar el hashtag si viene con #
        String hashtagLimpio = hashtag.startsWith("#") ? hashtag.substring(1) : hashtag;
        
        ArrayList<Publicacion> publicaciones = gestorINSTA.buscarPorHashtag(hashtagLimpio);
        
        if (publicaciones.isEmpty()) {
            mostrarMensajeVacio("No se encontraron publicaciones con #" + hashtagLimpio);
            return;
        }
        
        JLabel lblTitulo = new JLabel("Publicaciones con #" + hashtagLimpio);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTitulo.setForeground(TEXT_PRIMARY);
        lblTitulo.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelResultados.add(lblTitulo);
        panelResultados.add(Box.createVerticalStrut(16));
        
        for (Publicacion publicacion : publicaciones) {
            TarjetaPublicacion tarjeta = new TarjetaPublicacion(publicacion, gestorINSTA, ventanaPrincipal);
            panelResultados.add(tarjeta);
            panelResultados.add(Box.createVerticalStrut(15));
        }
    }
    
    private void buscarContenido(String termino) {
        ArrayList<Publicacion> publicaciones = gestorINSTA.buscarPublicaciones(termino);
        
        if (publicaciones.isEmpty()) {
            mostrarMensajeVacio("No se encontraron publicaciones");
            return;
        }
        
        JLabel lblTitulo = new JLabel("Resultados de búsqueda:");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTitulo.setForeground(TEXT_PRIMARY);
        lblTitulo.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelResultados.add(lblTitulo);
        panelResultados.add(Box.createVerticalStrut(16));
        
        for (Publicacion publicacion : publicaciones) {
            TarjetaPublicacion tarjeta = new TarjetaPublicacion(publicacion, gestorINSTA, ventanaPrincipal);
            panelResultados.add(tarjeta);
            panelResultados.add(Box.createVerticalStrut(15));
        }
    }
    
    private JPanel crearTarjetaUsuario(String username, String nombreCompleto) {
        JPanel tarjeta = new JPanel(new BorderLayout(12, 0));
        tarjeta.setBackground(CARD_COLOR);
        tarjeta.setBorder(new CompoundBorder(
            new LineBorder(BORDER_COLOR, 1),
            BorderFactory.createEmptyBorder(16, 16, 16, 16)
        ));
        tarjeta.setMaximumSize(new Dimension(600, 80));
        tarjeta.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // Info del usuario
        JPanel panelInfo = new JPanel();
        panelInfo.setLayout(new BoxLayout(panelInfo, BoxLayout.Y_AXIS));
        panelInfo.setBackground(CARD_COLOR);
        
        JButton btnUsername = new JButton("@" + username);
        btnUsername.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnUsername.setForeground(TEXT_PRIMARY);
        btnUsername.setBackground(CARD_COLOR);
        btnUsername.setBorderPainted(false);
        btnUsername.setContentAreaFilled(false);
        btnUsername.setFocusPainted(false);
        btnUsername.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnUsername.setHorizontalAlignment(SwingConstants.LEFT);
        btnUsername.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        btnUsername.addActionListener(e -> {
            ventanaPrincipal.mostrarPerfilDeUsuario(username);
        });
        
        JLabel lblNombre = new JLabel(nombreCompleto);
        lblNombre.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblNombre.setForeground(TEXT_SECONDARY);
        lblNombre.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        panelInfo.add(btnUsername);
        panelInfo.add(lblNombre);
        
        // Botón de seguir/siguiendo
        JButton btnSeguir = crearBotonSeguir(username);
        btnSeguir.setPreferredSize(new Dimension(100, 32));
        
        tarjeta.add(panelInfo, BorderLayout.CENTER);
        tarjeta.add(btnSeguir, BorderLayout.EAST);
        
        return tarjeta;
    }
    
    private JButton crearBotonSeguir(String username) {
        boolean estaSiguiendo = gestorINSTA.estaSiguiendo(username);
        
        JButton btn = new JButton(estaSiguiendo ? "Siguiendo" : "Seguir");
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        
        if (estaSiguiendo) {
            btn.setForeground(TEXT_PRIMARY);
            btn.setBackground(CARD_COLOR);
            btn.setBorder(new LineBorder(BORDER_COLOR, 1));
        } else {
            btn.setForeground(Color.WHITE);
            btn.setBackground(ACCENT_COLOR);
            btn.setBorder(null);
        }
        
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btn.addActionListener(e -> {
            gestorINSTA.toggleSeguir(username);
            
            // Actualizar botón
            boolean ahoraSiguiendo = gestorINSTA.estaSiguiendo(username);
            btn.setText(ahoraSiguiendo ? "Siguiendo" : "Seguir");
            
            if (ahoraSiguiendo) {
                btn.setForeground(TEXT_PRIMARY);
                btn.setBackground(CARD_COLOR);
                btn.setBorder(new LineBorder(BORDER_COLOR, 1));
            } else {
                btn.setForeground(Color.WHITE);
                btn.setBackground(ACCENT_COLOR);
                btn.setBorder(null);
            }
        });
        
        return btn;
    }
    
    private void mostrarSugerencias() {
        panelResultados.removeAll();
        
        JLabel lblTitulo = new JLabel("Explora Instagram");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTitulo.setForeground(TEXT_PRIMARY);
        lblTitulo.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelResultados.add(lblTitulo);
        panelResultados.add(Box.createVerticalStrut(16));
        
        JLabel lblMensaje = new JLabel("<html><p style='color: #8e8e8e;'>Busca usuarios por username, encuentra publicaciones por hashtag (#), o busca contenido específico.</p></html>");
        lblMensaje.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblMensaje.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelResultados.add(lblMensaje);
        
        panelResultados.revalidate();
        panelResultados.repaint();
    }
    
    private void mostrarMensajeVacio(String mensaje) {
        JLabel lblMensaje = new JLabel(mensaje);
        lblMensaje.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblMensaje.setForeground(TEXT_SECONDARY);
        lblMensaje.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        panelResultados.add(Box.createVerticalGlue());
        panelResultados.add(lblMensaje);
        panelResultados.add(Box.createVerticalGlue());
    }
    
    public void actualizarContenido() {
        if (txtBusqueda.getText().trim().isEmpty()) {
            mostrarSugerencias();
        } else {
            buscar();
        }
    }
}