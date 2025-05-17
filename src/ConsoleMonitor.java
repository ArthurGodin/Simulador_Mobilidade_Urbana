import cidade.*;
import estruturas.*;
import semaforo.*;
import trafego.*;

public class ConsoleMonitor {

    public static void imprimirEstado(int tempo, Lista<Intersecao> intersecoes, Fila<Veiculo> veiculos, ColetorEstatisticas coletor) {
        System.out.println("\n=== TEMPO ATUAL: " + tempo + " ===");

        // Exibe o estado dos semáforos
        System.out.println(">> ESTADO DOS SEMÁFOROS:");
        for (int i = 0; i < intersecoes.tamanho(); i++) {
            Intersecao inter = intersecoes.obter(i);
            Semaforo sem = inter.getSemaforo();
            System.out.printf("Interseção %d (ID %d) - Estado: %s\n", i, inter.getVertice().getId(), sem.getEstadoAtual());

            // Exibe o histórico de mudanças do semáforo
            rastrearMudancasSemaforo(sem);
        }

        // Exibe os veículos em movimento e o rastreamento de suas movimentações
        System.out.println("\n>> VEÍCULOS EM MOVIMENTO:");
        for (int i = 0; i < veiculos.tamanho(); i++) {
            Veiculo v = veiculos.obter(i);
            if (!v.chegouAoDestino()) {
                Intersecao atual = v.getIntersecaoAtual();
                Intersecao destino = v.getDestino();
                System.out.printf("Veículo %d - Na Interseção %d, Destino %d\n", i, atual.getVertice().getId(), destino.getVertice().getId());

                // Registra e exibe movimentação do veículo
                rastrearMovimentacaoVeiculo(v, atual);
            } else {
                System.out.printf("Veículo %d - Já chegou ao destino\n", i);
            }
        }

        // Exibe as estatísticas de veículos finalizados
        System.out.println("\n>> ESTATÍSTICAS:");
        System.out.println("Veículos que chegaram ao destino até agora: " + coletor.getTotalVeiculosFinalizados());
    }

    // Método para exibir as mudanças de estado do semáforo
    private static void rastrearMudancasSemaforo(Semaforo semaforo) {
        RastreadorDeMovimentacao rastreador = semaforo.getRastreador();
        if (rastreador != null) {
            rastreador.exibirMovimentacoes(); // Exibe o log de movimentações do semáforo
        }
    }

    // Método para rastrear a movimentação de um veículo
    private static void rastrearMovimentacaoVeiculo(Veiculo veiculo, Intersecao intersecao) {
        RastreadorDeMovimentacao rastreador = intersecao.getSemaforo().getRastreador();
        if (rastreador != null) {
            String estadoSemaforo = intersecao.getSemaforo().getEstadoAtual();
            rastreador.registrarMovimentacao(veiculo, intersecao, estadoSemaforo);
        }
    }
}
