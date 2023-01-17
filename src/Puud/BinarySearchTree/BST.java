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

