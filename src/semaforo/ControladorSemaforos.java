package semaforo;

import cidade.Intersecao;
import heuristica.HeuristicaControle;
import estruturas.ArrayList1;

public class ControladorSemaforos {
    private HeuristicaControle heuristica;

    public ControladorSemaforos(HeuristicaControle heuristica) {
        this.heuristica = heuristica;
    }

    public void controlarSemaforos(ArrayList1<Intersecao> intersecoes, int tempoAtual) {
        for (int i = 0; i < intersecoes.tamanho(); i++) {
            Intersecao inter = intersecoes.obter(i);
            if (inter.getSemaforo() != null) {
                heuristica.atualizarSemaforo(inter.getSemaforo(), tempoAtual);
                heuristica.ajustarSemaforo(inter, tempoAtual);
            }
        }
    }
}
