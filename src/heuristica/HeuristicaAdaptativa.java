package heuristica;

import cidade.Intersecao;
import semaforo.Semaforo;

public class HeuristicaAdaptativa implements HeuristicaControle {

    private final int tempoMinVerde;     // Tempo mínimo para ficar verde
    private final int tempoMinAmarelo;   // Tempo mínimo para amarelo
    private final int tempoMinVermelho;  // Tempo mínimo para vermelho
    private final int limiarFila;        // Quantidade de carros que ativa prioridade

    public HeuristicaAdaptativa(int tempoMinVerde, int tempoMinAmarelo, int tempoMinVermelho, int limiarFila) {
        this.tempoMinVerde = tempoMinVerde;
        this.tempoMinAmarelo = tempoMinAmarelo;
        this.tempoMinVermelho = tempoMinVermelho;
        this.limiarFila = limiarFila;
    }

    @Override
    public void atualizarSemaforo(Semaforo semaforo, int tempoAtual) {
        semaforo.atualizarTempoNoEstado();
    }

    @Override
    public void ajustarSemaforo(Intersecao intersecao, int tempoAtual) {
        Semaforo semaforo = intersecao.getSemaforo();
        int tamanhoFila = intersecao.getFilaVeiculos().tamanho();

        String estado = semaforo.getEstadoAtual();
        int tempoNoEstado = semaforo.getTempoNoEstado();

        // Lógica adaptativa: ajuste de tempos conforme o tamanho da fila
        switch (estado) {
            case "VERDE":
                if (tempoNoEstado >= tempoMinVerde && tamanhoFila <= limiarFila) {
                    semaforo.setEstado("AMARELO");
                } else if (tempoNoEstado >= tempoMinVerde) {
                    semaforo.setEstado("VERDE"); // Continue verde se a fila for grande
                }
                break;

            case "AMARELO":
                if (tempoNoEstado >= tempoMinAmarelo) {
                    semaforo.setEstado("VERMELHO");
                }
                break;

            case "VERMELHO":
                if (tempoNoEstado >= tempoMinVermelho && tamanhoFila > limiarFila) {
                    semaforo.setEstado("VERDE");
                }
                break;

            default:
                semaforo.setEstado("VERMELHO");
                break;
        }
    }
}
