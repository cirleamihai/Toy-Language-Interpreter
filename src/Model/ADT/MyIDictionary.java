package Model.ADT;

public interface MyIDictionary<K, V> {
    void put(K key, V value);

    V get(K key);

    boolean contains(K key);

    void remove(K key);

    Iterable<K> getAllKeys();

    Iterable<V> getAllValues();

    MyIDictionary<K, V> deepCopy();

    String toFile();
}
