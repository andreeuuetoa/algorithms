package Puud.BinomialHeap;

public class BinomialHeap {
	@SuppressWarnings("SameParameterValue")
	private static Node createBinomialHeap(int layer) {
		Node root = new Node(layer);
		Node rootChild = createBinomialFirstChild(new Node(layer - 1));
		root.setFirstChild(rootChild);
		return root;
	}

	private static Node createBinomialFirstChild(Node root) {
		if (root.getLayer() <= 0) {
			return root;
		}
		root.setFirstChild(createBinomialFirstChild(new Node(root.getLayer() - 1)));
		if (root.getLayer() >= 1) {
			root.setNextSibling(createBinomialFirstChild(new Node(root.getLayer() - 1)));
		}
		return root;
	}

	public static void main(String[] args) {
		Node root = createBinomialHeap(5);
		System.out.println(root.pseudoXMLRepresentation());
	}
}
