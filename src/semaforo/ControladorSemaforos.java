package semaforo;
import heuristica.*;

public class ControladorSemaforos {
    private HeuristicaControle heuristica;

    public ControladorSemaforos(ModoOperacao modo) {
        switch (modo) {
            case CICLO_FIXO:
                this.heuristica = new HeuristicaCicloFixo(10, 3, 7);
                break;
            case TEMPO_ESPERA:
                this.heuristica = new HeuristicaTempoEspera();
                break;
            case CONSUMO:
                this.heuristica = new HeuristicaConsumo();
                break;
        }
    }

    public void atualizarSemaforo(Semaforo s, int tempoAtual) {
        heuristica.atualizarSemaforo(s, tempoAtual);
    }
}

