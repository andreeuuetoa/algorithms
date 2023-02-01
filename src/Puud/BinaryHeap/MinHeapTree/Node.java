package Puud.BinaryHeap.MinHeapTree;

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

	public Integer getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public Node getLeft() {
		return left;
	}

	public void setLeft(Node left) {
		this.left = left;
	}

	public Node getRight() {
		return right;
	}

	public void setRight(Node right) {
		this.right = right;
	}

	private int getMinHeight() {
		return getMinHeightSubtree(1);
	}

	private int getMinHeightSubtree(int layer) {
		int height = 0;
		if (getLeft() == null && getRight() == null) {
			height = layer;
		}
		if (getLeft() != null) {
			int newHeight = getLeft().getMinHeightSubtree(layer + 1);
			if (newHeight < height || height == 0) {
				height = newHeight;
			}
		}
		if (getRight() != null) {
			int newHeight = getRight().getMinHeightSubtree(layer + 1);
			if (newHeight < height || height == 0) {
				height = newHeight;
			}
		}
		return height;
	}

	public int getBalanceFactor() {
		int rightHeight = getRight() != null ? getRight().getMinHeight() : 0;
		int leftHeight = getLeft() != null ? getLeft().getMinHeight() : 0;
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
		XML.append(getValue());
		XML.append(' ');
		if (getLeft() != null) {
			XML.append("\n");
			getLeft().fillXML(XML, ++layer);
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
		if (getRight() != null) {
			XML.append("\n");
			getRight().fillXML(XML, ++layer);
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

		if (getLeft() == null && getRight() == null) {
			return true;
		}
		if (getRight() == null) {
			return getValue() <= getLeft().getValue();
		}

		return getValue() <= getLeft().getValue()
				&& getValue() <= getRight().getValue()
				&& getLeft().isHeap()
				&& getRight().isHeap();
	}

	private void insert(int value) {
		if (getValue() == null) {
			setValue(value);
			return;
		}
		Node newNode = new Node(value);
		insertNode(newNode);
		heapify();
	}

	private void insertNode(Node newNode) {
		if (getLeft() == null) {
			setLeft(newNode);
			heapify();
			return;
		}
		if (getRight() == null) {
			setRight(newNode);
			heapify();
			return;
		}
		if (getBalanceFactor() == -1) {
			if (getLeft().getBalanceFactor() == -1) {
				getLeft().insertNode(newNode);
			} else {
				getRight().insertNode(newNode);
			}
		} else {
			if (getRight().getBalanceFactor() == -1) {
				getRight().insertNode(newNode);
			} else {
				getLeft().insertNode(newNode);
			}
		}
		heapify();
	}

	private void heapify() {
		if (getLeft() != null) {
			if (getLeft().getValue() < getValue()) {
				getLeft().setValue(getLeft().getValue() + getValue());
				setValue(getLeft().getValue() - getValue());
				getLeft().setValue(getLeft().getValue() - getValue());
			}
		}
		if (getRight() != null) {
			if (getRight().getValue() < getValue()) {
				getRight().setValue(getRight().getValue() + getValue());
				setValue(getRight().getValue() - getValue());
				getRight().setValue(getRight().getValue() - getValue());
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

