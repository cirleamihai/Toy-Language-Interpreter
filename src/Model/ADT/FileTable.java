package Model.ADT;

import java.util.Hashtable;

public class FileTable<Key, Val> implements MyIDictionary<Key, Val> {
    Hashtable<Key, Val> fileTable;
    // for this current assignment, it will be a Hashtable of (StringValue, BufferedReader)
    // could also be inherited from the MyDictionary class since rn it's the same thing
    // but will keep it as a separate class in case we want to add more functionality to it later

    public FileTable() {
        fileTable = new Hashtable<>();
    }

    public void put(Key key, Val value) {
        fileTable.put(key, value);
    }

    public void remove(Key key) {
        fileTable.remove(key);
    }

    public Val get(Key key) {
        return fileTable.get(key);
    }

    public boolean contains(Key key) {
        return fileTable.containsKey(key);
    }

    public Iterable<Key> getAllKeys() {
        return fileTable.keySet();
    }

    public Iterable<Val> getAllValues() {
        return fileTable.values();
    }

    @Override
    public MyIDictionary<Key, Val> deepCopy() {
        MyIDictionary<Key, Val> clone = new FileTable<>();

        for (Key key : fileTable.keySet()) {
            clone.put(key, fileTable.get(key));
        }

        return clone;
    }

    public String toString() {
        return fileTable.toString();
    }

    public String toFile() {
        StringBuilder s = new StringBuilder();

        for (Key key : fileTable.keySet()) {
            s.append("\n").append(key.toString());
        }

        return s.toString();
    }

}
