/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package RedSocialINSTA.GUI;

import RedSocialINSTA.Logica.GestorINSTA;
import RedSocialINSTA.Logica.GestorNotificaciones;
import RedSocialINSTA.Modelo.Notificacion;
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
    private GestorNotificaciones gestorNotificaciones;
    private VentanaINSTA ventanaPrincipal;
    
    private JPanel panelNotificaciones;
    private JScrollPane scrollPane;
    
    private static final Color BACKGROUND_COLOR = new Color(250, 250, 250);
    private static final Color CARD_COLOR = Color.WHITE;
    private static final Color BORDER_COLOR = new Color(219, 219, 219);
    private static final Color TEXT_PRIMARY = new Color(38, 38, 38);
    private static final Color TEXT_SECONDARY = new Color(142, 142, 142);
    
    public PanelNotificaciones(GestorINSTA gestor, GestorNotificaciones gestorNotif, VentanaINSTA ventana) {
        this.gestorINSTA = gestor;
        this.gestorNotificaciones = gestorNotif;
        this.ventanaPrincipal = ventana;
        
        initComponents();
    }
    
    private void initComponents() {
        setLayout(new BorderLayout());
        setBackground(BACKGROUND_COLOR);
        
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
        
        String usernameActual = gestorINSTA.getUsernameActual();
        
        ArrayList<Notificacion> notificaciones = gestorNotificaciones.obtenerNotificaciones(usernameActual);
        
        if (notificaciones.isEmpty()) {
            mostrarMensajeVacio();
        } else {
            gestorNotificaciones.marcarTodasComoLeidas(usernameActual);
            
            for (Notificacion notif : notificaciones) {
                JPanel tarjetaNotif = crearTarjetaNotificacion(notif);
                panelNotificaciones.add(tarjetaNotif);
                panelNotificaciones.add(Box.createVerticalStrut(8));
            }
        }
        
        panelNotificaciones.revalidate();
        panelNotificaciones.repaint();
    }

    private JPanel crearTarjetaNotificacion(Notificacion notif) {
        JPanel tarjeta = new JPanel(new BorderLayout(12, 0));
        tarjeta.setBackground(CARD_COLOR);
        tarjeta.setBorder(new CompoundBorder(
            new LineBorder(BORDER_COLOR, 1),
            BorderFactory.createEmptyBorder(12, 12, 12, 12)
        ));
        tarjeta.setMaximumSize(new Dimension(800, 80));
        tarjeta.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel lblAvatar = new JLabel();
        ImageIcon avatarIcon = IconManager.getDefaultAvatarScaled(40);
        lblAvatar.setIcon(avatarIcon);
        lblAvatar.setPreferredSize(new Dimension(40, 40));
        
        JPanel panelContenido = new JPanel();
        panelContenido.setLayout(new BoxLayout(panelContenido, BoxLayout.Y_AXIS));
        panelContenido.setBackground(CARD_COLOR);
        
        JPanel panelMensaje = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        panelMensaje.setBackground(CARD_COLOR);
        panelMensaje.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JButton btnUsuario = new JButton("@" + notif.getUsernameOrigen());
        btnUsuario.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnUsuario.setForeground(TEXT_PRIMARY);
        btnUsuario.setBackground(CARD_COLOR);
        btnUsuario.setBorderPainted(false);
        btnUsuario.setContentAreaFilled(false);
        btnUsuario.setFocusPainted(false);
        btnUsuario.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btnUsuario.addActionListener(e -> {
            ventanaPrincipal.mostrarPerfilDeUsuario(notif.getUsernameOrigen());
        });
        
        String textoAccion = "";
        switch (notif.getTipo()) {
            case LIKE:
                textoAccion = " le gustó tu publicación";
                break;
            case COMENTARIO:
                textoAccion = " comentó en tu publicación";
                break;
            case MENCION:
                textoAccion = " te mencionó en una publicación";
                break;
            case SEGUIDOR:
                textoAccion = " comenzó a seguirte";
                break;
        }
        
        JLabel lblAccion = new JLabel(textoAccion);
        lblAccion.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblAccion.setForeground(TEXT_PRIMARY);
        
        panelMensaje.add(btnUsuario);
        panelMensaje.add(lblAccion);
        
        JLabel lblTiempo = new JLabel(notif.getTiempoTranscurrido());
        lblTiempo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblTiempo.setForeground(TEXT_SECONDARY);
        lblTiempo.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        panelContenido.add(panelMensaje);
        panelContenido.add(Box.createVerticalStrut(4));
        panelContenido.add(lblTiempo);
        
        JButton btnAccion = null;
        if (notif.getIdPublicacion() != null) {
            btnAccion = new JButton("Ver publicación");
            btnAccion.setFont(new Font("Segoe UI", Font.BOLD, 13));
            btnAccion.setForeground(new Color(0, 149, 246));
            btnAccion.setBackground(CARD_COLOR);
            btnAccion.setBorder(new LineBorder(new Color(0, 149, 246), 1));
            btnAccion.setFocusPainted(false);
            btnAccion.setCursor(new Cursor(Cursor.HAND_CURSOR));
            btnAccion.setPreferredSize(new Dimension(140, 32));
            
            btnAccion.addActionListener(e -> {
                ventanaPrincipal.mostrarTimeline();
            });
        } else if (notif.getTipo() == Notificacion.TipoNotificacion.SEGUIDOR) {
            btnAccion = new JButton("Ver perfil");
            btnAccion.setFont(new Font("Segoe UI", Font.BOLD, 13));
            btnAccion.setForeground(new Color(0, 149, 246));
            btnAccion.setBackground(CARD_COLOR);
            btnAccion.setBorder(new LineBorder(new Color(0, 149, 246), 1));
            btnAccion.setFocusPainted(false);
            btnAccion.setCursor(new Cursor(Cursor.HAND_CURSOR));
            btnAccion.setPreferredSize(new Dimension(100, 32));
            
            btnAccion.addActionListener(e -> {
                ventanaPrincipal.mostrarPerfilDeUsuario(notif.getUsernameOrigen());
            });
        }
        
        tarjeta.add(lblAvatar, BorderLayout.WEST);
        tarjeta.add(panelContenido, BorderLayout.CENTER);
        if (btnAccion != null) {
            tarjeta.add(btnAccion, BorderLayout.EAST);
        }
        
        return tarjeta;
    }
    
    private void mostrarMensajeVacio() {
        JPanel panelVacio = new JPanel();
        panelVacio.setLayout(new BoxLayout(panelVacio, BoxLayout.Y_AXIS));
        panelVacio.setBackground(BACKGROUND_COLOR);
        
        JLabel lblMensaje = new JLabel("No tienes notificaciones nuevas");
        lblMensaje.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblMensaje.setForeground(TEXT_SECONDARY);
        lblMensaje.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        panelVacio.add(Box.createVerticalGlue());
        panelVacio.add(lblMensaje);
        panelVacio.add(Box.createVerticalGlue());
        
        panelNotificaciones.add(panelVacio);
    }
}
