/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MiniWindows;

import Sistema.MiniWindowsClass;
import Modelo.Usuario;
import VisorImagenes.GUIVisorImagenes;
import CMD.GUICMD;
import EditorTexto.GUIEditor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 *
 * @author najma
 */
public class VentanaPrincipal extends JFrame {
    
    private Usuario usuarioActual;
    private MiniWindowsClass sistema;
    
    private JPanel panelEscritorio;
    private JMenuBar barraMenu;
    
    public VentanaPrincipal(Usuario usuario, MiniWindowsClass sistema) {
        this.usuarioActual = usuario;
        this.sistema = sistema;
        
        inicializarComponentes();
        configurarVentana();
    }
    
    private void inicializarComponentes() {
        crearBarraMenu();
        
        panelEscritorio = new JPanel();
        panelEscritorio.setLayout(new BorderLayout());
        panelEscritorio.setBackground(new Color(0, 120, 215));
        
        JPanel panelBienvenida = new JPanel();
        panelBienvenida.setLayout(new BoxLayout(panelBienvenida, BoxLayout.Y_AXIS));
        panelBienvenida.setOpaque(false);
        
        JLabel lblBienvenida = new JLabel("Bienvenido a Mini-Windows");
        lblBienvenida.setFont(new Font("Segoe UI Light", Font.BOLD, 42));
        lblBienvenida.setForeground(Color.WHITE);
        lblBienvenida.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel lblUsuario = new JLabel(usuarioActual.getNombreCompleto());
        lblUsuario.setFont(new Font("Segoe UI", Font.PLAIN, 24));
        lblUsuario.setForeground(Color.WHITE);
        lblUsuario.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel lblRol = new JLabel(usuarioActual.esAdmin() ? "(Administrador)" : "(Usuario)");
        lblRol.setFont(new Font("Segoe UI", Font.ITALIC, 18));
        lblRol.setForeground(new Color(220, 220, 220));
        lblRol.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        panelBienvenida.add(Box.createVerticalGlue());
        panelBienvenida.add(lblBienvenida);
        panelBienvenida.add(Box.createRigidArea(new Dimension(0, 20)));
        panelBienvenida.add(lblUsuario);
        panelBienvenida.add(Box.createRigidArea(new Dimension(0, 10)));
        panelBienvenida.add(lblRol);
        panelBienvenida.add(Box.createVerticalGlue());
        
        panelEscritorio.add(panelBienvenida, BorderLayout.CENTER);
        
        add(panelEscritorio);
    }
    
    private void crearBarraMenu() {
        barraMenu = new JMenuBar();
        barraMenu.setBackground(Color.WHITE);
        
        JMenu menuArchivo = new JMenu("Archivo");
        menuArchivo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        
        JMenuItem itemNavegador = new JMenuItem("Navegador de Archivos");
        itemNavegador.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        itemNavegador.addActionListener(e -> abrirNavegador());
        
        JMenuItem itemCerrarSesion = new JMenuItem("Cerrar Sesión");
        itemCerrarSesion.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        itemCerrarSesion.addActionListener(e -> cerrarSesion());
        
        JMenuItem itemSalir = new JMenuItem("Salir");
        itemSalir.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        itemSalir.addActionListener(e -> salir());
        
        menuArchivo.add(itemNavegador);
        menuArchivo.addSeparator();
        menuArchivo.add(itemCerrarSesion);
        menuArchivo.add(itemSalir);
        
        JMenu menuAplicaciones = new JMenu("Aplicaciones");
        menuAplicaciones.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        
        JMenuItem itemCMD = new JMenuItem("Consola CMD");
        itemCMD.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        itemCMD.addActionListener(e -> abrirCMD());
        
        JMenuItem itemEditor = new JMenuItem("Editor de Texto");
        itemEditor.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        itemEditor.addActionListener(e -> abrirEditor());
        
        JMenuItem itemVisorImagenes = new JMenuItem("Visor de Imágenes");
        itemVisorImagenes.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        itemVisorImagenes.addActionListener(e -> abrirVisorImagenes());
        
        JMenuItem itemReproductor = new JMenuItem("Reproductor de Música");
        itemReproductor.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        itemReproductor.addActionListener(e -> abrirReproductor());
        
        menuAplicaciones.add(itemCMD);
        menuAplicaciones.add(itemEditor);
        menuAplicaciones.add(itemVisorImagenes);
        menuAplicaciones.add(itemReproductor);
        
        if (usuarioActual.esAdmin()) {
            JMenu menuSistema = new JMenu("Sistema");
            menuSistema.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            
            JMenuItem itemGestionUsuarios = new JMenuItem("Gestión de Usuarios");
            itemGestionUsuarios.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            itemGestionUsuarios.addActionListener(e -> gestionarUsuarios());
            
            menuSistema.add(itemGestionUsuarios);
            barraMenu.add(menuSistema);
        }
        
        JMenu menuAyuda = new JMenu("Ayuda");
        menuAyuda.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        
        JMenuItem itemAcerca = new JMenuItem("Acerca de");
        itemAcerca.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        itemAcerca.addActionListener(e -> mostrarAcercaDe());
        
        menuAyuda.add(itemAcerca);
        
        barraMenu.add(menuArchivo);
        barraMenu.add(menuAplicaciones);
        barraMenu.add(menuAyuda);
        
        setJMenuBar(barraMenu);
    }
    
    private void configurarVentana() {
        setTitle("Mini-Windows - " + usuarioActual.getUsername());
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                salir();
            }
        });
    }
    
    private void abrirNavegador() {
        NavegadorArchivos navegador = new NavegadorArchivos(this, usuarioActual, sistema);
        navegador.setVisible(true);
    }
    
    private void abrirCMD() {
        try {
            // Crear instancia de la consola CMD
            new GUICMD();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Error al abrir la consola CMD: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void abrirEditor() {
        try {
            // Crear instancia del Editor de Texto
            new GUIEditor();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Error al abrir el editor de texto: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void abrirVisorImagenes() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Seleccionar Imagen");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
            @Override
            public boolean accept(java.io.File f) {
                if (f.isDirectory()) {
                    return true;
                }
                String nombre = f.getName().toLowerCase();
                return nombre.endsWith(".jpg") || nombre.endsWith(".jpeg") || 
                       nombre.endsWith(".png") || nombre.endsWith(".gif") ||
                       nombre.endsWith(".bmp") || nombre.endsWith(".webp");
            }
            
            @Override
            public String getDescription() {
                return "Archivos de imagen (*.jpg, *.png, *.gif, *.bmp)";
            }
        });
        
        int resultado = fileChooser.showOpenDialog(this);
        
        if (resultado == JFileChooser.APPROVE_OPTION) {
            java.io.File archivo = fileChooser.getSelectedFile();
            try {
                GUIVisorImagenes visor = new GUIVisorImagenes(archivo);
                visor.setVisible(true);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                    "Error al abrir el visor de imágenes: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void abrirReproductor() {
        // Temporalmente comentado - requiere librerías externas (JLayer, JAudioTagger)
        JOptionPane.showMessageDialog(this,
            "Reproductor de Música\n\n" +
            "Para usar el reproductor necesitas agregar las librerías:\n" +
            "- JLayer (javazoom)\n" +
            "- JAudioTagger\n\n" +
            "Consulta con tu compañera sobre las librerías.",
            "Librerías Requeridas",
            JOptionPane.INFORMATION_MESSAGE);
        
        /* CÓDIGO ORIGINAL (requiere librerías):
        try {
            new GUIReproductorMusica();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Error al abrir el reproductor de música: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
        */
    }
    
    private void gestionarUsuarios() {
        JOptionPane.showMessageDialog(this, 
            "Gestión de Usuarios\n(Por implementar)", 
            "Próximamente", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void mostrarAcercaDe() {
        JOptionPane.showMessageDialog(this,
            "Mini-Windows v1.0\n\n" +
            "Sistema Operativo Virtual\n" +
            "Proyecto II - Programación II\n\n" +
            "Desarrollado por: Jiny Pérez y Najmah Zablah\n" +
            "UNITEC 2025",
            "Acerca de Mini-Windows",
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void cerrarSesion() {
        int confirmacion = JOptionPane.showConfirmDialog(this,
            "¿Está seguro que desea cerrar sesión?",
            "Confirmar Cierre de Sesión",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE);
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            sistema.logout();
            dispose();
            
            SwingUtilities.invokeLater(() -> {
                PantallaLogin login = new PantallaLogin();
                login.setVisible(true);
            });
        }
    }
    
    private void salir() {
        int confirmacion = JOptionPane.showConfirmDialog(this,
            "¿Está seguro que desea salir de Mini-Windows?",
            "Confirmar Salida",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE);
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            sistema.guardarSistema();
            System.exit(0);
        }
    }
}