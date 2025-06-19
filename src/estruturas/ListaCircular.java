package estruturas;

public class ListaCircular<T> {
    private No<T> inicio; // primeiro nó da lista circular
    private int tamanho;  // quantidade de elementos

    // Nó interno da lista circular
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

    // Adiciona um elemento ao final da lista circular
    public void adicionar(T elemento) {
        No<T> novo = new No<>(elemento);

        if (estaVazia()) {
            inicio = novo;
            novo.proximo = inicio; // aponta para ele mesmo, formando o ciclo
        } else {
            No<T> ultimo = inicio;
            // percorre até o último nó, que aponta para o início
            while (ultimo.proximo != inicio) {
                ultimo = ultimo.proximo;
            }
            ultimo.proximo = novo; // adiciona o novo após o último
            novo.proximo = inicio; // novo aponta para o início, mantendo o ciclo
        }
        tamanho++;
    }

    // Retorna o elemento no índice especificado
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

    // Retorna o tamanho da lista
    public int tamanho() {
        return tamanho;
    }

    // Verifica se a lista está vazia
    public boolean estaVazia() {
        return tamanho == 0;
    }

    // Representação textual da lista circular
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
