package semaforo;

public class Semaforo {
    private int tempoVerde;
    private int tempoAmarelo;
    private int tempoVermelho;

    // Construtor
    public Semaforo(int tempoVerde, int tempoAmarelo, int tempoVermelho) {
        this.tempoVerde = tempoVerde;
        this.tempoAmarelo = tempoAmarelo;
        this.tempoVermelho = tempoVermelho;
    }

    // Métodos para controlar o semáforo
    public int getTempoVerde() {
        return tempoVerde;
    }

    public int getTempoAmarelo() {
        return tempoAmarelo;
    }

    public int getTempoVermelho() {
        return tempoVermelho;
    }

    public void setTempoVerde(int tempoVerde) {
        this.tempoVerde = tempoVerde;
    }

    public void setTempoAmarelo(int tempoAmarelo) {
        this.tempoAmarelo = tempoAmarelo;
    }

    public void setTempoVermelho(int tempoVermelho) {
        this.tempoVermelho = tempoVermelho;
    }
}

