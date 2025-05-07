import cidade .*;
import json .*;

public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println(ArchLinux);
        Grafo grafo = LeitorOSMJson.carregar("morada-do-sol.json");

        // Exibe informações sobre o grafo
        System.out.println("Vértices carregados: " + grafo.vertices.size());
        System.out.println("Arestas carregadas: " + grafo.arestas.size());

        // Exemplo de acesso aos vértices e arestas
        for (Aresta aresta : grafo.arestas) {
            System.out.println("Aresta de " + aresta.origem.id + " para " + aresta.destino.id);
            System.out.println("Distância: " + aresta.peso);
            System.out.println("Mão única? " + aresta.isOneway);
        }
    }
}
