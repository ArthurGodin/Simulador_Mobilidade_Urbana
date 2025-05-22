package heuristica;

import cidade.Intersecao;
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
    public void atualizarSemaforo(Semaforo semaforo, int tempoAtual) {
        int cicloTotal = duracaoVerde + duracaoAmarelo + duracaoVermelho;
        int tempoNoCiclo = tempoAtual % cicloTotal;

        if (tempoNoCiclo < duracaoVerde) {
            semaforo.setEstado("VERDE");
        } else if (tempoNoCiclo < duracaoVerde + duracaoAmarelo) {
            semaforo.setEstado("AMARELO");
        } else {
            semaforo.setEstado("VERMELHO");
        }
    }


    @Override
    public void ajustarSemaforo(Intersecao intersecao, int tempoAtual) {
        Semaforo semaforo = intersecao.getSemaforo();
        int cicloTotal = duracaoVerde + duracaoAmarelo + duracaoVermelho;
        int tempoNoCiclo = tempoAtual % cicloTotal;

        // Ciclo fixo com tempo ajustado para maior eficiência dependendo do fluxo
        if (tempoNoCiclo < duracaoVerde) {
            semaforo.setEstado("VERDE");
        } else if (tempoNoCiclo < duracaoVerde + duracaoAmarelo) {
            semaforo.setEstado("AMARELO");
        } else {
            semaforo.setEstado("VERMELHO");
        }
    }
}
