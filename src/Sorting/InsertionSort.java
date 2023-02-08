package Sorting;

import java.util.Arrays;
import java.util.Random;

public class InsertionSort {
	public static void insertionSort(int[] numbers) {
		int lastIndex = 1;
		while (lastIndex < numbers.length) {
			int toInsert = numbers[lastIndex];
			int compIndex = lastIndex - 1;
			while (compIndex >= 0) {
				if (toInsert >= numbers[compIndex]) {
					break;
				}
				numbers[compIndex + 1] = numbers[compIndex];
				compIndex--;
			}
			numbers[compIndex + 1] = toInsert;
			lastIndex++;
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
