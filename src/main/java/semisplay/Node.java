package semisplay;

public class Node<E extends Comparable<E>> {
    private Node<E> rightChild;
    private Node<E> leftChild;
    private E key;

    public Node(E key) {
        this.key = key;
    }

    public Node<E> getRightChild() {
        return rightChild;
    }

    public void setRightChild(Node<E> rightChild) {
        this.rightChild = rightChild;
    }

    public Node<E> getLeftChild() {
        return leftChild;
    }

    public void setLeftChild(Node<E> leftChild) {
        this.leftChild = leftChild;
    }

    public E getKey() {
        return key;
    }

    public Node<E> searchChild(E key) {
        int compared = key.compareTo(this.key);
        return compared > 0 ? rightChild : compared < 0 ? leftChild : this;
    }
}
