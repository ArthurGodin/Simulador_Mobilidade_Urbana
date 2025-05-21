package estruturas;

import java.util.Objects;

public class HashMap<K, V> implements Map<K, V> {
    private static class Entry<K, V> {
        final K key;
        V value;
        Entry<K, V> next;

        Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private Entry<K, V>[] buckets;
    private int capacity = 16;  // capacidade inicial
    private int size = 0;

    @SuppressWarnings("unchecked")
    public HashMap() {
        buckets = (Entry<K, V>[]) new Entry[capacity];
    }

    private int hash(K key) {
        return (key == null) ? 0 : Math.abs(key.hashCode()) % capacity;
    }

    @Override
    public V put(K key, V value) {
        int index = hash(key);
        Entry<K, V> current = buckets[index];

        while (current != null) {
            if (Objects.equals(current.key, key)) {
                V oldValue = current.value;
                current.value = value;
                return oldValue;
            }
            current = current.next;
        }

        Entry<K, V> newEntry = new Entry<>(key, value);
        newEntry.next = buckets[index];
        buckets[index] = newEntry;
        size++;
        return null;
    }

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

    @Override
    public V remove(K key) {
        int index = hash(key);
        Entry<K, V> current = buckets[index];
        Entry<K, V> previous = null;

        while (current != null) {
            if (Objects.equals(current.key, key)) {
                if (previous == null) {
                    buckets[index] = current.next;
                } else {
                    previous.next = current.next;
                }
                size--;
                return current.value;
            }
            previous = current;
            current = current.next;
        }
        return null;
    }

    @Override
    public boolean containsKey(K key) {
        return get(key) != null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void clear() {
        for (int i = 0; i < capacity; i++) {
            buckets[i] = null;
        }
        size = 0;
    }
}
