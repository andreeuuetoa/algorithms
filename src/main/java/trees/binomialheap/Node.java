package trees.binomialheap;

import java.util.Random;

class Node {
	private int layer;
	private Node firstChild;
	private Node nextSibling;

	Node(int layer) {
		this.layer = layer;
		firstChild = null;
		nextSibling = null;
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
		XML.append(layer);
		XML.append(' ');
		if (firstChild != null) {
			XML.append("\n");
			firstChild.fillXML(XML, ++layer);
			layer--;
			XML.append("\t".repeat(Math.max(0, layer - 1)));
		}
		XML.append("</L");
		XML.append(layer);
		XML.append(">");
		XML.append("\n");
		if (nextSibling != null) {
			nextSibling.fillXML(XML, layer);
		}
	}

	private void createBinomialHeap(int layer) {
		this.layer = layer;
        firstChild = createBinomialFirstChild(new Node(layer - 1));
	}

	private static Node createBinomialFirstChild(Node root) {
		if (root.layer <= 0) {
			return root;
		}
		root.firstChild = createBinomialFirstChild(new Node(root.layer - 1));
		if (root.layer >= 1) {
			root.nextSibling = createBinomialFirstChild(new Node(root.layer - 1));
		}
		return root;
	}

	public static void main(String[] args) {
		int layers = new Random().nextInt(11);
		Node root = new Node(layers);
		root.createBinomialHeap(layers);
		System.out.println(root.pseudoXMLRepresentation());
	}
}
