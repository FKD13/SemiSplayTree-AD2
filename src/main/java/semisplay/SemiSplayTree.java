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
        Node<E> node = search(key);
        if (node.getKey() == key) {
            Node<E> replacement = node.getBiggestLeftChild();
            if (replacement == null) {
                node.getParent().addChild(node.getRightChild());
            } else {
                node.
            }
        } else {
            return false;
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
