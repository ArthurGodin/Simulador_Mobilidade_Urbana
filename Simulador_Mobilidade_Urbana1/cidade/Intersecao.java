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

    public Intersecao(String id) {
        this.id = id;
        this.vertice = vertice;
        this.ruasSaida = new Lista<>();
        this.ruasEntrada = new Lista<>();
        this.filaVeiculos = new Fila<>();
        this.semaforo = new Semaforo(10, 5 , 10);
    }

    public String getId() {
        return id;
    }

    public void adicionarRuaSaida(Rua rua) {
        ruasSaida.adicionar(rua);
    }

    public void adicionarRuaEntrada(Rua rua) {
        ruasEntrada.adicionar(rua);
    }

    public Lista<Rua> getRuasSaida() {
        return ruasSaida;
    }

    public Lista<Rua> getRuasEntrada() {
        return ruasEntrada;
    }

    public Semaforo getSemaforo() {
        return semaforo;
    }

    public void setSemaforo(Semaforo semaforo) {
        this.semaforo = semaforo;
    }

    public Fila<Veiculo> getFilaVeiculos() {
        return filaVeiculos;
    }

    public Vertice getVertice(){
        return vertice;
    }



    @Override
    public String toString() {
        return "Intersecao " + id;
    }

    public void setVertice(Vertice vertice) {
        this.vertice = vertice;
    }
}
