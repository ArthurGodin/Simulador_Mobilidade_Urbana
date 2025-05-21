import cidade.Intersecao;
import estruturas.Lista;
import semaforo.Semaforo;
import trafego.RastreadorDeMovimentacao;
import trafego.Veiculo;

import java.util.Set;

public class ConsoleMonitor {

    private static final int MAX_INTERSECOES_RESUMO = 20;

    public static void imprimirEstado(int tempoAtual,
                                      Lista<Intersecao> intersecoes,
                                      Lista<Veiculo> veiculos,
                                      ColetorEstatisticas coletor,
                                      RastreadorDeMovimentacao rastreador) {

        System.out.printf("\n=== TEMPO ATUAL: %d ===\n\n", tempoAtual);

        Set<Long> intersecoesMudaram = rastreador.getIntersecoesComMudancaEstado();

        // --- Estado dos Semáforos ---
        if (tempoAtual == 0) {
            System.out.println(">> ESTADO DOS SEMÁFOROS (Resumo inicial):");
            int total = intersecoes.tamanho();
            int maxMostrar = Math.min(total, MAX_INTERSECOES_RESUMO);

            int metade = maxMostrar / 2;
            for (int i = 0; i < metade; i++) {
                Intersecao inter = intersecoes.obter(i);
                System.out.printf("Interseção %d - Estado: %s\n", inter.getVertice().getId(),
                        rastreador.getUltimoEstadoSemaforo(inter.getVertice().getId()));
            }
            if (maxMostrar < total) System.out.println("... (outras interseções omitidas) ...");
            for (int i = total - metade; i < total; i++) {
                Intersecao inter = intersecoes.obter(i);
                System.out.printf("Interseção %d - Estado: %s\n", inter.getVertice().getId(),
                        rastreador.getUltimoEstadoSemaforo(inter.getVertice().getId()));
            }
        } else {
            System.out.println(">> ESTADO DOS SEMÁFOROS (Mudanças neste passo):");
            if (intersecoesMudaram.isEmpty()) {
                System.out.println("(Nenhuma mudança de estado de semáforo neste passo)");
            } else {
                for (Long idInter : intersecoesMudaram) {
                    String estado = rastreador.getUltimoEstadoSemaforo(idInter);
                    System.out.printf("Interseção %d - Estado: %s\n", idInter, estado);
                }
            }
        }

        // --- Caminhos calculados para cada veículo (com resumo) ---
        System.out.println("\n>> CAMINHOS CALCULADOS:");

        for (Veiculo veiculo : veiculos) {
            if (veiculo.getCaminho() == null) continue;
            Lista<Intersecao> caminho = veiculo.getCaminho();
            int tamanho = caminho.tamanho();

            System.out.printf("\nVeículo %d:\nOrigem: %d\nDestino: %d\nCaminho (%d vértices):\n",
                    veiculo.getId(),
                    caminho.obter(0).getVertice().getId(),
                    caminho.obter(tamanho - 1).getVertice().getId(),
                    tamanho);

            int limite = 5;
            if (tamanho <= 10) {
                for (Intersecao inter : caminho) {
                    System.out.printf("vertice( id = %d, lat = %.7f, lon = %.7f)\n",
                            inter.getVertice().getId(),
                            inter.getVertice().getLat(),
                            inter.getVertice().getLon());
                }
            } else {
                for (int i = 0; i < limite; i++) {
                    Intersecao inter = caminho.obter(i);
                    System.out.printf("vertice( id = %d, lat = %.7f, lon = %.7f)\n",
                            inter.getVertice().getId(),
                            inter.getVertice().getLat(),
                            inter.getVertice().getLon());
                }
                System.out.println("... (vértices intermediários omitidos) ...");
                for (int i = tamanho - limite; i < tamanho; i++) {
                    Intersecao inter = caminho.obter(i);
                    System.out.printf("vertice( id = %d, lat = %.7f, lon = %.7f)\n",
                            inter.getVertice().getId(),
                            inter.getVertice().getLat(),
                            inter.getVertice().getLon());
                }
            }
        }

        // --- Movimentações dos veículos (mostrar só veículos que se moveram) ---
        System.out.println("\n>> MOVIMENTAÇÕES:");

        System.out.printf("%-8s | %-12s | %-12s | %-17s | %-22s\n",
                "Veículo", "Origem", "Destino", "Estado Semáforo", "Posição atual no caminho");
        System.out.println("---------|--------------|--------------|-------------------|------------------------");

        for (Veiculo veiculo : veiculos) {
            Intersecao intersecaoAtual = veiculo.getIntersecaoAtual();
            if (intersecaoAtual == null) continue;

            // Mostrar somente veículos que se movimentaram no último passo
            if (!veiculo.isMovimentouNoUltimoPasso()) {
                continue;
            }

            long idOrigem = intersecaoAtual.getVertice().getId();
            long idDestino = veiculo.getDestino().getVertice().getId();
            String estadoSemaforo = rastreador.getUltimoEstadoSemaforo(idOrigem);
            int posicaoAtual = veiculo.getPosicaoAtual();

            String estadoDisplay = estadoSemaforo;
            if ("VERMELHO".equals(estadoSemaforo) && veiculo.isEstaParadoPorSemaforo()) {
                estadoDisplay += " (PARADO)";
            }

            System.out.printf("%-8d | %-12d | %-12d | %-17s | %-22d\n",
                    veiculo.getId(),
                    idOrigem,
                    idDestino,
                    estadoDisplay,
                    posicaoAtual + 1);
        }

        // --- Estatísticas ---
        System.out.println("\n>> ESTATÍSTICAS:");
        System.out.printf("Veículos que chegaram ao destino até agora: %d\n", coletor.getVeiculosFinalizados());

        rastreador.limparMudancasEstado();
    }
}
