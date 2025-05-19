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

        if (caminhoVertices == null || caminhoVertices.estaVazia()) {
            System.out.println("Nenhum caminho encontrado entre " + origem + " e " + destino);
            return;
        }

        // Resumo do caminho:
        int tamanhoCaminho = caminhoVertices.tamanho();
        Vertice primeiroVertice = caminhoVertices.obter(0);
        Vertice ultimoVertice = caminhoVertices.obter(tamanhoCaminho - 1);

        System.out.println(String.format("Caminho calculado (%d vértices): de %d até %d",
                tamanhoCaminho, primeiroVertice.getId(), ultimoVertice.getId()));

        // Continua a lógica normal pra montar caminhoIntersecoes, gerar veículo etc.
        Lista<Intersecao> caminhoIntersecoes = new Lista<>();
        while (!caminhoVertices.estaVazia()) {
            Vertice v = caminhoVertices.desenfileirar();
            if (v.getIntersecao() != null) {
                caminhoIntersecoes.adicionar(v.getIntersecao());
            }
        }

        // Gerar veículo
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
