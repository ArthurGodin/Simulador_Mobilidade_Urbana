package estruturas;

import java.util.Iterator;

public class ArrayList1<T> extends Lista<T> implements Iterable<T> {
    private T[] elementos; // array interno para armazenar os elementos
    private int tamanho;   // número atual de elementos na lista

    // Construtor padrão com capacidade inicial de 10
    public ArrayList1() {
        this(10);
    }

    // Construtor com capacidade inicial personalizada
    @SuppressWarnings("unchecked")
    public ArrayList1(int capacidadeInicial) {
        elementos = (T[]) new Object[capacidadeInicial]; // criação do array genérico
        tamanho = 0;
    }

    // Adiciona elemento no final da lista, redimensionando se necessário
    public void adicionar(T elemento) {
        if (tamanho == elementos.length) {
            redimensionar(); // dobra o tamanho do array
        }
        elementos[tamanho++] = elemento;
    }

    // Retorna o elemento na posição informada, verificando índice
    public T obter(int indice) {
        verificarIndice(indice);
        return elementos[indice];
    }

    // Substitui o elemento na posição informada
    public void definir(int indice, T elemento) {
        verificarIndice(indice);
        elementos[indice] = elemento;
    }

    // Remove elemento na posição e desloca os subsequentes para esquerda
    public T remover(int indice) {
        verificarIndice(indice);
        T removido = elementos[indice];
        for (int i = indice; i < tamanho - 1; i++) {
            elementos[i] = elementos[i + 1];
        }
        elementos[--tamanho] = null; // evita vazamento de memória
        return removido;
    }

    // Retorna a quantidade de elementos atuais
    public int tamanho() {
        return tamanho;
    }

    // Verifica se a lista está vazia
    public boolean estaVazio() {
        return tamanho == 0;
    }

    // Verifica se a lista contém determinado elemento
    public boolean contem(T elemento) {
        for (int i = 0; i < tamanho; i++) {
            if (elementos[i].equals(elemento)) {
                return true;
            }
        }
        return false;
    }

    // Redimensiona o array interno para o dobro do tamanho atual
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

    // Iterator para permitir uso em foreach e outras operações iterativas
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
