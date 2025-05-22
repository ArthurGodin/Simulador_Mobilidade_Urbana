package cidade;

public class Aresta {
    private Vertice origem, destino;
    private double peso;
    private boolean isOneway;

    public Aresta(Vertice origem, Vertice destino, boolean isOneway) {
        this.origem = origem;
        this.destino = destino;
        this.isOneway = isOneway;
        this.peso = calcularDistancia(origem, destino);
    }

    private double calcularDistancia(Vertice v1, Vertice v2) {
        double dx = v1.getLat() - v2.getLat();
        double dy = v1.getLon() - v2.getLon();
        return Math.sqrt(dx * dx + dy * dy);
    }

    public Vertice getOrigem() { return origem; }
    public Vertice getDestino() { return destino; }
    public double getPeso() { return peso; }
    public boolean isOneway() { return isOneway; }
}
