package trafego;

import cidade.Intersecao;
import estruturas.Lista;

public class Veiculo {
    private Intersecao origem;
    private Intersecao destino;
    private Lista<Intersecao> caminho;
    private int posicaoAtual;
    private int id; // Adicionando um ID único para cada veículo

    // Construtor
    public Veiculo(int id, Intersecao origem, Intersecao destino, Lista<Intersecao> caminho) {
        this.id = id; // Inicializa o ID
        this.origem = origem;
        this.destino = destino;
        this.caminho = caminho;
        this.posicaoAtual = 0; // Começa na origem
    }

    // Método para mover o veículo
    public void mover() {
        if (caminho != null && posicaoAtual < caminho.tamanho()) {
            posicaoAtual++;
            System.out.println("Veículo " + id + " se movendo para a posição: " + posicaoAtual + " de " + caminho.obter(posicaoAtual - 1));
        } else {
            System.out.println("Veículo " + id + " chegou ao destino " + caminho.obter(caminho.tamanho() - 1));
        }
    }

    // Método para verificar se chegou ao destino
    public boolean chegouAoDestino() {
        return posicaoAtual == caminho.tamanho() - 1;
    }

    // Retorna o caminho completo do veículo
    public Lista<Intersecao> getCaminho() {
        return caminho;
    }

    // Método para obter a interseção atual (posição atual no caminho)
    public Intersecao getIntersecaoAtual() {
        if (caminho != null && posicaoAtual < caminho.tamanho()) {
            return caminho.obter(posicaoAtual); // A interseção atual é a que corresponde à posição atual
        }
        return null; // Caso o veículo tenha terminado o caminho
    }

    // Método para obter o destino do veículo (última interseção do caminho)
    public Intersecao getDestino() {
        return destino; // O destino é a última interseção configurada para o veículo
    }

    // Métodos de acesso à origem e destino
    public Intersecao getOrigem() {
        return origem;
    }

    // Getter para a posição atual do veículo
    public int getPosicaoAtual() {
        return posicaoAtual;
    }

    // Método para resetar o veículo (caso seja necessário reiniciar a simulação)
    public void resetar() {
        this.posicaoAtual = 0;
    }

    // Getter para o ID do veículo
    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Veículo [ID: " + id + ", Origem: " + origem.getId() + ", Destino: " + destino.getId() + "]";
    }
}
