package semaforo;

public class Semaforo {
    private int tempoVerde;
    private int tempoAmarelo;
    private int tempoVermelho;
    private int tempoAtual;         // contador de tempo no estado atual
    private String estadoAtual;     // "VERDE", "AMARELO", "VERMELHO"

    private int tempoNoEstado = 0;  // tempo que está no estado atual, usado para heurísticas
    private String estadoAnterior = "VERDE";

    // Construtor inicializa os tempos e define estado inicial como VERDE
    public Semaforo(int tempoVerde, int tempoAmarelo, int tempoVermelho) {
        this.tempoVerde = tempoVerde;
        this.tempoAmarelo = tempoAmarelo;
        this.tempoVermelho = tempoVermelho;
        this.tempoAtual = 0;
        this.estadoAtual = "VERDE";
    }

    // Atualiza o tempo e troca o estado do semáforo se o tempo do estado atual for atingido
    public void atualizar() {
        tempoAtual++;
        switch (estadoAtual) {
            case "VERDE":
                if (tempoAtual >= tempoVerde) {
                    estadoAtual = "AMARELO";
                    tempoAtual = 0;
                }
                break;
            case "AMARELO":
                if (tempoAtual >= tempoAmarelo) {
                    estadoAtual = "VERMELHO";
                    tempoAtual = 0;
                }
                break;
            case "VERMELHO":
                if (tempoAtual >= tempoVermelho) {
                    estadoAtual = "VERDE";
                    tempoAtual = 0;
                }
                break;
        }
    }

    // Retorna o tempo que está no estado atual (útil para heurísticas)
    public int getTempoNoEstado() {
        return tempoNoEstado;
    }

    // Retorna o estado atual do semáforo
    public String getEstadoAtual() {
        return estadoAtual;
    }

    // Define os tempos para cada estado do semáforo
    public void setTempos(int verde, int amarelo, int vermelho) {
        this.tempoVerde = verde;
        this.tempoAmarelo = amarelo;
        this.tempoVermelho = vermelho;
    }

    // Define diretamente o estado atual do semáforo
    public void setEstado(String estado) {
        this.estadoAtual = estado;
    }

    // Atualiza o contador de tempo no estado, reseta se o estado mudou
    public void atualizarTempoNoEstado() {
        if (!estadoAtual.equals(estadoAnterior)) {
            tempoNoEstado = 0;
            estadoAnterior = estadoAtual;
        } else {
            tempoNoEstado++;
        }
    }
}
