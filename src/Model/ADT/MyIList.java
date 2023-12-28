package Model.ADT;

import java.util.List;

public interface MyIList<T> {
    void append(T v);

    T get(int index);

    void insert(int index, T v);

    void remove(T v);

    void remove(int index);

    void pop();

    int len();

    String toFile();

    List<T> getContent();
}
