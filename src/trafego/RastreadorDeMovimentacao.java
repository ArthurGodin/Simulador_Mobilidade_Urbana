package trafego;

import cidade.Intersecao;
import estruturas.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.Map;
import java.util.HashMap;

public class RastreadorDeMovimentacao {

    // Classe interna que representa um registro de movimentação do veículo
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

    private Map<Long, Integer> ultimaPosicaoVeiculo = new HashMap<>(); // Veículo ID -> última posição no caminho
    private Map<Long, String> ultimoEstadoSemaforo = new HashMap<>();  // Interseção ID -> último estado do semáforo
    private Map<Long, Boolean> veiculoParadoNoVermelho = new HashMap<>(); // Veículo ID -> está parado no vermelho?

    private Lista<Movimentacao> movimentacoes = new ArrayList1<>(); // Lista de movimentações registradas
    private Set<Long> intersecoesComMudancaEstado = new HashSet<>(); // Interseções com mudança recente no semáforo
    private int tempoAtual; // Tempo atual da simulação

    // Define o tempo atual da simulação
    public void setTempoAtual(int tempoAtual) {
        this.tempoAtual = tempoAtual;
    }

    // Registra movimentação de veículo se posição mudou ou saiu do vermelho
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

    // Registra que veículo está parado no semáforo vermelho
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

    // Exibe todas movimentações registradas e limpa a lista
    public void exibirMovimentacoes() {
        System.out.println("\n>> MOVIMENTAÇÕES REGISTRADAS:");
        for (Movimentacao m : movimentacoes) {
            System.out.println(m);
        }
        movimentacoes.clean();
    }

    // Registra mudança de estado do semáforo para uma interseção
    public void registrarMudancaEstadoSemaforo(long idIntersecao, String novoEstado) {
        String estadoAnterior = ultimoEstadoSemaforo.get(idIntersecao);
        if (estadoAnterior == null || !estadoAnterior.equals(novoEstado)) {
            ultimoEstadoSemaforo.put(idIntersecao, novoEstado);
            intersecoesComMudancaEstado.add(idIntersecao);
        }
    }

    // Retorna interseções que tiveram mudança recente no semáforo (não modificável)
    public Set<Long> getIntersecoesComMudancaEstado() {
        return Collections.unmodifiableSet(intersecoesComMudancaEstado);
    }

    // Retorna o último estado do semáforo para uma interseção
    public String getUltimoEstadoSemaforo(long idIntersecao) {
        return ultimoEstadoSemaforo.getOrDefault(idIntersecao, "DESCONHECIDO");
    }

    // Limpa a lista de interseções com mudanças recentes
    public void limparMudancasEstado() {
        intersecoesComMudancaEstado.clear();
    }
}
