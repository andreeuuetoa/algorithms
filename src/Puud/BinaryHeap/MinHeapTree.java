package Puud.BinaryHeap;

import java.util.Random;

public class MinHeapTree {
	private static boolean isHeap(Node root) {
		// Allikas: https://www.geeksforgeeks.org/how-to-check-if-a-given-array-represents-a-binary-heap/

		if (root.getLeft() == null && root.getRight() == null) {
			return true;
		}
		if (root.getRight() == null) {
			return root.getValue() <= root.getLeft().getValue();
		}

		return root.getValue() <= root.getLeft().getValue()
				&& root.getValue() <= root.getRight().getValue()
				&& isHeap(root.getLeft())
				&& isHeap(root.getRight());
	}

	private static Node insert(Node root, int value) {
		Node newNode = new Node(value);
		if (root.getValue() == null) {
			return newNode;
		}
		insertNode(root, newNode);
		return heapify(root);
	}

	private static Node insertNode(Node root, Node newNode) {
		if (root.getLeft() == null) {
			root.setLeft(newNode);
			return heapify(root);
		}
		if (root.getRight() == null) {
			root.setRight(newNode);
			return heapify(root);
		}
		if (root.getBalanceFactor() == -1) {
			if (root.getLeft().getBalanceFactor() == -1) {
				root.setLeft(insertNode(root.getLeft(), newNode));
			} else {
				root.setRight(insertNode(root.getRight(), newNode));
			}
		} else {
			if (root.getRight().getBalanceFactor() == -1) {
				root.setRight(insertNode(root.getRight(), newNode));
			} else {
				root.setLeft(insertNode(root.getLeft(), newNode));
			}
		}
		return heapify(root);
	}

	private static Node heapify(Node root) {
		if (root.getLeft() != null) {
			if (root.getLeft().getValue() < root.getValue()) {
				root.getLeft().setValue(root.getLeft().getValue() + root.getValue());
				root.setValue(root.getLeft().getValue() - root.getValue());
				root.getLeft().setValue(root.getLeft().getValue() - root.getValue());
			}
		}
		if (root.getRight() != null) {
			if (root.getRight().getValue() < root.getValue()) {
				root.getRight().setValue(root.getRight().getValue() + root.getValue());
				root.setValue(root.getRight().getValue() - root.getValue());
				root.getRight().setValue(root.getRight().getValue() - root.getValue());
			}
		}
		return root;
	}

	public static void main(String[] args) {
		Node root = new Node();
		for (int i = 0; i < 10; i++) {
			int value = new Random().nextInt(101);
			root = insert(root, value);
			System.out.println(root.pseudoXMLRepresentation());
		}
		if (isHeap(root)) {
			System.out.println("Yes");
		} else {
			System.out.println("No");
		}
	}
}
