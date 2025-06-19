import trafego.*;
import estruturas.Lista;

public class ColetorEstatisticas {
    private Lista<Veiculo> veiculosFinalizados = new Lista<>();
    private double somaTemposViagem = 0.0;
    private double somaConsumoEnergetico = 0.0;
    private int totalViagens = 0;

    // Registra um veículo finalizado e atualiza as estatísticas
    public void registrarVeiculoFinalizado(Veiculo v) {
        if (!foiRegistrado(v)) {
            veiculosFinalizados.adicionar(v);
            somaTemposViagem += v.getTempoViagem();
            somaConsumoEnergetico += v.getConsumoEnergetico();
            totalViagens++;
        }
    }

    // Verifica se o veículo já foi registrado
    public boolean foiRegistrado(Veiculo v) {
        return veiculosFinalizados.contem(v);
    }

    // Retorna o número total de veículos finalizados
    public int getVeiculosFinalizados() {
        return veiculosFinalizados.tamanho();
    }

    // Calcula a média do tempo de viagem dos veículos finalizados
    public double getMediaTempoViagem() {
        if (totalViagens == 0) return 0.0;
        return somaTemposViagem / totalViagens;
    }

    // Calcula a média do consumo energético dos veículos finalizados
    public double getMediaConsumoEnergetico() {
        if (totalViagens == 0) return 0.0;
        return somaConsumoEnergetico / totalViagens;
    }

    // Método para possível registro de veículos parados (não implementado)
    public void registrarVeiculoParado() {
        // Pode ser usado futuramente para monitorar veículos parados
    }

    // Método para incrementar passos (não implementado)
    public void incrementarPassos() {
        // Pode ser usado futuramente para contabilizar passos na simulação
    }
}
