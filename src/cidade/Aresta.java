package cidade;

public class Aresta {
    Vertice origem, destino;
    double peso; // Ex: distância Euclidiana
    boolean isOneway;

    public Aresta(Vertice origem, Vertice destino, boolean isOneway) {
        this.origem = origem;
        this.destino = destino;
        this.isOneway = isOneway;
        this.peso = calcularDistancia(origem, destino);
    }

    private double calcularDistancia(Vertice v1, Vertice v2) {
        double dx = v1.lat - v2.lat;
        double dy = v1.lon - v2.lon;
        return Math.sqrt(dx * dx + dy * dy);  // Distância Euclidiana
    }
}


