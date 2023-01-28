import java.util.ArrayList;
import java.util.List;

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
		List<Integer> primes = generatePrimesTo(200);
		System.out.println(primes);
	}
}
