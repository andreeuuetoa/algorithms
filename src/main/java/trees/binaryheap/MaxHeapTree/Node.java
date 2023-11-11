package trees.binaryheap.MaxHeapTree;

import java.util.Random;

public class Node {
	private Integer value;
	private Node left;
	private Node right;

	public Node() {
		this.value = null;
	}

	public Node(int value) {
		this.value = value;
	}

	private int getMinHeight() {
		return getMinHeightSubtree(1);
	}

	private int getMinHeightSubtree(int layer) {
		int height = 0;
		if (left == null && right == null) {
			height = layer;
		}
		if (left != null) {
			int newHeight = left.getMinHeightSubtree(layer + 1);
			if (newHeight < height || height == 0) {
				height = newHeight;
			}
		}
		if (right != null) {
			int newHeight = right.getMinHeightSubtree(layer + 1);
			if (newHeight < height || height == 0) {
				height = newHeight;
			}
		}
		return height;
	}

	public int getBalanceFactor() {
		int rightHeight = right != null ? right.getMinHeight() : 0;
		int leftHeight = left != null ? left.getMinHeight() : 0;
		return rightHeight - leftHeight;
	}

	public String pseudoXMLRepresentation() {
		StringBuilder XML = new StringBuilder();
		fillXML(XML, 1);
		return XML.toString();
	}

	private void fillXML(StringBuilder XML, int layer) {
		XML.append("\t".repeat(layer - 1));
		XML.append("<L");
		XML.append(layer);
		XML.append("> ");
		XML.append(value);
		XML.append(' ');
		if (left != null) {
			XML.append("\n");
			left.fillXML(XML, ++layer);
			layer--;
			XML.append("\t".repeat(Math.max(0, layer - 1)));
		} else {
			XML.append("\n");
			XML.append("\t".repeat(layer));
			XML.append("<L");
			XML.append(++layer);
			XML.append("> ");
			XML.append("</L");
			XML.append(layer--);
			XML.append(">");
		}
		if (right != null) {
			XML.append("\n");
			right.fillXML(XML, ++layer);
			layer--;
			XML.append("\t".repeat(Math.max(0, layer - 3)));
		} else {
			XML.append("\n");
			XML.append("\t".repeat(layer));
			XML.append("<L");
			XML.append(++layer);
			XML.append("> ");
			XML.append("</L");
			XML.append(layer--);
			XML.append(">\n");
		}
		XML.append("\t".repeat(layer - 1));
		XML.append("</L");
		XML.append(layer);
		XML.append(">");
		XML.append("\n");
	}

	private boolean isHeap() {
		// Allikas: https://www.geeksforgeeks.org/how-to-check-if-a-given-array-represents-a-binary-heap/

		if (left == null && right == null) {
			return true;
		}
		if (right == null) {
			return value >= left.value;
		}

		return value >= left.value
				&& value >= right.value
				&& left.isHeap()
				&& right.isHeap();
	}

	private void insert(int newValue) {
		if (this.value == null) {
			this.value = newValue;
			return;
		}
		Node newNode = new Node(newValue);
		insertNode(newNode);
		heapify();
	}

	private void insertNode(Node newNode) {
		if (left == null) {
			left = newNode;
			heapify();
			return;
		}
		if (right == null) {
			right = newNode;
			heapify();
			return;
		}
		if (getBalanceFactor() == -1) {
			if (left.getBalanceFactor() == -1) {
				left.insertNode(newNode);
			} else {
				right.insertNode(newNode);
			}
		} else {
			if (right.getBalanceFactor() == -1) {
				right.insertNode(newNode);
			} else {
				left.insertNode(newNode);
			}
		}
		heapify();
	}

	private void heapify() {
		if (left != null) {
			if (left.value > value) {
				left.value = left.value + value;
				value = left.value - value;
				left.value = left.value - value;
			}
		}
		if (right != null) {
			if (right.value > value) {
				right.value = right.value + value;
				value = right.value - value;
				right.value = right.value - value;
			}
		}
	}

	public static void main(String[] args) {
		Node root = new Node();
		for (int i = 0; i < 10; i++) {
			int value = new Random().nextInt(101);
			root.insert(value);
			System.out.println(root.pseudoXMLRepresentation());
		}
		if (root.isHeap()) {
			System.out.println("Yes");
		} else {
			System.out.println("No");
		}
	}
}

