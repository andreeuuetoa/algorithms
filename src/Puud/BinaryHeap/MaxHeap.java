package Puud.BinaryHeap;

import java.util.Arrays;

public class MaxHeap {
	private static int[] heap;

	// Returns true if arr[i..n-1]
	// represents a max-heap
	static boolean isHeap(int[] arr,
	                      int i, int n)
	{
		// If (2 * i) + 1 >= n, then leaf node, so return true
		if (i >= (n - 1) / 2)
		{
			return true;
		}

		// If an internal node and
		// is greater than its
		// children, and same is
		// recursively true for the
		// children
		return arr[i] >= arr[2 * i + 1]
				&& arr[i] >= arr[2 * i + 2]
				&& isHeap(arr, 2 * i + 1, n)
				&& isHeap(arr, 2 * i + 2, n);
	}

	private static void insert(int[] values, int index) {
		heap[index] = values[index];
		heapifyArrayFromIndex(index);
	}

	private static void heapifyArrayFromIndex(int index) {
		int parent = index / 2;
		if (heap[index] > heap[parent]) {
			heap[index] += heap[parent];
			heap[parent] = heap[index] - heap[parent];
			heap[index] -= heap[parent];
		}
		if (parent >= 1) {
			heapifyArrayFromIndex(parent);
		}
	}

	public static void main(String[] args) {
		int[] values = new int[] {28, 41, 14, 52, 21, 60, 28, 94, 42, 4};
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
