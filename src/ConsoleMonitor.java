import cidade.Intersecao;
import estruturas.Fila;
import estruturas.Lista;
import semaforo.Semaforo;
import trafego.RastreadorDeMovimentacao;
import trafego.Veiculo;

import java.util.Set;

public class ConsoleMonitor {

    public static void imprimirEstado(int tempo, Lista<Intersecao> intersecoes, Fila<Veiculo> veiculos,
                                      ColetorEstatisticas coletor, RastreadorDeMovimentacao rastreador) {
        System.out.println("\n=== TEMPO ATUAL: " + tempo + " ===");

        Set<Long> intersecoesComMudancaEstado = rastreador.getIntersecoesComMudancaEstado();

        System.out.println(">> ESTADO DOS SEMÁFOROS (somente mudanças):");
        for (int i = 0; i < intersecoes.tamanho(); i++) {
            Intersecao inter = intersecoes.obter(i);
            Semaforo sem = inter.getSemaforo();
            if (sem != null) {
                long idInter = inter.getVertice().getId();
                if (intersecoesComMudancaEstado.contains(idInter)) {
                    System.out.printf("Interseção %d (ID %d) - Estado: %s\n", i, idInter, sem.getEstadoAtual());
                }
            }
        }

        System.out.println("\n>> VEÍCULOS EM MOVIMENTO:");
        for (int i = 0; i < veiculos.tamanho(); i++) {
            Veiculo v = veiculos.obter(i);
            if (!v.chegouAoDestino()) {
                Intersecao atual = v.getIntersecaoAtual();
                Intersecao destino = v.getDestino();
                System.out.printf("Veículo %d - Na Interseção %d, Destino %d\n",
                        i, atual.getVertice().getId(), destino.getVertice().getId());
            } else {
                System.out.printf("Veículo %d - Já chegou ao destino\n", i);
            }
        }

        System.out.println("\n>> ESTATÍSTICAS:");
        System.out.println("Veículos que chegaram ao destino até agora: " + coletor.getTotalVeiculosFinalizados());
    }
}
