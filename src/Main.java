import cidade.*;
import estruturas.*;
import heuristica.*;
import json.*;

public class Main {

    public static void main(String[] args) {
        try {
            // Carregar o grafo a partir do arquivo JSON
            Grafo grafo = LeitorOSMJson.carregar("json/Morada_do_Sol.json");

            // Converter o grafo em Lista<Intersecao> (estrutura customizada)
            Lista<Intersecao> intersecoes = new Lista<>(); // Inicializa a lista corretamente
            for (int i = 0; i < grafo.vertices.tamanho(); i++) {
                Vertice vertice = grafo.vertices.obter(i);
                Intersecao intersecao = new Intersecao(vertice.toString());
                intersecao.setVertice(vertice); // Atribui o vértice à interseção
                intersecoes.adicionar(intersecao);
            }



            // Inicializar a heurística desejada
            HeuristicaControle heuristica = new HeuristicaCicloFixo(10, 5, 10);

            // Criar o simulador com interseções, heurística e duração
            Simulador simulador = new Simulador(intersecoes, heuristica, 10, grafo);


            // Executar a simulação
            System.out.println("Iniciando simulação...");
            simulador.executar();
            System.out.println("Simulação concluída!");
        } catch (Exception e) {
            System.err.println("Erro ao carregar ou executar a simulação: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
