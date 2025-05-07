package heuristica;
import semaforo.*;

public class HeuristicaConsumo implements HeuristicaControle {
    @Override
    public void atualizarSemaforo(Semaforo semaforo, int tempoAtual) {
        if (tempoAtual % 30 < 20) { // Ex: "evitar paradas" em horário de pico
            semaforo.setEstado(Semaforo.Estado.VERDE);
        } else {
            semaforo.setEstado(Semaforo.Estado.VERMELHO);
        }
    }
}

