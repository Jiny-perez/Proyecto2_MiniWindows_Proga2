package ReproductorMusical;

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
    JButton btnAgregar;
    JLabel lblListaVacia;

    public GUIReproductorMusica() {
        listaCanciones = new ListaCanciones();
        gestionMusica = new GestionMusica();
        reproductor = new Reproductor();
        initComponents();
        cargarCanciones();
    }

    public void initComponents() {
        JFrame VReproductorMusica = new JFrame("Reproductor de Música");
        VReproductorMusica.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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

        lblListaVacia = new JLabel("No hay ninguna canción agregada", SwingConstants.CENTER);
        lblListaVacia.setForeground(new Color(179, 179, 179));
        lblListaVacia.setFont(new Font("Arial", Font.ITALIC, 16));
        lblListaVacia.setBackground(new Color(18, 18, 18));
        lblListaVacia.setOpaque(true);

        panelListaContenedor.add(scrollPane, "Lista");
        panelListaContenedor.add(lblListaVacia, "Vacia");
        PPrincipal.add(panelListaContenedor, BorderLayout.CENTER);

        btnAgregar = new JButton("Agregar Canción");
        btnAgregar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnAgregar.setBackground(new Color(29, 185, 84));
        btnAgregar.setForeground(Color.WHITE);
        btnAgregar.setFocusPainted(false);
        btnAgregar.setBorderPainted(false);
        btnAgregar.setContentAreaFilled(true);
        btnAgregar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAgregar.setPreferredSize(new Dimension(890, 40));

        btnAgregar.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btnAgregar.setBackground(new Color(29, 185, 84).darker());
            }

            public void mouseExited(MouseEvent e) {
                btnAgregar.setBackground(new Color(29, 185, 84));
            }
        });

        btnAgregar.addActionListener(e -> agregarCancion());

        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        panelBoton.setBackground(new Color(18, 18, 18));
        panelBoton.add(btnAgregar);

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
    }

    private void cargarCanciones() {
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

    private void agregarCancion() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Seleccionar Archivo de Música (MP3)");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Archivos MP3 (*.mp3)", "mp3"));
        int resultado = fileChooser.showOpenDialog(null);

        if (resultado == JFileChooser.APPROVE_OPTION) {
            File archivoSeleccionado = fileChooser.getSelectedFile();
            String rutaArchivo = archivoSeleccionado.getAbsolutePath();
            if (!rutaArchivo.toLowerCase().endsWith(".mp3")) {
                JOptionPane.showMessageDialog(null, "Por favor, selecciona un archivo de música en formato MP3.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                Cancion nuevaCancion = gestionMusica.crearCancionDesdeArchivo(rutaArchivo);
                if (nuevaCancion != null) {
                    listaCanciones.addListaCanciones(nuevaCancion);
                    gestionMusica.GuardarListaCanciones(listaCanciones);
                    cargarCanciones();
                } else {
                    JOptionPane.showMessageDialog(null, "No se pudo crear el objeto Canción desde el archivo.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error al guardar la lista de canciones.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error al procesar el archivo: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GUIReproductorMusica());
    }
}
