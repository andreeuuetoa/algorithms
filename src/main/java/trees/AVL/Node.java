package trees.AVL;

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

	public int getHeight() {
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

	public void insert(int newValue) {
		if (getValue() == null) {
			setValue(newValue);
			return;
		}
		Node newNode = new Node(newValue);
		insertNode(newNode);
		balance();
	}

	private void insertNode(Node newNode) {
		int value = newNode.getValue();
		if (value < getValue()) {
			if (getLeft() == null) {
				setLeft(newNode);
				return;
			}
			getLeft().insertNode(newNode);
		} else {
			if (getRight() == null) {
				setRight(newNode);
				return;
			}
			getRight().insertNode(newNode);
		}
	}

	private void balance() {
		if (getLeft() != null) {
			getLeft().balance();
		}
		if (getRight() != null) {
			getRight().balance();
		}
		int originBF = getBalanceFactor();
		if (originBF == 2) {
			int rightBF = getRight().getBalanceFactor();
			if (rightBF == -1) {
				getRight().rightRotation();
			}
			leftRotation();
		} else if (originBF == -2) {
			int leftBF = getLeft().getBalanceFactor();
			if (leftBF == 1) {
				getLeft().leftRotation();
			}
			rightRotation();
		}
	}

	private void rightRotation() {
		Node formerThis = makeCopy();
		Node formerLeft = formerThis.getLeft();
		setValue(getLeft().getValue());
		setLeft(getLeft().getLeft());
		setRight(formerThis);
		getRight().setLeft(formerLeft.getRight());
	}

	private void leftRotation() {
		Node formerThis = makeCopy();
		Node formerRight = formerThis.getRight();
		setValue(getRight().getValue());
		setRight(getRight().getRight());
		setLeft(formerThis);
		getLeft().setRight(formerRight.getLeft());
	}

	private Node makeCopy() {
		Node copyThis = new Node(getValue());
		if (getLeft() != null) {
			copyThis.setLeft(getLeft().makeCopy());
		}
		if (getRight() != null) {
			copyThis.setRight(getRight().makeCopy());
		}
		return copyThis;
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
