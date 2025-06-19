package estruturas;

public interface Map<K, V> {
    // Adiciona ou substitui o valor para a chave
    V put(K key, V value);

    // Obtém o valor associado à chave
    V get(K key);

    // Remove o par chave-valor da chave
    V remove(K key);

    // Verifica se a chave existe no mapa
    boolean containsKey(K key);

    // Retorna o número de pares chave-valor
    int size();

    // Verifica se o mapa está vazio
    boolean isEmpty();

    // Limpa o mapa, removendo todos os pares
    void clear();

    // Método estático para obter valor ou padrão caso a chave não exista
    static <K, V> V getOrDefault(Map<K, V> map, K key, V defaultValue) {
        if (map.containsKey(key)) {
            return map.get(key);
        }
        return defaultValue;
    }
}
