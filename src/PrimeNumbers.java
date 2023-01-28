import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PrimeNumbers {
	public static List<Integer> generatePrimesTo(int upperLimit) {
		List<Integer> primes = new ArrayList<>();
		outerloop:
		for (int i = 2; i < upperLimit; i++) {
			if (primes.isEmpty()) {
				primes.add(i);
				continue;
			}
			for (Integer prime : primes) {
				if (i % prime == 0) {
					continue outerloop;
				}
			}
			primes.add(i);
		}
		return primes;
	}

	public static void main(String[] args) {
		int value = new Random().nextInt(501);
		List<Integer> primes = generatePrimesTo(value);
		System.out.println(primes);
	}
}
