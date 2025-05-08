
import trafego.Veiculo;
import estruturas.*;

public class ColetorEstatisticas {
    private Lista<Veiculo> veiculosFinalizados;

    public ColetorEstatisticas() {
        this.veiculosFinalizados = new Lista<>();
    }

    public void registrarVeiculoFinalizado(Veiculo veiculo) {
        veiculosFinalizados.adicionar(veiculo);
    }

    public void exibirEstatisticas() {
        int totalVeiculos = veiculosFinalizados.tamanho();
        System.out.println("Total de veículos que chegaram ao destino: " + totalVeiculos);
        // Adicionar mais estatísticas conforme necessário
    }
}
