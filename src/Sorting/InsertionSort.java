package Sorting;

import java.util.Arrays;
import java.util.Random;

public class InsertionSort {
	public static void insertionSort(int[] numbers) {
		for (int lastIndex = 1; lastIndex < numbers.length; lastIndex++) {
			int toInsert = numbers[lastIndex];
			int compIndex;
			for (compIndex = lastIndex - 1; compIndex >= 0; compIndex--) {
				if (toInsert >= numbers[compIndex]) {
					break;
				}
				numbers[compIndex + 1] = numbers[compIndex];
			}
			numbers[compIndex + 1] = toInsert;
		}
	}

	public static void main(String[] args) {
		int[] numbers = new int[10];
		Random random = new Random();
		for (int i = 0; i < 10; i++) {
			numbers[i] = random.nextInt(101);
		}
		System.out.println(Arrays.toString(numbers));
		insertionSort(numbers);
		System.out.println(Arrays.toString(numbers));
	}
}
