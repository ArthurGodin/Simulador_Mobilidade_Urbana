package cidade;

/**
 * Representa uma aresta (ligação) entre dois vértices no grafo.
 * Cada aresta possui origem, destino, peso (distância) e indicação se é unidirecional.
 */
public class Aresta {
    private final Vertice origem;
    private final Vertice destino;
    private final double peso;
    private final boolean isOneway;

    //Construtor que inicializa a aresta entre dois vértices e calcula o peso (distância).
     
    public Aresta(Vertice origem, Vertice destino, boolean isOneway) {
        this.origem = origem;
        this.destino = destino;
        this.isOneway = isOneway;
        this.peso = calcularDistancia(origem, destino);
    }

    // Calcula a distância euclidiana simples entre dois vértices.
    private double calcularDistancia(Vertice v1, Vertice v2) {
        double dx = v1.getLat() - v2.getLat();
        double dy = v1.getLon() - v2.getLon();
        return Math.sqrt(dx * dx + dy * dy);
    }

    // Getters padrão, mantendo a imutabilidade dos atributos
    public Vertice getOrigem() {
        return origem;
    }

    public Vertice getDestino() {
        return destino;
    }

    public double getPeso() {
        return peso;
    }

    public boolean isOneway() {
        return isOneway;
    }
}
