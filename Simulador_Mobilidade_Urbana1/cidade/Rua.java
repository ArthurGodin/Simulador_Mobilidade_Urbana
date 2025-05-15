package cidade;

public class Rua {
    private final String nome;
    private final Intersecao origem;
    private final Intersecao destino;
    private final int comprimento; // em metros
    private final int capacidade; // número máximo de veículos na rua

    public Rua(String nome, Intersecao origem, Intersecao destino, int comprimento, int capacidade) {
        this.nome = nome;
        this.origem = origem;
        this.destino = destino;
        this.comprimento = comprimento;
        this.capacidade = capacidade;
    }

    public String getNome() {
        return nome;
    }

    public Intersecao getOrigem() {
        return origem;
    }

    public Intersecao getDestino() {
        return destino;
    }

    public int getComprimento() {
        return comprimento;
    }

    public int getCapacidade() {
        return capacidade;
    }

    @Override
    public String toString() {
        return nome + " (" + origem.getId() + " → " + destino.getId() + ")";
    }
}
