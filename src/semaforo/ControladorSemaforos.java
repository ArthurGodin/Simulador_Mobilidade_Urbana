package semaforo;
import heuristica.*;

import cidade.Intersecao;
import heuristica.*;
import java.util.List;

public class ControladorSemaforos {
    private HeuristicaControle heuristica;

    public ControladorSemaforos(HeuristicaControle heuristica) {
        this.heuristica = heuristica;
    }

    public void controlarSemaforos(List<Intersecao> intersecoes, int tempoAtual) {
        for (Intersecao intersecao : intersecoes) {
            heuristica.atualizarSemaforo(intersecao.getSemaforo(), tempoAtual);
        }
    }
}

