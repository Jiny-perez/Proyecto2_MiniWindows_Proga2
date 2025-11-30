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
    JButton btnAgregarCancion;
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
        VReproductorMusica.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        VReproductorMusica.setSize(900, 700);
        VReproductorMusica.setLocationRelativeTo(null);
        VReproductorMusica.setLayout(new BorderLayout());
        VReproductorMusica.getContentPane().setBackground(new Color(18, 18, 18));

        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(new Color(18, 18, 18));
        JLabel lblTitulo = new JLabel("Biblioteca de Música", SwingConstants.CENTER);
        lblTitulo.setForeground(Color.white);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 30));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(30, 20, 30, 20));

        panelPrincipal.add(lblTitulo, BorderLayout.NORTH);
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
        panelPrincipal.add(panelListaContenedor, BorderLayout.CENTER);

        btnAgregarCancion = new JButton("Agregar Cancion");
        btnAgregarCancion.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnAgregarCancion.setBackground(new Color(29, 185, 84));
        btnAgregarCancion.setForeground(Color.WHITE);
        btnAgregarCancion.setFocusPainted(false);
        btnAgregarCancion.setBorderPainted(false);
        btnAgregarCancion.setContentAreaFilled(true);
        btnAgregarCancion.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAgregarCancion.setPreferredSize(new Dimension(890, 40));

        btnAgregarCancion.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btnAgregarCancion.setBackground(new Color(29, 185, 84).darker());
            }

            public void mouseExited(MouseEvent e) {
                btnAgregarCancion.setBackground(new Color(29, 185, 84));
            }
        });

        btnAgregarCancion.addActionListener(e -> agregarCancionSO());

        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        panelBoton.setBackground(new Color(18, 18, 18));
        panelBoton.add(btnAgregarCancion);

        JPanel panelBarraReproduccion = new JPanel();
        panelBarraReproduccion.setLayout(new BoxLayout(panelBarraReproduccion, BoxLayout.Y_AXIS));
        panelBarraReproduccion.setBackground(new Color(18, 18, 18));

        JPanel panelReproductor = reproductor.getPanel();
        panelReproductor.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelBarraReproduccion.add(panelReproductor);
        panelBarraReproduccion.add(Box.createRigidArea(new Dimension(0, 8)));
        panelBarraReproduccion.add(panelBoton);

        panelPrincipal.add(panelBarraReproduccion, BorderLayout.SOUTH);
        VReproductorMusica.add(panelPrincipal);
        VReproductorMusica.setVisible(true);
        actualizarVista();
    }

    private File fileVirtualToReal(String direccion) {
        String dirVir = direccion.replace("/", File.separator).replace("\\", File.separator);

        String dirBase = System.getProperty("user.dir") + File.separator + "Z";
        File dir = new File(dirBase);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        if (dirVir.startsWith("Z:" + File.separator)) {
            dirVir = dirVir.substring(2);
            if (dirVir.startsWith(File.separator)) {
                dirVir = dirVir.substring(1);
            }
        }

        return new File(dir, dirVir);
    }

    private void agregarCancionSO() {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Seleccione cancion");
        chooser.setMultiSelectionEnabled(true);
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);
        chooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
            public boolean accept(File f) {
                if (f.isDirectory()) {
                    return true;
                }
                String name = f.getName().toLowerCase();
                return name.endsWith(".mp3");
            }

            public String getDescription() {
                return "Archivos MP3 (*.mp3)";
            }
        });

        int resultado = chooser.showOpenDialog(null);
        if (resultado != JFileChooser.APPROVE_OPTION) {
            return;
        }

        File[] archivos = chooser.getSelectedFiles();
        if (archivos == null || archivos.length == 0) {
            return;
        }

        if (listaCanciones == null) {
            listaCanciones = new ListaCanciones();
        }

        MiniWindowsClass sistema = MiniWindowsClass.getInstance();
        if (sistema == null || sistema.getUsuarioActual() == null) {
            return;
        }
        String username = sistema.getUsuarioActual().getUsername();
        SistemaArchivos sistemaArchivos = sistema.getSistemaArchivos();
        ArchivoVirtual raiz = sistemaArchivos.getRaiz();

        ArchivoVirtual carpetaUsuario = raiz.buscarHijo(username);
        if (carpetaUsuario == null) {
            carpetaUsuario = new ArchivoVirtual(username, raiz.getRutaCompleta());
            raiz.agregarHijo(carpetaUsuario);
        }
        ArchivoVirtual carpetaMusica = carpetaUsuario.buscarHijo("Musica");
        if (carpetaMusica == null) {
            carpetaMusica = new ArchivoVirtual("Musica", carpetaUsuario.getRutaCompleta());
            carpetaUsuario.agregarHijo(carpetaMusica);
        }

        for (File f : archivos) {
            try {
                String titulo = f.getName().replaceFirst("[.][^.]+$", "");

                // Construir nuevo ArchivoVirtual (se usa para mantener estructura virtual)
                ArchivoVirtual nuevoArchivoVirtual = new ArchivoVirtual(f.getName(), carpetaMusica.getRutaCompleta(), "Audio", f.length());

                // Obtener ruta real usando método local
                String rutaVirtualCompleta = carpetaMusica.getRutaCompleta();
                // Asegurarnos de que la ruta virtual termina con separador antes de concatenar el nombre
                if (!rutaVirtualCompleta.endsWith("\\") && !rutaVirtualCompleta.endsWith("/")) {
                    rutaVirtualCompleta = rutaVirtualCompleta + File.separator;
                }
                File archivoDestino = fileVirtualToReal(rutaVirtualCompleta + f.getName());

                // Crear directorio padre si no existe
                File parent = archivoDestino.getParentFile();
                if (parent != null && !parent.exists()) {
                    if (!parent.mkdirs()) {
                        throw new IOException("No se pudo crear el directorio: " + parent.getAbsolutePath());
                    }
                }

                // Copiar fichero
                try (InputStream in = new FileInputStream(f); OutputStream out = new FileOutputStream(archivoDestino)) {
                    byte[] buffer = new byte[4096];
                    int len;
                    while ((len = in.read(buffer)) != -1) {
                        out.write(buffer, 0, len);
                    }
                }

                // Añadir al árbol virtual
                carpetaMusica.agregarHijo(nuevoArchivoVirtual);

                // Crear objeto Cancion y cargar en reproductor para obtener duración
                Cancion nueva = new Cancion(titulo, archivoDestino.getAbsolutePath());
                reproductor.cargarCancion(nueva);
                long dur = 0;
                if (reproductor.getCancionActual() != null) {
                    dur = reproductor.getCancionActual().getDuracion();
                }
                nueva.setDuracion(dur);
                reproductor.limpiar();

                // Agregar a lista y vista
                listaCanciones.agregarListaCanciones(nueva);
                actualizarVista();

                // Persistir en gestionMusica si existe
                try {
                    if (gestionMusica != null) {
                        gestionMusica.agregarCancion(nueva);
                    }
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private void cargarCanciones() {
        listaCanciones = new ListaCanciones();
        try {
            MiniWindowsClass sistema = MiniWindowsClass.getInstance();
            if (sistema == null || sistema.getUsuarioActual() == null) {
                return;
            }
            SistemaArchivos sistemaArchivos = sistema.getSistemaArchivos();
            String username = sistema.getUsuarioActual().getUsername();
            ArchivoVirtual raiz = sistemaArchivos.getRaiz();
            ArchivoVirtual carpetaUsuario = raiz.buscarHijo(username);
            if (carpetaUsuario != null) {
                ArchivoVirtual carpetaMusica = carpetaUsuario.buscarHijo("Musica");
                if (carpetaMusica != null && carpetaMusica.getHijos() != null) {
                    for (ArchivoVirtual archivo : carpetaMusica.getHijos()) {
                        try {
                            if (!archivo.isEsCarpeta()) {
                                String nombre = archivo.getNombre().toLowerCase();
                                if (nombre.endsWith(".mp3")) {
                                    // Obtener archivo físico usando la ruta virtual del archivo
                                    String rutaVirtual = archivo.getRutaCompleta();
                                    File archivoFisico = fileVirtualToReal(rutaVirtual);
                                    if (archivoFisico.exists()) {
                                        String titulo = archivo.getNombre().replaceFirst("[.][^.]+$", "");
                                        Cancion cancion = new Cancion(titulo, archivoFisico.getAbsolutePath());
                                        // Si quieres obtener la duración aquí, podrías cargar en reproductor como antes:
                                        listaCanciones.agregarListaCanciones(cancion);
                                    } else {
                                        // Si no existe el archivo físico, mostrar aviso (o manejarlo como prefieras)
                                        System.err.println("Archivo físico no encontrado: " + archivoFisico.getAbsolutePath());
                                    }
                                }
                            }
                        } catch (Exception inner) {
                            inner.printStackTrace();
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        actualizarVista();
    }

    private void actualizarVista() {

        listModel.clear();

        if (listaCanciones != null) {
            int totalCanciones = listaCanciones.tamanio();
            for (int i = 0; i < totalCanciones; i++) {
                Cancion cancion = listaCanciones.getCancion(i);
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
