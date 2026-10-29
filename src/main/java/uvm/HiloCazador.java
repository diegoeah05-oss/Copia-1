package uvm;

import javax.swing.*;
import java.awt.*;

public class HiloCazador extends Thread {
    
    private char[][] matrizDatos;
    private JLabel[][] etiquetasMatriz;
    private JLabel lblContador;
    private int contadorSimbolos;

    public HiloCazador(char[][] matrizDatos, JLabel[][] etiquetasMatriz, JLabel lblContador) {
        this.matrizDatos = matrizDatos;
        this.etiquetasMatriz = etiquetasMatriz;
        this.lblContador = lblContador;
        this.contadorSimbolos = 0;
    }

    @Override
    public void run() {
        int filas = matrizDatos.length;
        int columnas = matrizDatos[0].length;

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                
                if (matrizDatos[i][j] == '@') {
                    etiquetasMatriz[i][j].setForeground(Color.RED);
                    
                    try {
                        etiquetasMatriz[i][j].setVisible(false);
                        Thread.sleep(500); 
                        etiquetasMatriz[i][j].setVisible(true);
                        Thread.sleep(500); 
                    } catch (InterruptedException e) {
                        System.out.println("Error en el hilo: " + e.getMessage());
                    }

                    matrizDatos[i][j] = 'X';
                    etiquetasMatriz[i][j].setText("X");
                    etiquetasMatriz[i][j].setForeground(Color.BLACK);

                    contadorSimbolos++;
                    lblContador.setText("Símbolos '@' encontrados: " + contadorSimbolos);
                }

                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    System.out.println("Error en la pausa de escaneo: " + e.getMessage());
                }
            }
        }
    }

    public static void main(String[] args) {
        char[][] matriz = {
            {'.', '@', '.', '.'},
            {'.', '.', '@', '.'},
            {'@', '.', '.', '.'},
            {'.', '.', '.', '@'}
        };

        int filas = matriz.length;
        int columnas = matriz[0].length;

        JFrame ventana = new JFrame("Cazador de @");
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setSize(400, 450);
        ventana.setLayout(new BorderLayout());

        JLabel lblContador = new JLabel("Símbolos '@' encontrados: 0", SwingConstants.CENTER);
        lblContador.setFont(new Font("Arial", Font.BOLD, 16));
        ventana.add(lblContador, BorderLayout.NORTH);

        JPanel panelMatriz = new JPanel();
        panelMatriz.setLayout(new GridLayout(filas, columnas));

        JLabel[][] etiquetas = new JLabel[filas][columnas];

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                etiquetas[i][j] = new JLabel(String.valueOf(matriz[i][j]), SwingConstants.CENTER);
                etiquetas[i][j].setFont(new Font("Arial", Font.BOLD, 26));
                panelMatriz.add(etiquetas[i][j]);
            }
        }

        ventana.add(panelMatriz, BorderLayout.CENTER);
        ventana.setLocationRelativeTo(null); 
        ventana.setVisible(true);

        HiloCazador hilo = new HiloCazador(matriz, etiquetas, lblContador);
        hilo.start();
    }
}
