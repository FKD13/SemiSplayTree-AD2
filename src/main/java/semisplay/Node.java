package semisplay;

import java.util.LinkedList;

public class Node<E extends Comparable<E>> {
    private Node<E> rightChild;
    private Node<E> leftChild;
    private Node<E> parent;
    private E key;

    /**
     * Constructor
     * @param key the key of the {@link Node<E> Node}
     */
    public Node(E key) {
        this.key = key;
    }

    /**
     * Simple getter
     * @return the parent {@link Node<E> Node}.
     */
    public Node<E> getParent() {
        return parent;
    }

    /**
     * Simple setter
     * @param parent the new parent {@link Node<E> Node}.
     */
    public void setParent(Node<E> parent) {
        this.parent = parent;
    }

    /**
     * Simple setter
     * @param rightChild the new rightChild {@link Node<E> Node}.
     */
    public void setRightChild(Node<E> rightChild) {
        this.rightChild = rightChild;
        if (rightChild != null) {
            rightChild.setParent(this);
        }
    }

    /**
     * Simple setter
     * @param leftChild the new leftChild {@link Node<E> Node}.
     */
    public void setLeftChild(Node<E> leftChild) {
        this.leftChild = leftChild;
        if (leftChild != null) {
            leftChild.setParent(this);
        }
    }

    /**
     * Simple getter
     * @return the rightChild {@link Node<E> Node}.
     */
    public Node<E> getRightChild() {
        return rightChild;
    }

    /**
     * Simple getter
     * @return the leftChild {@link Node<E> Node}.
     */
    public Node<E> getLeftChild() {
        return leftChild;
    }

    /**
     * Simple getter
     * @return the key.
     */
    public E getKey() {
        return key;
    }

    /**
     * This method will return the {@link Node<E> Node} the closest to the given key.
     * @param key the searched value.
     * @return {@link Node<E> Node}
     */
    public Node<E> searchChild(E key) {
        int compared = key.compareTo(this.key);
        return compared > 0 ? rightChild : compared < 0 ? leftChild : this;
    }

    /**
     * Add a given value as a child of this {@link Node<E> Node}.
     * @param key the value to add
     * @return the {@link Node<E> Node} that has been added.
     */
    public Node<E> addChild(E key) {
        return addChild(new Node<>(key));
    }

    /**
     * Will add the given node as child of this {@link semisplay.Node<E> node}.
     * When the keys are equal nothing will happen and this {@link semisplay.Node<E> node} will be returned.
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

    /**
     * This method will return the biggest leftChild of this {@link Node<E> Node}.
     * @return the biggest leftChild.
     */
    public Node<E> getBiggestLeftChild() {
        Node<E> biggestLeftChild = this;
        Node<E> pending = leftChild;
        while (pending != null) {
            biggestLeftChild = pending;
            pending = pending.getRightChild();
        }
        return biggestLeftChild;
    }

    /**
     * this method resets the Parent, left- and rightChild {@link Node<E> Node} to null.
     */
    public void reset() {
        leftChild = null;
        rightChild = null;
        parent = null;
    }

    /**
     * This method will replace the given child {@link Node<E> Node} with the new {@link Node Node}, if possible.
     * @param original the original child {@link Node<E> Node}.
     * @param newNode the new child {@link Node<E> Node}.
     */
    public void replace(Node<E> original, Node<E> newNode) {
        if (leftChild != null && leftChild.getKey().equals(original.getKey())) {
            leftChild = newNode;
            if (newNode != null) {
                newNode.setParent(this);
            }
        } else if (rightChild != null && rightChild.getKey().equals(original.getKey())) {
            rightChild = newNode;
            if (newNode != null) {
                newNode.setParent(this);
            }
        }
    }

    /**
     * This is a help method to calculate the depth of the {@link SemiSplayTree<E> SemiSplayTree}
     * @return the maximum depth from this {@link Node<E> Node}.
     */
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

    /**
     * A method that adds the key of the {@link Node<E> Node} and its children to the {@link java.util.LinkedList<E> LinkedList} in order.
     * @param list the {@link java.util.LinkedList LinkedList} to add values to.
     */
    public void addToIterable(LinkedList<E> list) {
        if (leftChild != null) {
            leftChild.addToIterable(list);
        }
        list.add(key);
        if (rightChild != null) {
            rightChild.addToIterable(list);
        }
    }

    @Override
    public String toString() {
        return key + " -> |" + leftChild + "| & |" + rightChild + "| ";
    }
}
