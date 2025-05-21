package estruturas;

public interface Map<K, V> {
    V put(K key, V value);        // adiciona ou substitui o valor para a chave
    V get(K key);                 // obtém o valor associado à chave
    V remove(K key);              // remove o par chave-valor da chave
    boolean containsKey(K key);   // verifica se a chave existe
    int size();                   // retorna o número de pares
    boolean isEmpty();            // verifica se está vazio
    void clear();// limpa o mapa


    public static <K, V> V getOrDefault(Map<K, V> map, K key, V defaultValue) {
        if (map.containsKey(key)) {
            return map.get(key);
        }
        return defaultValue;
    }

}


