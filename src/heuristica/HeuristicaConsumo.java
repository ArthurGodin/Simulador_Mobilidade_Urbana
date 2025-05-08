package heuristica;

import cidade.Intersecao;
import heuristica.HeuristicaControle;
import semaforo.*;
import estruturas.*;

public class HeuristicaConsumo implements HeuristicaControle {
    @Override
    public void atualizarSemaforo(Semaforo semaforo, int tempoAtual) {
        // Exemplo simplificado: alterna o estado do semáforo a cada 15 unidades de tempo
        if (tempoAtual % 15 == 0) {
            semaforo.atualizar();
        }
    }

    @Override
    public void ajustarSemaforo(Intersecao intersecao, int tempoAtual) {

    }
}

