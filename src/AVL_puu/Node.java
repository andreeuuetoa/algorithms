package AVL_puu;

public class Node {

	private final int value;
	private Node left;
	private Node right;

	private Node(int value) {
		this.value = value;
		this.left = null;
		this.right = null;
	}

	public int getValue() {
		return value;
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

	public void insert(int newValue) {
		Node newNode = new Node(newValue);
		insertNode(newNode);
		balance();
	}

	public void insertNode(Node newNode) {
		int value = newNode.getValue();
		if (value < getValue()) {
			if (this.getLeft() == null) {
				this.setLeft(newNode);
				return;
			}
			this.getLeft().insertNode(newNode);
		} else if (value > getValue()) {
			if (this.getRight() == null) {
				this.setRight(newNode);
				return;
			}
			this.getRight().insertNode(newNode);
		}
	}

	private int getHeight() {
		return getHeightSubtree(1);
	}

	private int getHeightSubtree(int layer) {
		int height = 0;
		if (getLeft() == null && getRight() == null) {
			height = layer;
		}
		if (getLeft() != null) {
			int newHeight = getLeft().getHeightSubtree(layer + 1);
			if (newHeight > height) {
				height = newHeight;
			}
		}
		if (getRight() != null) {
			int newHeight = getRight().getHeightSubtree(layer + 1);
			if (newHeight > height) {
				height = newHeight;
			}
		}
		return height;
	}

	private void balance() {
		int rightHeight = getRight() != null ? getRight().getHeight() : 0;
		int leftHeight = getLeft() != null ? getLeft().getHeight() : 0;
		int balanceFactor = rightHeight - leftHeight;
		System.out.println(balanceFactor);
		if (balanceFactor == 2) {
			if (getRight().getLeft() != null) {
				getRight().leftRotation();
			}
			rightRotation();
		} else if (balanceFactor == -2) {
			if (getLeft().getRight() != null) {
				getLeft().rightRotation();
			}
			leftRotation();
		}
	}

	private void rightRotation() {
		this.getRight().setLeft(this);
	}

	private void leftRotation() {
		this.getLeft().setRight(this);
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

	public static void main(String[] args) {
		Node root = new Node(30);
		int[] values = new int[] {51, 66};
		for (int value : values) {
			root.insert(value);
		}
		System.out.println(root.pseudoXMLRepresentation());
		System.out.println(root.getHeight());
	}
}
