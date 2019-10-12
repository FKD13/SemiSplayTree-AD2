package semisplay;



import org.junit.Assert;
import org.junit.Test;

public class NodeTest {

    @Test
    public void testConstructor() {
        int i = 1;
        Assert.assertSame(1, new Node<>(1).getKey());
    }

    @Test
    public void testSearchChild() {
        Node<Integer> i = new Node<>(4);
        Node<Integer> j = new Node<>(6);
        Node<Integer> k = new Node<>(5);
        k.setLeftChild(i);
        Assert.assertSame(i, k.getLeftChild());
        k.setRightChild(j);
        Assert.assertSame(j, k.getRightChild());
        Assert.assertSame(i, k.searchChild(3));
        Assert.assertSame(i, k.searchChild(4));
        Assert.assertSame(k, k.searchChild(5));
        Assert.assertSame(j, k.searchChild(6));
        Assert.assertSame(j, k.searchChild(7));
    }
}
