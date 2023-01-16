package Puud.BinaryHeap;

import java.util.Arrays;
import java.util.Random;

public class MinHeap {
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

	private static void insert(int[] heap, int value, int index) {
		heap[index] = value;
		heapifyArrayFromIndex(heap, index);
	}

	private static void heapifyArrayFromIndex(int[] heap, int index) {
		int parent = index / 2;
		if (heap[index] < heap[parent]) {
			heap[index] += heap[parent];
			heap[parent] = heap[index] - heap[parent];
			heap[index] -= heap[parent];
		}
		if (parent >= 1) {
			heapifyArrayFromIndex(heap, parent);
		}
	}

	public static void main(String[] args) {
		int[] heap = new int[10];
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
	}
}
