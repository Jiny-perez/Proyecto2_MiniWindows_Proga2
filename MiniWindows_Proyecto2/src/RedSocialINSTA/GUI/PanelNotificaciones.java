/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package RedSocialINSTA.GUI;

import RedSocialINSTA.Logica.GestorINSTA;
import RedSocialINSTA.Modelo.Publicacion;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.ArrayList;

/**
 *
 * @author najma
 */
public class PanelNotificaciones extends JPanel {
    
    private GestorINSTA gestorINSTA;
    private VentanaINSTA ventanaPrincipal;
    
    private JPanel panelNotificaciones;
    private JScrollPane scrollPane;
    
    private static final Color BACKGROUND_COLOR = new Color(250, 250, 250);
    private static final Color CARD_COLOR = Color.WHITE;
    private static final Color BORDER_COLOR = new Color(219, 219, 219);
    private static final Color TEXT_PRIMARY = new Color(38, 38, 38);
    private static final Color TEXT_SECONDARY = new Color(142, 142, 142);
    
    public PanelNotificaciones(GestorINSTA gestor, VentanaINSTA ventana) {
        this.gestorINSTA = gestor;
        this.ventanaPrincipal = ventana;
        
        initComponents();
    }
    
    private void initComponents() {
        setLayout(new BorderLayout());
        setBackground(BACKGROUND_COLOR);
        
        // Header
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(CARD_COLOR);
        header.setBorder(new CompoundBorder(
            new MatteBorder(0, 0, 1, 0, BORDER_COLOR),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        
        JLabel lblTitulo = new JLabel("Notificaciones");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitulo.setForeground(TEXT_PRIMARY);
        
        header.add(lblTitulo, BorderLayout.WEST);
        
        add(header, BorderLayout.NORTH);
        
        // Panel de notificaciones
        panelNotificaciones = new JPanel();
        panelNotificaciones.setLayout(new BoxLayout(panelNotificaciones, BoxLayout.Y_AXIS));
        panelNotificaciones.setBackground(BACKGROUND_COLOR);
        panelNotificaciones.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        scrollPane = new JScrollPane(panelNotificaciones);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        
        add(scrollPane, BorderLayout.CENTER);
    }
    
    public void actualizarContenido() {
        panelNotificaciones.removeAll();
        
        // Buscar publicaciones donde mencionan al usuario
        String usernameActual = gestorINSTA.getUsernameActual();
        ArrayList<Publicacion> menciones = buscarMenciones(usernameActual);
        
        if (menciones.isEmpty()) {
            mostrarMensajeVacio();
        } else {
            for (Publicacion publicacion : menciones) {
                panelNotificaciones.add(crearNotificacion(publicacion));
                panelNotificaciones.add(Box.createVerticalStrut(12));
            }
        }
        
        panelNotificaciones.revalidate();
        panelNotificaciones.repaint();
    }
    
    private ArrayList<Publicacion> buscarMenciones(String username) {
        ArrayList<Publicacion> menciones = new ArrayList<>();
        String busqueda = "@" + username;
        
        // Buscar en todas las publicaciones
        ArrayList<Publicacion> todasPublicaciones = gestorINSTA.buscarPublicaciones(busqueda);
        
        // Filtrar solo las que no son del usuario actual
        for (Publicacion pub : todasPublicaciones) {
            if (!pub.getUsername().equals(username)) {
                menciones.add(pub);
            }
        }
        
        return menciones;
    }
    
    private JPanel crearNotificacion(Publicacion publicacion) {
        JPanel notificacion = new JPanel(new BorderLayout(12, 0));
        notificacion.setBackground(CARD_COLOR);
        notificacion.setBorder(new CompoundBorder(
            new LineBorder(BORDER_COLOR, 1),
            BorderFactory.createEmptyBorder(16, 16, 16, 16)
        ));
        notificacion.setMaximumSize(new Dimension(600, 120));
        notificacion.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel lblIcono = new JLabel();
        ImageIcon avatarIcon = IconDrawer.createDefaultAvatar(40);
        lblIcono.setIcon(avatarIcon);
        lblIcono.setPreferredSize(new Dimension(40, 40));
        
        JPanel panelContenido = new JPanel();
        panelContenido.setLayout(new BoxLayout(panelContenido, BoxLayout.Y_AXIS));
        panelContenido.setBackground(CARD_COLOR);
        
        JButton btnUsuario = new JButton("@" + publicacion.getUsername() + " te mencionó");
        btnUsuario.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnUsuario.setForeground(TEXT_PRIMARY);
        btnUsuario.setBackground(CARD_COLOR);
        btnUsuario.setBorderPainted(false);
        btnUsuario.setContentAreaFilled(false);
        btnUsuario.setFocusPainted(false);
        btnUsuario.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnUsuario.setHorizontalAlignment(SwingConstants.LEFT);
        btnUsuario.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        btnUsuario.addActionListener(e -> {
            ventanaPrincipal.mostrarPerfilDeUsuario(publicacion.getUsername());
        });
        
        JLabel lblContenido = new JLabel("<html>" + limitarTexto(publicacion.getContenido(), 80) + "</html>");
        lblContenido.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblContenido.setForeground(TEXT_SECONDARY);
        lblContenido.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel lblTiempo = new JLabel(publicacion.getTiempoTranscurrido());
        lblTiempo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblTiempo.setForeground(TEXT_SECONDARY);
        lblTiempo.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        panelContenido.add(btnUsuario);
        panelContenido.add(Box.createVerticalStrut(4));
        panelContenido.add(lblContenido);
        panelContenido.add(Box.createVerticalStrut(4));
        panelContenido.add(lblTiempo);
        
        notificacion.add(lblIcono, BorderLayout.WEST);
        notificacion.add(panelContenido, BorderLayout.CENTER);
        
        return notificacion;
    }
    
    private String limitarTexto(String texto, int maxCaracteres) {
        if (texto.length() <= maxCaracteres) {
            return texto;
        }
        return texto.substring(0, maxCaracteres) + "...";
    }
    
    private void mostrarMensajeVacio() {
        JPanel panelVacio = new JPanel();
        panelVacio.setLayout(new BoxLayout(panelVacio, BoxLayout.Y_AXIS));
        panelVacio.setBackground(BACKGROUND_COLOR);
        
        JLabel lblIcono = new JLabel("🔔");
        lblIcono.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 60));
        lblIcono.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel lblMensaje = new JLabel("No tienes notificaciones nuevas");
        lblMensaje.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblMensaje.setForeground(TEXT_SECONDARY);
        lblMensaje.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        panelVacio.add(Box.createVerticalGlue());
        panelVacio.add(lblIcono);
        panelVacio.add(Box.createVerticalStrut(16));
        panelVacio.add(lblMensaje);
        panelVacio.add(Box.createVerticalGlue());
        
        panelNotificaciones.add(panelVacio);
    }
}