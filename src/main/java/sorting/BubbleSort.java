package sorting;

import java.util.Arrays;
import java.util.Random;

public class BubbleSort {
	public static void bubbleSort(int[] numbers) {
		for (int lastIndex = 0; lastIndex < numbers.length - 1; lastIndex++) {
			for (int compIndex = 0; compIndex < numbers.length - 1; compIndex++) {
				int left = numbers[compIndex];
				int right = numbers[compIndex + 1];
				if (right < left) {
					numbers[compIndex] = right;
					numbers[compIndex + 1] = left;
				}
			}
		}
	}

	public static void main(String[] args) {
		int[] numbers = new int[10];
		Random random = new Random();
		for (int i = 0; i < 10; i++) {
			numbers[i] = random.nextInt(101);
		}
		System.out.println(Arrays.toString(numbers));
		bubbleSort(numbers);
		System.out.println(Arrays.toString(numbers));
	}
}
