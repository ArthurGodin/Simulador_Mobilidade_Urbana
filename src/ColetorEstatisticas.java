import trafego.*;

import estruturas.Lista;

public class ColetorEstatisticas {
    private Lista<Veiculo> veiculosFinalizados = new Lista<>();
    private double somaTemposViagem = 0.0;
    private double somaConsumoEnergetico = 0.0;
    private int totalViagens = 0;

    public void registrarVeiculoFinalizado(Veiculo v) {
        if (!foiRegistrado(v)) {
            veiculosFinalizados.adicionar(v);
            somaTemposViagem += v.getTempoViagem();
            somaConsumoEnergetico += v.getConsumoEnergetico();
            totalViagens++;
        }
    }

    public boolean foiRegistrado(Veiculo v) {
        return veiculosFinalizados.contem(v);
    }

    public int getVeiculosFinalizados() {
        return veiculosFinalizados.tamanho();
    }

    public double getMediaTempoViagem() {
        if (totalViagens == 0) return 0.0;
        return somaTemposViagem / totalViagens;
    }

    public double getMediaConsumoEnergetico() {
        if (totalViagens == 0) return 0.0;
        return somaConsumoEnergetico / totalViagens;
    }

    public void registrarVeiculoParado() {
        // Essa funcionalidade pode ser usada para registrar veículos parados durante a simulação
    }

    public void incrementarPassos() {
        // Incrementa o total de passos
    }
}
