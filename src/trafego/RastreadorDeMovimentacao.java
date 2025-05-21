package trafego;

import cidade.Intersecao;
import estruturas.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.Map;
import java.util.HashMap;

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

    private Map<Long, Integer> ultimaPosicaoVeiculo = new HashMap<>(); // veiculoID -> indice da última posição
    private Map<Long, String> ultimoEstadoSemaforo = new HashMap<>();  // intersecaoID -> estado do semáforo
    private Map<Long, Boolean> veiculoParadoNoVermelho = new HashMap<>(); // veiculoID -> parado no vermelho

    private Lista<Movimentacao> movimentacoes = new ArrayList1<>();
    private Set<Long> intersecoesComMudancaEstado = new HashSet<>();
    private int tempoAtual;

    public void setTempoAtual(int tempoAtual) {
        this.tempoAtual = tempoAtual;
    }

    public void registrarMovimentacao(Veiculo veiculo, Intersecao intersecaoAtual, String estadoSemaforo) {
        long idVeiculo = veiculo.getId();
        int posicaoAtual = veiculo.getPosicaoAtual();
        long origemId = intersecaoAtual.getVertice().getId();
        long destinoId = veiculo.getDestino().getVertice().getId();

        Integer ultimaPos = ultimaPosicaoVeiculo.get(idVeiculo);
        if (ultimaPos == null || ultimaPos != posicaoAtual) {
            movimentacoes.adicionar(new Movimentacao(tempoAtual, idVeiculo, origemId, destinoId, estadoSemaforo));
            ultimaPosicaoVeiculo.put(idVeiculo, posicaoAtual);
        }

        Boolean parado = veiculoParadoNoVermelho.get(idVeiculo);
        if (parado != null && parado) {
            movimentacoes.adicionar(new Movimentacao(tempoAtual, idVeiculo, origemId, destinoId, estadoSemaforo + " (SAIU DO VERMELHO)"));
            veiculoParadoNoVermelho.put(idVeiculo, false);
        }
    }

    public void registrarParadaEmSemaforo(Veiculo veiculo, Intersecao intersecaoAtual) {
        long idVeiculo = veiculo.getId();
        int posicaoAtual = veiculo.getPosicaoAtual();
        long origemId = intersecaoAtual.getVertice().getId();
        long destinoId = veiculo.getDestino().getVertice().getId();

        Boolean parado = veiculoParadoNoVermelho.get(idVeiculo);
        Integer ultimaPos = ultimaPosicaoVeiculo.get(idVeiculo);
        if (parado == null || !parado || (ultimaPos == null || ultimaPos != posicaoAtual)) {
            movimentacoes.adicionar(new Movimentacao(tempoAtual, idVeiculo, origemId, destinoId, "VERMELHO (PARADO)"));
            veiculoParadoNoVermelho.put(idVeiculo, true);
            ultimaPosicaoVeiculo.put(idVeiculo, posicaoAtual);
        }
    }

    public void exibirMovimentacoes() {
        System.out.println("\n>> MOVIMENTAÇÕES REGISTRADAS:");
        for (Movimentacao m : movimentacoes) {
            System.out.println(m);
        }
        movimentacoes.clean();
    }

    public void registrarMudancaEstadoSemaforo(long idIntersecao, String novoEstado) {
        String estadoAnterior = ultimoEstadoSemaforo.get(idIntersecao);
        if (estadoAnterior == null || !estadoAnterior.equals(novoEstado)) {
            ultimoEstadoSemaforo.put(idIntersecao, novoEstado);
            intersecoesComMudancaEstado.add(idIntersecao);
        }
    }

    public Set<Long> getIntersecoesComMudancaEstado() {
        return Collections.unmodifiableSet(intersecoesComMudancaEstado);
    }

    public String getUltimoEstadoSemaforo(long idIntersecao) {
        if (ultimoEstadoSemaforo.containsKey(idIntersecao)) {
            return ultimoEstadoSemaforo.get(idIntersecao);
        }
        return "DESCONHECIDO";
    }

    public void limparMudancasEstado() {
        intersecoesComMudancaEstado.clear();
    }
}
