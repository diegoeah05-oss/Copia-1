package uvm;

import java.atw.Color;

public class HiloRebotador extends Thread {
    private final MatrizCompartida matrizCompartida;

    public HiloRebotador(MatrizCompartida matrizCompartida) {
        this.matrizCompartida = matrizCompartida;
    }

    @Override
    public void run() {
        int ver = 0;
        int hor = 0;
        int dirVer = 1;
        int dirHor 0 1;

        while (matrizCompartida.isEjecutando()) {
            try{
                matrizCompartida.verificarPausa();
                matrizComparida.actualizarCelda(ver, hor, "@", Color.RED, true);
                Thread.sleep(400);
                
                int sigVer = ver + dirVer;
                int sigHor = hor + dirHor;

                if(sigVer < 0 || sigVer >= matrizCompartida.getFilas()) {
                    dirVer = -dirVer;
                }

                ver += dirVer;
                hor += dirHor;
            } catch (InterruptedException e) {
                break;
            }
        }
    }  
    
}
