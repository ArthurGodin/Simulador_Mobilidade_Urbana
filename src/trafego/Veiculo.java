package trafego;

import cidade.Intersecao;
import cidade.Rua;
import estruturas.*;
import semaforo.Semaforo;

public class Veiculo {
    private Intersecao origem;
    private Intersecao destino;
    private Lista<Rua> caminho;
    private int posicaoAtual; // Índice da rua atual no caminho
    private boolean chegouAoDestino;

    public Veiculo(Intersecao origem, Intersecao destino) {
        this.origem = origem;
        this.destino = destino;
        this.caminho = caminho;
        this.posicaoAtual = 0;
        this.chegouAoDestino = false;
    }

    public void mover() {
        if (chegouAoDestino) return;

        Rua ruaAtual = caminho.obter(posicaoAtual);
        Intersecao proximaIntersecao = ruaAtual.getDestino();
        Semaforo semaforo = proximaIntersecao.getSemaforo();

        if (semaforo.getEstadoAtual().equals("VERDE")) {
            posicaoAtual++;
            if (posicaoAtual >= caminho.tamanho()) {
                chegouAoDestino = true;
            }
        }
    }

    public boolean chegouAoDestino() {
        return chegouAoDestino;
    }
}

