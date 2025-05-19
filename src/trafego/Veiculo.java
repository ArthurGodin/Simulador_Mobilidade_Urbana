package trafego;

import cidade.Intersecao;
import estruturas.Lista;

public class Veiculo {
    private static long contadorId = 0;
    private long id;
    private Intersecao origem;
    private Intersecao destino;
    private Lista<Intersecao> caminho;
    private int posicaoAtual;

    public Veiculo(Intersecao origem, Intersecao destino, Lista<Intersecao> caminho) {
        this.id = ++contadorId;
        this.origem = origem;
        this.destino = destino;
        this.caminho = caminho;
        this.posicaoAtual = 0;
    }

    public long getId() {
        return id;
    }

    public void mover() {
        if (caminho != null && posicaoAtual < caminho.tamanho() - 1) {
            posicaoAtual++;
            System.out.println("Veículo " + id + " se movendo para a posição: " + posicaoAtual + " de " + caminho.obter(posicaoAtual));
        } else {
            System.out.println("Veículo " + id + " chegou ao destino " + caminho.obter(caminho.tamanho() - 1));
        }
    }

    public boolean chegouAoDestino() {
        return posicaoAtual == caminho.tamanho() - 1;
    }

    public Lista<Intersecao> getCaminho() {
        return caminho;
    }

    public Intersecao getIntersecaoAtual() {
        if (caminho != null && posicaoAtual < caminho.tamanho()) {
            return caminho.obter(posicaoAtual);
        }
        return null;
    }

    public Intersecao getDestino() {
        return destino;
    }

    public Intersecao getOrigem() {
        return origem;
    }

    public int getPosicaoAtual() {
        return posicaoAtual;
    }

    public void resetar() {
        this.posicaoAtual = 0;
    }

    @Override
    public String toString() {
        return String.format("Veículo [ID: %d, Origem: %d, Destino: %d]", id, origem.getVertice().getId(), destino.getVertice().getId());
    }
}
