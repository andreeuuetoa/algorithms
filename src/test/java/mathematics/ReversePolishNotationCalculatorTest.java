package mathematics;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ReversePolishNotationCalculatorTest {
    private ReversePolishNotationCalculator calculator;

    @BeforeEach
    public void createCalculator() {
        calculator = new ReversePolishNotationCalculator();
    }

    @Test
    public void testCalculatorIsEmpty() {
        assertTrue(calculator.isEmpty());
    }

    @Test
    public void testCalculatorCanInterpretOneElement() {
        Number result = calculator.interpret("3");
        assertEquals(3.0, result);
    }

    @Test
    public void testCalculatorCannotInterpretTwoElements() {
        assertThrows(RuntimeException.class,
                () -> calculator.interpret("3 5"),
                "An exception was expected.");
    }

    @Test
    public void testCalculatorCanAddTwoNumbersAndReturnTheSum() {
        Number result = calculator.interpret("2.3 7 +");
        assertEquals(9.3, result);
    }

    @Test
    public void testCalculatorCannotAddWithNothingInTheStack() {
        assertThrows(RuntimeException.class,
                () -> calculator.interpret("+"),
                "Cannot add with nothing in the stack.");
    }

    @Test
    public void testCalculatorCannotAddIfStackContainsASingleNumber() {
        assertThrows(RuntimeException.class,
                () -> calculator.interpret("6 +"),
                "Cannot add with one element in the stack.");
    }

    @Test
    public void testCalculatorCanSubtractAndReturnTheDifference() {
        Number result = calculator.interpret("9.6 5.1 -");
        assertEquals(4.5, result);
    }

    @Test
    public void testCalculatorCannotSubtractWithNothingInTheStack() {
        assertThrows(RuntimeException.class,
                () -> calculator.interpret("-"),
                "Cannot subtract with nothing in the stack.");
    }

    @Test
    public void testCalculatorCannotSubtractIfStackContainsASingleNumber() {
        assertThrows(RuntimeException.class,
                () -> calculator.interpret("9 -"),
                "Cannot subtract with one element in the stack.");
    }

    @Test
    public void testCalculatorCanMultiplyAndReturnTheProduct() {
        Number result = calculator.interpret("3.2 4.0 *");
        assertEquals(12.8, result);
    }

    @Test
    public void testCalculatorCannotMultiplyWithNothingInTheStack() {
        assertThrows(RuntimeException.class,
                () -> calculator.interpret("*"),
                "Cannot multiply with nothing in the stack.");
    }

    @Test
    public void testCalculatorCannotMultiplyIfStackContainsASingleNumber() {
        assertThrows(RuntimeException.class,
                () -> calculator.interpret("12.4 *"),
                "Cannot multiply with one element in the stack.");
    }

    @Test
    public void testCalculatorCanDivideAndReturnTheQuotient() {
        Number result = calculator.interpret("15 5 /");
        assertEquals(3.0, result);
    }

    @Test
    public void testCalculatorCannotDivideWithNothingInTheStack() {
        assertThrows(RuntimeException.class,
                () -> calculator.interpret("/"),
                "Cannot divide with nothing in the stack.");
    }

    @Test
    public void testCalculatorCannotDivideIfStackContainsASingleNumber() {
        assertThrows(RuntimeException.class,
                () -> calculator.interpret("9 /"),
                "Cannot divide with one element in the stack.");
    }

    @Test
    public void testCalculatorCanDoManyOperations() {
        Number result = calculator.interpret("5 3 9 8 * 7 2 + - * + 2 /");
        assertEquals(97.0, result);
    }

    @Test
    public void testCalculatorCanDoExponents() {
        Number result = calculator.interpret("5 3 ^");
        assertEquals(125.0, result);
    }

    @Test
    public void testCalculatorCannotDoExponentsIfStackContainsASingleNumber() {
        assertThrows(RuntimeException.class,
                () -> calculator.interpret("14 ^"),
                "Cannot take an element to some power with one element in the stack.");
    }
}
