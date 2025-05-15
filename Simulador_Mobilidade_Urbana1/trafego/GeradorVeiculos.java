package trafego;

import cidade.*;
import estruturas.*;
import java.util.Random;

public class GeradorVeiculos {
    private Lista<Intersecao> intersecoes;
    private Fila<Veiculo> veiculos;
    private Random random;

    private Grafo grafo; // novo campo

    // Corrigido o construtor para aceitar o grafo
    public GeradorVeiculos(Lista<Intersecao> intersecoes, Grafo grafo) {
        this.grafo = grafo;
        this.intersecoes = intersecoes;
        this.veiculos = new Fila<>();
        this.random = new Random();
        System.out.println("GeradorVeiculos inicializado com grafo: " + (grafo != null));
    }


    public void gerarVeiculo() {
        Intersecao origem = intersecoes.obter(random.nextInt(intersecoes.tamanho()));
        Intersecao destino = intersecoes.obter(random.nextInt(intersecoes.tamanho()));
        while (destino == origem) {
            destino = intersecoes.obter(random.nextInt(intersecoes.tamanho()));
        }

        // Usar Dijkstra para obter o caminho
        Fila<Vertice> caminhoVertices = Dijkstra.encontrarMenorCaminho(grafo, origem.getVertice(), destino.getVertice());
        System.out.println("Caminho de " + origem + " até " + destino + ": " + caminhoVertices.tamanho());

        // Converter caminho para Lista<Intersecao>
        Lista<Intersecao> caminhoIntersecoes = new Lista<>();
        while (!caminhoVertices.estaVazia()) {
            Vertice v = caminhoVertices.desenfileirar();
            caminhoIntersecoes.adicionar(v.getIntersecao()); // ← garanta que existe esse método!
        }

        // Criar o veículo com caminho real
        Veiculo veiculo = new Veiculo(origem, destino, caminhoIntersecoes);
        veiculos.enfileirar(veiculo);
        origem.getFilaVeiculos().enfileirar(veiculo);
    }

    public Fila<Veiculo> getVeiculos() {
        return veiculos;
    }
}
