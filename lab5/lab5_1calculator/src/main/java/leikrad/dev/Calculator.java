package leikrad.dev;

import java.util.Stack;

public class Calculator {
    private Stack<Double> stack = new Stack<>();

    public Calculator() {
    }

    public void push(int arg) {
        stack.push((double) arg);
    }

    public void push(String op) {
        if (op.equals("+")) {
            stack.push(stack.pop() + stack.pop());
        } else if (op.equals("-")) {
            stack.push(-stack.pop() + stack.pop());
        }
    }

    public double value() {
        return stack.peek();
    }
}
