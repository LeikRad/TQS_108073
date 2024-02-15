package stack;

import java.util.LinkedList;

public class TqsStack<T> implements Stack<T> {
    private static final String NOT_IMPLEMENTED = "Not implemented yet";

    private LinkedList<T> list = new LinkedList<T>();

    public TqsStack() {
        throw new UnsupportedOperationException(NOT_IMPLEMENTED);
    }

    @Override
    public T pop() {
        throw new UnsupportedOperationException(NOT_IMPLEMENTED);
    }

    @Override
    public void push(T element) {
        throw new UnsupportedOperationException(NOT_IMPLEMENTED);
    }

    @Override
    public T peek() {
        throw new UnsupportedOperationException(NOT_IMPLEMENTED);
    }

    @Override
    public int size() {
        throw new UnsupportedOperationException(NOT_IMPLEMENTED);
    }

    @Override
    public boolean isEmpty() {
        throw new UnsupportedOperationException(NOT_IMPLEMENTED);
    }
}
