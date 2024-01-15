package mathematics;

import java.util.EmptyStackException;
import java.util.Stack;

public class ReversePolishNotationCalculator {
    private final Stack<Double> doubleStack;

    public ReversePolishNotationCalculator() {
        doubleStack = new Stack<>();
    }

    public boolean isEmpty() {
        return doubleStack.isEmpty();
    }

    public double interpret(String expression) {
        String[] elements = expression.split(" ");
        for (String element : elements) {
            pushToStackOrOperate(element);
        }

        if (doubleStack.size() != 1) {
            throw new IllegalStateException("Only one element must have been returned - the result of the expression.");
        }
        return doubleStack.peek();
    }

    private void pushToStackOrOperate(String element) {
        try {
            double elemAsNumber = Double.parseDouble(element);
            doubleStack.push(elemAsNumber);
        } catch (NumberFormatException e) {
            operate(element);
        }
    }

    private void operate(String operator) {
        double second;
        try {
            second = doubleStack.pop();
        } catch (EmptyStackException e) {
            throw new RuntimeException("Cannot perform operation " + operator + " on no elements!");
        }
        double first;
        try {
            first = doubleStack.pop();
        } catch (EmptyStackException e) {
            throw new RuntimeException("Cannot perform operation " + operator + " on one element!");
        }

        doubleStack.push(getResultOfOperation(operator, first, second));
    }

    private double getResultOfOperation(String operator, double first, double second) {
        return switch (operator) {
            case "+" -> first + second;
            case "-" -> first - second;
            case "*" -> first * second;
            case "/" -> first / second;
            case "^" -> Math.pow(first, second);
            case "root" -> Math.pow(first, 1 / second);
            default -> throw new IllegalStateException("The operation " + operator + " is not supported!");
        };
    }
}
