package Model.ADT;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Map;

public class MyDictionary<Key, Val> implements MyIDictionary<Key, Val> {
    Hashtable<Key, Val> dict;

    public MyDictionary() {
        dict = new Hashtable<>();
    }

    public void put(Key key, Val val) {
        dict.put(key, val);
    }

    public Val get(Key key) {
        return dict.get(key);
    }

    public boolean contains(Key key) {
        return dict.containsKey(key);
    }

    public void remove(Key key) {
        dict.remove(key);
    }

    public Iterable<Key> getAllKeys() {
        return dict.keySet();
    }

    public Iterable<Val> getAllValues() {
        return dict.values();
    }

    public MyIDictionary<Key, Val> deepCopy() {
        MyIDictionary<Key, Val> clone = new MyDictionary<>();
        for (Key key : dict.keySet()) {
            clone.put(key, dict.get(key));
        }
        return clone;
    }

    public Map<Key, Val> getContent() {
        return dict;
    }

    public String toString() {
        return dict.toString();
    }

    public String toFile() {
        StringBuilder s = new StringBuilder();
        for (Key key : dict.keySet()) {
            s.append("\n").append(key.toString()).append(" --> ").append(dict.get(key).toString());
        }
        return s.toString();
    }
}
