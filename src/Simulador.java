
import cidade.Intersecao;
import semaforo.ControladorSemaforos;
import trafego.GeradorVeiculos;
import heuristica.HeuristicaControle;
import estruturas.*;
import trafego.Veiculo;

import java.util.List;

public class Simulador {
    private Lista<Intersecao> intersecoes;
    private ControladorSemaforos controladorSemaforos;
    private GeradorVeiculos geradorVeiculos;
    private int tempoAtual;
    private int duracaoSimulacao;

    public Simulador(Lista<Intersecao> intersecoes, HeuristicaControle heuristica, int duracaoSimulacao) {
        this.intersecoes = intersecoes;
        this.controladorSemaforos = new ControladorSemaforos(heuristica);
        this.geradorVeiculos = new GeradorVeiculos((List<Intersecao>) intersecoes);
        this.tempoAtual = 0;
        this.duracaoSimulacao = duracaoSimulacao;
    }

    public void executar() {
        while (tempoAtual < duracaoSimulacao) {
            // Atualiza os semáforos com base na heurística escolhida
            controladorSemaforos.controlarSemaforos(intersecoes, tempoAtual);

            // Gera novos veículos com base em alguma lógica (aleatória ou fixa)
            geradorVeiculos.gerarVeiculo();

            // Move todos os veículos existentes
            for (Veiculo veiculo : geradorVeiculos.getVeiculos()) {
                if (!veiculo.chegouAoDestino()) {
                    veiculo.mover();
                    if (veiculo.chegouAoDestino()) {
                        ColetorEstatisticas.registrarVeiculoFinalizado(veiculo);
                    }
                }
            }

            tempoAtual++;
        }

        // Após simulação, exibir estatísticas
        ColetorEstatisticas.exibirEstatisticas();
    }
}


