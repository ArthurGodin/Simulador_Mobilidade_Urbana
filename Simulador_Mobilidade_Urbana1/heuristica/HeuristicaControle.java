package heuristica;

import cidade.Intersecao;
import semaforo.Semaforo;

public interface HeuristicaControle {
    void atualizarSemaforo(Semaforo semaforo, int tempoAtual);

    void ajustarSemaforo(Intersecao intersecao, int tempoAtual);
}
