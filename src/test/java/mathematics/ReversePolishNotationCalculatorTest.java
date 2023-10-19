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
                "An operation was expected.");
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
                "Cannot do addition on nothing.");
    }

    @Test
    public void testCalculatorCannotAddIfStackContainsASingleNumber() {
        assertThrows(RuntimeException.class,
                () -> calculator.interpret("6 +"),
                "Cannot do addition on one element.");
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
                "Cannot do subtraction on nothing.");
    }

    @Test
    public void testCalculatorCannotSubtractIfStackContainsASingleNumber() {
        assertThrows(RuntimeException.class,
                () -> calculator.interpret("9 -"),
                "Cannot do subtraction on one element.");
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
                "Cannot do multiplication on nothing.");
    }

    @Test
    public void testCalculatorCannotMultiplyIfStackContainsASingleNumber() {
        assertThrows(RuntimeException.class,
                () -> calculator.interpret("12.4 *"),
                "Cannot do multiplication on one element.");
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
                "Cannot do division on nothing.");
    }

    @Test
    public void testCalculatorCannotDivideIfStackContainsASingleNumber() {
        assertThrows(RuntimeException.class,
                () -> calculator.interpret("9 /"),
                "Cannot do division on one element.");
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
                "Cannot to exponent on one element.");
    }

    @Test
    public void testCalculatorCanDoRoots() {
        Number result = calculator.interpret("36 2 root");
        assertEquals(result, 6.0);
    }

    @Test
    public void testCalculatorCannotDoRootsIfStackContainsASingleNumber() {
        assertThrows(RuntimeException.class,
                () -> calculator.interpret("14 root"),
                "Cannot to rooting on one element.");
    }
}
