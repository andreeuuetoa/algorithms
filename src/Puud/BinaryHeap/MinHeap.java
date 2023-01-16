package Puud.BinaryHeap;

import java.util.Arrays;

public class MinHeap {
	private static int[] heap;

	private static boolean isHeap(int[] arr, int i, int n) {
		// Allikas: https://www.geeksforgeeks.org/how-to-check-if-a-given-array-represents-a-binary-heap/

		if (i >= (n - 1) / 2)
		{
			return true;
		}

		return arr[i] <= arr[2 * i + 1]
				&& arr[i] <= arr[2 * i + 2]
				&& isHeap(arr, 2 * i + 1, n)
				&& isHeap(arr, 2 * i + 2, n);
	}

	private static void insert(int[] values, int index) {
		heap[index] = values[index];
		heapifyArrayFromIndex(index);
	}

	private static void heapifyArrayFromIndex(int index) {
		int parent = index / 2;
		if (heap[index] < heap[parent]) {
			heap[index] += heap[parent];
			heap[parent] = heap[index] - heap[parent];
			heap[index] -= heap[parent];
		}
		if (parent >= 1) {
			heapifyArrayFromIndex(parent);
		}
	}

	public static void main(String[] args) {
		int[] values = new int[] {28, 90, 88, 3, 65, 97, 17, 31, 99, 98};
		heap = new int[values.length];
		for (int i = 0; i < values.length; i++) {
			insert(values, i);
			System.out.println(Arrays.toString(heap));
		}
		int n = values.length - 1;
		if (isHeap(heap, 0, n)) {
			System.out.println("Yes");
		}
		else {
			System.out.println("No");
		}
	}
}
