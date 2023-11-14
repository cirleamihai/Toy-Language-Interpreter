package Model.ADT;

public interface MyIStack<T> {
    void push(T v);

    T pop();

    boolean isEmpty();

    String toFile();
}
