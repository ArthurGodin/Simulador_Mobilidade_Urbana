import semaforo.*;
import cidade.*;
import estruturas.*;
import heuristica.HeuristicaControle;
import trafego.GeradorVeiculos;
import trafego.Veiculo;

public class Simulador {
    private final int duracaoSimulacao;
    private Lista<Intersecao> intersecoes;
    private ControladorSemaforos controladorSemaforos;
    private GeradorVeiculos geradorVeiculos;
    private HeuristicaControle heuristica;
    private Grafo grafo;

    public Simulador(Lista<Intersecao> intersecoes, HeuristicaControle heuristica, int duracaoSimulacao, Grafo grafo) {
        this.intersecoes = intersecoes;
        this.heuristica = heuristica;
        this.grafo = grafo;
        this.controladorSemaforos = new ControladorSemaforos(heuristica);
        this.geradorVeiculos = new GeradorVeiculos(intersecoes, grafo);
        this.duracaoSimulacao = duracaoSimulacao;
    }

    // Novo método para executar a simulação passo a passo no tempo atual
    public void executarPasso(int tempoAtual) {
        // Controlar semáforos no tempoAtual
        controladorSemaforos.controlarSemaforos(converterLista(intersecoes), tempoAtual);

        // Gerar veículos — pode ajustar para gerar com frequência desejada
        geradorVeiculos.gerarVeiculo();

        // Mover veículos que ainda não chegaram ao destino
        for (Veiculo veiculo : geradorVeiculos.getVeiculos()) {
            if (!veiculo.chegouAoDestino()) {
                veiculo.mover();
            }
        }
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
}
