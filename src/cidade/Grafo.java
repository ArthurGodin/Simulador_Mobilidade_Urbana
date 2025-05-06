package cidade;

import estruturas.ArrayList;
import estruturas.Lista;

import java.lang.reflect.Array;

import java.util.*;

public class Grafo {
    // Substituindo Map por uma lista de vértices, já que a ordem importa
    Lista<Vertice> vertices = new Lista<>();
    Lista<Aresta> arestas = new Lista<>();

    public void adicionarVertice(Vertice v) {
        vertices.adicionar(v);
    }

    public void adicionarAresta(Aresta a) {
        arestas.adicionar(a);
    }

    public Vertice buscarVertice(long id) {
        for (int i = 0; i < vertices.tamanho(); i++) {
            if (vertices.obter(i).id == id) {
                return vertices.obter(i);
            }
        }
        return null; // Não encontrou
    }

    public Aresta buscarAresta(Vertice origem, Vertice destino) {
        for (int i = 0; i < arestas.tamanho(); i++) {
            Aresta a = arestas.obter(i);
            if (a.origem == origem && a.destino == destino) {
                return a;
            }
        }
        return null; // Não encontrou
    }
}
