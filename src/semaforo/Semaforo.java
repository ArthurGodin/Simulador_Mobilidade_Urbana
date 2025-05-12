package semaforo;
import trafego.*;
import estruturas.*;

public class Semaforo {
    private int tempoVerde;
    private int tempoAmarelo;
    private int tempoVermelho;
    private int tempoAtual;
    private String estadoAtual; // "VERDE", "AMARELO", "VERMELHO"

    public Semaforo(int tempoVerde, int tempoAmarelo, int tempoVermelho) {
        this.tempoVerde = tempoVerde;
        this.tempoAmarelo = tempoAmarelo;
        this.tempoVermelho = tempoVermelho;
        this.tempoAtual = 0;
        this.estadoAtual = "VERDE";
    }


    public void atualizar() {
        tempoAtual++;
        switch (estadoAtual) {
            case "VERDE":
                if (tempoAtual >= tempoVerde) {
                    estadoAtual = "AMARELO";
                    tempoAtual = 0;
                }
                break;
            case "AMARELO":
                if (tempoAtual >= tempoAmarelo) {
                    estadoAtual = "VERMELHO";
                    tempoAtual = 0;
                }
                break;
            case "VERMELHO":
                if (tempoAtual >= tempoVermelho) {
                    estadoAtual = "VERDE";
                    tempoAtual = 0;
                }
                break;
        }
    }

    public String getEstadoAtual() {
        return estadoAtual;
    }

    public void setTempos(int verde, int amarelo, int vermelho) {
        this.tempoVerde = verde;
        this.tempoAmarelo = amarelo;
        this.tempoVermelho = vermelho;
    }

    public void setEstado(String estado) {
        this.estadoAtual = estado;
    }

}

