import trafego.*;
import estruturas.*;

public class ColetorEstatisticas {
    private Lista<Veiculo> veiculosFinalizados = new Lista<>();

    public void registrarVeiculoFinalizado(Veiculo v) {
        if (!foiRegistrado(v)) {
            veiculosFinalizados.adicionar(v);
        }
    }

    public boolean foiRegistrado(Veiculo v) {
        return veiculosFinalizados.contem(v);
    }

    public int getVeiculosFinalizados() {
        return veiculosFinalizados.tamanho();
    }
}
