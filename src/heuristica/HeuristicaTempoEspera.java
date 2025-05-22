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

        // Ajustar com base na fila e no tempo de espera
        if (tempoAtual % 20 == 0) {
            semaforo.setEstado("VERDE");  // Exemplo de ajuste dinâmico
        }
    }


    @Override
    public void ajustarSemaforo(Intersecao intersecao, int tempoAtual) {

    }
}


