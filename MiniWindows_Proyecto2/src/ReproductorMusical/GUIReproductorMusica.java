package ReproductorMusical;

import Sistema.MiniWindowsClass;
import Sistema.SistemaArchivos;
import Modelo.ArchivoVirtual;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

/**
 *
 * @author marye
 */
public class GUIReproductorMusica {

    ListaCanciones listaCanciones;
    GestionMusica gestionMusica;
    Reproductor reproductor;

    JList<Cancion> JListaCanciones;
    DefaultListModel<Cancion> listModel;
    JButton btnActualizar;
    JLabel lblListaVacia;

    public GUIReproductorMusica() {
        listaCanciones = new ListaCanciones();
        gestionMusica = new GestionMusica();
        reproductor = new Reproductor();
        initComponents();
        cargarCancionesDesdeNavegador();
    }

    public void initComponents() {
        JFrame VReproductorMusica = new JFrame("Reproductor de Música");
        VReproductorMusica.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        VReproductorMusica.setSize(900, 700);
        VReproductorMusica.setLocationRelativeTo(null);
        VReproductorMusica.setLayout(new BorderLayout());
        VReproductorMusica.getContentPane().setBackground(new Color(18, 18, 18));

        JPanel PPrincipal = new JPanel(new BorderLayout());
        PPrincipal.setBackground(new Color(18, 18, 18));

        JLabel lblTitulo = new JLabel("Biblioteca de Música", SwingConstants.CENTER);
        lblTitulo.setForeground(Color.white);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 30));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(30, 20, 30, 20));
        PPrincipal.add(lblTitulo, BorderLayout.NORTH);

        listModel = new DefaultListModel<>();
        JListaCanciones = new JList<>(listModel);
        JListaCanciones.setCellRenderer(new CancionListCellRenderer());
        JListaCanciones.setBackground(new Color(18, 18, 18));
        JListaCanciones.setForeground(Color.WHITE);
        JListaCanciones.setSelectionBackground(new Color(40, 40, 40));
        JListaCanciones.setSelectionForeground(new Color(29, 185, 84));
        JListaCanciones.setFont(new Font("Arial", Font.PLAIN, 12));

        JListaCanciones.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                Cancion seleccion = JListaCanciones.getSelectedValue();
                if (seleccion != null) {
                    try {
                        reproductor.cargarCancion(seleccion);
                        reproductor.play();
                        JListaCanciones.repaint();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Error al reproducir: " + ex.getMessage(),
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        reproductor.setOnFinished(() -> SwingUtilities.invokeLater(() -> {
            if (JListaCanciones != null) {
                JListaCanciones.clearSelection();
            }
        }));

        JScrollPane scrollPane = new JScrollPane(JListaCanciones);
        scrollPane.getViewport().setBackground(new Color(18, 18, 18));
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        JPanel panelListaContenedor = new JPanel(new CardLayout());
        panelListaContenedor.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        lblListaVacia = new JLabel("No hay canciones en la carpeta Musica", SwingConstants.CENTER);
        lblListaVacia.setForeground(new Color(179, 179, 179));
        lblListaVacia.setFont(new Font("Arial", Font.ITALIC, 16));
        lblListaVacia.setBackground(new Color(18, 18, 18));
        lblListaVacia.setOpaque(true);

        panelListaContenedor.add(scrollPane, "Lista");
        panelListaContenedor.add(lblListaVacia, "Vacia");
        PPrincipal.add(panelListaContenedor, BorderLayout.CENTER);

        btnActualizar = new JButton("Actualizar Lista");
        btnActualizar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnActualizar.setBackground(new Color(29, 185, 84));
        btnActualizar.setForeground(Color.WHITE);
        btnActualizar.setFocusPainted(false);
        btnActualizar.setBorderPainted(false);
        btnActualizar.setContentAreaFilled(true);
        btnActualizar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnActualizar.setPreferredSize(new Dimension(890, 40));

        btnActualizar.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btnActualizar.setBackground(new Color(29, 185, 84).darker());
            }

            public void mouseExited(MouseEvent e) {
                btnActualizar.setBackground(new Color(29, 185, 84));
            }
        });

        btnActualizar.addActionListener(e -> cargarCancionesDesdeNavegador());

        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        panelBoton.setBackground(new Color(18, 18, 18));
        panelBoton.add(btnActualizar);

        JPanel panelSouth = new JPanel();
        panelSouth.setLayout(new BoxLayout(panelSouth, BoxLayout.Y_AXIS));
        panelSouth.setBackground(new Color(18, 18, 18));

        JPanel panelReproductor = reproductor.getPanel();
        panelReproductor.setAlignmentX(Component.CENTER_ALIGNMENT);

        panelSouth.add(panelReproductor);
        panelSouth.add(Box.createRigidArea(new Dimension(0, 8)));
        panelSouth.add(panelBoton);

        PPrincipal.add(panelSouth, BorderLayout.SOUTH);

        VReproductorMusica.add(PPrincipal);
        VReproductorMusica.setVisible(true);
        
        actualizarVista();
    }

    private void cargarCancionesDesdeNavegador() {
        listaCanciones = new ListaCanciones();
        
        try {
            MiniWindowsClass sistema = MiniWindowsClass.getInstance();
            SistemaArchivos sistemaArchivos = sistema.getSistemaArchivos();
            String username = sistema.getUsuarioActual().getUsername();
            
            ArchivoVirtual raiz = sistemaArchivos.getRaiz();
            ArchivoVirtual carpetaUsuario = raiz.buscarHijo(username);
            
            if (carpetaUsuario != null) {
                ArchivoVirtual carpetaMusica = carpetaUsuario.buscarHijo("Musica");
                
                if (carpetaMusica != null && carpetaMusica.getHijos() != null) {
                    for (ArchivoVirtual archivo : carpetaMusica.getHijos()) {
                        if (!archivo.isEsCarpeta()) {
                            String nombre = archivo.getNombre().toLowerCase();
                            if (nombre.endsWith(".mp3")) {
                                String rutaVirtual = archivo.getRutaCompleta();
                                rutaVirtual = rutaVirtual.replace("Z:", "").replace("Z:\\", "");
                                if (rutaVirtual.startsWith("\\")) {
                                    rutaVirtual = rutaVirtual.substring(1);
                                }
                                
                                File archivoFisico = new File(rutaVirtual);
                                if (archivoFisico.exists()) {
                                    String titulo = archivo.getNombre().replaceFirst("[.][^.]+$", "");
                                    Cancion cancion = new Cancion(titulo, archivoFisico.getAbsolutePath());
                                    listaCanciones.addListaCanciones(cancion);
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error al cargar canciones: " + e.getMessage());
            e.printStackTrace();
        }
        
        actualizarVista();
    }

    private void actualizarVista() {
        listModel.clear();
        if (listaCanciones != null) {
            int totalCanciones = listaCanciones.tamanio();
            for (int i = 0; i < totalCanciones; i++) {
                Cancion cancion = listaCanciones.obtenerCancion(i);
                if (cancion != null) {
                    listModel.addElement(cancion);
                }
            }
        }
        CardLayout cl = (CardLayout) (lblListaVacia.getParent().getLayout());
        if (listModel.isEmpty()) {
            cl.show(lblListaVacia.getParent(), "Vacia");
        } else {
            cl.show(lblListaVacia.getParent(), "Lista");
        }
    }

    private class CancionListCellRenderer extends DefaultListCellRenderer {

        private Cancion getCancionActual() {
            return reproductor != null ? reproductor.getCancionActual() : null;
        }

        @Override
        public Component getListCellRendererComponent(
                JList<?> list, Object value, int index,
                boolean isSelected, boolean cellHasFocus) {

            if (value instanceof Cancion) {
                Cancion cancion = (Cancion) value;

                JPanel panel = new JPanel(new BorderLayout());
                panel.setOpaque(true);
                panel.setPreferredSize(new Dimension(0, 60));

                boolean isCurrentlyPlaying = false;
                Cancion actual = getCancionActual();
                if (actual != null && actual.equals(cancion)) {
                    isCurrentlyPlaying = true;
                }

                if (isSelected) {
                    panel.setBackground(new Color(40, 40, 40));
                } else if (isCurrentlyPlaying) {
                    panel.setBackground(new Color(25, 25, 25));
                } else {
                    panel.setBackground(new Color(18, 18, 18));
                }

                JLabel lblImgDefault = new JLabel(cancion.getImgDefault());
                lblImgDefault.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

                JPanel panelInfo = new JPanel(new GridLayout(2, 1));
                panelInfo.setOpaque(false);

                JLabel lblTitulo = new JLabel(cancion.getTitulo());
                lblTitulo.setFont(new Font("Arial", Font.BOLD, 14));
                lblTitulo.setForeground((isSelected || isCurrentlyPlaying) ? new Color(29, 185, 84) : Color.WHITE);
                panelInfo.add(lblTitulo);

                JLabel lblDuracion = new JLabel(cancion.DuracionFormateada());
                lblDuracion.setForeground(new Color(179, 179, 179));
                lblDuracion.setFont(new Font("Arial", Font.PLAIN, 12));
                lblDuracion.setHorizontalAlignment(SwingConstants.RIGHT);
                lblDuracion.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));

                panel.add(lblImgDefault, BorderLayout.WEST);
                panel.add(panelInfo, BorderLayout.CENTER);
                panel.add(lblDuracion, BorderLayout.EAST);
                return panel;
            }
            return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GUIReproductorMusica());
    }
}
