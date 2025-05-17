import cidade.*;
import estruturas.*;
import semaforo.*;
import trafego.*;

public class ConsoleMonitor {

    public static void imprimirEstado(int tempo, Lista<Intersecao> intersecoes, Fila<Veiculo> veiculos, ColetorEstatisticas coletor, RastreadorDeMovimentacao rastreador) {
        System.out.println("\n=== TEMPO ATUAL: " + tempo + " ===");

        // Exibe o estado dos semáforos
        System.out.println(">> ESTADO DOS SEMÁFOROS:");
        for (int i = 0; i < intersecoes.tamanho(); i++) {
            Intersecao inter = intersecoes.obter(i);
            Semaforo sem = inter.getSemaforo();
            System.out.printf("Interseção %d (ID %d) - Estado: %s\n", i, inter.getVertice().getId(), sem.getEstadoAtual());
        }

        // Exibe os veículos em movimento e registra movimentação via rastreador
        System.out.println("\n>> VEÍCULOS EM MOVIMENTO:");
        for (int i = 0; i < veiculos.tamanho(); i++) {
            Veiculo v = veiculos.obter(i);
            if (!v.chegouAoDestino()) {
                Intersecao atual = v.getIntersecaoAtual();
                Intersecao destino = v.getDestino();
                System.out.printf("Veículo %d - Na Interseção %d, Destino %d\n", i, atual.getVertice().getId(), destino.getVertice().getId());

                // Registra movimentação no rastreador central
                String estadoSemaforo = atual.getSemaforo().getEstadoAtual();
                rastreador.registrarMovimentacao(v, atual, estadoSemaforo);
            } else {
                System.out.printf("Veículo %d - Já chegou ao destino\n", i);
            }
        }

        // Exibe as movimentações registradas no rastreador
        rastreador.exibirMovimentacoes();

        // Exibe as estatísticas de veículos finalizados
        System.out.println("\n>> ESTATÍSTICAS:");
        System.out.println("Veículos que chegaram ao destino até agora: " + coletor.getTotalVeiculosFinalizados());
    }
}
