package cidade;

public class Rua {
    private final String nome;
    private final Intersecao origem;
    private final Intersecao destino;
    private final int comprimento; // comprimento da rua em metros
    private final int capacidade;  // capacidade máxima de veículos

    // Construtor recebe nome, origem, destino, comprimento e capacidade
    public Rua(String nome, Intersecao origem, Intersecao destino, int comprimento, int capacidade) {
        this.nome = nome;
        this.origem = origem;
        this.destino = destino;
        this.comprimento = comprimento;
        this.capacidade = capacidade;
    }

    // Retorna o nome da rua
    public String getNome() {
        return nome;
    }

    // Retorna a interseção de origem da rua
    public Intersecao getOrigem() {
        return origem;
    }

    // Retorna a interseção de destino da rua
    public Intersecao getDestino() {
        return destino;
    }

    // Retorna o comprimento da rua
    public int getComprimento() {
        return comprimento;
    }

    // Retorna a capacidade máxima da rua
    public int getCapacidade() {
        return capacidade;
    }

    @Override
    public String toString() {
        return nome + " (" + origem.getId() + " → " + destino.getId() + ")";
    }
}
