package heuristica;

import cidade.Intersecao;
import semaforo.Semaforo;

public class HeuristicaAdaptativa implements HeuristicaControle {

    private final int tempoMinVerde;     // Tempo mínimo para ficar verde
    private final int tempoMinAmarelo;   // Tempo mínimo para amarelo
    private final int tempoMinVermelho;  // Tempo mínimo para vermelho
    private final int limiarFila;        // Quantidade de carros que ativa prioridade

    // Para controlar quanto tempo o semáforo está em um estado
    private int tempoNoEstado = 0;
    private String estadoAnterior = "";

    public HeuristicaAdaptativa(int tempoMinVerde, int tempoMinAmarelo, int tempoMinVermelho, int limiarFila) {
        this.tempoMinVerde = tempoMinVerde;
        this.tempoMinAmarelo = tempoMinAmarelo;
        this.tempoMinVermelho = tempoMinVermelho;
        this.limiarFila = limiarFila;
    }

    @Override
    public void atualizarSemaforo(Semaforo semaforo, int tempoAtual) {
        // Atualiza temporizador para controle de tempo mínimo no estado
        if (!semaforo.getEstadoAtual().equals(estadoAnterior)) {
            // Mudou de estado, zera contador
            tempoNoEstado = 0;
            estadoAnterior = semaforo.getEstadoAtual();
        } else {
            tempoNoEstado++;
        }
    }

    @Override
    public void ajustarSemaforo(Intersecao intersecao, int tempoAtual) {
        Semaforo semaforo = intersecao.getSemaforo();
        int tamanhoFila = intersecao.getFilaVeiculos().tamanho();

        String estado = semaforo.getEstadoAtual();

        switch (estado) {
            case "VERDE":
                // Se o tempo mínimo verde passou e a fila está pequena, mudar para amarelo
                if (tempoNoEstado >= tempoMinVerde && tamanhoFila <= limiarFila) {
                    semaforo.setEstado("AMARELO");
                }
                // Se fila grande mantém verde
                break;

            case "AMARELO":
                // Se tempo mínimo amarelo passou, vai para vermelho
                if (tempoNoEstado >= tempoMinAmarelo) {
                    semaforo.setEstado("VERMELHO");
                }
                break;

            case "VERMELHO":
                // Se tempo mínimo vermelho passou e fila está grande, muda para verde
                if (tempoNoEstado >= tempoMinVermelho && tamanhoFila > limiarFila) {
                    semaforo.setEstado("VERDE");
                }
                break;

            default:
                // Caso estado inválido, seta vermelho para segurança
                semaforo.setEstado("VERMELHO");
                break;
        }
    }
}
