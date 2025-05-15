package json;

import org.json.*;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import cidade.*;
import estruturas.*;

public class LeitorOSMJson {

    public static Grafo carregar(String caminho) throws JSONException, IOException {
        InputStream inputStream = LeitorOSMJson.class.getClassLoader().getResourceAsStream(caminho);

        if (inputStream == null) {
            throw new IllegalArgumentException("Arquivo JSON não encontrado: " + caminho);
        }

        String conteudo = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        JSONObject json = new JSONObject(conteudo);
        JSONArray elementos = json.getJSONArray("elements");

        Grafo grafo = new Grafo();
        Lista<Vertice> mapaNos = new Lista<>();

        for (int i = 0; i < elementos.length(); i++) {
            JSONObject elemento = elementos.getJSONObject(i);
            String tipo = elemento.getString("type");

            if (tipo.equals("node")) {
                // Criar um vértice para cada node
                long id = elemento.getLong("id");
                double lat = elemento.getDouble("lat");
                double lon = elemento.getDouble("lon");
                Vertice v = new Vertice(id, lat, lon);
                grafo.adicionarVertice(v);
                mapaNos.adicionar(v);
            }

            // Verificação de "way" e seus nodes
            if (tipo.equals("way") && elemento.has("nodes")) {
                JSONArray nos = elemento.getJSONArray("nodes");
                boolean isOneway = elemento.has("tags") && elemento.getJSONObject("tags").has("oneway");

                // Adicionar as arestas de cada par de nodes consecutivos
                for (int j = 0; j < nos.length() - 1; j++) {
                    long origemId = nos.getLong(j);
                    long destinoId = nos.getLong(j + 1);
                    Vertice origem = buscarVerticePorId(mapaNos, origemId);
                    Vertice destino = buscarVerticePorId(mapaNos, destinoId);

                    // Verificar se os vértices realmente existem antes de adicionar a aresta
                    if (origem != null && destino != null) {
                        grafo.adicionarAresta(origem, destino, isOneway);
                    } else {
                        System.err.println("Erro ao adicionar aresta: um dos vértices não encontrado.");
                    }
                }
            }
        }

        return grafo;
    }

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
