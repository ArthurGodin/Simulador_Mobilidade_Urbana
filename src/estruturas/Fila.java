package estruturas;

import java.util.Iterator;

public class Fila<T> implements Iterable<T> {

    // Classe interna que representa um nó da fila
    private static class No<T> {
        T dado;
        No<T> proximo;

        No(T dado) {
            this.dado = dado;
        }
    }

    private No<T> primeiro; // primeiro elemento da fila
    private No<T> ultimo;   // último elemento da fila
    private int tamanho;    // quantidade de elementos na fila

    // Adiciona um elemento ao final da fila
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

    // Remove e retorna o elemento do início da fila
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

    // Retorna o elemento no índice especificado (sem remover)
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

    // Retorna o elemento da frente da fila sem remover
    public T frente() {
        return estaVazia() ? null : primeiro.dado;
    }

    // Verifica se a fila está vazia
    public boolean estaVazia() {
        return tamanho == 0;
    }

    // Retorna o tamanho atual da fila
    public int tamanho() {
        return tamanho;
    }

    // Implementa o iterator para permitir foreach e iteração simples
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
