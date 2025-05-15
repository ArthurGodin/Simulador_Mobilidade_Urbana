package estruturas;

public class ListaCircular<T> {
    private No<T> inicio;
    private int tamanho;

    private static class No<T> {
        T dado;
        No<T> proximo;

        No(T dado) {
            this.dado = dado;
            this.proximo = null;
        }
    }
    public ListaCircular() {
        inicio = null;
        tamanho = 0;
    }
    public void adicionar(T elemento) {
        No<T> novo = new No<>(elemento);

        if (estaVazia()) {
            inicio = novo;
            novo.proximo = inicio;
        } else {
            No<T> ultimo = inicio;
            while (ultimo.proximo != inicio) {
                ultimo = ultimo.proximo;
            }
            ultimo.proximo = novo;
            novo.proximo = inicio;
        }
        tamanho++;
    }
    public T obter(int indice) {
        if (indice < 0 || indice >= tamanho) {
            throw new IndexOutOfBoundsException("Índice inválido: " + indice);
        }

        No<T> atual = inicio;
        for (int i = 0; i < indice; i++) {
            atual = atual.proximo;
        }
        return atual.dado;
    }

    public int tamanho() {
        return tamanho;
    }

    public boolean estaVazia() {
        return tamanho == 0;
    }
    @Override
    public String toString() {
        if (estaVazia()) return "[]";

        StringBuilder sb = new StringBuilder("[");
        No<T> atual = inicio;
        do {
            sb.append(atual.dado);
            if (atual.proximo != inicio) sb.append(", ");
            atual = atual.proximo;
        } while (atual != inicio);
        sb.append("]");
        return sb.toString();
    }
}