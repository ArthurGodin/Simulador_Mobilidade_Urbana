package estruturas;

import java.util.Objects;

public class HashMap1<K, V> implements Map<K, V> {

    // Classe interna que representa uma entrada (par chave-valor) da tabela hash
    private static class Entry<K, V> {
        final K key;
        V value;
        Entry<K, V> next;  // referência para colisão em lista encadeada

        Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private Entry<K, V>[] buckets; // array de entradas (baldes)
    private int capacity = 16;     // capacidade inicial padrão
    private int size = 0;          // quantidade de pares armazenados

    @SuppressWarnings("unchecked")
    public HashMap1() {
        // Cria o array de buckets com a capacidade inicial
        buckets = (Entry<K, V>[]) new Entry[capacity];
    }

    // Calcula o índice do bucket para a chave dada, evitando índices negativos
    private int hash(K key) {
        return (key == null) ? 0 : Math.abs(key.hashCode()) % capacity;
    }

    // Insere ou atualiza um par chave-valor no mapa
    @Override
    public V put(K key, V value) {
        int index = hash(key);
        Entry<K, V> current = buckets[index];

        // Procura se a chave já existe, atualiza valor se achar
        while (current != null) {
            if (Objects.equals(current.key, key)) {
                V oldValue = current.value;
                current.value = value;
                return oldValue; // retorna valor antigo
            }
            current = current.next;
        }

        // Caso a chave não exista, cria nova entrada e adiciona no início da lista
        Entry<K, V> newEntry = new Entry<>(key, value);
        newEntry.next = buckets[index];
        buckets[index] = newEntry;
        size++;
        return null; // não havia valor antigo
    }

    // Retorna o valor associado a uma chave, ou null se não existir
    @Override
    public V get(K key) {
        int index = hash(key);
        Entry<K, V> current = buckets[index];
        while (current != null) {
            if (Objects.equals(current.key, key)) {
                return current.value;
            }
            current = current.next;
        }
        return null;
    }

    // Remove o par pela chave e retorna o valor removido, ou null se não encontrado
    @Override
    public V remove(K key) {
        int index = hash(key);
        Entry<K, V> current = buckets[index];
        Entry<K, V> previous = null;

        while (current != null) {
            if (Objects.equals(current.key, key)) {
                if (previous == null) {
                    buckets[index] = current.next; // remove o primeiro da lista
                } else {
                    previous.next = current.next;  // remove da lista encadeada
                }
                size--;
                return current.value;
            }
            previous = current;
            current = current.next;
        }
        return null;
    }

    // Verifica se contém a chave no mapa
    @Override
    public boolean containsKey(K key) {
        return get(key) != null;
    }

    // Retorna o número de pares armazenados
    @Override
    public int size() {
        return size;
    }

    // Verifica se o mapa está vazio
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    // Remove todos os pares do mapa
    @Override
    public void clear() {
        for (int i = 0; i < capacity; i++) {
            buckets[i] = null;
        }
        size = 0;
    }
}
