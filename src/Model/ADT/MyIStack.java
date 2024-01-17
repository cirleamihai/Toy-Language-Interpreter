package Model.ADT;

import java.util.List;

public interface MyIStack<T> {
    void push(T v);

    T pop();

    boolean isEmpty();

    String toFile();

    List<String> toStrList();
}
