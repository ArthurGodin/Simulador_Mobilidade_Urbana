package heuristica;

import cidade.Intersecao;
import semaforo.Semaforo;

public class HeuristicaConsumo implements HeuristicaControle {

    @Override
    public void atualizarSemaforo(Semaforo semaforo, int tempoAtual) {
        // A cada 5 unidades de tempo, atualiza o estado do semáforo
        if (tempoAtual % 5 == 0) {
            semaforo.atualizar();
        }

        // A cada 30 unidades de tempo, força o semáforo para vermelho
        // simulando redução de consumo em períodos de baixo tráfego
        if (tempoAtual % 30 == 0) {
            semaforo.setEstado("VERMELHO");
        }
    }

    @Override
    public void ajustarSemaforo(Intersecao intersecao, int tempoAtual) {
        // Não implementado para esta heurística
    }
}
