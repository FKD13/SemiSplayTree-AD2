package semisplay;

import java.util.Iterator;

public class SemiSplayTree<E extends Comparable<E>> implements SearchTree<E> {

    private int splaySize;
    private Node<E> root;

    /**
     * Constructor for the SemiSplayTree
     * @param splaySize
     */
    public SemiSplayTree(int splaySize) {
        this.splaySize = splaySize;
    }

    private Node<E> search(E key) {
        Node<E> node = root;
        Node<E> pNode = null;
        while (node != null && node != pNode) {
            pNode = node;
            node = node.searchChild(key);
        }
        return pNode;
    }

    @Override
    public boolean add(E key) {
        Node<E> node = search(key);
        if (node == null) {
            root = new Node<>(key);
            return true;
        } else if (node.getKey() == key) {
            return false;
        } else {
            node.addChild(key);
            return true;
        }
    }

    @Override
    public boolean contains(E key) {
        Node result = search(key);
        return result != null && result.getKey() == key;
    }

    @Override
    public boolean remove(E key) {
        System.out.println("Remove:  " + key);
        Node<E> node = search(key);
        System.out.println("Found: " + node.getKey() + " Searched: " + key);
        System.out.println(node);
        if (node.getKey() == key) {
            System.out.println(" Removing: " + key);
            remove(node);
            return true;
        } else {
            System.out.println(" Aborting");
            return false;
        }
    }

    private void remove(Node<E> node) {
        Node<E> replacement = node.getBiggestLeftChild();
        if (replacement == node) {
            if (node.getParent() != null) {
                node.getParent().setRightChild(node.getRightChild());
                replacement.getRightChild().setParent(replacement.getParent());
            } else {
                System.out.println("  setting Root: " + replacement.getRightChild().getKey());
                root = replacement.getRightChild();
                System.out.println("  New Root: " + root.getKey());
                replacement.getRightChild().setParent(null);
            }
        } else {
            remove(replacement);
            Node<E> parent = node.getParent();
            replacement.setParent(parent);
            if (parent != null) {
                if (parent.getLeftChild() == node) {
                    parent.setLeftChild(replacement);
                } else {
                    parent.setRightChild(replacement);
                }
            } else {
                System.out.println("  setting Root" + replacement.getKey());
                root = replacement;
                System.out.println("  ²&New Root: " + root.getKey());
            }
            replacement.setRightChild(node.getRightChild());
            replacement.setLeftChild(node.getLeftChild());
        }
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public int depth() {
        return 0;
    }

    @Override
    public Iterator iterator() {
        return null;
    }
}
