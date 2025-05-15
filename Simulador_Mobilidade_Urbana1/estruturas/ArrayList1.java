package estruturas;

import java.util.Iterator;

public class ArrayList1<T> extends Lista<T> implements Iterable<T> {
    private T[] elementos;
    private int tamanho;

    // Construtor com capacidade inicial padrão
    public ArrayList1() {
        this(10); // capacidade inicial padrão
    }

    // Construtor com capacidade personalizada
    @SuppressWarnings("unchecked")
    public ArrayList1(int capacidadeInicial) {
        elementos = (T[]) new Object[capacidadeInicial];
        tamanho = 0;
    }

    // Adiciona um elemento ao final da lista
    public void adicionar(T elemento) {
        if (tamanho == elementos.length) {
            redimensionar(); // dobra o tamanho
        }
        elementos[tamanho++] = elemento;
    }

    // Retorna o elemento na posição especificada
    public T obter(int indice) {
        verificarIndice(indice);
        return elementos[indice]; // O tipo aqui é T, que será Intersecao no seu caso
    }

    // Define (substitui) o elemento na posição especificada
    public void definir(int indice, T elemento) {
        verificarIndice(indice);
        elementos[indice] = elemento;
    }

    // Remove um elemento da posição especificada e desloca os seguintes
    public T remover(int indice) {
        verificarIndice(indice);
        T removido = elementos[indice];
        for (int i = indice; i < tamanho - 1; i++) {
            elementos[i] = elementos[i + 1];
        }
        elementos[--tamanho] = null; // evita memory leak
        return removido;
    }

    // Retorna o número de elementos na lista
    public int tamanho() {
        return tamanho;
    }

    // Verifica se está vazio
    public boolean estaVazio() {
        return tamanho == 0;
    }

    // Verifica se contém determinado elemento
    public boolean contem(T elemento) {
        for (int i = 0; i < tamanho; i++) {
            if (elementos[i].equals(elemento)) {
                return true;
            }
        }
        return false;
    }

    // Redimensiona o array interno para o dobro do tamanho
    @SuppressWarnings("unchecked")
    private void redimensionar() {
        T[] novoArray = (T[]) new Object[elementos.length * 2];
        for (int i = 0; i < elementos.length; i++) {
            novoArray[i] = elementos[i];
        }
        elementos = novoArray;
    }

    // Verifica se o índice está dentro dos limites válidos
    private void verificarIndice(int indice) {
        if (indice < 0 || indice >= tamanho) {
            throw new IndexOutOfBoundsException("Índice inválido: " + indice);
        }
    }

    // Implementação do método iterator para a interface Iterable
    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private int indice = 0;

            @Override
            public boolean hasNext() {
                return indice < tamanho;
            }

            @Override
            public T next() {
                return elementos[indice++];
            }
        };
    }
}
