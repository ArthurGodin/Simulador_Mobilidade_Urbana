package trafego;

import cidade.Intersecao;
import semaforo.Semaforo;
import java.util.ArrayList;
import java.util.List;

public class RastreadorDeMovimentacao {
    // Lista para armazenar as movimentações registradas
    private List<String> movimentacoes;

    public RastreadorDeMovimentacao() {
        movimentacoes = new ArrayList<>();
    }

    // Método para rastrear o movimento de um veículo
    public void registrarMovimentacao(Veiculo veiculo, Intersecao intersecaoAtual, String estadoSemaforo) {
        String movimento = "Veículo " + veiculo.toString() +
                " movendo-se de " + intersecaoAtual.getVertice().getId() +
                " para " + veiculo.getDestino().getVertice().getId() +
                " | Semáforo atual: " + estadoSemaforo;
        movimentacoes.add(movimento);
    }

    // Método para registrar a parada do veículo no semáforo
    public void registrarParadaEmSemaforo(Veiculo veiculo, Intersecao intersecaoAtual) {
        String parada = "Veículo " + veiculo.toString() +
                " parou na interseção " + intersecaoAtual.getVertice().getId() +
                " devido ao semáforo vermelho.";
        movimentacoes.add(parada);
    }

    // Método para exibir todas as movimentações registradas
    public void exibirMovimentacoes() {
        System.out.println("\n>> MOVIMENTAÇÕES REGISTRADAS:");
        for (String movimento : movimentacoes) {
            System.out.println(movimento);
        }
    }
}
