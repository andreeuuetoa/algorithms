package Puud.BinarySearchTree;

import java.util.Random;

class Node {
	private Integer value;
	private Node left;
	private Node right;

	Node() {
		this.value = null;
	}

	Node(int value) {
		this.value = value;
	}

	public Integer getValue() {
		return value;
	}

	private void setValue(int value) {
		this.value = value;
	}

	public Node getLeft() {
		return left;
	}

	private void setLeft(Node left) {
		this.left = left;
	}

	public Node getRight() {
		return right;
	}

	private void setRight(Node right) {
		this.right = right;
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

	public void insert(int newValue) {
		if (getValue() == null) {
			setValue(newValue);
			return;
		}
		Node newNode = new Node(newValue);
		insertNode(newNode);
	}

	private void insertNode(Node newNode) {
		int value = newNode.getValue();
		if (value < getValue()) {
			if (getLeft() == null) {
				setLeft(newNode);
				return;
			}
			getLeft().insertNode(newNode);
		} else if (value > getValue()) {
			if (getRight() == null) {
				setRight(newNode);
				return;
			}
			getRight().insertNode(newNode);
		}
	}

	public static void main(String[] args) {
		Node root = new Node();
		for (int i = 0; i <= 10; i++) {
			System.out.println(root.pseudoXMLRepresentation());
			int value = new Random().nextInt(101);
			root.insert(value);
		}
		System.out.println(root.pseudoXMLRepresentation());
	}
}
