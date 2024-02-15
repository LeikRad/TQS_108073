package stack;

import java.util.LinkedList;
import java.util.NoSuchElementException;

public class TqsStack<T> implements Stack<T> {
    private static final String NOT_IMPLEMENTED = "Not implemented yet";

    private LinkedList<T> list = null;
    private int max_size = -1;

    public TqsStack() {
        list = new LinkedList<>();
    }

    public TqsStack(int bound) {
        max_size = bound;
        list = new LinkedList<>();
    }

    @Override
    public T pop() {
        if (list.isEmpty()) {
            throw new NoSuchElementException("Stack is Empty.");
        }

        return list.pop();
    }

    @Override
    public void push(T element) {
        if (max_size != -1 && list.size() >= max_size) {
            throw new IllegalStateException("Stack has reached max size.");
        }
        list.push(element);
    }

    @Override
    public T peek() {
        if (list.isEmpty()) {
            throw new NoSuchElementException("Stack is Empty.");
        }

        return list.peek();
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }
}
