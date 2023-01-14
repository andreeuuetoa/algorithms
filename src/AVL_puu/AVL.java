package AVL_puu;

public class AVL {
	public static Node insert(Node origin, int newValue) {
		Node newNode = new Node(newValue);
		if (origin.getValue() == null) {
			return newNode;
		}
		insertNode(origin, newNode);
		return balance(origin);
	}

	public static void insertNode(Node origin, Node newNode) {
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
		int[] values = new int[] {'M', 'N', 'O', 'L', 'K', 'Q', 'P', 'H', 'I', 'A'};
		for (int value : values) {
			System.out.println(root.pseudoXMLRepresentation());
			root = insert(root, value);
		}
		System.out.println(root.pseudoXMLRepresentation());
		System.out.println(root.getHeight());
	}
}

class Node {

	private final Integer value;
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

	int getHeight() {
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

	int getBalanceFactor() {
		int rightHeight = getRight() != null ? getRight().getHeight() : 0;
		int leftHeight = getLeft() != null ? getLeft().getHeight() : 0;
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
