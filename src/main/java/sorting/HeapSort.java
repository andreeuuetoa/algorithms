package sorting;

import java.util.Arrays;
import java.util.Random;

public class HeapSort {
    private static final int HEAP_SIZE = 10;
    private static int LAST_INDEX = HEAP_SIZE - 1;

    private static boolean isHeap(Integer[] numbers, int i, int n) {
        // Allikas: https://www.geeksforgeeks.org/how-to-check-if-a-given-array-represents-a-binary-heap/

        if (i >= (n - 1) / 2)
        {
            return true;
        }

        return numbers[i] >= numbers[2 * i + 1]
                && numbers[i] >= numbers[2 * i + 2]
                && isHeap(numbers, 2 * i + 1, n)
                && isHeap(numbers, 2 * i + 2, n);
    }

    public static int pop(Integer[] heap) {
        int last = heap[LAST_INDEX];
        heap[LAST_INDEX] = null;
        LAST_INDEX--;
        heapifyArrayFromIndex(heap, LAST_INDEX);
        return last;
    }

    private static void heapifyArrayFromIndex(Integer[] integers, int index) {
        if (integers[index] == null) {
            heapifyArrayFromIndex(integers, index - 1);
            return;
        }
        int parentIndex = index / 2;
        if (integers[index] > integers[parentIndex]) {
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

    public static void heapSort(Integer[] numbers) {
        heapifyArrayFromIndex(numbers, numbers.length - 1);
        for (int i = 0; i < numbers.length; i++) {
            int largest = pop(numbers);
            System.out.println(largest);
            numbers[numbers.length - 1] = largest;
        }
    }

    public static void main(String[] args) {
        Integer[] numbers = new Integer[HEAP_SIZE];
        for (int i = 0; i < numbers.length; i++) {
            int value = new Random().nextInt(101);
            numbers[i] = value;
        }
        System.out.println(Arrays.toString(numbers));
        heapSort(numbers);
        System.out.println(Arrays.toString(numbers));
    }
}
