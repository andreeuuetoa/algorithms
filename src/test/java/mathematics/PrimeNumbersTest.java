package mathematics;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PrimeNumbersTest {
    @Test
    public void testPrimeNumbersUpTo100() {
        List<Integer> primeNumbersTo100 = new PrimeNumberGenerator().generatePrimesUpTo(100);
        assertEquals(primeNumbersTo100.size(), 25);
    }

    @Test
    public void testPrimeNumbersUpTo1000() {
        List<Integer> primeNumbersTo100 = new PrimeNumberGenerator().generatePrimesUpTo(1000);
        assertEquals(primeNumbersTo100.size(), 168);
    }

    @Test
    public void testPrimeNumbersUpTo10000() {
        List<Integer> primeNumbersTo100 = new PrimeNumberGenerator().generatePrimesUpTo(10000);
        assertEquals(primeNumbersTo100.size(), 1229);
    }

    @Test
    public void testPrimeNumbersUpTo100000() {
        List<Integer> primeNumbersTo100 = new PrimeNumberGenerator().generatePrimesUpTo(100000);
        assertEquals(primeNumbersTo100.size(), 9592);
    }
}
