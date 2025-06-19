package estruturas;

public class Pilha<T> {
    private No<T> topo;
    private int tamanho;

    // Nó interno para representar cada elemento da pilha
    private static class No<T> {
        T dado;
        No<T> abaixo;

        No(T dado) {
            this.dado = dado;
            this.abaixo = null;
        }
    }

    public Pilha() {
        topo = null;
        tamanho = 0;
    }

    // Adiciona elemento ao topo da pilha
    public void empilhar(T elemento) {
        No<T> novo = new No<>(elemento);
        novo.abaixo = topo;
        topo = novo;
        tamanho++;
    }

    // Remove e retorna o elemento do topo da pilha
    public T desempilhar() {
        if (topo == null) {
            return null;
        }
        T dado = topo.dado;
        topo = topo.abaixo;
        tamanho--;
        return dado;
    }

    // Retorna o elemento do topo sem remover
    public T topo() {
        return (topo != null) ? topo.dado : null;
    }

    // Verifica se a pilha está vazia
    public boolean estaVazia() {
        return tamanho == 0;
    }

    // Retorna o tamanho atual da pilha
    public int tamanho() {
        return tamanho;
    }
}
