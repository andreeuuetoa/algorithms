package Puud.BinarySearchTree;

import java.util.Random;

public class BST {
    public static Node insert(Node origin, int newValue) {
        Node newNode = new Node(newValue);
        if (origin.getValue() == null) {
            return newNode;
        }
        insertNode(origin, newNode);
        return origin;
    }

    private static void insertNode(Node origin, Node newNode) {
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

    public static void main(String[] args) {
        Node root = new Node();
        for (int i = 0; i <= 10; i++) {
            System.out.println(root.pseudoXMLRepresentation());
            int value = new Random().nextInt(101);
            root = insert(root, value);
        }
        System.out.println(root.pseudoXMLRepresentation());
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