package semisplay;

import java.util.LinkedList;

public class Node<E extends Comparable<E>> {
    private Node<E> rightChild;
    private Node<E> leftChild;
    private Node<E> parent;
    private E key;

    public Node(E key) {
        this.key = key;
    }

    public Node<E> getParent() {
        return parent;
    }

    public void setParent(Node<E> parent) {
        this.parent = parent;
    }

    public void setRightChild(Node<E> rightChild) {
        this.rightChild = rightChild;
    }

    public void setLeftChild(Node<E> leftChild) {
        this.leftChild = leftChild;
    }

    public Node<E> getRightChild() {
        return rightChild;
    }

    public Node<E> getLeftChild() {
        return leftChild;
    }

    public E getKey() {
        return key;
    }

    public Node<E> searchChild(E key) {
        int compared = key.compareTo(this.key);
        return compared > 0 ? rightChild : compared < 0 ? leftChild : this;
    }

    public Node<E> addChild(E key) {
        return addChild(new Node<>(key));
    }

    /**
     * Will add the given node as child of this {@link semisplay.Node node}.
     * When the keys are equal nothing will happen and this {@link semisplay.Node node} will be returned.
     * Otherwise the node will be added as a child and returned. The adding can overwrite existing children.
     * @param node The node to add.
     * @return The result node.
     */
    public Node<E> addChild(Node<E> node) {
        //add the node as a child node
        int compared = node.getKey().compareTo(key);
        if (compared > 0) {
            rightChild = node;
        } else if (compared < 0) {
            leftChild = node;
        }
        //return the right node
        if (compared == 0) {
            return this;
        } else {
            node.setParent(this);
            return node;
        }
    }

    public Node<E> getBiggestLeftChild() {
        Node<E> biggestLeftChild = this;
        Node<E> pending = leftChild;
        while (pending != null) {
            biggestLeftChild = pending;
            pending = pending.getRightChild();
        }
        return biggestLeftChild;
    }

    public String toString() {
        return "-------\nKey: " + getKey() + "\nParent: " + "Redacted" + "\nLeftchild: " + leftChild + "\nRighchild: " + rightChild + "\n--------\n";
    }

    public void reset() {
        leftChild = null;
        rightChild = null;
        parent = null;
    }

    public void replace(Node<E> original, Node<E> newNode) {
        if (leftChild != null && leftChild.getKey() == original.getKey()) {
            leftChild = newNode;
        } else if (rightChild != null && rightChild.getKey() == original.getKey()) {
            rightChild = newNode;
        }
    }

    public int depth() {
        int max = 0;
        if (rightChild != null) {
            int n = rightChild.depth();
            if (max < n) {
                max = n;
            }
        }
        if (leftChild != null) {
            int n = leftChild.depth();
            if (max < n) {
                max = n;
            }
        }
        return max + 1;
    }

    public void addToIterable(LinkedList<E> list) {
        if (leftChild != null) {
            leftChild.addToIterable(list);
        }
        list.add(key);
        if (rightChild != null) {
            rightChild.addToIterable(list);
        }
    }
}
