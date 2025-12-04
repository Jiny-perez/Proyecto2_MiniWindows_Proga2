/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package RedSocialINSTA.GUI;

import Modelo.Usuario;
import RedSocialINSTA.Logica.GestorINSTA;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

/**
 *
 * @author najma
 */
public class VentanaINSTA extends JFrame {
    
    private GestorINSTA gestorINSTA;
    private Usuario usuarioActual;
    
    // Componentes principales
    private JPanel panelLateral;
    private JPanel panelContenido;
    private CardLayout cardLayout;
    
    // Paneles de contenido
    private PanelTimeline panelTimeline;
    private PanelExplorar panelExplorar;
    private PanelPerfil panelPerfil;
    private PanelMensajes panelMensajes;
    private PanelNotificaciones panelNotificaciones;
    
    // Colores del tema Instagram
    private static final Color BACKGROUND_COLOR = new Color(250, 250, 250);
    private static final Color SIDEBAR_COLOR = Color.WHITE;
    private static final Color BORDER_COLOR = new Color(219, 219, 219);
    private static final Color TEXT_PRIMARY = new Color(38, 38, 38);
    private static final Color TEXT_SECONDARY = new Color(142, 142, 142);
    private static final Color ACCENT_COLOR = new Color(0, 149, 246);
    private static final Color HOVER_COLOR = new Color(250, 250, 250);
    
    public VentanaINSTA(Usuario usuario, GestorINSTA gestor) {
        this.usuarioActual = usuario;
        this.gestorINSTA = gestor;
        
        initComponents();
        configurarVentana();
    }
    
    private void initComponents() {
        setLayout(new BorderLayout());
        getContentPane().setBackground(BACKGROUND_COLOR);
        
        // Panel lateral (sidebar)
        crearPanelLateral();
        
        // Panel de contenido principal con CardLayout
        cardLayout = new CardLayout();
        panelContenido = new JPanel(cardLayout);
        panelContenido.setBackground(BACKGROUND_COLOR);
        
        // Crear los diferentes paneles
        panelTimeline = new PanelTimeline(gestorINSTA, this);
        panelExplorar = new PanelExplorar(gestorINSTA, this);
        panelPerfil = new PanelPerfil(gestorINSTA, usuarioActual.getUsername(), this);
        panelMensajes = new PanelMensajes(gestorINSTA);
        panelNotificaciones = new PanelNotificaciones(gestorINSTA, this);
        
        // Agregar paneles al CardLayout
        panelContenido.add(panelTimeline, "TIMELINE");
        panelContenido.add(panelExplorar, "EXPLORAR");
        panelContenido.add(panelPerfil, "PERFIL");
        panelContenido.add(panelMensajes, "MENSAJES");
        panelContenido.add(panelNotificaciones, "NOTIFICACIONES");
        
        add(panelLateral, BorderLayout.WEST);
        add(panelContenido, BorderLayout.CENTER);
        
        // Mostrar timeline por defecto
        mostrarTimeline();
    }
    
    private void crearPanelLateral() {
        panelLateral = new JPanel();
        panelLateral.setLayout(new BoxLayout(panelLateral, BoxLayout.Y_AXIS));
        panelLateral.setBackground(SIDEBAR_COLOR);
        panelLateral.setBorder(new MatteBorder(0, 0, 0, 1, BORDER_COLOR));
        panelLateral.setPreferredSize(new Dimension(245, 600));
        
        JLabel lblLogo = new JLabel();
        try {
            ImageIcon logoIcon = IconManager.getLogoScaled(140, 41);
            lblLogo.setIcon(logoIcon);
        } catch (Exception e) {
            ImageIcon logoIcon = IconDrawer.createInstagramLogo(140, 41);
            lblLogo.setIcon(logoIcon);
        }
        lblLogo.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblLogo.setBorder(BorderFactory.createEmptyBorder(30, 20, 30, 20));
        panelLateral.add(lblLogo);
        
        // Botones de navegación con iconos
        panelLateral.add(crearBotonNavegacion("Inicio", IconManager.ICON_HOME, e -> mostrarTimeline()));
        panelLateral.add(Box.createVerticalStrut(5));
        
        panelLateral.add(crearBotonNavegacion("Explorar", IconManager.ICON_SEARCH, e -> mostrarExplorar()));
        panelLateral.add(Box.createVerticalStrut(5));
        
        panelLateral.add(crearBotonNavegacion("Mensajes", IconManager.ICON_MESSAGES, e -> mostrarMensajes()));
        panelLateral.add(Box.createVerticalStrut(5));
        
        panelLateral.add(crearBotonNavegacion("Notificaciones", IconManager.ICON_NOTIFICATIONS, e -> mostrarNotificaciones()));
        panelLateral.add(Box.createVerticalStrut(5));
        
        panelLateral.add(crearBotonNavegacion("Crear", IconManager.ICON_CREATE, e -> crearPublicacion()));
        panelLateral.add(Box.createVerticalStrut(5));
        
        panelLateral.add(crearBotonNavegacion("Perfil", IconManager.ICON_PROFILE, e -> mostrarPerfil()));
        panelLateral.add(Box.createVerticalStrut(5));
        
        // Espaciador
        panelLateral.add(Box.createVerticalGlue());
        
        // Botón de cerrar sesión
        panelLateral.add(crearBotonNavegacion("Cerrar Sesión", IconManager.ICON_LOGOUT, e -> cerrarSesion()));
        panelLateral.add(Box.createVerticalStrut(20));
    }
    
    private JButton crearBotonNavegacion(String texto, String iconoNombre, ActionListener action) {
        JButton btn = new JButton(texto);
        
        // Usar IconManager que intenta PNG primero, luego IconDrawer como fallback
        ImageIcon icono = IconManager.getIconScaled(iconoNombre, 24, 24);
        
        if (icono != null) {
            btn.setIcon(icono);
            btn.setHorizontalTextPosition(SwingConstants.RIGHT);
            btn.setIconTextGap(15);
        }
        
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);
        btn.setMaximumSize(new Dimension(225, 50));
        btn.setPreferredSize(new Dimension(225, 50));
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        btn.setForeground(TEXT_PRIMARY);
        btn.setBackground(SIDEBAR_COLOR);
        btn.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 20));
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(HOVER_COLOR);
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                btn.setBackground(SIDEBAR_COLOR);
            }
        });
        
        btn.addActionListener(action);
        
        return btn;
    }
    
    public void mostrarTimeline() {
        cardLayout.show(panelContenido, "TIMELINE");
        panelTimeline.actualizarContenido();
    }
    
    public void mostrarExplorar() {
        cardLayout.show(panelContenido, "EXPLORAR");
        panelExplorar.actualizarContenido();
    }
    
    public void mostrarPerfil() {
        cardLayout.show(panelContenido, "PERFIL");
        panelPerfil.actualizarContenido();
    }
    
    public void mostrarMensajes() {
        cardLayout.show(panelContenido, "MENSAJES");
    }
    
    public void mostrarNotificaciones() {
        cardLayout.show(panelContenido, "NOTIFICACIONES");
        panelNotificaciones.actualizarContenido();
    }
    
    private void crearPublicacion() {
        DialogCrearPublicacion dialog = new DialogCrearPublicacion(this, gestorINSTA);
        dialog.setVisible(true);
        
        if (dialog.isPublicacionCreada()) {
            mostrarTimeline();
        }
    }
    
    private void cerrarSesion() {
        int opcion = JOptionPane.showConfirmDialog(
            this,
            "¿Estás seguro de que deseas cerrar sesión?",
            "Cerrar Sesión",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );
        
        if (opcion == JOptionPane.YES_OPTION) {
            gestorINSTA.guardarDatos();
            dispose();
            
            SwingUtilities.invokeLater(() -> {
                VentanaLogin ventanaLogin = new VentanaLogin();
                ventanaLogin.setVisible(true);
            });
        }
    }
    
    private void configurarVentana() {
        setTitle("Instagram - @" + usuarioActual.getUsername());
        setSize(1200, 800);
        setMinimumSize(new Dimension(1000, 600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }
    
    public void mostrarPerfilDeUsuario(String username) {
        PanelPerfil perfilUsuario = new PanelPerfil(gestorINSTA, username, this);
        panelContenido.add(perfilUsuario, "PERFIL_" + username);
        cardLayout.show(panelContenido, "PERFIL_" + username);
        perfilUsuario.actualizarContenido();
    }
    
    public Usuario getUsuarioActual() {
        return usuarioActual;
    }
    
    public GestorINSTA getGestorINSTA() {
        return gestorINSTA;
    }
}
