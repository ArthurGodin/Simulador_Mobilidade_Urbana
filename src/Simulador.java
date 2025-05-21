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
    }

    public void executarPasso(int tempoAtual) {

        // Limpa flags de movimentação no início do passo
        for (Veiculo veiculo : geradorVeiculos.getVeiculos()) {
            veiculo.setMovimentouNoUltimoPasso(false);
        }

        rastreadorDeMovimentacao.limparMudancasEstado();
        rastreadorDeMovimentacao.setTempoAtual(tempoAtual);

        controladorSemaforos.controlarSemaforos(converterLista(intersecoes), tempoAtual);

        for (Intersecao inter : converterLista(intersecoes)) {
            Semaforo semaforo = inter.getSemaforo();
            if (semaforo != null) {
                rastreadorDeMovimentacao.registrarMudancaEstadoSemaforo(inter.getVertice().getId(), semaforo.getEstadoAtual());
            }
        }

        // Usar geração espaçada e limitada de veículos
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
                    rastreadorDeMovimentacao.registrarParadaEmSemaforo(veiculo, intersecaoAtual);
                }

                veiculo.atualizarEstadoMovimento(movimentou);

                if (veiculo.getPassosParadoConsecutivos() > 5) {
                    System.out.println("ALERTA: Veículo " + veiculo.getId() + " parado por " + veiculo.getPassosParadoConsecutivos() + " passos consecutivos.");
                }
            } else if (!coletor.foiRegistrado(veiculo)) {
                coletor.registrarVeiculoFinalizado(veiculo);
            }
        }

        rastreadorDeMovimentacao.exibirMovimentacoes();

        Lista<Veiculo> listaVeiculos = converterFilaParaLista(geradorVeiculos.getVeiculos());

        ConsoleMonitor.imprimirEstado(tempoAtual, intersecoes, listaVeiculos, coletor, rastreadorDeMovimentacao);
    }

    private ArrayList1<Intersecao> converterLista(Lista<Intersecao> listaCustom) {
        ArrayList1<Intersecao> listaJava = new ArrayList1<>();
        for (int i = 0; i < listaCustom.tamanho(); i++) {
            listaJava.adicionar(listaCustom.obter(i));
        }
        return listaJava;
    }

    private Lista<Veiculo> converterFilaParaLista(Fila<Veiculo> fila) {
        Lista<Veiculo> lista = new Lista<>();
        for (Veiculo v : fila) {
            lista.adicionar(v);
        }
        return lista;
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
