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

    public Lista<Aresta> obterArestas(Vertice origem) {
        Lista<Aresta> arestasDeOrigem = new Lista<>();
        for (int i = 0; i < arestas.tamanho(); i++) {
            Aresta a = arestas.obter(i);
            if (a.getOrigem() == origem) {
                arestasDeOrigem.adicionar(a);
            }
        }
        return arestasDeOrigem;
    }

    public Lista<Intersecao> converterParaIntersecoes() {
        Lista<Intersecao> intersecoes = new Lista<>();
        Lista<Long> ids = new Lista<>();

        // Criar interseções a partir dos vértices e associá-las aos vértices
        for (int i = 0; i < vertices.tamanho(); i++) {
            Vertice v = vertices.obter(i);
            Intersecao intersecao = new Intersecao(String.valueOf(v.getId()));
            intersecao.setVertice(v);
            intersecoes.adicionar(intersecao);
            ids.adicionar(v.getId());
            v.setIntersecao(intersecao);  // **Essa linha é ESSENCIAL para associar a interseção ao vértice**
        }

        // Associar ruas às interseções (mantém igual)
        for (int i = 0; i < arestas.tamanho(); i++) {
            Aresta a = arestas.obter(i);
            Vertice origem = a.getOrigem();
            Vertice destino = a.getDestino();

            Intersecao interOrigem = encontrarIntersecao(intersecoes, ids, origem.getId());
            Intersecao interDestino = encontrarIntersecao(intersecoes, ids, destino.getId());

            if (interOrigem != null && interDestino != null) {
                double distancia = calcularDistancia(origem, destino);
                Rua rua = new Rua("Rua " + interOrigem.getId() + "->" + interDestino.getId(), interOrigem, interDestino, 5, 10);

                interOrigem.adicionarRuaSaida(rua);
                interDestino.adicionarRuaEntrada(rua);
            }
        }

        return intersecoes;
    }


    private Intersecao encontrarIntersecao(Lista<Intersecao> intersecoes, Lista<Long> ids, long id) {
        for (int i = 0; i < ids.tamanho(); i++) {
            if (ids.obter(i) == id) {
                return intersecoes.obter(i);
            }
        }
        return null;
    }

    private double calcularDistancia(Vertice v1, Vertice v2) {
        double lat1 = v1.getLat();
        double lon1 = v1.getLon();
        double lat2 = v2.getLat();
        double lon2 = v2.getLon();
        double dx = lat2 - lat1;
        double dy = lon2 - lon1;
        return Math.sqrt(dx * dx + dy * dy) * 111000; // conversão simplificada para metros
    }
}