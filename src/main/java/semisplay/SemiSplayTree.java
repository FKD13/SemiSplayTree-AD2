package semisplay;

import java.util.Iterator;
import java.util.LinkedList;

public class SemiSplayTree<E extends Comparable<E>> implements SearchTree<E> {

    private int splaySize;
    private Node<E> root;
    private int size;
    private State state = null;

    /**
     * Constructor for the {@link SemiSplayTree<E> SemiSplayTree}.
     * @param splaySize The splay size
     */
    public SemiSplayTree(int splaySize) {
        this.splaySize = splaySize;
        size = 0;
    }

    /**
     * A help method that will search the {@link SemiSplayTree<E> SemiSplayTree} for a given key.
     * This will return the {@link Node<E> Node} corresponding to the key, or the last {@link Node<E> Node} visited before finding a null child
     * @param key The value that will be searched
     * @return the resulting node
     */
    private Node<E> search(E key) {
        state = null;
        Node<E> node = root;
        Node<E> pNode = null;
        while (node != null && node != pNode) {
            pNode = node;
            node = node.searchChild(key);
        }
        return pNode;
    }

    /**
     * This method will add a given key to the {@link SemiSplayTree<E> SemiSplayTree}.
     * @param key the value to add
     * @return True when key was added, false when key is already in the tree.
     */
    @Override
    public boolean add(E key) {
        state = null;
        Node<E> node = search(key);
        //System.out.println(node);
        if (node == null) {
            root = new Node<>(key);
            size++;
            return true;
        } else if (node.getKey().equals(key)) {
            splay(node);
            return false;
        } else {
            node.addChild(key);
            size++;
            splay(node.searchChild(key));
            return true;
        }
    }

    /**
     * This method will search a given key in the {@link SemiSplayTree<E> SemiSplayTree}.
     * @param key the value to search.
     * @return true if found, false otherwise.
     */
    @Override
    public boolean contains(E key) {
        Node<E> result = search(key);
        splay(result);
        return result != null && result.getKey().equals(key);
    }

    /**
     * This method will remove a value from the {@link SemiSplayTree<E> SemiSplayTree}.
     * @param key the value to remove
     * @return true if found and removed, false otherwise.
     */
    @Override
    public boolean remove(E key) {
        state = null;
        Node<E> node = search(key);
        if (node.getKey().equals(key)) {
            remove(node);
            size--;
            return true;
        } else {
            splay(node);
            return false;
        }
    }

    /**
     * A helper function that removes a given {@link Node<E> node} from the {@link SemiSplayTree<E> SemiSplayTree}.
     * @param node the {@link Node node} to remove.
     */
    private void remove(Node<E> node) {
        // get replacement node
        Node<E> replacement = node.getBiggestLeftChild();
        // if replacement == node
        if (replacement.getKey().equals(node.getKey())) {
            if (node.getParent() != null) {
                node.getParent().replace(node, node.getRightChild());
                if (node.getRightChild() != null) {
                    node.getRightChild().setParent(node.getParent());
                }
                splay(node.getParent());
            } else {
                root = node.getRightChild();
                if (root != null) {
                    replacement.getRightChild().setParent(null);
                }
            }
        } else {
            Node<E> splayStart = removeBiggestLeftChild(replacement);
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
            splay(splayStart);
        }
        node.reset();
    }

    private Node<E> removeBiggestLeftChild(Node<E> node) {
        node.getParent().replace(node, node.getLeftChild());
        return node.getParent();
    }

    /**
     * A field to keep track of which outsideTree to add next in the buildRecursive step.
     */
    private int outsideTreePos = 0;

    /**
     * This method will splay the {@link SemiSplayTree<E> SemiSplayTree} if necessary.
     * @param startnode the lowest {@link Node<E> Node} in the path, will work it's way up from there.
     */
    private void splay(Node<E> startnode) {
        state = null;
        while (startnode != null) {
            Node<E>[] nodes = (Node<E>[]) new Node[splaySize];
            // create the path
            int i = 1;
            nodes[0] = startnode;
            while (i < splaySize && nodes[i - 1] != null) {
                nodes[i] = nodes[i - 1].getParent();
                i++;
            }
            if (i != splaySize || nodes[i - 1] == null) {
                // stop splaying
                startnode = null;
            } else {

                Node<E> attach_point = nodes[i - 1].getParent();
                Node<E>[] sortedNodes = (Node<E>[]) new Node[splaySize];
                Node<E>[] outsideTrees = (Node<E>[]) new Node[splaySize + 1];
                outsideTreePos = 0;

                int leftPtr = 0; // most left free space
                int rightPtr = splaySize-1; // most right free space
                while (i > 1) {
                    i--;
                    // next node in path is right child
                    if (nodes[i].getRightChild() == nodes[i - 1]) {
                        sortedNodes[leftPtr] = nodes[i]; // place current node at left side of array
                        outsideTrees[leftPtr] = nodes[i].getLeftChild();
                        leftPtr++;
                    } else { // next node in path is left child
                        sortedNodes[rightPtr] = nodes[i]; // place current node at right side of array
                        outsideTrees[rightPtr+1] = nodes[i].getRightChild();
                        rightPtr--;
                    }
                }
                // handle last element
                i--;
                assert rightPtr == leftPtr; // left and right pointer should be the same a this point
                sortedNodes[rightPtr] = nodes[i]; // place last element in the list
                outsideTrees[leftPtr] = nodes[i].getLeftChild(); // add left child
                outsideTrees[rightPtr+1] = nodes[i].getRightChild(); // add right child

                for (Node n : sortedNodes) {
                    n.reset();
                }
                Node<E> topNode = buildRecursive(sortedNodes, outsideTrees, 0, splaySize);
                if (attach_point == null) {
                    root = topNode;
                } else if (topNode != null) {
                    attach_point.addChild(topNode);
                }
                startnode = attach_point;
            }
        }
    }

    /**
     * This recusive method will build a tree in O(n) given a sorted Array of {@link Node<E> Nodes}.
     * @param sortedNodes an Array containing the {@link Node<E> Nodes} sorted using KeyValue, lowest in front.
     * @param outsideTrees an Array containing the outsideTree {@link Node<E> Nodes}.
     * @param start the Start index.
     * @param end the end index, exclusive.
     * @return the top {@link Node<E> Node} of the new tree.
     */
    private Node<E> buildRecursive(Node<E>[] sortedNodes, Node<E>[] outsideTrees, int start, int end) {
        if (start == end) {
            return null;
        }
        if (start+1 == end) {
            sortedNodes[start].reset();
            if (outsideTrees[outsideTreePos] != null) {
                sortedNodes[start].setLeftChild(outsideTrees[outsideTreePos]);
            }
            if (outsideTrees[outsideTreePos+1] != null) {
                sortedNodes[start].setRightChild(outsideTrees[outsideTreePos + 1]);
            }
            outsideTreePos += 2;
            return sortedNodes[start];
        } else {
            Node <E> tempRoot = sortedNodes[(int) Math.floor((end-start)/2.0)+start];

            tempRoot.reset();
            Node<E> left = buildRecursive(sortedNodes, outsideTrees, start, (int) Math.floor((end-start)/2.0)+start);
            Node<E> right = buildRecursive(sortedNodes, outsideTrees, (int) Math.floor((end-start)/2.0)+start + 1, end);
            if (left != null) {
                tempRoot.setLeftChild(left);
                left.setParent(tempRoot);
            } else {
                if (outsideTrees[outsideTreePos] != null) {
                    tempRoot.setLeftChild(outsideTrees[outsideTreePos]);
                }
                outsideTreePos++;
            }
            if (right != null) {
                tempRoot.setRightChild(right);
                right.setParent(tempRoot);
            } else {
                if (outsideTrees[outsideTreePos] != null) {
                    tempRoot.setRightChild(outsideTrees[outsideTreePos]);
                }
                outsideTreePos++;
            }
            return tempRoot;
        }
    }

    /**
     * A simple getter.
     * @return the size of the {@link SemiSplayTree<E> SemiSplayTree}.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * A simple getter.
     * @return the depth of the {@link SemiSplayTree<E> SemiSplayTree}.
     */
    @Override
    public int depth() {
        if (state == null) {
            if (root != null) {
                int depth = root.depth() - 1;
                state = new State(depth);
                return depth;
            }
            return 0;
        } else {
            return state.getDepth();
        }
    }

    /**
     * All elements sorted.
     * @return {@link java.util.Iterator<E> Iterator} containing all elements in the tree.
     */
    @Override
    public Iterator<E> iterator() {
        LinkedList<E> list = new LinkedList<>();
        if (root != null) {
            root.addToIterable(list);
        }
        return list.iterator();
    }

    @Override
    public String toString() {
        return root != null ? root.toString() : null;
    }
}
