package uvm;
import java.awt.Color;

public class HiloRebotador extends Thread {
    private VentanaMatrizHilos ventana;
    private volatile boolean pausado= false;
    private volatile boolean corriendo = true;
    private int velocidad = 300;
}

public HiloRebotador(VentanaMatrizHilos ventana) {
    this.ventana = ventana;
}
public synchronized void pausarHilo() {
    pausado = true;
}
public void reanudarHilo() {
    pausado = false;
    notifyAll();
}
public void terminarHilo() {
    corriendo=false;
    reanudarHilo();
}

private synchronized void verificarPausa() {
    while (pausado && corriendo) {
        try {
            wait();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

@Override
    public void run() {
    int filas = ventana.getFilas();
    int columnas = ventana.getColumnas();

    int x = 0;
    int y = 0;
    int dirX = 1;
    int dirY = 1;

    if (filas <= 0 || columnas <= 0) return;

    while (corriendo) {
        verificarPausa();
        if(!corriendo)break;

        int sigX = x + dirX;
        int sigY = y + dirY

        if (sigX < 0 || sigX >= filas) {
            dirX *= -1;
            sigX = x + dirX;
        }

        if (sigY < 0 || sigY >= columnas) {
            dirY *= -1;
            sigY = y + dirY;
        }

    x = sigX;
    y = sigY;

    ventana.actualizarCelda(x, y, "@", Color.RED);

    try {
        Thread.sleep(velocidad);
    } catch (InterruptedException e) {
        break;
    }
   }
}
}
