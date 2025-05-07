package heuristica;
import semaforo.*;

public class HeuristicaCicloFixo implements HeuristicaControle {
    private int tempoVerde, tempoAmarelo, tempoVermelho;

    public HeuristicaCicloFixo(int verde, int amarelo, int vermelho) {
        this.tempoVerde = verde;
        this.tempoAmarelo = amarelo;
        this.tempoVermelho = vermelho;
    }

    @Override
    public void atualizarSemaforo(Semaforo semaforo, int tempoAtual) {
        int ciclo = tempoVerde + tempoAmarelo + tempoVermelho;
        int tempoNoCiclo = tempoAtual % ciclo;

        if (tempoNoCiclo < tempoVerde) {
            semaforo.setEstado(Semaforo.Estado.VERDE);
        } else if (tempoNoCiclo < tempoVerde + tempoAmarelo) {
            semaforo.setEstado(Semaforo.Estado.AMARELO);
        } else {
            semaforo.setEstado(Semaforo.Estado.VERMELHO);
        }
    }
}

