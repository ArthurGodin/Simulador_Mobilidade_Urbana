package heuristica;

import cidade.Intersecao;
import semaforo.Semaforo;

public class HeuristicaTempoEspera implements HeuristicaControle {

    @Override
    public void atualizarSemaforo(Semaforo semaforo, int tempoAtual) {
        // Exemplo simplificado: alterna o estado do semáforo a cada 10 unidades de tempo
        if (tempoAtual % 10 == 0) {
            semaforo.atualizar();
        }
    }

    @Override
    public void ajustarSemaforo(Intersecao intersecao, int tempoAtual) {

    }
}


