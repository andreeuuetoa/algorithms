package compression.huffman;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Prefix codes and Huffman tree.
 * Tree depends on source data.
 */
public class Huffman {

    private final HashMap<Byte, Integer> byteOcc;
    private final HashMap<Byte, String> byteCode;
    private Node codeTree;

    /** Constructor to build the Huffman code for a given bytearray.
     *
     * @param original source data
     */
    Huffman (byte[] original) {
        byteOcc = new HashMap<>();
        for (byte b : original) {
            if (byteOcc.containsKey(b)) {
                byteOcc.put(b, byteOcc.get(b) + 1);
            } else {
                byteOcc.put(b, 1);
            }
        }
        byteCode = new HashMap<>();
        if (original.length == 0) {
            return;
        }
        createCodeTreeFromMap();
        if (codeTree.getLeftChild() == null && codeTree.getRightChild() == null) {
            byteCode.put(codeTree.getByte(), "0");
        } else {
            createCodesFromTree(codeTree, "");
        }
        Byte lastByte = original[original.length - 1];
        String lastByteCode = byteCode.get(lastByte);
        while (lastByteCode.charAt(lastByteCode.length() - 1) == '0') {
            Node parentByteNode = getByteNode(codeTree, lastByte);
            if (parentByteNode == null) {
                return;
            }
            invertCodeTreeAtNode(parentByteNode);
            if (codeTree.getLeftChild() == null && codeTree.getRightChild() == null) {
                byteCode.put(codeTree.getByte(), "0");
            } else {
                createCodesFromTree(codeTree, "");
            }
        }
    }

    private void createCodeTreeFromMap() {
        LinkedList<Node> nodes = byteOcc.entrySet()
                .stream()
                .map(x -> new Node(x.getKey(), x.getValue()))
                .sorted(Comparator.comparing(Node::getSize))
                .collect(Collectors.toCollection(LinkedList::new));
        coalesceLeaves(nodes);
    }

    private void coalesceLeaves(LinkedList<Node> nodes) {
        if (nodes.size() < 2) {
            codeTree = nodes.get(0);
            return;
        }
        Node firstNode = nodes.removeFirst();
        Node secondNode = nodes.removeFirst();
        nodes.add(new Node(secondNode, firstNode));
        nodes = nodes
                .stream()
                .sorted(Comparator.comparing(Node::getSize))
                .collect(Collectors.toCollection(LinkedList::new));
        coalesceLeaves(nodes);
    }

    private void createCodesFromTree(Node codeTree, String prefix) {
        Node leftChild = codeTree.getLeftChild();
        if (leftChild != null) {
            createCodesFromTree(leftChild, prefix + "0");
        }
        Node rightChild = codeTree.getRightChild();
        if (rightChild != null) {
            createCodesFromTree(rightChild, prefix + "1");
        }
        if (codeTree.getByte() != null) {
            byteCode.put(codeTree.getByte(), prefix);
        }
    }

    private Node getByteNode(Node from, Byte code) {
        Node nodeFound = null;
        if (from.getLeftChild() != null && code.equals(from.getLeftChild().getByte()) ||
                from.getRightChild() != null && code.equals(from.getRightChild().getByte())) {
            nodeFound = from;
        }
        if (from.getLeftChild() != null) {
            getByteNode(from.getLeftChild(), code);
        }
        if (from.getRightChild() != null) {
            getByteNode(from.getRightChild(), code);
        }
        return nodeFound;
    }

    private void invertCodeTreeAtNode(Node node) {
        Node left = node.getLeftChild();
        Node right = node.getRightChild();
        node.setLeftChild(right);
        node.setRightChild(left);
    }

    /** Length of encoded data in bits.
     * @return number of bits
     */
    public int bitLength() {
        return byteOcc.entrySet()
                .stream()
                .mapToInt(x -> x.getValue() * byteCode.get(x.getKey()).length())
                .sum();
    }


    /** Encoding the byte array using this prefixcode.
     * @param origData original data
     * @return encoded data
     */
    public byte[] encode (byte[] origData) {
        BitSet bitSet = new BitSet();
        int bitSetIndex = 0;
        for (byte b : origData) {
            String code = byteCode.get(b);
            for (char c : code.toCharArray()) {
                if (c == '1') {
                    bitSet.set(bitSetIndex);
                }
                bitSetIndex++;
            }
        }
        if (bitSet.isEmpty()) {
            return new byte[]{0};
        }
        return bitSet.toByteArray();
    }

    /** Decoding the byte array using this prefixcode.
     * @param encodedData encoded data
     * @return decoded data (hopefully identical to original)
     */
    public byte[] decode (byte[] encodedData) {
        byte[] decoded = new byte[256];
        int decodedIndex = 0;
        int dataLength = 0;
        String codeToSearch = "";
        for (byte b : encodedData) {
            char[] bitsInB = byteToBits(b).toCharArray();
            for (char bit : bitsInB) {
                codeToSearch += bit;
                Byte keyForValue = getByteCodeKey(byteCode, codeToSearch);
                if (keyForValue != null) {
                    decoded[decodedIndex] = keyForValue;
                    codeToSearch = "";
                    decodedIndex++;
                }
                dataLength++;
                if (dataLength == bitLength()) {
                    return removeExcess(decoded, decodedIndex);
                }
            }
        }
        return removeExcess(decoded, dataLength);
    }

    /**
     * Break a byte into an array of bits.
     * <a href="http://www.java2s.com/example/java-utility-method/byte-to-bit-index-0.html">Source</a>
     */
    private String byteToBits(Byte b) {
        StringBuilder bits = new StringBuilder(Integer.toBinaryString(b & 0xFF));
        while (bits.length() < 8) {
            bits.insert(0, "0");
        }
        bits.reverse();
        return bits.toString();
    }

    /**
     * Find a key from a map by its value.
     * <a href="https://www.baeldung.com/java-map-key-from-value">Source</a>
     */
    public Byte getByteCodeKey(Map<Byte, String> map, String value) {
        List<Map.Entry<Byte, String>> matchingBytes = map.entrySet()
                .stream()
                .filter(x -> x.getValue().equals(value))
                .toList();
        if (matchingBytes.size() == 0) {
            return null;
        }
        return matchingBytes.get(0).getKey();
    }

    /**
     * Removes elements with value 0 from data and returns it.
     */
    private byte[] removeExcess(byte[] data, int size) {
        byte[] decoded = new byte[size];
        int decodedIndex = 0;
        for (byte b : data) {
            if (decodedIndex == size) {
                return decoded;
            }
            decoded[decodedIndex] = b;
            decodedIndex++;
        }
        return decoded;
    }

    /** Main method. */
    public static void main (String[] params) {
        String tekst = "AAAAABBBB";
        byte[] orig = tekst.getBytes();
        System.out.println(Arrays.toString(orig));
        Huffman huf = new Huffman (orig);
        byte[] kood = huf.encode (orig);
        System.out.println(Arrays.toString(kood));
        byte[] orig2 = huf.decode (kood);
        System.out.println(Arrays.toString(orig2));
        // must be equal: orig, orig2
        System.out.println (Arrays.equals (orig, orig2));
        int length = huf.bitLength();
        System.out.println ("Length of encoded data in bits: " + length);
    }
}

class Node {
    private final Byte aByte;
    private final int size;
    private Node leftChild;
    private Node rightChild;

    public Node(byte name, int size) {
        this.aByte = name;
        this.size = size;
        this.leftChild = null;
        this.rightChild = null;
    }

    public Node(Node leftChild, Node rightChild) {
        this.aByte = null;
        this.size = leftChild.getSize() + rightChild.getSize();
        this.leftChild = leftChild;
        this.rightChild = rightChild;
    }

    public Byte getByte() {
        return aByte;
    }

    public Integer getSize() {
        return size;
    }

    public Node getLeftChild() {
        return leftChild;
    }

    public Node getRightChild() {
        return rightChild;
    }

    public void setLeftChild(Node leftChild) {
        this.leftChild = leftChild;
    }

    public void setRightChild(Node rightChild) {
        this.rightChild = rightChild;
    }
}
