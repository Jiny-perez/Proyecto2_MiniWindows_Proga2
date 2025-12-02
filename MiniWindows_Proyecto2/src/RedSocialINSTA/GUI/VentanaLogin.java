/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package RedSocialINSTA.GUI;

import Modelo.Usuario;
import RedSocialINSTA.Logica.GestorINSTA;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;

/**
 *
 * @author najma
 */
public class VentanaLogin extends JFrame {
  
    private static HashMap<String, Usuario> usuarios = new HashMap<>();
    
    private JPanel panelPrincipal;
    private JPanel panelLogin;
    private JPanel panelRegistro;
    private CardLayout cardLayout;
    
    private JTextField txtUsernameLogin;
    private JPasswordField txtPasswordLogin;
    private JButton btnLogin;
    private JButton btnIrARegistro;
    
    private JTextField txtUsernameRegistro;
    private JTextField txtNombreCompleto;
    private JPasswordField txtPasswordRegistro;
    private JPasswordField txtConfirmarPassword;
    private JButton btnRegistrar;
    private JButton btnIrALogin;
    
    private static final Color INSTAGRAM_BLUE = new Color(0, 149, 246);
    private static final Color INSTAGRAM_BLUE_HOVER = new Color(24, 119, 242);
    private static final Color BACKGROUND_COLOR = new Color(250, 250, 250);
    private static final Color TEXT_PRIMARY = new Color(38, 38, 38);
    private static final Color TEXT_SECONDARY = new Color(142, 142, 142);
    private static final Color BORDER_COLOR = new Color(219, 219, 219);
    
    private static final Color GRADIENT_START = new Color(193, 53, 132);
    private static final Color GRADIENT_END = new Color(245, 133, 41);
    
    public VentanaLogin() {
        initComponents();
        configurarVentana();
    }
    
    private void initComponents() {
        cardLayout = new CardLayout();
        panelPrincipal = new JPanel(cardLayout);
        panelPrincipal.setBackground(BACKGROUND_COLOR);
        
        panelLogin = crearPanelLogin();
        panelRegistro = crearPanelRegistro();
        
        panelPrincipal.add(panelLogin, "LOGIN");
        panelPrincipal.add(panelRegistro, "REGISTRO");
        
        getContentPane().add(panelPrincipal);
    }
    
    private JPanel crearPanelLogin() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(BACKGROUND_COLOR);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setBackground(Color.WHITE);
        container.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR, 1),
            BorderFactory.createEmptyBorder(40, 40, 40, 40)
        ));
        container.setMaximumSize(new Dimension(350, 500));
        
        
        JLabel lblLogo = new JLabel();
        try {
            ImageIcon logoIcon = IconManager.getLogoScaled(175, 51);
            lblLogo.setIcon(logoIcon);
        } catch (Exception e) {
            lblLogo.setText("Instagram");
            lblLogo.setFont(new Font("Brush Script MT", Font.ITALIC, 36));
            lblLogo.setForeground(new Color(193, 53, 132));
        }
        lblLogo.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblLogo.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        container.add(lblLogo);
        container.add(Box.createVerticalStrut(30));
        
        txtUsernameLogin = crearCampoTexto("Username");
        txtUsernameLogin.setMaximumSize(new Dimension(270, 40));
        txtUsernameLogin.setAlignmentX(Component.CENTER_ALIGNMENT);
        container.add(txtUsernameLogin);
        container.add(Box.createVerticalStrut(10));
        
        txtPasswordLogin = crearCampoPassword("Contraseña");
        txtPasswordLogin.setMaximumSize(new Dimension(270, 40));
        txtPasswordLogin.setAlignmentX(Component.CENTER_ALIGNMENT);
        container.add(txtPasswordLogin);
        container.add(Box.createVerticalStrut(20));
        
        btnLogin = crearBotonPrincipal("Iniciar Sesión");
        btnLogin.addActionListener(e -> intentarLogin());
        btnLogin.setMaximumSize(new Dimension(270, 40));
        btnLogin.setAlignmentX(Component.CENTER_ALIGNMENT);
        container.add(btnLogin);
        container.add(Box.createVerticalStrut(30));
        
        JSeparator separador = new JSeparator();
        separador.setMaximumSize(new Dimension(270, 1));
        separador.setAlignmentX(Component.CENTER_ALIGNMENT);
        container.add(separador);
        container.add(Box.createVerticalStrut(20));
        
        JLabel lblNoTienesCuenta = new JLabel("¿No tienes una cuenta?");
        lblNoTienesCuenta.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblNoTienesCuenta.setForeground(TEXT_SECONDARY);
        lblNoTienesCuenta.setAlignmentX(Component.CENTER_ALIGNMENT);
        container.add(lblNoTienesCuenta);
        container.add(Box.createVerticalStrut(10));
        
        btnIrARegistro = crearBotonSecundario("Regístrate");
        btnIrARegistro.addActionListener(e -> mostrarRegistro());
        btnIrARegistro.setMaximumSize(new Dimension(270, 40));
        btnIrARegistro.setAlignmentX(Component.CENTER_ALIGNMENT);
        container.add(btnIrARegistro);
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(container, gbc);
        
        txtPasswordLogin.addActionListener(e -> btnLogin.doClick());
        
        return panel;
    }
    
    private JPanel crearPanelRegistro() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(BACKGROUND_COLOR);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setBackground(Color.WHITE);
        container.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR, 1),
            BorderFactory.createEmptyBorder(40, 40, 40, 40)
        ));
        container.setMaximumSize(new Dimension(350, 600));
        
        
        JLabel lblLogo = new JLabel();
        try {
            ImageIcon logoIcon = IconManager.getLogoScaled(150, 44);
            lblLogo.setIcon(logoIcon);
        } catch (Exception e) {
            lblLogo.setText("Instagram");
            lblLogo.setFont(new Font("Brush Script MT", Font.ITALIC, 32));
            lblLogo.setForeground(new Color(193, 53, 132));
        }
        lblLogo.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblLogo.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        container.add(lblLogo);
        container.add(Box.createVerticalStrut(10));
        
        JLabel lblTitulo = new JLabel("Regístrate para ver fotos de tus amigos");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTitulo.setForeground(TEXT_SECONDARY);
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        container.add(lblTitulo);
        container.add(Box.createVerticalStrut(20));
        
        txtUsernameRegistro = crearCampoTexto("Username");
        txtUsernameRegistro.setMaximumSize(new Dimension(270, 40));
        txtUsernameRegistro.setAlignmentX(Component.CENTER_ALIGNMENT);
        container.add(txtUsernameRegistro);
        container.add(Box.createVerticalStrut(10));
        
        txtNombreCompleto = crearCampoTexto("Nombre completo");
        txtNombreCompleto.setMaximumSize(new Dimension(270, 40));
        txtNombreCompleto.setAlignmentX(Component.CENTER_ALIGNMENT);
        container.add(txtNombreCompleto);
        container.add(Box.createVerticalStrut(10));
        
        txtPasswordRegistro = crearCampoPassword("Contraseña");
        txtPasswordRegistro.setMaximumSize(new Dimension(270, 40));
        txtPasswordRegistro.setAlignmentX(Component.CENTER_ALIGNMENT);
        container.add(txtPasswordRegistro);
        container.add(Box.createVerticalStrut(10));
        
        txtConfirmarPassword = crearCampoPassword("Confirmar contraseña");
        txtConfirmarPassword.setMaximumSize(new Dimension(270, 40));
        txtConfirmarPassword.setAlignmentX(Component.CENTER_ALIGNMENT);
        container.add(txtConfirmarPassword);
        container.add(Box.createVerticalStrut(20));
        
        btnRegistrar = crearBotonPrincipal("Registrarse");
        btnRegistrar.addActionListener(e -> intentarRegistro());
        btnRegistrar.setMaximumSize(new Dimension(270, 40));
        btnRegistrar.setAlignmentX(Component.CENTER_ALIGNMENT);
        container.add(btnRegistrar);
        container.add(Box.createVerticalStrut(20));
        
        JSeparator separador = new JSeparator();
        separador.setMaximumSize(new Dimension(270, 1));
        separador.setAlignmentX(Component.CENTER_ALIGNMENT);
        container.add(separador);
        container.add(Box.createVerticalStrut(15));
        
        JLabel lblYaTienesCuenta = new JLabel("¿Ya tienes una cuenta?");
        lblYaTienesCuenta.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblYaTienesCuenta.setForeground(TEXT_SECONDARY);
        lblYaTienesCuenta.setAlignmentX(Component.CENTER_ALIGNMENT);
        container.add(lblYaTienesCuenta);
        container.add(Box.createVerticalStrut(10));
        
        btnIrALogin = crearBotonSecundario("Inicia sesión");
        btnIrALogin.addActionListener(e -> mostrarLogin());
        btnIrALogin.setMaximumSize(new Dimension(270, 40));
        btnIrALogin.setAlignmentX(Component.CENTER_ALIGNMENT);
        container.add(btnIrALogin);
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(container, gbc);
        
        return panel;
    }
    
    private JTextField crearCampoTexto(String placeholder) {
        JTextField campo = new JTextField();
        campo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        campo.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR, 1),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        
        campo.setText(placeholder);
        campo.setForeground(TEXT_SECONDARY);
        
        campo.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (campo.getText().equals(placeholder)) {
                    campo.setText("");
                    campo.setForeground(TEXT_PRIMARY);
                }
                campo.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(INSTAGRAM_BLUE, 1),
                    BorderFactory.createEmptyBorder(8, 12, 8, 12)
                ));
            }
            
            @Override
            public void focusLost(FocusEvent e) {
                if (campo.getText().isEmpty()) {
                    campo.setText(placeholder);
                    campo.setForeground(TEXT_SECONDARY);
                }
                campo.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(BORDER_COLOR, 1),
                    BorderFactory.createEmptyBorder(8, 12, 8, 12)
                ));
            }
        });
        
        return campo;
    }
    
    private JPasswordField crearCampoPassword(String placeholder) {
        JPasswordField campo = new JPasswordField();
        campo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        campo.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR, 1),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        campo.setEchoChar((char) 0);
        campo.setText(placeholder);
        campo.setForeground(TEXT_SECONDARY);
        
        campo.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (String.valueOf(campo.getPassword()).equals(placeholder)) {
                    campo.setText("");
                    campo.setEchoChar('•');
                    campo.setForeground(TEXT_PRIMARY);
                }
                campo.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(INSTAGRAM_BLUE, 1),
                    BorderFactory.createEmptyBorder(8, 12, 8, 12)
                ));
            }
            
            @Override
            public void focusLost(FocusEvent e) {
                if (String.valueOf(campo.getPassword()).isEmpty()) {
                    campo.setEchoChar((char) 0);
                    campo.setText(placeholder);
                    campo.setForeground(TEXT_SECONDARY);
                }
                campo.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(BORDER_COLOR, 1),
                    BorderFactory.createEmptyBorder(8, 12, 8, 12)
                ));
            }
        });
        
        return campo;
    }
    
    private JButton crearBotonPrincipal(String texto) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        boton.setForeground(Color.WHITE);
        boton.setBackground(INSTAGRAM_BLUE);
        boton.setBorderPainted(false);
        boton.setFocusPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        boton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                boton.setBackground(INSTAGRAM_BLUE_HOVER);
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                boton.setBackground(INSTAGRAM_BLUE);
            }
        });
        
        return boton;
    }
    
    private JButton crearBotonSecundario(String texto) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        boton.setForeground(INSTAGRAM_BLUE);
        boton.setBackground(Color.WHITE);
        boton.setBorder(BorderFactory.createLineBorder(INSTAGRAM_BLUE, 1));
        boton.setFocusPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        boton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                boton.setBackground(new Color(240, 248, 255));
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                boton.setBackground(Color.WHITE);
            }
        });
        
        return boton;
    }
    
    private void mostrarLogin() {
        cardLayout.show(panelPrincipal, "LOGIN");
    }
    
    private void mostrarRegistro() {
        cardLayout.show(panelPrincipal, "REGISTRO");
    }
    
    private void intentarLogin() {
        String username = txtUsernameLogin.getText().trim();
        String password = String.valueOf(txtPasswordLogin.getPassword());
        
        if (username.isEmpty() || username.equals("Username")) {
            mostrarError("Por favor ingresa tu username");
            return;
        }
        
        if (password.isEmpty() || password.equals("Contraseña")) {
            mostrarError("Por favor ingresa tu contraseña");
            return;
        }
        
        Usuario usuario = usuarios.get(username);
        
        if (usuario != null && usuario.getPassword().equals(password)) {
            if (usuario.isActivo()) {
                abrirInstagram(usuario);
            } else {
                mostrarError("Esta cuenta está desactivada");
            }
        } else {
            mostrarError("Username o contraseña incorrectos");
        }
    }
    
    private void intentarRegistro() {
        String username = txtUsernameRegistro.getText().trim();
        String nombreCompleto = txtNombreCompleto.getText().trim();
        String password = String.valueOf(txtPasswordRegistro.getPassword());
        String confirmarPassword = String.valueOf(txtConfirmarPassword.getPassword());
        
        if (username.isEmpty() || username.equals("Username")) {
            mostrarError("Por favor ingresa un username");
            return;
        }
        
        if (nombreCompleto.isEmpty() || nombreCompleto.equals("Nombre completo")) {
            mostrarError("Por favor ingresa tu nombre completo");
            return;
        }
        
        if (password.isEmpty() || password.equals("Contraseña")) {
            mostrarError("Por favor ingresa una contraseña");
            return;
        }
        
        if (password.length() < 6) {
            mostrarError("La contraseña debe tener al menos 6 caracteres");
            return;
        }
        
        if (!password.equals(confirmarPassword)) {
            mostrarError("Las contraseñas no coinciden");
            return;
        }
        
        if (usuarios.containsKey(username)) {
            mostrarError("Este username ya está en uso");
            return;
        }
        
        Usuario nuevoUsuario = new Usuario(username, nombreCompleto, password, true);
        
        usuarios.put(username, nuevoUsuario);
        
        mostrarExito("¡Cuenta creada exitosamente!");
        
        abrirInstagram(nuevoUsuario);
    }
    
    private void abrirInstagram(Usuario usuario) {
        GestorINSTA gestorINSTA = new GestorINSTA(usuario);
        VentanaINSTA ventanaINSTA = new VentanaINSTA(usuario, gestorINSTA);
        ventanaINSTA.setVisible(true);
        
        dispose();
    }
    
    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(
            this,
            mensaje,
            "Error",
            JOptionPane.ERROR_MESSAGE
        );
    }
    
    private void mostrarExito(String mensaje) {
        JOptionPane.showMessageDialog(
            this,
            mensaje,
            "Éxito",
            JOptionPane.INFORMATION_MESSAGE
        );
    }
    
    private void configurarVentana() {
        setTitle("Instagram - Iniciar Sesión");
        setSize(450, 650);
        setMinimumSize(new Dimension(450, 650));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
    }
    
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> {
            VentanaLogin ventana = new VentanaLogin();
            ventana.setVisible(true);
        });
    }
}