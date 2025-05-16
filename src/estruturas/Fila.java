package estruturas;

import java.util.Iterator;

public class Fila<T> implements Iterable<T> {




    private static class No<T> {
        T dado;
        No<T> proximo;

        No(T dado) {
            this.dado = dado;
        }
    }

    private No<T> primeiro;
    private No<T> ultimo;
    private int tamanho;

    public void enfileirar(T elemento) {
        No<T> novoNo = new No<>(elemento);
        if (estaVazia()) {
            primeiro = novoNo;
        } else {
            ultimo.proximo = novoNo;
        }
        ultimo = novoNo;
        tamanho++;
    }

    public T desenfileirar() {
        if (estaVazia()) return null;
        T elemento = primeiro.dado;
        primeiro = primeiro.proximo;
        if (primeiro == null) {
            ultimo = null;
        }
        tamanho--;
        return elemento;
    }


    public T obter(int indice) {
        if (indice < 0 || indice >= tamanho) {
            throw new IndexOutOfBoundsException("Índice fora do limite da fila");
        }
        No<T> atual = primeiro;
        int pos = 0;
        while (pos < indice) {
            atual = atual.proximo;
            pos++;
        }
        return atual.dado;
    }



    public T frente() {
        return estaVazia() ? null : primeiro.dado;
    }

    public boolean estaVazia() {
        return tamanho == 0;
    }

    public int tamanho() {
        return tamanho;
    }

    // Implementação de Iterable<T>
    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private No<T> atual = primeiro;

            @Override
            public boolean hasNext() {
                return atual != null;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new java.util.NoSuchElementException();
                }
                T dado = atual.dado;
                atual = atual.proximo;
                return dado;
            }
        };
    }
}
