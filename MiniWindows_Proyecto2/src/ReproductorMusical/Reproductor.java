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
    private double FRAMES_PER_SECOND_APPROX = 38.0;

    private Runnable onFinished;

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

        JPanel panelCentral = new JPanel();
        panelCentral.setLayout(new BoxLayout(panelCentral, BoxLayout.Y_AXIS));
        panelCentral.setBackground(new Color(40, 40, 40));
        panelCentral.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JPanel panelControles = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        panelControles.setBackground(new Color(40, 40, 40));

        String[] nombres = {"REINICIAR", "DETENER"};
        JButton[] botones = new JButton[2];

        for (int i = 0; i < nombres.length; i++) {

            JButton b = new JButton(nombres[i]);
            b.setBackground(new Color(220, 53, 69));
            b.setForeground(Color.WHITE);
            b.setFocusPainted(false);
            b.setBorderPainted(false);
            b.setFont(new Font("Arial", Font.BOLD, 16));
            b.setPreferredSize(new Dimension(273, 35));
            b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            b.setBorder(BorderFactory.createEmptyBorder());
            b.setContentAreaFilled(false);
            b.setOpaque(true);
            b.setEnabled(false);

            Color base = new Color(220, 53, 69);
            Color hover = base.brighter();

            b.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseEntered(java.awt.event.MouseEvent e) {
                    if (b.isEnabled()) {
                        b.setBackground(hover);
                    }
                }

                @Override
                public void mouseExited(java.awt.event.MouseEvent e) {
                    b.setBackground(base);
                }
            });

            botones[i] = b;
            panelControles.add(b);
        }

        btnPlayPause = botones[0];
        btnStop = botones[1];

        JPanel panelProgreso = new JPanel(new BorderLayout());
        panelProgreso.setBackground(new Color(40, 40, 40));
        panelProgreso.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));

        barraProgreso = new JProgressBar(0, 100);
        barraProgreso.setBackground(new Color(80, 80, 80));
        barraProgreso.setForeground(new Color(29, 185, 84));
        barraProgreso.setBorderPainted(false);
        barraProgreso.setPreferredSize(new Dimension(400, 4));

        lblTiempo = new JLabel("0:00 / 0:00");
        lblTiempo.setForeground(new Color(179, 179, 179));
        lblTiempo.setFont(new Font("Arial", Font.PLAIN, 11));
        lblTiempo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTiempo.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));

        panelProgreso.add(barraProgreso, BorderLayout.CENTER);
        panelProgreso.add(lblTiempo, BorderLayout.SOUTH);

        panelCentral.add(panelControles);
        panelCentral.add(panelProgreso);

        reproductorPanel.add(panelInfo, BorderLayout.WEST);
        reproductorPanel.add(panelCentral, BorderLayout.CENTER);

        btnPlayPause.addActionListener(e -> reiniciar());

        btnStop.addActionListener(e -> {
            if (isPlaying) {
                togglePauseResume();      
            } else if (isPaused) {
                togglePauseResume();      
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
                    terminarPorFinNatural();
                }
            }
        });
    }

    public void cargarCancion(Cancion cancion) {
        if (cancion == null) {
            return;
        }
        detenerRecursos();
        this.cancionActual = cancion;
        this.duracionTotal = cancion.getDuracion();
        this.tiempoTranscurrido = 0;
        this.lastFrame = 0;
        this.userPaused = false;
        lblTitulo.setText(cancion.getTitulo());
        btnPlayPause.setEnabled(true);
        btnStop.setEnabled(true);
        btnPlayPause.setText("REINICIAR");
        btnStop.setText("DETENER");
        actualizar();
    }

    public void reproducir(Cancion cancion) {
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
        // si estaba pausado, reanudamos desde lastFrame
        if (isPaused && lastFrame > 0) {
            userPaused = false;
            isPaused = false;
            startPlayerFromFrame(lastFrame);
            return;
        }
        userPaused = false;
        // inicio desde el principio
        lastFrame = 0;
        tiempoTranscurrido = 0;
        startPlayerFromFrame(0);
    }

    /**
     * Pausa o continua. Importante: antes de cerrar player, guardamos lastFrame
     * a partir de tiempoTranscurrido para asegurarnos de poder reanudar desde
     * el mismo punto.
     */
    public void togglePauseResume() {
        if (isPlaying) {
            // PAUSAR: guardamos posición actual en frames usando tiempoTranscurrido
            userPaused = true;
            // Guardar lastFrame aproximado *antes* de cerrar el player
            lastFrame = secondsToFrames(tiempoTranscurrido);
            // Paramos timer y cerramos player
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
            // streams cerrados en detenerRecursos por seguridad
            closeStreamsSilently();
        } else if (isPaused) {
            // CONTINUAR: reanudar desde lastFrame *sin* resetear tiempoTranscurrido
            userPaused = false;
            isPaused = false;
            startPlayerFromFrame(lastFrame);
            btnStop.setText("DETENER");
        } else {
            // si no estaba ni reproduciendo ni pausado, iniciar
            play();
        }
    }

    public void reiniciar() {
        if (cancionActual == null) {
            return;
        }
        // reset absoluto
        userPaused = false;
        isPaused = false;
        isPlaying = false;
        lastFrame = 0;
        tiempoTranscurrido = 0;
        startPlayerFromFrame(0);
        btnStop.setText("DETENER");
        btnPlayPause.setText("REINICIAR");
    }

    private void startPlayerFromFrame(int startFrame) {
        detenerRecursos();
        try {
            fis = new FileInputStream(cancionActual.getDireccion());
            bis = new BufferedInputStream(fis);
            player = new AdvancedPlayer(bis);

            // sincronizar tiempoTranscurrido con el frame de inicio
            if (startFrame > 0) {
                tiempoTranscurrido = framesToSeconds(startFrame);
                lastFrame = startFrame;
            } else {
                // si arrancamos desde cero, dejamos tiempo en 0
                tiempoTranscurrido = tiempoTranscurrido; // mantiene tiempoTranscurrido si reanudamos desde 0 o reiniciar ya lo puso en 0
            }

            player.setPlayBackListener(new PlaybackListener() {
                @Override
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

                @Override
                public void playbackFinished(PlaybackEvent evt) {
                    // intento de obtener frame desde evento; si es 0 usamos tiempoTranscurrido
                    int frameFromEvt = evt.getFrame();
                    if (frameFromEvt > 0) {
                        lastFrame = frameFromEvt;
                    } else {
                        lastFrame = secondsToFrames(tiempoTranscurrido);
                    }

                    // Si el cierre se produjo por userPaused, no tratamos como fin natural
                    if (userPaused) {
                        // mantener isPaused == true (estado ya puesto por togglePauseResume)
                        return;
                    }

                    // Si no fue pausa del usuario, fin natural
                    SwingUtilities.invokeLater(() -> terminarPorFinNatural());
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
                        detenerRecursos();
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

    private void terminarPorFinNatural() {
        detenerRecursos();
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

    private void detenerRecursos() {
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
        return (int) Math.round(seconds * FRAMES_PER_SECOND_APPROX);
    }

    private int framesToSeconds(int frames) {
        return (int) Math.round(frames / FRAMES_PER_SECOND_APPROX);
    }

    public void limpiar() {
        detenerRecursos();
        cancionActual = null;
        lblTitulo.setText("Selecciona una canción");
        lblTiempo.setText("0:00 / 0:00");
        barraProgreso.setValue(0);
        btnPlayPause.setEnabled(false);
        btnStop.setEnabled(false);
        btnPlayPause.setText("REINICIAR");
    }
}
