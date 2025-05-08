package cidade;

import estruturas.*;

public class Grafo {

    public Lista<Vertice> vertices = new Lista<>();
    private Lista<Aresta> arestas = new Lista<>();

    public void adicionarVertice(Vertice v) {
        vertices.adicionar(v);
    }

    public void adicionarAresta(Aresta a) {
        arestas.adicionar(a);
    }

    public void adicionarAresta(Vertice origem, Vertice destino, boolean isOneway) {
        Aresta a = new Aresta(origem, destino, isOneway);
        arestas.adicionar(a);
    }

    public Vertice buscarVertice(long id) {
        for (int i = 0; i < vertices.tamanho(); i++) {
            Vertice v = vertices.obter(i);
            if (v.getId() == id) return v;
        }
        return null;
    }

    public Aresta buscarAresta(Vertice origem, Vertice destino) {
        for (int i = 0; i < arestas.tamanho(); i++) {
            Aresta a = arestas.obter(i);
            if (a.getOrigem() == origem && a.getDestino() == destino) return a;
        }
        return null;
    }

    public Aresta obterArestas(Vertice origem , Vertice destino) {
        for (int i = 0; i < arestas.tamanho(); i++) {
            Aresta a = arestas.obter(i);
            if (a.getOrigem() == origem && a.getDestino() == destino) return a;
        }
        return null;
    }
}