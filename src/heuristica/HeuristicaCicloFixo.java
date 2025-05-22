package heuristica;

import cidade.Intersecao;
import semaforo.Semaforo;

public class HeuristicaCicloFixo implements HeuristicaControle {
    private int duracaoVerde;     // duração do sinal verde em unidades de tempo
    private int duracaoAmarelo;   // duração do sinal amarelo
    private int duracaoVermelho;  // duração do sinal vermelho

    public HeuristicaCicloFixo(int duracaoVerde, int duracaoAmarelo, int duracaoVermelho) {
        this.duracaoVerde = duracaoVerde;
        this.duracaoAmarelo = duracaoAmarelo;
        this.duracaoVermelho = duracaoVermelho;
    }

    @Override
    public void atualizarSemaforo(Semaforo semaforo, int tempoAtual) {
        int cicloTotal = duracaoVerde + duracaoAmarelo + duracaoVermelho;
        int tempoNoCiclo = tempoAtual % cicloTotal; // tempo dentro do ciclo

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
        // Ajuste do semáforo baseado no tempo atual e durações fixas do ciclo
        Semaforo semaforo = intersecao.getSemaforo();
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
}
