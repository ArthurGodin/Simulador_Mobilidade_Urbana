package estruturas;

import java.util.ArrayList;
import java.util.HashMap;

public class MeuGrafo {
    public class No {
        public int id;
        public double lat, lon;

        public No(int id, double lat, double lon) {
            this.id = id;
            this.lat = lat;
            this.lon = lon;
        }
    }

    public class Aresta {
        public int origem;
        public int destino;
        public double peso;

        public Aresta(int origem, int destino, double peso) {
            this.origem = origem;
            this.destino = destino;
            this.peso = peso;
        }
    }

    private HashMap<Integer, No> nos = new HashMap<>();
    private HashMap<Integer, ArrayList<Aresta>> adj = new HashMap<>();

    public void adicionarNo(int id, double lat, double lon) {
        nos.put(id, new No(id, lat, lon));
        adj.put(id, new ArrayList<>());
    }

    public void adicionarAresta(int origem, int destino, double peso) {
        adj.get(origem).add(new Aresta(origem, destino, peso));
    }

    public ArrayList<Aresta> getVizinhos(int id) {
        return adj.get(id);
    }

    public HashMap<Integer, No> getNos() {
        return nos;
    }
}
