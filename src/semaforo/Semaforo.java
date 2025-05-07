package semaforo;
import trafego.*;
import estruturas.*;

public class Semaforo {
    public enum Estado { VERDE, AMARELO, VERMELHO }

    private Estado estadoAtual;
    private Lista<Veiculo> fila = new Lista<>();

    public void setEstado(Estado novoEstado) {
        this.estadoAtual = novoEstado;
    }

    public Estado getEstado() {
        return estadoAtual;
    }

    public Lista<Veiculo> getFila() {
        return fila;
    }
}
