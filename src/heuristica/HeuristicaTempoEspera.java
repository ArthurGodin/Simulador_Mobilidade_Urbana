package heuristica;

import cidade.Intersecao;
import semaforo.Semaforo;

// Implementação de heurística que altera semáforo baseada no tempo de espera
public class HeuristicaTempoEspera implements HeuristicaControle {

    @Override
    public void atualizarSemaforo(Semaforo semaforo, int tempoAtual) {
        // A cada 10 unidades de tempo, atualiza o estado do semáforo
        if (tempoAtual % 10 == 0) {
            semaforo.atualizar();
        }

        // A cada 20 unidades de tempo, força o semáforo para verde (exemplo simples)
        if (tempoAtual % 20 == 0) {
            semaforo.setEstado("VERDE");
        }
    }

    @Override
    public void ajustarSemaforo(Intersecao intersecao, int tempoAtual) {
        // Método ainda não implementado
    }
}
