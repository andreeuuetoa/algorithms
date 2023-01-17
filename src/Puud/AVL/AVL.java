package Puud.AVL;

import java.util.Random;

public class AVL {
	public static Node insert(Node origin, int newValue) {
		Node newNode = new Node(newValue);
		if (origin.getValue() == null) {
			return newNode;
		}
		insertNode(origin, newNode);
		return balance(origin);
	}

	private static void insertNode(Node origin, Node newNode) {
		int value = newNode.getValue();
		if (value < origin.getValue()) {
			if (origin.getLeft() == null) {
				origin.setLeft(newNode);
				return;
			}
			insertNode(origin.getLeft(), newNode);
		} else if (value > origin.getValue()) {
			if (origin.getRight() == null) {
				origin.setRight(newNode);
				return;
			}
			insertNode(origin.getRight(), newNode);
		}
	}

	private static Node balance(Node origin) {
		if (origin.getLeft() != null) {
			origin.setLeft(balance(origin.getLeft()));
		}
		if (origin.getRight() != null) {
			origin.setRight(balance(origin.getRight()));
		}
		int originBF = origin.getBalanceFactor();
		if (originBF == 2) {
			int rightBF = origin.getRight().getBalanceFactor();
			if (rightBF == -1) {
				origin.setRight(leftRotation(origin.getRight()));
			}
			origin = rightRotation(origin);
		} else if (originBF == -2) {
			int leftBF = origin.getLeft().getBalanceFactor();
			if (leftBF == 1) {
				origin.setLeft(rightRotation(origin.getLeft()));
			}
			origin = leftRotation(origin);
		}
		return origin;
	}

	private static Node rightRotation(Node origin) {
		Node formerRight = origin.getRight();
		origin.setRight(formerRight.getLeft());
		formerRight.setLeft(origin);
		return formerRight;
	}

	private static Node leftRotation(Node origin) {
		Node formerLeft = origin.getLeft();
		origin.setLeft(formerLeft.getRight());
		formerLeft.setRight(origin);
		return formerLeft;
	}

	public static void main(String[] args) {
		Node root = new Node();
		for (int i = 0; i < 10; i++) {
			System.out.println(root.pseudoXMLRepresentation());
			int value = new Random().nextInt(101);
			root = insert(root, value);
		}
		System.out.println(root.pseudoXMLRepresentation());
		System.out.println(root.getHeight());
	}
}

