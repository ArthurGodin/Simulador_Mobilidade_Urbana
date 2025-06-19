package heuristica;

import cidade.Intersecao;
import semaforo.Semaforo;

// Interface para definir o controle de heurísticas dos semáforos
public interface HeuristicaControle {
    // Atualiza o estado do semáforo conforme a heurística e o tempo atual
    void atualizarSemaforo(Semaforo semaforo, int tempoAtual);

    // Ajusta o semáforo com base em características da interseção e tempo atual
    void ajustarSemaforo(Intersecao intersecao, int tempoAtual);
}
