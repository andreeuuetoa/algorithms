package mathematics;

import java.util.Stack;

public class ReversePolishNotationCalculator {
    private final Stack<Double> doubleStack;

    public ReversePolishNotationCalculator() {
        doubleStack = new Stack<>();
    }

    public boolean isEmpty() {
        return doubleStack.isEmpty();
    }

    public Number interpret(String expression) {
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
        double second = doubleStack.pop();
        double first = doubleStack.pop();

        switch (operator) {
            case "+":
                doubleStack.push(first + second);
                return;
            case "-":
                doubleStack.push(first - second);
                return;
            case "*":
                doubleStack.push(first * second);
                return;
            case "/":
                doubleStack.push(first / second);
                return;
            case "^":
                doubleStack.push(Math.pow(first, second));
                return;
            case "root":
                doubleStack.push(Math.pow(first, 1 / second));
                return;
            default:
                throw new IllegalStateException("The operation " + operator + " is not supported!");
        }
    }
}
