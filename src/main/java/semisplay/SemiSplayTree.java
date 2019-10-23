package semisplay;

import java.util.Iterator;

public class SemiSplayTree<E extends Comparable<E>> implements SearchTree<E> {

    private int splaySize;
    private Node<E> root;
    private int size;

    /**
     * Constructor for the SemiSplayTree
     * @param splaySize
     */
    public SemiSplayTree(int splaySize) {
        this.splaySize = splaySize;
        size = 0;
    }

    //public for testing purposes
    public Node<E> search(E key) {
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
            size++;
            return true;
        } else if (node.getKey() == key) {
            return false;
        } else {
            node.addChild(key);
            size++;
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
        Node<E> node = search(key);
        if (node.getKey().equals(key)) {
            remove(node);
            size--;
            return true;
        } else {
            return false;
        }
    }

    private void remove(Node<E> node) {
        Node<E> replacement = node.getBiggestLeftChild();
        if (replacement.getKey() == node.getKey()) {
            if (node.getParent() != null) {
                node.getParent().replace(node, node.getRightChild());
                if (node.getRightChild() != null) {
                    node.getRightChild().setParent(node.getParent());
                }
            } else {
                root = node.getRightChild();
                if (root != null) {
                    replacement.getRightChild().setParent(null);
                }
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
                root = replacement;
            }
            replacement.setRightChild(node.getRightChild());
            replacement.setLeftChild(node.getLeftChild());
            if (replacement.getRightChild() != null) {
                replacement.getRightChild().setParent(replacement);
            }
            if (replacement.getLeftChild() != null) {
                replacement.getLeftChild().setParent(replacement);
            }
        }
        node.reset();
    }

    @Override
    public int size() {
        return size;
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
