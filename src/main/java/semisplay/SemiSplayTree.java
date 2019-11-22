package semisplay;

import java.util.Iterator;
import java.util.LinkedList;

public class SemiSplayTree<E extends Comparable<E>> implements SearchTree<E> {

    private int splaySize;
    private Node<E> root;
    private int size;

    /**
     * Constructor for the {@link SemiSplayTree SemiSplayTree}.
     * @param splaySize The splay size
     */
    public SemiSplayTree(int splaySize) {
        this.splaySize = splaySize;
        size = 0;
    }

    /**
     * A help method that will search the {@link SemiSplayTree SemiSplayTree} for a given key.
     * This will return the {@link Node Node} corresponding to the key, or the last {@link Node Node} visited before finding a null child
     * @param key The value that will be searched
     * @return the resulting node
     */
    private Node<E> search(E key) {
        Node<E> node = root;
        Node<E> pNode = null;
        while (node != null && node != pNode) {
            pNode = node;
            node = node.searchChild(key);
        }
        return pNode;
    }

    /**
     * This method will add a given key to the {@link SemiSplayTree SemiSplayTree}.
     * @param key the value to add
     * @return True when key was added, false when key is already in the tree.
     */
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
            splay(node);
            return true;
        }
    }

    /**
     * This method will search a given key in the {@link SemiSplayTree SemiSplayTree}.
     * @param key the value to search.
     * @return true if found, false otherwise.
     */
    @Override
    public boolean contains(E key) {
        Node result = search(key);
        return result != null && result.getKey() == key;
    }

    /**
     * This method will remove a value from the {@link SemiSplayTree SemiSplayTree}.
     * @param key the value to remove
     * @return true if found and removed, false otherwise.
     */
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

    /**
     * A helper function that removes a given {@link Node node} from the {@link SemiSplayTree SemiSplayTree}.
     * @param node the {@link Node node} to remove.
     */
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

    private void splay(Node<E> startnode) {
        Node[] nodes = new Node[splaySize];
        // create the path
        int i = 1;
        nodes[0] = startnode;
        while (i < splaySize && nodes[i-1] != null) {
            nodes[i] = nodes[i-1].getParent();
            i++;
        }
        if (i != splaySize || nodes[i-1] == null) {
            return;
        }
        // save the attach point
        for (Node n : nodes) {
            if (n != null) {
                System.out.println(n.getKey());
            } else {
                System.out.println("null");
            }
        }

        Node attach_point = nodes[i-1].getParent();
        Node[] sortedNodes = new Node[splaySize];
        Node[] outsideTrees = new Node[splaySize + 1];

        // 0: pointers for the nodes, 1: pointers for the outiside trees
        int[] rightPtr = new int[]{splaySize-1, splaySize}; // most right free place
        int[] leftPtr = new int[]{0, 0}; // most left free place
        while (i > 1) {
            i--;
            // next node in path is right child
            if (nodes[i].getRightChild() ==  nodes[i-1]) {
                sortedNodes[leftPtr[0]] = nodes[i]; // place current node at left side of array
                outsideTrees[leftPtr[1]] = nodes[i].getLeftChild();
                leftPtr[0]++;
                leftPtr[1]++;
            } else { // next node in path is left child
                sortedNodes[rightPtr[0]] = nodes[i]; // place current node at right side of array
                outsideTrees[rightPtr[1]] = nodes[i].getRightChild();
                rightPtr[0]--;
                rightPtr[1]--;
            }
        }
        // handle last element
        assert rightPtr[0] == leftPtr[0]; // left and right pointer should be the same a this point
        sortedNodes[rightPtr[0]] = nodes[i]; // place last element in the list
        outsideTrees[leftPtr[1]] = nodes[i].getLeftChild(); // add left child
        outsideTrees[rightPtr[1]] = nodes[i].getRightChild(); // add right child

        for (Node n : sortedNodes) {
            System.out.println(n.getKey());
        }
        splay((Node<E>) attach_point);
    }

    /**
     * A simple getter.
     * @return the size of the {@link SemiSplayTree SemiSplayTree}.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * A simple getter.
     * @return the depth of the {@link SemiSplayTree SemiSplayTree}.
     */
    @Override
    public int depth() {
        if (root != null) {
            return root.depth() - 1;
        }
        return 0;
    }

    /**
     * All elements sorted.
     * @return {@link java.util.Iterator Iterator} containing all elements in the tree.
     */
    @Override
    public Iterator<E> iterator() {
        LinkedList<E> list = new LinkedList<>();
        if (root != null) {
            root.addToIterable(list);
        }
        return list.iterator();
    }
}
