package estruturas;

public class ListaEncadeada<T> {
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

    public ListaEncadeada() {
        inicio = null;
        tamanho = 0;
    }

    public void adicionar(T elemento) {
        No<T> novo = new No<>(elemento);
        if (inicio == null) {
            inicio = novo;
        } else {
            No<T> atual = inicio;
            while (atual.proximo != null) {
                atual = atual.proximo;
            }
            atual.proximo = novo;
        }
        tamanho++;
    }

    public T obter(int indice) {
        if (indice < 0 || indice >= tamanho) {
            throw new IndexOutOfBoundsException("Índice inválido");
        }
        No<T> atual = inicio;
        for (int i = 0; i < indice; i++) {
            atual = atual.proximo;
        }
        return atual.dado;
    }

    public T remover(int indice) {
        if (indice < 0 || indice >= tamanho) {
            throw new IndexOutOfBoundsException("Índice inválido");
        }
        T dado;
        if (indice == 0) {
            dado = inicio.dado;
            inicio = inicio.proximo;
        } else {
            No<T> anterior = inicio;
            for (int i = 0; i < indice - 1; i++) {
                anterior = anterior.proximo;
            }
            No<T> atual = anterior.proximo;
            dado = atual.dado;
            anterior.proximo = atual.proximo;
        }
        tamanho--;
        return dado;
    }

    public int tamanho() {
        return tamanho;
    }

    public boolean estaVazia() {
        return tamanho == 0;
    }
}