package ReproductorMusical;

import javax.swing.*;
import java.awt.*;

/**
 *
 *
 * @author marye
 */
public class Reproductor {

    private JPanel reproductorPanel;
    private Cancion cancionActual;

    private JLabel lblImgDefault;
    private JLabel lblTitulo;

    private JButton btnPlayPause;
    private JButton btnStop;
    private JProgressBar barraProgreso;
    private JLabel lblTiempo;

    public Reproductor() {
        reproductorPanel = new JPanel();
        initComponents();
    }

    public JPanel getPanel() {
        return reproductorPanel;
    }

    public Cancion getCancionActual() {
        return cancionActual;
    }

    public void setOnFinished(Runnable r) {
    }

    private void initComponents() {
        reproductorPanel.setLayout(new BorderLayout());
        reproductorPanel.setBackground(new Color(40, 40, 40));
        reproductorPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(80, 80, 80)));
        reproductorPanel.setPreferredSize(new Dimension(0, 110));

        JPanel panelInfo = new JPanel(new BorderLayout());
        panelInfo.setBackground(new Color(40, 40, 40));
        panelInfo.setPreferredSize(new Dimension(280, 0));

        JPanel panelTexto = new JPanel(new GridLayout(1, 1));
        panelTexto.setBackground(new Color(40, 40, 40));
        panelTexto.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));

        lblTitulo = new JLabel("Selecciona una canción");
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 14));
        panelTexto.add(lblTitulo);
        panelInfo.add(panelTexto, BorderLayout.CENTER);

        lblImgDefault = new JLabel();
        lblImgDefault.setPreferredSize(new Dimension(100, 100));
        lblImgDefault.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        lblImgDefault.setHorizontalAlignment(SwingConstants.CENTER);
        lblImgDefault.setVerticalAlignment(SwingConstants.CENTER);

        JPanel panelRight = new JPanel();
        panelRight.setLayout(new BoxLayout(panelRight, BoxLayout.Y_AXIS));
        panelRight.setBackground(new Color(40, 40, 40));
        panelRight.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel panelProgreso = new JPanel(new BorderLayout());
        panelProgreso.setBackground(new Color(40, 40, 40));
        panelProgreso.setBorder(BorderFactory.createEmptyBorder(5, 0, 10, 0));
        barraProgreso = new JProgressBar(0, 100);

        barraProgreso.setBackground(new Color(80, 80, 80));
        barraProgreso.setForeground(new Color(29, 185, 84));
        barraProgreso.setBorderPainted(false);
        barraProgreso.setPreferredSize(new Dimension(420, 12));
        barraProgreso.setMaximumSize(new Dimension(420, 12));

        lblTiempo = new JLabel("0:00 / 0:00");
        lblTiempo.setForeground(new Color(179, 179, 179));
        lblTiempo.setFont(new Font("Arial", Font.PLAIN, 11));
        lblTiempo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTiempo.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
        panelProgreso.add(barraProgreso, BorderLayout.CENTER);
        panelProgreso.add(lblTiempo, BorderLayout.SOUTH);

        JPanel panelControles = new JPanel(new GridLayout(1, 2, 10, 0));
        panelControles.setBackground(new Color(40, 40, 40));
        panelControles.setPreferredSize(new Dimension(420, 40));
        panelControles.setMaximumSize(new Dimension(420, 40));
        panelControles.setAlignmentX(Component.CENTER_ALIGNMENT);

        String[] nombres = {"REPRODUCIR", "DETENER"};
        JButton[] botones = new JButton[2];
        for (int i = 0; i < nombres.length; i++) {
            JButton b = new JButton(nombres[i]);
            b.setBackground(new Color(220, 53, 69));
            b.setForeground(Color.WHITE);
            b.setFocusPainted(false);
            b.setBorderPainted(false);
            b.setFont(new Font("Arial", Font.BOLD, 16));
            b.setPreferredSize(new Dimension(205, 36));
            b.setMinimumSize(new Dimension(205, 36));
            b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            b.setBorder(BorderFactory.createEmptyBorder());
            b.setContentAreaFilled(false);
            b.setOpaque(true);
            b.setEnabled(false);
            b.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseEntered(java.awt.event.MouseEvent e) {
                    if (b.isEnabled()) {
                        b.setBackground(new Color(220, 53, 69).brighter());
                    }
                }

                @Override
                public void mouseExited(java.awt.event.MouseEvent e) {
                    b.setBackground(new Color(220, 53, 69));
                }
            });
            botones[i] = b;
            panelControles.add(b);
        }
        btnPlayPause = botones[0];
        btnStop = botones[1];

        panelRight.add(panelProgreso);
        panelRight.add(Box.createVerticalStrut(8));
        panelRight.add(panelControles);

        reproductorPanel.add(lblImgDefault, BorderLayout.WEST);
        reproductorPanel.add(panelInfo, BorderLayout.CENTER);
        reproductorPanel.add(panelRight, BorderLayout.EAST);
        
        btnPlayPause.addActionListener(e -> play());
        btnStop.addActionListener(e -> stop());
    }

    public void cargarCancion(Cancion cancion) {
        if (cancion == null) {
            return;
        }
        this.cancionActual = cancion;
        lblTitulo.setText(cancion.getTitulo());

        if (lblImgDefault != null) {
            lblImgDefault.setIcon(cancion.getImgDefault());
        }

        btnPlayPause.setEnabled(true);
        btnStop.setEnabled(true);
        btnPlayPause.setText("REPRODUCIR");
        btnStop.setText("DETENER");
    }

    public void play() {
        if (cancionActual == null) {
            return;
        }
        
        JOptionPane.showMessageDialog(reproductorPanel,
            "Reproduciendo: " + cancionActual.getTitulo() + "\n" +
            "Archivo: " + cancionActual.getDireccion() + "\n\n" +
            "Nota: La reproducción real de audio requiere librerías adicionales.",
            "Reproductor",
            JOptionPane.INFORMATION_MESSAGE);
    }

    public void stop() {
        lblTitulo.setText("Detenido");
    }

    public void limpiar() {
        cancionActual = null;
        lblTitulo.setText("Selecciona una canción");
        lblTiempo.setText("0:00 / 0:00");
        barraProgreso.setValue(0);
        btnPlayPause.setEnabled(false);
        btnStop.setEnabled(false);
        btnPlayPause.setText("REPRODUCIR");

        if (lblImgDefault != null) {
            lblImgDefault.setIcon(null);
        }
    }
}
