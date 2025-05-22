import cidade.*;
import estruturas.*;
import heuristica.HeuristicaControle;
import semaforo.ControladorSemaforos;
import semaforo.Semaforo;
import trafego.GeradorVeiculos;
import trafego.RastreadorDeMovimentacao;
import trafego.Veiculo;

public class Simulador {

    private final int duracaoSimulacao;
    private Lista<Intersecao> intersecoes;
    private ControladorSemaforos controladorSemaforos;
    private GeradorVeiculos geradorVeiculos;
    private HeuristicaControle heuristica;
    private Grafo grafo;
    private ColetorEstatisticas coletor;
    private RastreadorDeMovimentacao rastreadorDeMovimentacao;

    public Simulador(Lista<Intersecao> intersecoes, HeuristicaControle heuristica, int duracaoSimulacao, Grafo grafo) {
        this.intersecoes = intersecoes;
        this.heuristica = heuristica;
        this.grafo = grafo;
        this.controladorSemaforos = new ControladorSemaforos(heuristica);
        this.geradorVeiculos = new GeradorVeiculos(intersecoes, grafo);
        this.duracaoSimulacao = duracaoSimulacao;
        this.coletor = new ColetorEstatisticas();
        this.rastreadorDeMovimentacao = new RastreadorDeMovimentacao();
    }

    public void executar() {
        for (int tempoAtual = 0; tempoAtual < duracaoSimulacao; tempoAtual++) {
            executarPasso(tempoAtual);
        }
        imprimirRelatorioFinal();
    }

    public void imprimirRelatorioFinal() {
        System.out.println("\n=== RELATÓRIO FINAL DA SIMULAÇÃO ===");
        System.out.printf("Duração total da simulação: %d passos\n", duracaoSimulacao);
        System.out.printf("Total de veículos criados: %d\n", geradorVeiculos.getTotalVeiculosCriados());
        System.out.printf("Veículos que chegaram ao destino: %d\n", coletor.getVeiculosFinalizados());
        System.out.printf("Média de tempo de viagem: %.2f passos\n", coletor.getMediaTempoViagem());
        System.out.printf("Média de consumo energético: %.2f unidades\n", coletor.getMediaConsumoEnergetico());

        // Nova saída: imprimir trajetos dos veículos
        System.out.println("\n=== Trajetos percorridos pelos veículos ===");
        for (Veiculo veiculo : geradorVeiculos.getVeiculos()) {
            Lista<Intersecao> trajeto = veiculo.getTrajetoPercorrido();
            System.out.print("Veículo ID " + veiculo.getId() + " - Trajeto: ");
            for (int i = 0; i < trajeto.tamanho(); i++) {
                System.out.print(trajeto.obter(i).getVertice().getId());
                if (i < trajeto.tamanho() - 1) System.out.print(" -> ");
            }
            System.out.println();
        }
    }

    public void executarPasso(int tempoAtual) {
        // Limpa flags de movimentação no início do passo
        for (Veiculo veiculo : geradorVeiculos.getVeiculos()) {
            veiculo.setMovimentouNoUltimoPasso(false);
        }

        rastreadorDeMovimentacao.limparMudancasEstado();
        rastreadorDeMovimentacao.setTempoAtual(tempoAtual);

        controladorSemaforos.controlarSemaforos(converterLista(intersecoes), tempoAtual);

        // Geração espaçada e limitada de veículos
        geradorVeiculos.tentarGerarVeiculo();

        for (Veiculo veiculo : geradorVeiculos.getVeiculos()) {
            if (!veiculo.chegouAoDestino()) {
                Intersecao intersecaoAtual = veiculo.getIntersecaoAtual();
                Semaforo semaforoAtual = intersecaoAtual.getSemaforo();

                boolean movimentou = false;
                if (semaforoAtual != null && "VERDE".equals(semaforoAtual.getEstadoAtual())) {
                    veiculo.mover();
                    movimentou = true;
                    rastreadorDeMovimentacao.registrarMovimentacao(veiculo, intersecaoAtual, semaforoAtual.getEstadoAtual());
                } else {
                    veiculo.parar();
                    rastreadorDeMovimentacao.registrarParadaEmSemaforo(veiculo, intersecaoAtual);
                }

                veiculo.atualizarEstadoMovimento(movimentou);
            } else if (!coletor.foiRegistrado(veiculo)) {
                coletor.registrarVeiculoFinalizado(veiculo);
                veiculo.registrarChegada(tempoAtual);
            }
        }

        rastreadorDeMovimentacao.exibirMovimentacoes();
    }

    private ArrayList1<Intersecao> converterLista(Lista<Intersecao> listaCustom) {
        ArrayList1<Intersecao> listaJava = new ArrayList1<>();
        for (int i = 0; i < listaCustom.tamanho(); i++) {
            listaJava.adicionar(listaCustom.obter(i));
        }
        return listaJava;
    }

    public Lista<Intersecao> getIntersecoes() {
        return intersecoes;
    }

    public Grafo getGrafo() {
        return grafo;
    }

    public GeradorVeiculos getGeradorVeiculos() {
        return geradorVeiculos;
    }

    public int getDuracaoSimulacao() {
        return duracaoSimulacao;
    }

    public ColetorEstatisticas getColetor() {
        return coletor;
    }
}
