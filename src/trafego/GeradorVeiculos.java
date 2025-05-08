package trafego;

import cidade.Intersecao;
import trafego.Veiculo;
import estruturas.Fila;
import java.util.List;
import java.util.Random;

public class GeradorVeiculos {
    private List<Intersecao> intersecoes;
    private Fila<Veiculo> veiculos;
    private Random random;

    public GeradorVeiculos(List<Intersecao> intersecoes) {
        this.intersecoes = intersecoes;
        this.veiculos = new Fila<>();
        this.random = new Random();
    }

    public void gerarVeiculo() {
        Intersecao origem = intersecoes.get(random.nextInt(intersecoes.size()));
        Intersecao destino = intersecoes.get(random.nextInt(intersecoes.size()));
        while (destino == origem) {
            destino = intersecoes.get(random.nextInt(intersecoes.size()));
        }
        Veiculo veiculo = new Veiculo(origem, destino);
        veiculos.enfileirar(veiculo);
        origem.getFilaVeiculos().enfileirar(veiculo);
    }

    public Fila<Veiculo> getVeiculos() {
        return veiculos;
    }
}
