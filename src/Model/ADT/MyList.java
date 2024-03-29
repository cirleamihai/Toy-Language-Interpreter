package Model.ADT;

import java.util.ArrayList;
import java.util.List;

public class MyList<T> implements MyIList<T> {
    ArrayList<T> list;

    public MyList() {
        this.list = new java.util.ArrayList<>();
    }

    public void insert(int index, T v) {
        list.add(index, v);
    }

    public void append(T v) {
        list.add(v);
    }

    public void remove(T v) {
        list.remove(v);
    }

    public void remove(int index) {
        list.remove(index);
    }

    public void pop() {
        list.remove(list.size() - 1);
    }

    public String toString() {
        return list.toString();
    }

    public int len() {
        return list.size();
    }

    public T get(int index) {
        return list.get(index);
    }

    public List<T> getContent() {
        return list;
    }

    public String toFile() {
        StringBuilder s = new StringBuilder();
        for (T e : list) {
            s.append("\n").append(e.toString());
        }
        return s.toString();
    }
}
