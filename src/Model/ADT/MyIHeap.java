package Model.ADT;

import java.util.Map;

public interface MyIHeap<K, V> {
    K defaultPut(V contentValue);

    void put(K address, V contentValue);

    void update(K address, V contentValue);

    V get(K address);

    K getAddress();

    K getNextAddress();

    boolean contains(K address);

    void remove(K address);

    Iterable<K> getAllKeys();

    Iterable<V> getAllValues();

    MyIHeap<K, V> deepCopy();

    Map<K, V> getContent();

    void setContent(Map<K, V> newContent);

    String toFile();
}
