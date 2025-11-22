package ReproductorMusical;

import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;

/**
 *
 *
 * @author marye
 */
public class Reproductor {

    private JPanel reproductorPanel;
    private Cancion cancionActual;
    private AdvancedPlayer player;
    private Thread reproductorThread;
    private FileInputStream fis;
    private BufferedInputStream bis;

    private boolean isPlaying = false;
    private boolean isPaused = false;

    private int lastFrame = 0;
    private boolean userPaused = false;

    private Runnable onFinished;
    private JLabel lblImgDefault;
    private JLabel lblTitulo;

    private JButton btnPlayPause;
    private JButton btnStop;
    private JProgressBar barraProgreso;
    private JLabel lblTiempo;
    private Timer timerUI;
    private long tiempoTranscurrido = 0;
    private long duracionTotal = 0;

    public Reproductor() {
        reproductorPanel = new JPanel();
        initComponents();
        setupTimer();
    }

    public JPanel getPanel() {
        return reproductorPanel;
    }

    public Cancion getCancionActual() {
        return cancionActual;
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public boolean isPaused() {
        return isPaused;
    }

    public void setOnFinished(Runnable r) {
        this.onFinished = r;
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

        lblTitulo = new JLabel(" ");
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

        String[] nombres = {"REINICIAR", "DETENER"};
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
        
        btnPlayPause.addActionListener(e -> reiniciarCancion());
        btnStop.addActionListener(e -> {
            if (isPlaying) {
                detenerContinuar();
            } else if (isPaused) {
                detenerContinuar();
            } else {
                play();
            }
        });
    }

    private void setupTimer() {
        timerUI = new Timer(1000, e -> {
            if (isPlaying && !isPaused) {
                tiempoTranscurrido++;
                actualizar();
                if (duracionTotal > 0 && tiempoTranscurrido >= duracionTotal) {
                    cancionTerminada();
                }
            }
        });
    }

    public void cargarCancion(Cancion cancion) {
        if (cancion == null) {
            return;
        }
        detenerCancion();
        this.cancionActual = cancion;
        this.duracionTotal = cancion.getDuracion();
        this.tiempoTranscurrido = 0;
        this.lastFrame = 0;
        this.userPaused = false;
        lblTitulo.setText(cancion.getTitulo());

        if (lblImgDefault != null) {
            ImageIcon ic = cancion.getImgDefault();
            lblImgDefault.setIcon(cancion.getImgDefault());
        }

        btnPlayPause.setEnabled(true);
        btnStop.setEnabled(true);
        btnPlayPause.setText("REINICIAR");
        btnStop.setText("DETENER");
        actualizar();
    }

    public void reproducirCancion(Cancion cancion) {
        if (cancion == null) {
            return;
        }
        cargarCancion(cancion);
        play();
    }

    public void play() {
        if (cancionActual == null) {
            return;
        }
        if (isPaused && lastFrame > 0) {
            userPaused = false;
            isPaused = false;
            ejecutarReproduccion(lastFrame);
            return;
        }
        userPaused = false;

        lastFrame = 0;
        tiempoTranscurrido = 0;
        ejecutarReproduccion(0);
    }

    public void detenerContinuar() {
        if (isPlaying) {
            userPaused = true;
            lastFrame = secondsToFrames(tiempoTranscurrido);
            if (timerUI != null) {
                timerUI.stop();
            }
            if (player != null) {
                try {
                    player.close();
                } catch (Exception ignored) {
                }
            }
            if (reproductorThread != null && reproductorThread.isAlive()) {
                reproductorThread.interrupt();
            }
            isPaused = true;
            isPlaying = false;
            btnStop.setText("CONTINUAR");
            btnPlayPause.setText("REINICIAR");
            closeStreamsSilently();
        } else if (isPaused) {
            userPaused = false;
            isPaused = false;
            ejecutarReproduccion(lastFrame);
            btnStop.setText("DETENER");
        } else {
            play();
        }
    }

    public void reiniciarCancion() {
        if (cancionActual == null) {
            return;
        }

        userPaused = false;
        isPaused = false;
        isPlaying = false;
        lastFrame = 0;
        tiempoTranscurrido = 0;
        ejecutarReproduccion(0);
        btnStop.setText("DETENER");
        btnPlayPause.setText("REINICIAR");
    }

    private void ejecutarReproduccion(int startFrame) {
        detenerCancion();
        try {
            fis = new FileInputStream(cancionActual.getDireccion());
            bis = new BufferedInputStream(fis);
            player = new AdvancedPlayer(bis);

            if (startFrame > 0) {
                tiempoTranscurrido = framesToSeconds(startFrame);
                lastFrame = startFrame;
            } else {
                tiempoTranscurrido = tiempoTranscurrido;
            }

            player.setPlayBackListener(new PlaybackListener() {
                public void playbackStarted(PlaybackEvent evt) {
                    isPlaying = true;
                    isPaused = false;
                    SwingUtilities.invokeLater(() -> {
                        btnPlayPause.setText("REINICIAR");
                        btnStop.setText("DETENER");
                        if (timerUI != null) {
                            timerUI.start();
                        }
                        actualizar();
                    });
                }

                public void playbackFinished(PlaybackEvent evt) {
                    int frameFromEvt = evt.getFrame();
                    if (frameFromEvt > 0) {
                        lastFrame = frameFromEvt;
                    } else {
                        lastFrame = secondsToFrames(tiempoTranscurrido);
                    }

                    if (userPaused) {
                        return;
                    }

                    SwingUtilities.invokeLater(() -> cancionTerminada());
                }
            });

            reproductorThread = new Thread(() -> {
                try {
                    if (startFrame > 0) {
                        player.play(startFrame, Integer.MAX_VALUE);
                    } else {
                        player.play();
                    }
                } catch (Exception e) {
                    closeStreamsSilently();
                    SwingUtilities.invokeLater(() -> {
                        JOptionPane.showMessageDialog(reproductorPanel,
                                "Error al reproducir: " + e.getMessage(),
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                        detenerCancion();
                        actualizar();
                    });
                }
            }, "reproductor-thread");
            reproductorThread.start();
        } catch (Exception e) {
            closeStreamsSilently();
            JOptionPane.showMessageDialog(reproductorPanel,
                    "Error al cargar el archivo: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cancionTerminada() {
        detenerCancion();
        isPlaying = false;
        isPaused = false;
        tiempoTranscurrido = 0;
        lastFrame = 0;
        if (timerUI != null) {
            timerUI.stop();
        }
        btnPlayPause.setText("REINICIAR");
        btnStop.setText("DETENER");
        actualizar();

        if (onFinished != null) {
            try {
                onFinished.run();
            } catch (Exception ignored) {
            }
        }
    }

    private void detenerCancion() {
        if (player != null) {
            try {
                player.close();
            } catch (Exception ignored) {
            }
            player = null;
        }
        if (reproductorThread != null && reproductorThread.isAlive()) {
            reproductorThread.interrupt();
            reproductorThread = null;
        }
        closeStreamsSilently();
    }

    private void actualizar() {
        if (duracionTotal > 0) {
            int progreso = (int) ((double) tiempoTranscurrido / duracionTotal * 100);
            barraProgreso.setValue(Math.min(progreso, 100));
        } else {
            barraProgreso.setValue(0);
        }
        String tiempoActual = formatearTiempo((int) tiempoTranscurrido);
        String tiempoTotal = formatearTiempo((int) duracionTotal);
        lblTiempo.setText(tiempoActual + " / " + tiempoTotal);
    }

    private String formatearTiempo(int segundos) {
        int minutos = segundos / 60;
        int segs = segundos % 60;
        return String.format("%d:%02d", minutos, segs);
    }

    private void closeStreamsSilently() {
        try {
            if (bis != null) {
                bis.close();
                bis = null;
            }
        } catch (IOException ignored) {
        }
        try {
            if (fis != null) {
                fis.close();
                fis = null;
            }
        } catch (IOException ignored) {
        }
    }

    private int secondsToFrames(long seconds) {
        return (int) Math.round(seconds * 38.0);
    }

    private int framesToSeconds(int frames) {
        return (int) Math.round(frames / 38.0);
    }

    public void limpiar() {
        detenerCancion();
        cancionActual = null;
        lblTitulo.setText("Selecciona una canción");
        lblTiempo.setText("0:00 / 0:00");
        barraProgreso.setValue(0);
        btnPlayPause.setEnabled(false);
        btnStop.setEnabled(false);
        btnPlayPause.setText("REINICIAR");

        if (lblImgDefault != null) {
            lblImgDefault.setIcon(null);
        }
    }
}
