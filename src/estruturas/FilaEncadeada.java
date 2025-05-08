package estruturas;

public class FilaEncadeada<T> {
    private No<T> inicio;
    private No<T> fim;
    private int tamanho;

    private static class No<T> {
        T dado;
        No<T> proximo;

        No(T dado) {
            this.dado = dado;
            this.proximo = null;
        }
    }

    public FilaEncadeada() {
        inicio = null;
        fim = null;
        tamanho = 0;
    }

    public void enfileirar(T elemento) {
        No<T> novo = new No<>(elemento);
        if (inicio == null) {
            inicio = novo;
            fim = novo;
        } else {
            fim.proximo = novo;
            fim = novo;
        }
        tamanho++;
    }

    public T desenfileirar() {
        if (inicio == null) {
            return null;
        }
        T dado = inicio.dado;
        inicio = inicio.proximo;
        if (inicio == null) {
            fim = null;
        }
        tamanho--;
        return dado;
    }

    public T primeiro() {
        return (inicio != null) ? inicio.dado : null;
    }

    public boolean estaVazia() {
        return tamanho == 0;
    }

    public int tamanho() {
        return tamanho;
    }
}
