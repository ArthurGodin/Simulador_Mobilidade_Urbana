package trafego;

import cidade.*;
import estruturas.*;
import java.util.Random;

public class GeradorVeiculos {
    private Lista<Intersecao> intersecoes;  // Lista de interseções disponíveis
    private Fila<Veiculo> veiculos;          // Fila com veículos gerados
    private Random random;                   // Para escolha aleatória de origem/destino
    private Grafo grafo;                    // Grafo para cálculo de rotas

    private int totalVeiculosCriados = 0;   // Contador de veículos criados

    private int maxVeiculosParaCriar = Integer.MAX_VALUE; // Limite máximo para criação
    private int contadorPassos = 0;         // Contador de passos para espaçar criação
    private int passosParaGerarVeiculo = 3; // Quantidade de passos entre criações
    private int limiteMaximoVerticesCaminho = 15; // Limite máximo de vértices no caminho

    public GeradorVeiculos(Lista<Intersecao> intersecoes, Grafo grafo) {
        this.grafo = grafo;
        this.intersecoes = intersecoes;
        this.veiculos = new Fila<>();
        this.random = new Random();
        System.out.println("GeradorVeiculos inicializado com grafo: " + (grafo != null));
    }

    // Setters para configuração dos parâmetros
    public void setMaxVeiculosParaCriar(int max) {
        this.maxVeiculosParaCriar = max;
    }

    public void setPassosParaGerarVeiculo(int passos) {
        this.passosParaGerarVeiculo = passos;
    }

    public void setLimiteMaximoVerticesCaminho(int limite) {
        this.limiteMaximoVerticesCaminho = limite;
    }

    // Getters para os parâmetros
    public int getMaxVeiculosParaCriar() {
        return maxVeiculosParaCriar;
    }

    public int getPassosParaGerarVeiculo() {
        return passosParaGerarVeiculo;
    }

    public int getLimiteMaximoVerticesCaminho() {
        return limiteMaximoVerticesCaminho;
    }

    // Tenta gerar um veículo baseado na contagem de passos e limite
    public void tentarGerarVeiculo() {
        contadorPassos++;
        if (contadorPassos >= passosParaGerarVeiculo && totalVeiculosCriados < maxVeiculosParaCriar) {
            gerarVeiculo();
            contadorPassos = 0;
        }
    }

    // Gera um veículo com origem e destino válidos e adiciona à fila
    public void gerarVeiculo() {
        if (totalVeiculosCriados >= maxVeiculosParaCriar) {
            return; // Limite atingido, não cria mais
        }

        Intersecao origem;
        Intersecao destino;
        Fila<Vertice> caminhoVertices;
        int tamanhoCaminho;

        int tentativas = 0;
        do {
            origem = intersecoes.obter(random.nextInt(intersecoes.tamanho()));
            destino = intersecoes.obter(random.nextInt(intersecoes.tamanho()));
            // Garante que origem e destino sejam diferentes
            while (destino == origem) {
                destino = intersecoes.obter(random.nextInt(intersecoes.tamanho()));
            }

            // Calcula o menor caminho entre origem e destino
            caminhoVertices = Dijkstra.encontrarMenorCaminho(grafo, origem.getVertice(), destino.getVertice());
            tamanhoCaminho = (caminhoVertices == null) ? 0 : caminhoVertices.tamanho();

            tentativas++;
            if (tentativas > 50) {
                System.out.println("Não foi possível encontrar caminho válido com mais de 1 vértice após 50 tentativas.");
                return;
            }
        } while (tamanhoCaminho <= 1);

        // Limita o caminho ao tamanho máximo configurado
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
        // Converte vértices do caminho para interseções correspondentes
        while (!caminhoVertices.estaVazia()) {
            Vertice v = caminhoVertices.desenfileirar();
            if (v.getIntersecao() != null) {
                caminhoIntersecoes.adicionar(v.getIntersecao());
            }
        }

        // Ajusta destino para último vértice do caminho limitado
        Intersecao destinoLimitado = caminhoIntersecoes.obter(caminhoIntersecoes.tamanho() - 1);

        // Cria o veículo com origem, destino e caminho calculado
        Veiculo veiculo = new Veiculo(origem, destinoLimitado, caminhoIntersecoes, 0); // tempoEntrada = 0 (exemplo)
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