package trafego;

import cidade.*;
import estruturas.*;
import java.util.Random;

public class GeradorVeiculos {
    private Lista<Intersecao> intersecoes;
    private Fila<Veiculo> veiculos;
    private Random random;
    private Grafo grafo;

    private int totalVeiculosCriados = 0;

    // Controle máximo de veículos criados
    private int maxVeiculosParaCriar = Integer.MAX_VALUE;

    // Controle de espaçamento na geração dos veículos
    private int contadorPassos = 0;
    private int passosParaGerarVeiculo = 3;

    // Limite máximo de vértices no caminho do veículo
    private int limiteMaximoVerticesCaminho = 15;

    public GeradorVeiculos(Lista<Intersecao> intersecoes, Grafo grafo) {
        this.grafo = grafo;
        this.intersecoes = intersecoes;
        this.veiculos = new Fila<>();
        this.random = new Random();
        System.out.println("GeradorVeiculos inicializado com grafo: " + (grafo != null));
    }

    public void setMaxVeiculosParaCriar(int max) {
        this.maxVeiculosParaCriar = max;
    }

    public void setPassosParaGerarVeiculo(int passos) {
        this.passosParaGerarVeiculo = passos;
    }

    public void setLimiteMaximoVerticesCaminho(int limite) {
        this.limiteMaximoVerticesCaminho = limite;
    }

    public int getMaxVeiculosParaCriar() {
        return maxVeiculosParaCriar;
    }

    public int getPassosParaGerarVeiculo() {
        return passosParaGerarVeiculo;
    }

    public int getLimiteMaximoVerticesCaminho() {
        return limiteMaximoVerticesCaminho;
    }

    public void tentarGerarVeiculo() {
        contadorPassos++;
        if (contadorPassos >= passosParaGerarVeiculo && totalVeiculosCriados < maxVeiculosParaCriar) {
            gerarVeiculo();
            contadorPassos = 0;
        }
    }

    public void gerarVeiculo() {
        if (totalVeiculosCriados >= maxVeiculosParaCriar) {
            return; // Não cria mais veículos
        }

        Intersecao origem;
        Intersecao destino;
        Fila<Vertice> caminhoVertices;
        int tamanhoCaminho;

        int tentativas = 0;
        do {
            origem = intersecoes.obter(random.nextInt(intersecoes.tamanho()));
            destino = intersecoes.obter(random.nextInt(intersecoes.tamanho()));
            while (destino == origem) {
                destino = intersecoes.obter(random.nextInt(intersecoes.tamanho()));
            }

            caminhoVertices = Dijkstra.encontrarMenorCaminho(grafo, origem.getVertice(), destino.getVertice());
            tamanhoCaminho = (caminhoVertices == null) ? 0 : caminhoVertices.tamanho();

            tentativas++;
            if (tentativas > 50) {
                System.out.println("Não foi possível encontrar caminho válido com mais de 1 vértice após 50 tentativas.");
                return;
            }
        } while (tamanhoCaminho <= 1);

        // Limita o tamanho do caminho ao máximo configurado
        if (caminhoVertices.tamanho() > limiteMaximoVerticesCaminho) {
            Fila<Vertice> caminhoLimitado = new Fila<>();
            int count = 0;
            while (!caminhoVertices.estaVazia() && count < limiteMaximoVerticesCaminho) {
                caminhoLimitado.enfileirar(caminhoVertices.desenfileirar());
                count++;
            }
            caminhoVertices = caminhoLimitado;
        }

        System.out.println(String.format("Caminho calculado (%d vértices): de %d até %d",
                caminhoVertices.tamanho(),
                origem.getVertice().getId(),
                destino.getVertice().getId()));

        Lista<Intersecao> caminhoIntersecoes = new Lista<>();
        while (!caminhoVertices.estaVazia()) {
            Vertice v = caminhoVertices.desenfileirar();
            if (v.getIntersecao() != null) {
                caminhoIntersecoes.adicionar(v.getIntersecao());
            }
        }

        // Ajusta o destino para o último vértice do caminho limitado
        Intersecao destinoLimitado = caminhoIntersecoes.obter(caminhoIntersecoes.tamanho() - 1);

        Veiculo veiculo = new Veiculo(origem, destinoLimitado, caminhoIntersecoes);
        veiculos.enfileirar(veiculo);
        origem.getFilaVeiculos().enfileirar(veiculo);

        totalVeiculosCriados++;
    }

    public Fila<Veiculo> getVeiculos() {
        return veiculos;
    }

    public Lista<Intersecao> getIntersecoes() {
        return intersecoes;
    }

    public int getTotalVeiculosCriados() {
        return totalVeiculosCriados;
    }
}
