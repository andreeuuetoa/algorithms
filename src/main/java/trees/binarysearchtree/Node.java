package trees.binarysearchtree;

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

	public void insert(int newValue) {
		if (value == null) {
			value = newValue;
			return;
		}
		Node newNode = new Node(newValue);
		insertNode(newNode);
	}

	private void insertNode(Node newNode) {
		int newValue = newNode.value;
		if (newValue < this.value) {
			if (left == null) {
				left = newNode;
				return;
			}
			left.insertNode(newNode);
		} else if (newValue > this.value) {
			if (right == null) {
				right = newNode;
				return;
			}
			right.insertNode(newNode);
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
