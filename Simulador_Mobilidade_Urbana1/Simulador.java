import cidade.*;
import semaforo.*;
import heuristica.HeuristicaControle;
import estruturas.*;
import trafego.*;

public class Simulador {
    private Lista<Intersecao> intersecoes;
    private ControladorSemaforos controladorSemaforos;
    private GeradorVeiculos geradorVeiculos;
    private int tempoAtual;
    private int duracaoSimulacao;
    private HeuristicaControle heuristica;

    private Grafo grafo; // adicione esse campo

    public Simulador(Lista<Intersecao> intersecoes, HeuristicaControle heuristica, int duracaoSimulacao, Grafo grafo) {
        this.intersecoes = intersecoes;
        this.heuristica = heuristica;
        this.duracaoSimulacao = duracaoSimulacao;
        this.tempoAtual = 0;
        this.controladorSemaforos = new ControladorSemaforos(heuristica);
        this.grafo = grafo;
        this.geradorVeiculos = new GeradorVeiculos(converterLista(intersecoes), grafo);
    }


    private void inicializarSemaforos() {
        for (int i = 0; i < intersecoes.tamanho(); i++) {
            Intersecao intersecao = intersecoes.obter(i);
            if (intersecao.getSemaforo() == null) {
                intersecao.setSemaforo(new Semaforo(10, 5, 1));
            }
        }
    }

    // Corrigido para retornar lista com tipo correto
    private ArrayList1<Intersecao> converterLista(Lista<Intersecao> listaCustom) {
        ArrayList1<Intersecao> listaJava = new ArrayList1<>();
        for (int i = 0; i < listaCustom.tamanho(); i++) {
            listaJava.adicionar(listaCustom.obter(i));
        }
        return listaJava;
    }

    public void executar() {
        ColetorEstatisticas coletorEstatisticas = new ColetorEstatisticas();

        while (tempoAtual < duracaoSimulacao) {
            controladorSemaforos.controlarSemaforos(converterLista(intersecoes), tempoAtual);
            geradorVeiculos.gerarVeiculo();

            for (Veiculo veiculo : geradorVeiculos.getVeiculos()) {
                if (!veiculo.chegouAoDestino()) {
                    veiculo.mover();
                    if (veiculo.chegouAoDestino()) {
                        coletorEstatisticas.registrarVeiculoFinalizado(veiculo);
                    }
                }
            }

            tempoAtual++;
        }

        coletorEstatisticas.exibirEstatisticas();
    }

    // Corrigido para retornar lista de Veiculo tipada corretamente
    private ArrayList1<Veiculo> converterFilaParaLista(Fila<Veiculo> fila) {
        ArrayList1<Veiculo> lista = new ArrayList1<>();
        Fila<Veiculo> auxiliar = new Fila<>();

        while (!fila.estaVazia()) {
            Veiculo v = fila.desenfileirar();
            lista.adicionar(v);
            auxiliar.enfileirar(v);
        }

        while (!auxiliar.estaVazia()) {
            fila.enfileirar(auxiliar.desenfileirar());
        }

        return lista;
    }
}
