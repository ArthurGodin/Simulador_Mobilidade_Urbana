import semaforo.*;
import cidade.*;
import estruturas.*;
import heuristica.HeuristicaControle;
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
    private RastreadorDeMovimentacao rastreadorDeMovimentacao; // Instância do rastreador de movimentação

    public Simulador(Lista<Intersecao> intersecoes, HeuristicaControle heuristica, int duracaoSimulacao, Grafo grafo) {
        this.intersecoes = intersecoes;
        this.heuristica = heuristica;
        this.grafo = grafo;
        this.controladorSemaforos = new ControladorSemaforos(heuristica);
        this.geradorVeiculos = new GeradorVeiculos(intersecoes, grafo);
        this.duracaoSimulacao = duracaoSimulacao;
        this.coletor = new ColetorEstatisticas();
        this.rastreadorDeMovimentacao = new RastreadorDeMovimentacao(); // Inicializando o rastreador
    }

    // Novo método para executar a simulação passo a passo no tempo atual
    public void executarPasso(int tempoAtual) {
        // Controla os semáforos
        controladorSemaforos.controlarSemaforos(converterLista(intersecoes), tempoAtual);

        // Gera novos veículos
        geradorVeiculos.gerarVeiculo();

        // Itera sobre os veículos gerados
        for (Veiculo veiculo : geradorVeiculos.getVeiculos()) {
            if (!veiculo.chegouAoDestino()) {
                Intersecao intersecaoAtual = veiculo.getIntersecaoAtual();
                Semaforo semaforoAtual = intersecaoAtual.getSemaforo();

                // Verifica se o semáforo está vermelho e o veículo está parado
                if (semaforoAtual.getEstadoAtual().equals("VERMELHO")) {
                    rastreadorDeMovimentacao.registrarParadaEmSemaforo(veiculo, intersecaoAtual);
                } else {
                    rastreadorDeMovimentacao.registrarMovimentacao(veiculo, intersecaoAtual, semaforoAtual.getEstadoAtual());
                }

                // Move o veículo
                veiculo.mover();
            } else if (!coletor.foiRegistrado(veiculo)) {
                coletor.registrarVeiculoFinalizado(veiculo);
            }
        }

        // Exibe as movimentações e o estado de cada passo da simulação
        rastreadorDeMovimentacao.exibirMovimentacoes();
        ConsoleMonitor.imprimirEstado(tempoAtual, intersecoes, geradorVeiculos.getVeiculos(), coletor, rastreadorDeMovimentacao);

    }

    // Método original, que chama executarPasso para todos os tempos
    public void executar() {
        for (int tempoAtual = 0; tempoAtual < duracaoSimulacao; tempoAtual++) {
            executarPasso(tempoAtual);
        }
    }

    // Método auxiliar para converter sua lista customizada para ArrayList1 (se necessário)
    private ArrayList1<Intersecao> converterLista(Lista<Intersecao> listaCustom) {
        ArrayList1<Intersecao> listaJava = new ArrayList1<>();
        for (int i = 0; i < listaCustom.tamanho(); i++) {
            listaJava.adicionar(listaCustom.obter(i));
        }
        return listaJava;
    }

    // Getters importantes para o visual acessar:

    public Lista<Intersecao> getIntersecoes() {
        return intersecoes;
    }

    public Grafo getGrafo() {
        return grafo;
    }

    public GeradorVeiculos getGeradorVeiculos() {
        return geradorVeiculos;
    }

    public int getDuracaoSimulacao(){
        return duracaoSimulacao;
    }

    public ColetorEstatisticas getColetor(){
        return coletor;
    }
}
