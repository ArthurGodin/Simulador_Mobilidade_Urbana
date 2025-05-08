package heuristica;

import cidade.Intersecao;
import heuristica.*;
import semaforo.*;

public class HeuristicaCicloFixo implements HeuristicaControle {
    private int duracaoVerde;
    private int duracaoAmarelo;
    private int duracaoVermelho;

    public HeuristicaCicloFixo(int duracaoVerde, int duracaoAmarelo, int duracaoVermelho) {
        this.duracaoVerde = duracaoVerde;
        this.duracaoAmarelo = duracaoAmarelo;
        this.duracaoVermelho = duracaoVermelho;
    }

    @Override
    public void ajustarSemaforo(Intersecao intersecao, int tempoAtual) {
        Semaforo semaforo = intersecao.getSemaforo();
        int cicloTotal = duracaoVerde + duracaoAmarelo + duracaoVermelho;
        int tempoNoCiclo = tempoAtual % cicloTotal;

        if (tempoNoCiclo < duracaoVerde) {
            semaforo.setEstado(Semaforo.Estado.VERDE);
        } else if (tempoNoCiclo < duracaoVerde + duracaoAmarelo) {
            semaforo.setEstado(Semaforo.Estado.AMARELO);
        } else {
            semaforo.setEstado(Semaforo.Estado.VERMELHO);
        }
    }
}
