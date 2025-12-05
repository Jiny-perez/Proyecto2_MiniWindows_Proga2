/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package RedSocialINSTA.GUI;

import Modelo.Usuario;
import RedSocialINSTA.Logica.GestorINSTA;
import RedSocialINSTA.Logica.GestorUsuariosLocal;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 *
 * @author najma
 */
public class VentanaLogin extends JFrame {
  
    private GestorUsuariosLocal gestorUsuarios;
    
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
    private JComboBox<String> cmbGenero;
    private JSpinner spnEdad;
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
        gestorUsuarios = new GestorUsuariosLocal();
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
            ImageIcon logoIcon = IconManager.getLogoScaled(200, 58);
            lblLogo.setIcon(logoIcon);
        } catch (Exception e) {
            ImageIcon logoIcon = IconDrawer.createInstagramLogo(200, 58);
            lblLogo.setIcon(logoIcon);
        }
        lblLogo.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblLogo.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        container.add(lblLogo);
        container.add(Box.createVerticalStrut(30));
        
        txtUsernameLogin = crearCampoTexto("Username");
        txtUsernameLogin.setMaximumSize(new Dimension(270, 40));
        txtUsernameLogin.setAlignmentX(Component.CENTER_ALIGNMENT);
        container.add(txtUsernameLogin);
        container.add(Box.createVerticalStrut(10));
        
        JPanel panelPassword = new JPanel(new BorderLayout(0, 0));
        panelPassword.setMaximumSize(new Dimension(270, 40));
        panelPassword.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelPassword.setBackground(Color.WHITE);
        
        txtPasswordLogin = crearCampoPassword("Contraseña");
        txtPasswordLogin.setPreferredSize(new Dimension(230, 40));
        
        JButton btnMostrarPassword = new JButton();
        btnMostrarPassword.setIcon(IconDrawer.createEyeClosedIcon(20));
        btnMostrarPassword.setPreferredSize(new Dimension(40, 40));
        btnMostrarPassword.setBackground(Color.WHITE);
        btnMostrarPassword.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(1, 0, 1, 1, BORDER_COLOR),
            BorderFactory.createEmptyBorder(0, 0, 0, 0)
        ));
        btnMostrarPassword.setFocusPainted(false);
        btnMostrarPassword.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        final boolean[] passwordVisible = {false};
        btnMostrarPassword.addActionListener(e -> {
            passwordVisible[0] = !passwordVisible[0];
            if (passwordVisible[0]) {
                txtPasswordLogin.setEchoChar((char) 0);
                btnMostrarPassword.setIcon(IconDrawer.createEyeOpenIcon(20));
            } else {
                String currentText = String.valueOf(txtPasswordLogin.getPassword());
                if (!currentText.equals("Contraseña") && !currentText.isEmpty()) {
                    txtPasswordLogin.setEchoChar('•');
                }
                btnMostrarPassword.setIcon(IconDrawer.createEyeClosedIcon(20));
            }
        });
        
        panelPassword.add(txtPasswordLogin, BorderLayout.CENTER);
        panelPassword.add(btnMostrarPassword, BorderLayout.EAST);
        
        container.add(panelPassword);
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
        container.setMaximumSize(new Dimension(350, 700));
        
        JLabel lblLogo = new JLabel();
        try {
            ImageIcon logoIcon = IconManager.getLogoScaled(175, 51);
            lblLogo.setIcon(logoIcon);
        } catch (Exception e) {
            ImageIcon logoIcon = IconDrawer.createInstagramLogo(175, 51);
            lblLogo.setIcon(logoIcon);
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
        
        cmbGenero = new JComboBox<>(new String[]{"Género", "Masculino", "Femenino"});
        cmbGenero.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cmbGenero.setMaximumSize(new Dimension(270, 40));
        cmbGenero.setAlignmentX(Component.CENTER_ALIGNMENT);
        cmbGenero.setBackground(Color.WHITE);
        cmbGenero.setForeground(TEXT_SECONDARY);
        cmbGenero.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR, 1),
            BorderFactory.createEmptyBorder(8, 8, 8, 8)
        ));
        cmbGenero.addActionListener(e -> {
            if (cmbGenero.getSelectedIndex() > 0) {
                cmbGenero.setForeground(TEXT_PRIMARY);
            }
        });
        container.add(cmbGenero);
        container.add(Box.createVerticalStrut(10));
        
        JPanel panelEdad = new JPanel(new BorderLayout(10, 0));
        panelEdad.setMaximumSize(new Dimension(270, 40));
        panelEdad.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelEdad.setBackground(Color.WHITE);
        panelEdad.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR, 1),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        
        JLabel lblEdad = new JLabel("Edad:");
        lblEdad.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblEdad.setForeground(TEXT_SECONDARY);
        
        SpinnerNumberModel modeloEdad = new SpinnerNumberModel(18, 16, 120, 1);
        spnEdad = new JSpinner(modeloEdad);
        spnEdad.setFont(new Font("Segoe UI", Font.BOLD, 15));
        spnEdad.setPreferredSize(new Dimension(100, 24));
        
        JSpinner.DefaultEditor editor = (JSpinner.DefaultEditor) spnEdad.getEditor();
        editor.getTextField().setHorizontalAlignment(JTextField.CENTER);
        editor.getTextField().setEditable(false);
        editor.getTextField().setFont(new Font("Segoe UI", Font.BOLD, 15));
        
        spnEdad.setBorder(BorderFactory.createLineBorder(BORDER_COLOR, 1));
        
        panelEdad.add(lblEdad, BorderLayout.WEST);
        panelEdad.add(spnEdad, BorderLayout.EAST);
        
        container.add(panelEdad);
        container.add(Box.createVerticalStrut(10));
        
        JPanel panelPasswordReg = new JPanel(new BorderLayout(0, 0));
        panelPasswordReg.setMaximumSize(new Dimension(270, 40));
        panelPasswordReg.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelPasswordReg.setBackground(Color.WHITE);
        
        txtPasswordRegistro = crearCampoPassword("Contraseña");
        txtPasswordRegistro.setPreferredSize(new Dimension(230, 40));
        
        JButton btnMostrarPasswordReg = new JButton();
        btnMostrarPasswordReg.setIcon(IconDrawer.createEyeClosedIcon(20));
        btnMostrarPasswordReg.setPreferredSize(new Dimension(40, 40));
        btnMostrarPasswordReg.setBackground(Color.WHITE);
        btnMostrarPasswordReg.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(1, 0, 1, 1, BORDER_COLOR),
            BorderFactory.createEmptyBorder(0, 0, 0, 0)
        ));
        btnMostrarPasswordReg.setFocusPainted(false);
        btnMostrarPasswordReg.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        final boolean[] passwordVisibleReg = {false};
        btnMostrarPasswordReg.addActionListener(e -> {
            passwordVisibleReg[0] = !passwordVisibleReg[0];
            if (passwordVisibleReg[0]) {
                txtPasswordRegistro.setEchoChar((char) 0);
                btnMostrarPasswordReg.setIcon(IconDrawer.createEyeOpenIcon(20));
            } else {
                String currentText = String.valueOf(txtPasswordRegistro.getPassword());
                if (!currentText.equals("Contraseña") && !currentText.isEmpty()) {
                    txtPasswordRegistro.setEchoChar('•');
                }
                btnMostrarPasswordReg.setIcon(IconDrawer.createEyeClosedIcon(20));
            }
        });
        
        panelPasswordReg.add(txtPasswordRegistro, BorderLayout.CENTER);
        panelPasswordReg.add(btnMostrarPasswordReg, BorderLayout.EAST);
        
        container.add(panelPasswordReg);
        container.add(Box.createVerticalStrut(10));
        
        JPanel panelConfirmarPassword = new JPanel(new BorderLayout(0, 0));
        panelConfirmarPassword.setMaximumSize(new Dimension(270, 40));
        panelConfirmarPassword.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelConfirmarPassword.setBackground(Color.WHITE);
        
        txtConfirmarPassword = crearCampoPassword("Confirmar contraseña");
        txtConfirmarPassword.setPreferredSize(new Dimension(230, 40));
        
        JButton btnMostrarConfirmar = new JButton();
        btnMostrarConfirmar.setIcon(IconDrawer.createEyeClosedIcon(20));
        btnMostrarConfirmar.setPreferredSize(new Dimension(40, 40));
        btnMostrarConfirmar.setBackground(Color.WHITE);
        btnMostrarConfirmar.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(1, 0, 1, 1, BORDER_COLOR),
            BorderFactory.createEmptyBorder(0, 0, 0, 0)
        ));
        btnMostrarConfirmar.setFocusPainted(false);
        btnMostrarConfirmar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        final boolean[] confirmarVisible = {false};
        btnMostrarConfirmar.addActionListener(e -> {
            confirmarVisible[0] = !confirmarVisible[0];
            if (confirmarVisible[0]) {
                txtConfirmarPassword.setEchoChar((char) 0);
                btnMostrarConfirmar.setIcon(IconDrawer.createEyeOpenIcon(20));
            } else {
                String currentText = String.valueOf(txtConfirmarPassword.getPassword());
                if (!currentText.equals("Confirmar contraseña") && !currentText.isEmpty()) {
                    txtConfirmarPassword.setEchoChar('•');
                }
                btnMostrarConfirmar.setIcon(IconDrawer.createEyeClosedIcon(20));
            }
        });
        
        panelConfirmarPassword.add(txtConfirmarPassword, BorderLayout.CENTER);
        panelConfirmarPassword.add(btnMostrarConfirmar, BorderLayout.EAST);
        
        container.add(panelConfirmarPassword);
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
        
        if (gestorUsuarios.validarLogin(username, password)) {
            Usuario usuario = gestorUsuarios.obtenerUsuario(username);
            abrirInstagram(usuario);
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
        
        if (cmbGenero.getSelectedIndex() == 0) {
            mostrarError("Por favor selecciona tu género");
            return;
        }
        
        int edad = (int) spnEdad.getValue();
        
        if (edad < 16) {
            mostrarError("Debes tener al menos 16 años para usar Instagram");
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
        
        if (gestorUsuarios.existeUsuario(username)) {
            mostrarError("Este username ya está en uso");
            return;
        }
        
        char genero = cmbGenero.getSelectedIndex() == 1 ? 'M' : 'F';
        
        if (gestorUsuarios.registrarUsuario(username, nombreCompleto, genero, edad, password)) {
            mostrarExito("¡Cuenta creada exitosamente!");
            Usuario usuario = gestorUsuarios.obtenerUsuario(username);
            abrirInstagram(usuario);
        } else {
            mostrarError("Error al crear la cuenta");
        }
    }
    
    private void abrirInstagram(Usuario usuario) {
        GestorINSTA gestorINSTA = new GestorINSTA(usuario);
        VentanaINSTA ventanaINSTA = new VentanaINSTA(usuario, gestorINSTA, gestorUsuarios);
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