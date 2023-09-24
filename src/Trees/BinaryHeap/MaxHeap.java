package Trees.BinaryHeap;

import java.util.Arrays;
import java.util.Random;

public class MaxHeap {
	private static final int HEAP_SIZE = 10;
	private static int LAST_INDEX = -1;

	private static boolean isHeap(Integer[] arr, int i, int n) {
		// Allikas: https://www.geeksforgeeks.org/how-to-check-if-a-given-array-represents-a-binary-heap/

		if (i >= (n - 1) / 2)
		{
			return true;
		}

		return arr[i] >= arr[2 * i + 1]
				&& arr[i] >= arr[2 * i + 2]
				&& isHeap(arr, 2 * i + 1, n)
				&& isHeap(arr, 2 * i + 2, n);
	}

	public static void insert(Integer[] heap, int value, int index) {
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

	private static void heapifyArrayFromIndex(Integer[] heap, int index) {
		if (heap[index] == null) {
			heapifyArrayFromIndex(heap, index - 1);
			return;
		}
		int parentIndex = index / 2;
		if (heap[index] > heap[parentIndex]) {
			heap[index] += heap[parentIndex];
			heap[parentIndex] = heap[index] - heap[parentIndex];
			heap[index] -= heap[parentIndex];
		}
		if (parentIndex >= 1) {
			heapifyArrayFromIndex(heap, parentIndex);
		}
		if (!isHeap(heap, 0, LAST_INDEX)) {
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
