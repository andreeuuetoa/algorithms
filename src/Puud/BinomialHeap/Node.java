package Puud.BinomialHeap;

public class Node {
	private final int layer;
	private Node firstChild;
	private Node nextSibling;

	Node(int layer) {
		this.layer = layer;
		firstChild = null;
		nextSibling = null;
	}

	public int getLayer() {
		return layer;
	}

	public Node getFirstChild() {
		return firstChild;
	}

	public void setFirstChild(Node firstChild) {
		this.firstChild = firstChild;
	}

	public Node getNextSibling() {
		return nextSibling;
	}

	public void setNextSibling(Node nextSibling) {
		this.nextSibling = nextSibling;
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
		XML.append(getLayer());
		XML.append(' ');
		if (getFirstChild() != null) {
			XML.append("\n");
			getFirstChild().fillXML(XML, ++layer);
			layer--;
			XML.append("\t".repeat(Math.max(0, layer - 1)));
		}
		XML.append("</L");
		XML.append(layer);
		XML.append(">");
		XML.append("\n");
		if (getNextSibling() != null) {
			getNextSibling().fillXML(XML, layer);
		}
	}
}
