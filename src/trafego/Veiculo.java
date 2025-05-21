package trafego;

import cidade.Intersecao;
import estruturas.Lista;

public class Veiculo {
    private static long contadorId = 0;
    private long id;
    private Intersecao origem;
    private Intersecao destino;  // Será o último vértice do caminho limitado
    private Lista<Intersecao> caminho;
    private int posicaoAtual;
    private boolean estaParadoPorSemaforo = false;
    private boolean movimentouNoUltimoPasso = false;

    private int passosParadoConsecutivos = 0;

    public Veiculo(Intersecao origem, Intersecao destino, Lista<Intersecao> caminho) {
        this.id = ++contadorId;
        this.origem = origem;
        this.destino = destino;
        this.caminho = caminho;
        this.posicaoAtual = 0;
    }

    public int getId() {
        return (int) id;
    }

    public void mover() {
        if (!chegouAoDestino()) {
            posicaoAtual++;
            System.out.println("Veículo " + id + " movendo para posição " + posicaoAtual + " no caminho");
        } else {
            System.out.println("Veículo " + id + " já chegou ao destino.");
        }
    }

    public boolean chegouAoDestino() {
        // Chegou ao destino se posição atual é o último índice do caminho
        return posicaoAtual >= caminho.tamanho() - 1;
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

    public boolean isEstaParadoPorSemaforo() {
        return estaParadoPorSemaforo;
    }

    public void setEstaParadoPorSemaforo(boolean estaParado) {
        this.estaParadoPorSemaforo = estaParado;
    }

    public void setMovimentouNoUltimoPasso(boolean movimentou) {
        this.movimentouNoUltimoPasso = movimentou;
    }

    public boolean isMovimentouNoUltimoPasso() {
        return movimentouNoUltimoPasso;
    }

    public int getPassosParadoConsecutivos() {
        return passosParadoConsecutivos;
    }

    public void resetarPassosParado() {
        passosParadoConsecutivos = 0;
    }

    public void incrementarPassosParado() {
        passosParadoConsecutivos++;
    }

    public void atualizarEstadoMovimento(boolean movimentou) {
        if (movimentou) {
            resetarPassosParado();
        } else {
            incrementarPassosParado();
        }
        setMovimentouNoUltimoPasso(movimentou);
    }

    @Override
    public String toString() {
        return String.format("Veículo [ID: %d, Origem: %d, Destino: %d]", id, origem.getVertice().getId(), destino.getVertice().getId());
    }
}
