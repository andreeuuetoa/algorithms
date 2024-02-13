package trees.binaryheap;

import java.util.Arrays;
import java.util.Random;

public class MinHeap {
	private int LAST_INDEX = -1;
	private final Integer[] heap;

	public MinHeap(int size) {
		heap = new Integer[size];
	}

	public Integer[] getHeap() {
		return heap;
	}

	private boolean isHeap(int i, int n) {
		// Allikas: https://www.geeksforgeeks.org/how-to-check-if-a-given-array-represents-a-binary-heap/

		if (i >= (n - 1) / 2)
		{
			return true;
		}

		return heap[i] <= heap[2 * i + 1]
				&& heap[i] <= heap[2 * i + 2]
				&& isHeap(2 * i + 1, n)
				&& isHeap(2 * i + 2, n);
	}

	public void insert(int value, int index) {
		heap[index] = value;
		heapifyArrayFromIndex(index);
		LAST_INDEX++;
	}

	public int pop() {
		int last = heap[LAST_INDEX];
		heap[LAST_INDEX] = null;
		heapifyArrayFromIndex(LAST_INDEX);
		LAST_INDEX--;
		return last;
	}

	private void heapifyArrayFromIndex(int index) {
		if (heap[index] == null) {
			heapifyArrayFromIndex(index - 1);
			return;
		}
		int parentIndex = index / 2;
		if (heap[index] < heap[parentIndex]) {
			heap[index] += heap[parentIndex];
			heap[parentIndex] = heap[index] - heap[parentIndex];
			heap[index] -= heap[parentIndex];
		}
		if (parentIndex >= 1) {
			heapifyArrayFromIndex(parentIndex);
		}
		if (!isHeap(0, index)) {
			throw new RuntimeException("The array is not a heap!");
		}
	}

	public static void main(String[] args) {
		MinHeap heap = new MinHeap(10);
		for (int i = 0; i < heap.getHeap().length; i++) {
			int value = new Random().nextInt(101);
			heap.insert(value, i);
			System.out.println(Arrays.toString(heap.getHeap()));
		}
		int n = heap.getHeap().length - 1;
		if (heap.isHeap(0, n)) {
			System.out.println("Yes");
		}
		else {
			System.out.println("No");
		}
		int last = heap.pop();
		System.out.println(last);
		System.out.println(Arrays.toString(heap.getHeap()));
	}
}
