package Model.ADT;

import Model.Value.Value;

import java.util.HashMap;
import java.util.Map;

public class Heap implements MyIHeap<Integer, Value> {
    // cannot use generics since address must be of type int
    // and this makes everything more primitive
    HashMap<Integer, Value> heap;
    int address;


    public Heap() {
        heap = new HashMap<>();
        address = 0;
    }

    public void setAddress(int new_address) {
        address = new_address;
    }

    @Override
    public Integer defaultPut(Value contentValue) {
        heap.put(++address, contentValue); // we always increment the address with 1

        return address;
    }

    @Override
    public void put(Integer address, Value contentValue) {
        heap.put(address, contentValue);
    }

    @Override
    public void update(Integer address, Value contentValue) {
        heap.replace(address, contentValue);
    }

    @Override
    public Value get(Integer address) {
        return heap.get(address);
    }

    @Override
    public Integer getAddress() {
        return address;
    }

    @Override
    public Integer getNextAddress() {
        return address + 1;
    }

    @Override
    public boolean contains(Integer address) {
        return heap.containsKey(address);
    }

    @Override
    public void remove(Integer address) {
        heap.remove(address);
    }

    public Iterable<Integer> getAllKeys() {
        return heap.keySet();
    }

    public Iterable<Value> getAllValues() {
        return heap.values();
    }

    @Override
    public MyIHeap<Integer, Value> deepCopy() {
        Heap new_heap = new Heap();
        for (Integer key: getAllKeys()) {
            new_heap.put(key, get(key));
        }

        new_heap.setAddress(address);

        return new_heap;
    }

    @Override
    public Map<Integer, Value> getContent() {
        return heap;
    }

    public void setContent(Map<Integer, Value> newContent) {
        heap = (HashMap<Integer, Value>) newContent;

        // we need to find the max address
        int max_address = 0;
        for (Integer key: getAllKeys()) {
            if (key > max_address) {
                max_address = key;
            }
        }

        address = max_address;  // and then we replace it
    }

    @Override
    public String toFile() {
        StringBuilder s = new StringBuilder();
        for (Integer key : heap.keySet()) {
            s.append("\n").append(key.toString()).append(" --> ").append(heap.get(key).toString());
        }
        return s.toString();
    }
}
