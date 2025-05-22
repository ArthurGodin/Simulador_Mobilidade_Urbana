package cidade;

public class Vertice {
    private long id;
    private double lat, lon;      // latitude e longitude
    private Intersecao intersecao; // referência à interseção associada

    // Construtor para criar vértice com id, latitude e longitude
    public Vertice(long id, double lat, double lon) {
        this.id = id;
        this.lat = lat;
        this.lon = lon;
    }

    // Retorna o id do vértice
    public long getId() {
        return id;
    }

    // Retorna a latitude do vértice
    public double getLat() {
        return lat;
    }

    // Retorna a longitude do vértice
    public double getLon() {
        return lon;
    }

    // Define a interseção associada a este vértice
    public void setIntersecao(Intersecao intersecao){
        this.intersecao = intersecao;
    }

    // Retorna a interseção associada a este vértice
    public Intersecao getIntersecao(){
        return intersecao;
    }

    // Representação em string para facilitar depuração
    @Override
    public String toString(){
        return "vertice( id = " + id + ", lat = " + lat + ", lon = " + lon + ")";
    }
}
