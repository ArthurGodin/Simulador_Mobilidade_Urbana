package trafego;

import cidade.Intersecao;
import semaforo.Semaforo;

import java.util.*;

public class RastreadorDeMovimentacao {

    private static class Movimentacao {
        int tempo;
        long idVeiculo;
        long origemId;
        long destinoId;
        String estadoSemaforo;

        Movimentacao(int tempo, long idVeiculo, long origemId, long destinoId, String estadoSemaforo) {
            this.tempo = tempo;
            this.idVeiculo = idVeiculo;
            this.origemId = origemId;
            this.destinoId = destinoId;
            this.estadoSemaforo = estadoSemaforo;
        }

        @Override
        public String toString() {
            return String.format("Tempo %d - Veículo ID %d movendo-se de %d para %d | Semáforo atual: %s",
                    tempo, idVeiculo, origemId, destinoId, estadoSemaforo);
        }
    }

    private Map<Long, Integer> ultimaPosicaoVeiculo = new HashMap<>(); // veiculoID -> ultima posicao
    private Map<Long, String> ultimoEstadoSemaforo = new HashMap<>();  // intersecaoID -> estado
    private List<Movimentacao> movimentacoes = new ArrayList<>();
    private int tempoAtual;

    private Set<Long> intersecoesComMudancaEstado = new HashSet<>();

    public void setTempoAtual(int tempoAtual) {
        this.tempoAtual = tempoAtual;
    }

    public void iniciarNovoPasso() {
        intersecoesComMudancaEstado.clear();
        movimentacoes.clear();
    }

    public Set<Long> getIntersecoesComMudancaEstado() {
        return intersecoesComMudancaEstado;
    }

    public void registrarMovimentacao(Veiculo veiculo, Intersecao intersecaoAtual, String estadoSemaforo) {
        long idVeiculo = veiculo.getId();
        int posicaoAtual = veiculo.getPosicaoAtual();
        long origemId = intersecaoAtual.getVertice().getId();
        long destinoId = veiculo.getDestino().getVertice().getId();

        Integer ultimaPos = ultimaPosicaoVeiculo.get(idVeiculo);
        if (ultimaPos == null || ultimaPos != posicaoAtual) {
            movimentacoes.add(new Movimentacao(tempoAtual, idVeiculo, origemId, destinoId, estadoSemaforo));
            ultimaPosicaoVeiculo.put(idVeiculo, posicaoAtual);
        }

        String estadoAnterior = ultimoEstadoSemaforo.get(origemId);
        if (estadoAnterior == null || !estadoAnterior.equals(estadoSemaforo)) {
            System.out.printf("Tempo %d - Semáforo na Interseção %d mudou para %s%n", tempoAtual, origemId, estadoSemaforo);
            ultimoEstadoSemaforo.put(origemId, estadoSemaforo);
            intersecoesComMudancaEstado.add(origemId);
        }
    }

    public void registrarParadaEmSemaforo(Veiculo veiculo, Intersecao intersecaoAtual) {
        long idVeiculo = veiculo.getId();
        long origemId = intersecaoAtual.getVertice().getId();
        long destinoId = veiculo.getDestino().getVertice().getId();
        String estadoSemaforo = "VERMELHO";

        Integer ultimaPos = ultimaPosicaoVeiculo.get(idVeiculo);
        if (ultimaPos == null || ultimaPos != veiculo.getPosicaoAtual()) {
            movimentacoes.add(new Movimentacao(tempoAtual, idVeiculo, origemId, destinoId, estadoSemaforo + " (PARADO)"));
            ultimaPosicaoVeiculo.put(idVeiculo, veiculo.getPosicaoAtual());
        }
    }

    public void exibirMovimentacoes() {
        System.out.println("\n>> MOVIMENTAÇÕES REGISTRADAS:");
        for (Movimentacao m : movimentacoes) {
            System.out.println(m);
        }
    }
}
