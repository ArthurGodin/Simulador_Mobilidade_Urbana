package json;

import org.json.*;
import java.nio.file.*;
import java.util.*;
import cidade.*;
import estruturas.*;

import static estruturas.Lista.buscarVerticePorId;


public class LeitorOSMJson {

    public static Grafo carregar(String caminho) throws Exception {
        // Lê o arquivo JSON
        String conteudo = new String(Files.readAllBytes(Paths.get(caminho)));
        JSONObject json = new JSONObject(conteudo);
        JSONArray elementos = json.getJSONArray("elements");

        // Criação do grafo
        Grafo grafo = new Grafo();
        Lista<Vertice> mapaNos = new Lista<>();
        Lista<long[]> conexoesTemporarias = new Lista<>();

        // Processamento dos nodes
        for (int i = 0; i < elementos.length(); i++) {
            JSONObject elemento = elementos.getJSONObject(i);
            String tipo = elemento.getString("type");

            if (tipo.equals("node")) {
                long id = elemento.getLong("id");
                double lat = elemento.getDouble("lat");
                double lon = elemento.getDouble("lon");
                Vertice v = new Vertice(id, lat, lon);
                grafo.adicionarVertice(v);
                mapaNos.adicionar(v);
            }

            // Processamento das ways
            if (tipo.equals("way") && elemento.has("nodes")) {
                JSONArray nos = elemento.getJSONArray("nodes");
                boolean isOneway = elemento.has("tags") && elemento.getJSONObject("tags").has("oneway");
                for (int j = 0; j < nos.length() - 1; j++) {
                    long origemId = nos.getLong(j);
                    long destinoId = nos.getLong(j + 1);
                    Vertice origem = buscarVerticePorId(mapaNos, origemId);
                    Vertice destino = buscarVerticePorId(mapaNos, destinoId);
                    if (origem != null && destino != null) {
                        grafo.adicionarAresta(new Aresta(origem, destino, isOneway));
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

