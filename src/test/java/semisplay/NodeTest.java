package semisplay;



import org.junit.Assert;
import org.junit.Test;

public class NodeTest {

    @Test
    public void testConstructor() {
        Assert.assertSame(1, new Node<>(1).getKey());
    }

    @Test
    public void testAddChild() {
        Node<Integer> k = new Node<>(5);
        Node<Integer> i = new Node<>(4);
        Node<Integer> j = new Node<>(6);
        Node<Integer> tmp = k.addChild(1);
        Assert.assertSame(tmp, k.getLeftChild());
        tmp = k.addChild(2);
        Assert.assertSame(tmp, k.getLeftChild());
        Assert.assertSame(null, k.getRightChild());
        tmp = k.addChild(7);
        Assert.assertSame(tmp, k.getRightChild());
        tmp = k.addChild(8);
        Assert.assertSame(tmp, k.getRightChild());
        Assert.assertSame(k, k.addChild(5));
        Assert.assertSame(k, k.addChild(k));
        Assert.assertSame(i, k.addChild(i));
        Assert.assertSame(j, k.addChild(j));

    }

    @Test
    public void testSearchChild() {
        int i = 4;
        int j = 6;
        Node<Integer> k = new Node<>(5);
        k.addChild(i);
        k.addChild(j);
        Assert.assertSame(i, k.searchChild(3).getKey());
        Assert.assertSame(i, k.searchChild(4).getKey());
        Assert.assertSame(k.getKey(), k.searchChild(5).getKey());
        Assert.assertSame(j, k.searchChild(6).getKey());
        Assert.assertSame(j, k.searchChild(7).getKey());
    }

    @Test
    public void testGetBiggestLeftChild() {
        Node<Integer> node1 = new Node<>(1);
        Node<Integer> node2 = new Node<>(0);
        node1.addChild(node2);
        Assert.assertEquals(node2, node1.getBiggestLeftChild());
        Assert.assertEquals(node2, node2.getBiggestLeftChild());
    }
}
