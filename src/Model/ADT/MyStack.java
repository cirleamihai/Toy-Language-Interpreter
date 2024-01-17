package Model.ADT;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class MyStack<T> implements MyIStack<T> {
    Stack<T> stack;

    public MyStack() {
        stack = new Stack<>();
    }

    @Override
    public void push(T v) {
        stack.push(v);
    }

    @Override
    public T pop() {
        return stack.pop();
    }

    public boolean isEmpty() {
        return stack.isEmpty();
    }

    public String toString() {
        return stack.toString();
    }

    public String toFile() {
        StringBuilder s = new StringBuilder();

        for (int i = stack.size() - 1; i >= 0; --i) {
            s.append("\n").append(stack.get(i).toString());
        }

        return s.toString();
    }

    public List<String> toStrList() {
        List<String> strStackList = new ArrayList<>();

        for (int i = stack.size() - 1; i >= 0; --i) {
            strStackList.add(stack.get(i).toString());
        }

        return strStackList;
    }
}
