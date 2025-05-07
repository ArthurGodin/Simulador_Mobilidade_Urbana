package heuristica;
import semaforo.*;


public class HeuristicaTempoEspera implements HeuristicaControle {
    @Override
    public void atualizarSemaforo(Semaforo semaforo, int tempoAtual) {
        int tamanhoFila = semaforo.getFila().tamanho();
        if (tamanhoFila > 3) {
            semaforo.setEstado(Semaforo.Estado.VERDE);
        } else if (tamanhoFila > 0) {
            semaforo.setEstado(Semaforo.Estado.AMARELO);
        } else {
            semaforo.setEstado(Semaforo.Estado.VERMELHO);
        }
    }
}

