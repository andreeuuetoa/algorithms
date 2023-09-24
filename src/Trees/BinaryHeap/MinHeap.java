package Trees.BinaryHeap;

import java.util.Arrays;
import java.util.Random;

public class MinHeap {
	private static final int HEAP_SIZE = 10;
	private static int LAST_INDEX = -1;

	private static boolean isHeap(Integer[] integers, int i, int n) {
		// Allikas: https://www.geeksforgeeks.org/how-to-check-if-a-given-array-represents-a-binary-heap/

		if (i >= (n - 1) / 2)
		{
			return true;
		}

		return integers[i] <= integers[2 * i + 1]
				&& integers[i] <= integers[2 * i + 2]
				&& isHeap(integers, 2 * i + 1, n)
				&& isHeap(integers, 2 * i + 2, n);
	}

	private static void insert(Integer[] heap, int value, int index) {
		heap[index] = value;
		heapifyArrayFromIndex(heap, index);
		LAST_INDEX++;
	}

	public static int pop(Integer[] heap) {
		int last = heap[LAST_INDEX];
		heap[LAST_INDEX] = null;
		heapifyArrayFromIndex(heap, LAST_INDEX);
		LAST_INDEX--;
		return last;
	}

	private static void heapifyArrayFromIndex(Integer[] integers, int index) {
		if (integers[index] == null) {
			heapifyArrayFromIndex(integers, index - 1);
			return;
		}
		int parentIndex = index / 2;
		if (integers[index] < integers[parentIndex]) {
			integers[index] += integers[parentIndex];
			integers[parentIndex] = integers[index] - integers[parentIndex];
			integers[index] -= integers[parentIndex];
		}
		if (parentIndex >= 1) {
			heapifyArrayFromIndex(integers, parentIndex);
		}
		if (!isHeap(integers, 0, index)) {
			throw new RuntimeException("The array is not a heap!");
		}
	}

	public static void main(String[] args) {
		Integer[] heap = new Integer[HEAP_SIZE];
		for (int i = 0; i < heap.length; i++) {
			int value = new Random().nextInt(101);
			insert(heap, value, i);
			System.out.println(Arrays.toString(heap));
		}
		int n = heap.length - 1;
		if (isHeap(heap, 0, n)) {
			System.out.println("Yes");
		}
		else {
			System.out.println("No");
		}
		int last = pop(heap);
		System.out.println(last);
		System.out.println(Arrays.toString(heap));
	}
}
