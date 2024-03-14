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
        double b = stack.pop();
        double a = stack.pop();
        switch (op) {
            case "+":
                stack.push(a + b);
                break;
            case "-":
                stack.push(a - b);
                break;
            case "*":
                stack.push(a * b);
                break;
            case "/":
                stack.push(a / b);
                break;
            default:
                break;
        }
    }

    public double value() {
        return stack.peek();
    }
}
