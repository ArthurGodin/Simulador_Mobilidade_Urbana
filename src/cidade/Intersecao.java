package cidade;

import estruturas.*;
import trafego.*;
import semaforo.*;

public class Intersecao {
    private final String id;
    private Vertice vertice = null;
    private final Lista<Rua> ruasSaida;
    private final Lista<Rua> ruasEntrada;
    private Semaforo semaforo;
    private final Fila<Veiculo> filaVeiculos;

    // Construtor inicializa as listas e o semáforo padrão
    public Intersecao(String id) {
        this.id = id;
        this.ruasSaida = new Lista<>();
        this.ruasEntrada = new Lista<>();
        this.filaVeiculos = new Fila<>();
        this.semaforo = new Semaforo(10, 5, 10);
    }

    // Retorna o id da interseção
    public String getId() {
        return id;
    }

    // Adiciona rua de saída
    public void adicionarRuaSaida(Rua rua) {
        ruasSaida.adicionar(rua);
    }

    // Adiciona rua de entrada
    public void adicionarRuaEntrada(Rua rua) {
        ruasEntrada.adicionar(rua);
    }

    // Retorna lista de ruas de saída
    public Lista<Rua> getRuasSaida() {
        return ruasSaida;
    }

    // Retorna lista de ruas de entrada
    public Lista<Rua> getRuasEntrada() {
        return ruasEntrada;
    }

    // Retorna o semáforo associado
    public Semaforo getSemaforo() {
        return semaforo;
    }

    // Define um novo semáforo para a interseção
    public void setSemaforo(Semaforo semaforo) {
        this.semaforo = semaforo;
    }

    // Retorna a fila de veículos esperando na interseção
    public Fila<Veiculo> getFilaVeiculos() {
        return filaVeiculos;
    }

    // Retorna o vértice associado à interseção
    public Vertice getVertice() {
        return vertice;
    }

    // Define o vértice associado à interseção
    public void setVertice(Vertice vertice) {
        this.vertice = vertice;
    }

    @Override
    public String toString() {
        return "Intersecao " + id;
    }
}
