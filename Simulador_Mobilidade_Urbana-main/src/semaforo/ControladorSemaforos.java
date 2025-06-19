package semaforo;

import cidade.Intersecao;
import heuristica.HeuristicaControle;
import estruturas.ArrayList1;

public class ControladorSemaforos {
    private HeuristicaControle heuristica;

    // Construtor que recebe a heurística usada para controle dos semáforos
    public ControladorSemaforos(HeuristicaControle heuristica) {
        this.heuristica = heuristica;
    }

    // Método que atualiza o estado de todos os semáforos nas interseções fornecidas
    public void controlarSemaforos(ArrayList1<Intersecao> intersecoes, int tempoAtual) {
        for (int i = 0; i < intersecoes.tamanho(); i++) {
            Intersecao inter = intersecoes.obter(i);
            Semaforo semaforo = inter.getSemaforo();

            if (semaforo != null) {
                semaforo.atualizar(); // atualiza temporizador interno do semáforo
                heuristica.atualizarSemaforo(semaforo, tempoAtual); // atualiza estado via heurística
                heuristica.ajustarSemaforo(inter, tempoAtual);      // ajusta estado conforme heurística e contexto
            }
        }
    }
}
