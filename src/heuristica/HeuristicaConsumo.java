package heuristica;

import cidade.Intersecao;
import semaforo.*;


public class HeuristicaConsumo implements HeuristicaControle {
    @Override
    public void atualizarSemaforo(Semaforo semaforo, int tempoAtual) {
        // Exemplo simplificado: alterna o estado do semáforo a cada 5 unidades de tempo
        if (tempoAtual % 5 == 0) {
            semaforo.atualizar(); // Atualiza o estado com base no tempo
        }

        // Consumo de energia durante períodos de tráfego baixo
        if (tempoAtual % 30 == 0) {
            semaforo.setEstado("VERMELHO"); // Reduz o tempo verde em horários de baixa demanda
        }
    }


    @Override
    public void ajustarSemaforo(Intersecao intersecao, int tempoAtual) {

    }
}

