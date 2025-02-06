package trees.redblacktree;

import java.util.Random;

class Node {
	private Integer value;
	private Node left;
	private Node right;
	private Colour colour;

	Node() {
		this.value = null;
	}

	Node(int value) {
		this.value = value;
	}

	public int getHeight() {
		return getHeightSubtree(1);
	}

	private int getHeightSubtree(int layer) {
		int height = 0;
		if (left == null && right == null) {
			height = layer;
		}
		if (left != null) {
			int newHeight = left.getHeightSubtree(layer + 1);
			if (newHeight > height) {
				height = newHeight;
			}
		}
		if (right != null) {
			int newHeight = right.getHeightSubtree(layer + 1);
			if (newHeight > height) {
				height = newHeight;
			}
		}
		return height;
	}

	int getBalanceFactor() {
		int rightHeight = right != null ? right.getHeight() : 0;
		int leftHeight = left != null ? left.getHeight() : 0;
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
		XML.append(colour);
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
		balance();
		colour();
	}

	private void insertNode(Node newNode) {
		int newValue = newNode.value;
		if (newValue < this.value) {
			if (left == null) {
				left = newNode;
				return;
			}
			left.insertNode(newNode);
		} else {
			if (right == null) {
				right = newNode;
				return;
			}
			right.insertNode(newNode);
		}
	}

	private void balance() {
		if (left != null) {
			left.balance();
		}
		if (right != null) {
			right.balance();
		}
		int originBF = getBalanceFactor();
		if (originBF == 2) {
			int rightBF = right.getBalanceFactor();
			if (rightBF == -1) {
				right.rightRotation();
			}
			leftRotation();
		} else if (originBF == -2) {
			int leftBF = left.getBalanceFactor();
			if (leftBF == 1) {
				left.leftRotation();
			}
			rightRotation();
		}
	}

	private void rightRotation() {
		Node formerThis = makeCopy();
		Node formerLeft = formerThis.left;
		value = left.value;
		left = left.left;
		right = formerThis;
		right.left = formerLeft.right;
	}

	private void leftRotation() {
		Node formerThis = makeCopy();
		Node formerRight = formerThis.right;
		value = right.value;
		right = right.right;
		left = formerThis;
		left.right = formerRight.left;
	}

	private Node makeCopy() {
		Node copyThis = new Node(value);
		if (left != null) {
			copyThis.left = left.makeCopy();
		}
		if (right != null) {
			copyThis.right = right.makeCopy();
		}
		return copyThis;
	}

	// TODO
	private void colour() {

	}

	public static void main(String[] args) {
		Node root = new Node();
		for (int i = 0; i < 10; i++) {
			System.out.println(root.pseudoXMLRepresentation());
			int value = new Random().nextInt(101);
			root.insert(value);
		}
		System.out.println(root.pseudoXMLRepresentation());
		System.out.println(root.getHeight());
	}
}
