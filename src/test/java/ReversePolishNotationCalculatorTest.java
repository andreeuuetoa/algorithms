import mathematics.ReversePolishNotationCalculator;
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
        assertEquals(3, result);
    }

    @Test
    public void testCalculatorCannotInterpretTwoElements() {
        assertThrows(RuntimeException.class,
                () -> calculator.interpret("3 5"),
                "An exception was expected.");
    }

    @Test
    public void testCalculatorCanAddTwoNumbersAndReturnTheSum() {
        Number result = calculator.interpret("2 7 +");
        assertEquals(9, result);
    }

    @Test
    public void testCalculatorCannotAddIfStackContainsASingleNumber() {
        assertThrows(RuntimeException.class,
                () -> calculator.interpret("6 +"),
                "Cannot add with one element in the stack.");
    }

    @Test
    public void testCalculatorCanSubtractAndReturnTheDifference() {
        Number result = calculator.interpret("9 5 -");
        assertEquals(4, result);
    }

    @Test
    public void testCalculatorCannotSubtractIfStackContainsASingleNumber() {
        assertThrows(RuntimeException.class,
                () -> calculator.interpret("9 -"),
                "Cannot add with one element in the stack.");
    }

    @Test
    public void testCalculatorCanMultiplyAndReturnTheProduct() {
        Number result = calculator.interpret("3 4 *");
        assertEquals(12, result);
    }

    @Test
    public void testCalculatorCannotMultiplyIfStackContainsASingleNumber() {
        assertThrows(RuntimeException.class,
                () -> calculator.interpret("12 *"),
                "Cannot add with one element in the stack.");
    }

    @Test
    public void testCalculatorCanDivideAndReturnTheQuotient() {
        Number result = calculator.interpret("15 5 /");
        assertEquals(3, result);
    }

    @Test
    public void testCalculatorCannotDivideIfStackContainsASingleNumber() {
        assertThrows(RuntimeException.class,
                () -> calculator.interpret("9 /"),
                "Cannot add with one element in the stack.");
    }

    @Test
    public void testCalculatorCanDoManyOperations() {
        Number result = calculator.interpret("5 3 9 8 * 7 2 + - * + 2 /");
        assertEquals(97, result);
    }
}
