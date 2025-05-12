package semaforo;
import heuristica.*;
import estruturas.*;
import cidade.Intersecao;
import heuristica.*;


public class ControladorSemaforos {
    private HeuristicaControle heuristica;

    public ControladorSemaforos(HeuristicaControle heuristica) {
        this.heuristica = heuristica;
    }

    public void controlarSemaforos(Lista<Intersecao> intersecoes, int tempoAtual) {
        for (Intersecao intersecao : intersecoes) {
            heuristica.atualizarSemaforo(intersecao.getSemaforo(), tempoAtual);
        }
    }
}

