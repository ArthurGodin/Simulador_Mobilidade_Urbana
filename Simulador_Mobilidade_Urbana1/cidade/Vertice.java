package cidade;

public class Vertice {
    private long id;
    private double lat, lon;
    private Intersecao intersecao;

    public Vertice(long id, double lat, double lon) {
        this.id = id;
        this.lat = lat;
        this.lon = lon;
    }

    public long getId() {
        return id;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public void setIntersecao(Intersecao intersecao){
        this.intersecao = intersecao;
    }

    public Intersecao getIntersecao(){
        return new Intersecao(this.toString());
    }
}
