import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PrimeNumberGenerator {
	private final List<Integer> primes;

	public PrimeNumberGenerator() {
		this.primes = new ArrayList<>();
	}

	public List<Integer> generatePrimesTo(int upperLimit) {
		for (int i = 2; i < upperLimit; i++) {
			if (isNumberPrime(i)) {
				primes.add(i);
			}
		}
		return primes;
	}

	private boolean isNumberPrime(int number) {
		for (Integer prime : primes) {
			if (number % prime == 0) {
				return false;
			}
		}
		return true;
	}

	public static void main(String[] args) {
		int value = new Random().nextInt(501);
		List<Integer> primes = new PrimeNumberGenerator().generatePrimesTo(value);
		System.out.println(primes);
	}
}
