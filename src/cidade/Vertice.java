package cidade;

public class Vertice {
    long id;
    double lat, lon;

    public Vertice(long id, double lat, double lon) {
        this.id = id;
        this.lat = lat;
        this.lon = lon;
    }

    public long getId() {
        return this.id;
    }

}

