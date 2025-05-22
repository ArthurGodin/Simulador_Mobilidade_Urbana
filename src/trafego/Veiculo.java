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
    private boolean estaParadoPorSemaforo = false;
    private boolean movimentouNoUltimoPasso = false;

    private int passosParadoConsecutivos = 0;
    private int tempoEntrada;
    private int tempoChegada = -1; // -1 significa que o veículo ainda não chegou
    private double consumoEnergetico = 0.0;
    private static final double CONSUMO_POR_DESLOCAMENTO = 1.0;
    private static final double CONSUMO_POR_PARADA = 0.5;

    // NOVO: Lista para registrar trajeto percorrido (intersecoes visitadas)
    private Lista<Intersecao> trajetoPercorrido = new Lista<>();

    public Veiculo(Intersecao origem, Intersecao destino, Lista<Intersecao> caminho, int tempoEntrada) {
        this.id = ++contadorId;
        this.origem = origem;
        this.destino = destino;
        this.caminho = caminho;
        this.posicaoAtual = 0;
        this.tempoEntrada = tempoEntrada;

        // Registra posição inicial no trajeto
        if (origem != null) {
            trajetoPercorrido.adicionar(origem);
        }
    }

    public int getId() {
        return (int) id;
    }

    public void mover() {
        if (!chegouAoDestino()) {
            posicaoAtual++;
            consumoEnergetico += CONSUMO_POR_DESLOCAMENTO;

            // Registrar a nova posição no trajeto
            Intersecao atual = getIntersecaoAtual();
            if (atual != null) {
                trajetoPercorrido.adicionar(atual);
            }

            System.out.println("Veículo " + id + " movendo para posição " + posicaoAtual + " no caminho");
        } else {
            System.out.println("Veículo " + id + " já chegou ao destino.");
        }
    }

    public void parar() {
        estaParadoPorSemaforo = true;
        consumoEnergetico += CONSUMO_POR_PARADA;
        System.out.println("Veículo " + id + " parado por semáforo.");
    }

    public void registrarChegada(int tempoAtual) {
        this.tempoChegada = tempoAtual;
    }

    public boolean chegouAoDestino() {
        return posicaoAtual >= caminho.tamanho() - 1;
    }

    public int getTempoViagem() {
        if (tempoChegada == -1) return -1;
        return tempoChegada - tempoEntrada;
    }

    public double getConsumoEnergetico() {
        return consumoEnergetico;
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

    public Lista<Intersecao> getTrajetoPercorrido() {
        return trajetoPercorrido;
    }

    @Override
    public String toString() {
        return String.format("Veículo [ID: %d, Origem: %d, Destino: %d]", id, origem.getVertice().getId(), destino.getVertice().getId());
    }

    public int getPosicaoAtual() {
        return posicaoAtual;
    }

    public Intersecao getDestino() {
        return destino;
    }

    public Lista<Intersecao> getCaminho() {
        return caminho;
    }

    public boolean isEstaParadoPorSemaforo() {
        return estaParadoPorSemaforo;
    }

    public Intersecao getIntersecaoAtual() {
        if (caminho != null && posicaoAtual < caminho.tamanho()) {
            return caminho.obter(posicaoAtual);
        }
        return null;
    }
}
