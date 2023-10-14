package mathematics;

import java.util.Stack;

public class ReversePolishNotationCalculator {
    private final Stack<Double> integerStack;

    public ReversePolishNotationCalculator() {
        integerStack = new Stack<>();
    }

    public boolean isEmpty() {
        return integerStack.isEmpty();
    }

    public Number interpret(String expression) {
        String[] elements = expression.split(" ");
        for (String element : elements) {
            pushToStackOrOperate(element);
        }

        if (integerStack.size() != 1) {
            throw new IllegalStateException("The stack must contain only one element - the result of the expression.");
        }
        return integerStack.peek();
    }

    private void pushToStackOrOperate(String element) {
        try {
            double elemAsNumber = Double.parseDouble(element);
            integerStack.push(elemAsNumber);
        } catch (NumberFormatException e) {
            operate(element);
        }
    }

    private void operate(String operator) {
        double second = integerStack.pop();
        double first = integerStack.pop();

        switch (operator) {
            case "+":
                integerStack.push(first + second);
                return;
            case "-":
                integerStack.push(first - second);
                return;
            case "*":
                integerStack.push(first * second);
                return;
            case "/":
                integerStack.push(first / second);
                return;
            case "^":
                integerStack.push(Math.pow(first, second));
                return;
            default:
                throw new IllegalStateException("The operation " + operator + " is not supported!");
        }
    }
}
