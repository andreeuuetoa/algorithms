package Puud.BinaryHeap;

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

	int getMinHeight() {
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

	int getBalanceFactor() {
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
}

