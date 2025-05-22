package json;

import org.json.*;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import cidade.*;
import estruturas.*;

public class LeitorOSMJson {

    // Método principal para carregar o grafo a partir do arquivo JSON
    public static Grafo carregar(String caminho) throws JSONException, IOException {
        InputStream inputStream = LeitorOSMJson.class.getClassLoader().getResourceAsStream(caminho);

        // Verifica se o arquivo existe
        if (inputStream == null) {
            throw new IllegalArgumentException("Arquivo JSON não encontrado: " + caminho);
        }

        // Lê todo o conteúdo do arquivo como String
        String conteudo = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);

        // Parseia o JSON
        JSONObject json = new JSONObject(conteudo);
        JSONArray elementos = json.getJSONArray("elements");

        Grafo grafo = new Grafo();
        Lista<Vertice> mapaNos = new Lista<>();

        // Percorre todos os elementos do JSON
        for (int i = 0; i < elementos.length(); i++) {
            JSONObject elemento = elementos.getJSONObject(i);
            String tipo = elemento.getString("type");

            // Se for um nó, cria um vértice e adiciona ao grafo e à lista
            if (tipo.equals("node")) {
                long id = elemento.getLong("id");
                double lat = elemento.getDouble("lat");
                double lon = elemento.getDouble("lon");
                Vertice v = new Vertice(id, lat, lon);
                grafo.adicionarVertice(v);
                mapaNos.adicionar(v);
            }

            // Se for uma "way" com nodes, cria arestas entre os nós consecutivos
            if (tipo.equals("way") && elemento.has("nodes")) {
                JSONArray nos = elemento.getJSONArray("nodes");

                // Verifica se a way é one-way através das tags
                boolean isOneway = elemento.has("tags") && elemento.getJSONObject("tags").has("oneway");

                // Cria arestas para cada par consecutivo de nodes
                for (int j = 0; j < nos.length() - 1; j++) {
                    long origemId = nos.getLong(j);
                    long destinoId = nos.getLong(j + 1);

                    Vertice origem = buscarVerticePorId(mapaNos, origemId);
                    Vertice destino = buscarVerticePorId(mapaNos, destinoId);

                    // Só adiciona se ambos os vértices existirem
                    if (origem != null && destino != null) {
                        grafo.adicionarAresta(origem, destino, isOneway);
                    } else {
                        System.err.println("Erro ao adicionar aresta: vértice não encontrado.");
                    }
                }
            }
        }

        return grafo;
    }

    // Busca um vértice na lista pelo id
    private static Vertice buscarVerticePorId(Lista<Vertice> lista, long id) {
        for (int i = 0; i < lista.tamanho(); i++) {
            Vertice v = lista.obter(i);
            if (v.getId() == id) {
                return v;
            }
        }
        return null;
    }
}
