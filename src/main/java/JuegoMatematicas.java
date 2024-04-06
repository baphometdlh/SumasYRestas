import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

public class JuegoMatematicas extends JFrame {
    private Random random = new Random();
    private int num1, num2, resultadoCorrecto;
    private String operador;

    private int intentosRestantes = 3;
    private int aciertos = 0;
    private int fallos = 0;
    private StringBuilder historial = new StringBuilder();

    private JLabel labelOperacion;
    private JTextField textFieldRespuesta;

    public JuegoMatematicas() {
        setTitle("Juego de Matemáticas");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout());

        // Panel superior con altura 10 sin contenido
        JPanel panelSuperior = new JPanel();
        panelSuperior.setPreferredSize(new Dimension(10, 10));
        getContentPane().add(panelSuperior, BorderLayout.NORTH);

        labelOperacion = new JLabel();
        labelOperacion.setHorizontalAlignment(SwingConstants.LEFT);
        labelOperacion.setVerticalAlignment(SwingConstants.TOP);
        labelOperacion.setFont(new Font("Courier New", Font.BOLD, 26));

        textFieldRespuesta = new JTextField(10); // Se establece un tamaño inicial
        textFieldRespuesta.setFont(new Font("Arial", Font.PLAIN, 20));

        JButton btnEmpezar = new JButton("Empezar");
        btnEmpezar.setFont(new Font("Arial", Font.PLAIN, 20));
        btnEmpezar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                GenerarOperacion();
                textFieldRespuesta.setText("");
                textFieldRespuesta.requestFocus();
            }
        });

        JButton btnFinalizar = new JButton("Finalizar");
        btnFinalizar.setFont(new Font("Arial", Font.PLAIN, 20));
        btnFinalizar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MostrarResumen();
            }
        });

        textFieldRespuesta.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                VerificarRespuesta();
            }
        });

        JPanel panelOperacion = new JPanel();
        panelOperacion.add(labelOperacion);

        JPanel panelRespuesta = new JPanel();
        panelRespuesta.add(textFieldRespuesta);

        JPanel panelCentral = new JPanel(new GridLayout(2, 1));
        panelCentral.add(panelOperacion);
        panelCentral.add(panelRespuesta);

        JPanel panelInferior = new JPanel();
        panelInferior.add(btnEmpezar);
        panelInferior.add(btnFinalizar);

        getContentPane().add(panelCentral, BorderLayout.CENTER);
        getContentPane().add(panelInferior, BorderLayout.SOUTH);

        // Centrar ventana en la pantalla
        setLocationRelativeTo(null);
    }

    private void GenerarOperacion() {
        num1 = random.nextInt(100) + 1;
        num2 = random.nextInt(100) + 1;
        operador = random.nextBoolean() ? "+" : "-";
        if (operador.equals("-")) {
            // Asegurar que la resta no dé negativo
            if (num1 < num2) {
                int temp = num1;
                num1 = num2;
                num2 = temp;
            }
        }

        resultadoCorrecto = operador.equals("+") ? num1 + num2 : num1 - num2;

        labelOperacion.setText(num1 + "\n " + operador + " " + num2);
    }

    private void VerificarRespuesta() {
        int respuesta;
        try {
            respuesta = Integer.parseInt(textFieldRespuesta.getText());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Por favor, introduce un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (respuesta == resultadoCorrecto) {
            aciertos++;
            historial.append(num1).append(" ").append(operador).append(" ").append(num2).append(" = ").append(respuesta).append(" (Correcta)\n");
            GenerarOperacion();
            intentosRestantes = 3;
        } else {
            fallos++;
            intentosRestantes--;
            historial.append(num1).append(" ").append(operador).append(" ").append(num2).append(" = ").append(respuesta).append(" (Incorrecta)\n");
            if (intentosRestantes == 0) {
                MostrarRespuestaCorrecta();
                GenerarOperacion();
                intentosRestantes = 3;
            } else {
                JOptionPane.showMessageDialog(this, "Respuesta incorrecta. Te quedan " + intentosRestantes + " intentos.", "Incorrecto", JOptionPane.WARNING_MESSAGE);
            }
        }
        textFieldRespuesta.setText("");
    }

    private void MostrarRespuestaCorrecta() {
        JOptionPane.showMessageDialog(this, "La respuesta correcta es: " + resultadoCorrecto, "Respuesta Correcta", JOptionPane.INFORMATION_MESSAGE);
    }

    private void MostrarResumen() {
        JOptionPane.showMessageDialog(this, 
            "Resumen:\n\n" +
            "Aciertos: " + aciertos + "\n" +
            "Fallos: " + fallos + "\n\n" +
            "Historial:\n" + historial.toString()
        );
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JuegoMatematicas juego = new JuegoMatematicas();
                juego.setVisible(true);
            }
        });
    }
}
