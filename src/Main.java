
import cidade.*;
import estruturas.*;
import heuristica.*;
import semaforo.*;
import trafego.*;

public class Main {
    public static void main(String[] args) {
        // Criar interseções
        Intersecao intersecaoA = new Intersecao("A");
        Intersecao intersecaoB = new Intersecao("B");
        Intersecao intersecaoC = new Intersecao("C");

        // Criar ruas
        Rua ruaAB = new Rua("Rua AB", intersecaoA, intersecaoB, 500, 10);
        Rua ruaBC = new Rua("Rua BC", intersecaoB, intersecaoC, 300, 8);
        Rua ruaCA = new Rua("Rua CA", intersecaoC, intersecaoA, 400, 9);

        // Adicionar ruas às interseções
        intersecaoA.adicionarRuaSaida(ruaAB);
        intersecaoB.adicionarRuaEntrada(ruaAB);

        intersecaoB.adicionarRuaSaida(ruaBC);
        intersecaoC.adicionarRuaEntrada(ruaBC);

        intersecaoC.adicionarRuaSaida(ruaCA);
        intersecaoA.adicionarRuaEntrada(ruaCA);

        // Criar lista de interseções (usando estrutura própria)
        Lista<Intersecao> intersecoes = new Lista<>();
        intersecoes.adicionar(intersecaoA);
        intersecoes.adicionar(intersecaoB);
        intersecoes.adicionar(intersecaoC);

        // Definir heurística
        HeuristicaControle heuristica = new controle.HeuristicaCicloFixo(10, 5, 10);

        // Criar simulador
        Simulador simulador = new Simulador(intersecoes, heuristica, 100);

        // Executar simulação
        simulador.executar();
    }
}


