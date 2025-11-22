package CMD;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;

/**
 *
 * @author marye
 */
public class GUICMD {

    private CMD consola = new CMD();
    private int posPrompt;
    private JTextArea txtArea = new JTextArea();

    public GUICMD() {
        initComponents();
    }

    public void initComponents() {
        JFrame ventana = new JFrame("Administrador: Commando Prompt");
        ventana.setSize(820, 520);
        ventana.setLocationRelativeTo(null);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setLayout(new BorderLayout());

        txtArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));
        txtArea.setBackground(Color.BLACK);
        txtArea.setForeground(Color.WHITE);
        txtArea.setCaretColor(Color.WHITE);
        txtArea.setLineWrap(false);

        JScrollPane scroll = new JScrollPane(txtArea);
        ventana.add(scroll, BorderLayout.CENTER);

        imprimirLinea("Microsoft Windows [Version 10.0.22621.521]");
        imprimirLinea("(c) Microsoft Corporation. All rights reserved.");
        imprimirLinea("");
        imprimirPrompt();

        ((AbstractDocument) txtArea.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void remove(FilterBypass fb, int off, int len) throws BadLocationException {
                if (off < posPrompt) {
                    return;
                }
                super.remove(fb, off, len);
            }

            @Override
            public void replace(FilterBypass fb, int off, int len, String text, AttributeSet attrs)
                    throws BadLocationException {
                if (off < posPrompt) {
                    return;
                }
                super.replace(fb, off, len, text, attrs);
            }

            @Override
            public void insertString(FilterBypass fb, int off, String str, AttributeSet attr)
                    throws BadLocationException {
                if (off < posPrompt) {
                    txtArea.setCaretPosition(txtArea.getDocument().getLength());
                    return;
                }
                super.insertString(fb, off, str, attr);
            }
        });

        txtArea.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_HOME) {
                    txtArea.setCaretPosition(posPrompt);
                    e.consume();
                } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    e.consume();
                    ejecutarComando();
                } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    if (txtArea.getCaretPosition() <= posPrompt) {
                        e.consume();
                        txtArea.setCaretPosition(posPrompt);
                    }
                }
            }
        });

        ventana.setVisible(true);
    }

    private void imprimirLinea(String linea) {
        txtArea.append(linea + "\n");
    }

    private void imprimirPrompt() {
        txtArea.append(consola.getPrompt());
        posPrompt = txtArea.getDocument().getLength();
        txtArea.setCaretPosition(posPrompt);
    }

    private void ejecutarComando() {
        try {
            Document doc = txtArea.getDocument();
            String linea = doc.getText(posPrompt, doc.getLength() - posPrompt).trim();

            txtArea.append("\n");
            if (linea.isEmpty()) {
                imprimirPrompt();
                return;
            }

            String salida = consola.Ejecutar(linea);
            if (salida != null && !salida.isEmpty()) {
                imprimirLinea(salida);
            }

            imprimirPrompt();
        } catch (BadLocationException ex) {
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new GUICMD(); 
        });   
    }
}
