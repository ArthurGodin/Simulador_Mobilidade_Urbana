package heuristica;

import cidade.Intersecao;
import semaforo.Semaforo;

public class HeuristicaAdaptativa implements HeuristicaControle {

    private final int tempoMinVerde;     // Tempo mínimo do semáforo verde
    private final int tempoMinAmarelo;   // Tempo mínimo do semáforo amarelo
    private final int tempoMinVermelho;  // Tempo mínimo do semáforo vermelho
    private final int limiarFila;        // Quantidade mínima de veículos para priorizar o verde

    public HeuristicaAdaptativa(int tempoMinVerde, int tempoMinAmarelo, int tempoMinVermelho, int limiarFila) {
        this.tempoMinVerde = tempoMinVerde;
        this.tempoMinAmarelo = tempoMinAmarelo;
        this.tempoMinVermelho = tempoMinVermelho;
        this.limiarFila = limiarFila;
    }

    @Override
    public void atualizarSemaforo(Semaforo semaforo, int tempoAtual) {
        // Atualiza o tempo que o semáforo está no estado atual
        semaforo.atualizarTempoNoEstado();
    }

    @Override
    public void ajustarSemaforo(Intersecao intersecao, int tempoAtual) {
        Semaforo semaforo = intersecao.getSemaforo();
        int tamanhoFila = intersecao.getFilaVeiculos().tamanho();

        String estado = semaforo.getEstadoAtual();
        int tempoNoEstado = semaforo.getTempoNoEstado();

        // Decide se deve mudar o estado do semáforo baseado no tempo e tamanho da fila
        switch (estado) {
            case "VERDE":
                if (tempoNoEstado >= tempoMinVerde && tamanhoFila <= limiarFila) {
                    semaforo.setEstado("AMARELO");  // Muda para amarelo se tempo mínimo passou e fila pequena
                } else if (tempoNoEstado >= tempoMinVerde) {
                    semaforo.setEstado("VERDE");   // Mantém verde se fila é grande
                }
                break;

            case "AMARELO":
                if (tempoNoEstado >= tempoMinAmarelo) {
                    semaforo.setEstado("VERMELHO"); // Vai para vermelho após tempo mínimo amarelo
                }
                break;

            case "VERMELHO":
                if (tempoNoEstado >= tempoMinVermelho && tamanhoFila > limiarFila) {
                    semaforo.setEstado("VERDE");    // Volta para verde se fila grande e tempo mínimo vermelho passou
                }
                break;

            default:
                // Caso algum estado inválido apareça, seta para vermelho por segurança
                semaforo.setEstado("VERMELHO");
                break;
        }
    }
}
