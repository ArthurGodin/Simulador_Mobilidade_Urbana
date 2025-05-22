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

    // Construtor principal do simulador
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

    // Executa toda a simulação de forma sequencial
    public void executar() {
        for (int tempoAtual = 0; tempoAtual < duracaoSimulacao; tempoAtual++) {
            executarPasso(tempoAtual);
        }
        imprimirRelatorioFinal();
    }

    // Imprime estatísticas e trajetos ao final da simulação
    public void imprimirRelatorioFinal() {
        System.out.println("\n=== RELATÓRIO FINAL DA SIMULAÇÃO ===");
        System.out.printf("Duração total da simulação: %d passos\n", duracaoSimulacao);
        System.out.printf("Total de veículos criados: %d\n", geradorVeiculos.getTotalVeiculosCriados());
        System.out.printf("Veículos que chegaram ao destino: %d\n", coletor.getVeiculosFinalizados());
        System.out.printf("Média de tempo de viagem: %.2f passos\n", coletor.getMediaTempoViagem());
        System.out.printf("Média de consumo energético: %.2f unidades\n", coletor.getMediaConsumoEnergetico());

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

    // Executa um passo da simulação, atualizando veículos e semáforos
    public void executarPasso(int tempoAtual) {
        // Resetar flags de movimentação para cada veículo
        for (Veiculo veiculo : geradorVeiculos.getVeiculos()) {
            veiculo.setMovimentouNoUltimoPasso(false);
        }

        rastreadorDeMovimentacao.limparMudancasEstado();
        rastreadorDeMovimentacao.setTempoAtual(tempoAtual);

        // Atualizar estados dos semáforos
        controladorSemaforos.controlarSemaforos(converterLista(intersecoes), tempoAtual);

        // Tentar gerar novos veículos com base nas regras do gerador
        geradorVeiculos.tentarGerarVeiculo();

        // Atualizar estados e posições dos veículos
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

    // Converte lista customizada para ArrayList1 (Java-like) para integração
    private ArrayList1<Intersecao> converterLista(Lista<Intersecao> listaCustom) {
        ArrayList1<Intersecao> listaJava = new ArrayList1<>();
        for (int i = 0; i < listaCustom.tamanho(); i++) {
            listaJava.adicionar(listaCustom.obter(i));
        }
        return listaJava;
    }

    // Getters para acessar propriedades importantes
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
