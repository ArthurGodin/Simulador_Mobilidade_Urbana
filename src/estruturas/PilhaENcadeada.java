package estruturas;

public class PilhaEncadeada<T> {
    private No<T> topo;
    private int tamanho;

    private static class No<T> {
        T dado;
        No<T> abaixo;

        No(T dado) {
            this.dado = dado;
            this.abaixo = null;
        }
    }

    public PilhaEncadeada() {
        topo = null;
        tamanho = 0;
    }

    public void empilhar(T elemento) {
        No<T> novo = new No<>(elemento);
        novo.abaixo = topo;
        topo = novo;
        tamanho++;
    }

    public T desempilhar() {
        if (topo == null) {
            return null;
        }
        T dado = topo.dado;
        topo = topo.abaixo;
        tamanho--;
        return dado;
    }

    public T topo() {
        return (topo != null) ? topo.dado : null;
    }

    public boolean estaVazia() {
        return tamanho == 0;
    }

    public int tamanho() {
        return tamanho;
    }
}
