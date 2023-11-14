package Model.ADT;

import java.util.ArrayList;

public class MyList<T> implements MyIList<T> {
    ArrayList<T> list;

    public MyList() {
        this.list = new java.util.ArrayList<T>();
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
}
