package trafego;

import cidade.*;
import estruturas.*;
import java.util.Random;

public class GeradorVeiculos {
    private Lista<Intersecao> intersecoes;
    private Fila<Veiculo> veiculos;
    private Random random;
    private Grafo grafo;

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

        Fila<Vertice> caminhoVertices = Dijkstra.encontrarMenorCaminho(grafo, origem.getVertice(), destino.getVertice());

        System.out.println("Gerando veículo de " + origem + " para " + destino);

        if (intersecoes == null || intersecoes.tamanho() < 2) {
            System.out.println("Interseções insuficientes para gerar veículos. Tamanho: " + (intersecoes == null ? "null" : intersecoes.tamanho()));
            return;
        }

        if (caminhoVertices == null || caminhoVertices.estaVazia()) {
            System.out.println("Nenhum caminho encontrado entre " + origem + " e " + destino);
            return;
        }

        Lista<Intersecao> caminhoIntersecoes = new Lista<>();
        while (!caminhoVertices.estaVazia()) {
            Vertice v = caminhoVertices.desenfileirar();
            System.out.println("Checando vértice: " + v + ", Intersecao associada? " + (v.getIntersecao() != null));
            if (v.getIntersecao() != null) {
                caminhoIntersecoes.adicionar(v.getIntersecao());
            } else {
                System.err.println("Erro: vértice sem intersecao associada: " + v);
            }
        }

        if (caminhoIntersecoes.tamanho() == 0) {
            System.out.println("Caminho não possui interseções válidas. Veículo não criado.");
            return;
        }

        Veiculo veiculo = new Veiculo(origem, destino, caminhoIntersecoes);
        veiculos.enfileirar(veiculo);
        origem.getFilaVeiculos().enfileirar(veiculo);
    }

    public Fila<Veiculo> getVeiculos() {
        return veiculos;
    }

    public Lista<Intersecao> getIntersecoes() {
        return intersecoes;
    }
}
