package trafego;

import cidade.Intersecao;
import cidade.Rua;
import estruturas.*;
import semaforo.Semaforo;

public class Veiculo {
    private Intersecao origem;
    private Intersecao destino;
    private Lista<Intersecao> caminho;
    private int posicaoAtual;

    public Veiculo(Intersecao origem, Intersecao destino, Lista<Intersecao> caminho) {
        this.origem = origem;
        this.destino = destino;
        this.caminho = caminho;
        this.posicaoAtual = 0;
    }

    public void mover() {
        if (caminho != null && posicaoAtual < caminho.tamanho()) {
            posicaoAtual++;
            System.out.println("Veículo se movendo para a posição: " + posicaoAtual + " de " + caminho.obter(posicaoAtual - 1));
        }
        else {
            System.out.println("Veículo chegou ao destino " + caminho.obter(caminho.tamanho() - 1));
        }
    }

    public boolean chegouAoDestino() {
        return posicaoAtual == caminho.tamanho() - 1;
    }


    public Lista<Intersecao> getCaminho() {
        return caminho;
    }


}

