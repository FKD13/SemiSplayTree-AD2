package semisplay;

import org.junit.Assert;
import org.junit.Test;

public class SemiSplayTreeTest {

    @Test
    public void testAdd() {
        SearchTree<Integer> tree = new SemiSplayTree<>(7);
        for (int i = 0; i < 100; i++) {
            Assert.assertSame(true, tree.add(i));
            Assert.assertSame(false, tree.add(i));
        }
    }

    @Test
    public void testBasicContains() {
        SearchTree<Integer> tree = new SemiSplayTree<>(7);
        for (int i = 0; i < 100; i++) {
            Assert.assertSame(true, tree.add(i));
        }
        for (int i = 0; i < 100; i++) {
            Assert.assertTrue(tree.contains(i));
        }
        for (int i = 100; i < 200; i++) {
            Assert.assertFalse(tree.contains(i));
        }
        for (int i = -100; i < 0; i++) {
            Assert.assertFalse(tree.contains(i));
        }
    }

    @Test
    public void testBasicSearch() {
        SemiSplayTree<Integer> tree = new SemiSplayTree<>(7);
        for (int i = 0; i < 100; i++) {
            Assert.assertSame(true, tree.add(i));
        }
        for (int i = 0; i < 100; i++) {
            Assert.assertSame(i, tree.search(i).getKey());
        }
    }


    @Test
    public void testRemove() {
        SearchTree<Integer> tree = new SemiSplayTree<>(7);
        for (int i = 0; i < 1000; i++) {
            Assert.assertSame(true, tree.add(i));
        }
        for (int i = 0; i < 100; i++) {
            Assert.assertSame(true, tree.remove(i*10));
            Assert.assertSame(false, tree.remove(i*10));
            Assert.assertSame(true, tree.add(i*10));
        }
    }

    @Test
    public void testSearch() {
        SearchTree<Integer> tree = new SemiSplayTree<>(7);
        for (int i = 0; i < 100; i++) {
            Assert.assertSame(true, tree.add(i));
        }
        for (int i = 0; i < 100; i++) {
            Assert.assertSame(true, tree.contains(i));
        }
        for (int i = 0; i < 100; i++) {
            Assert.assertSame(true, tree.remove(i));
        }
        for (int i = 0; i < 100; i++) {
            Assert.assertSame(false, tree.contains(i));
        }
    }

    @Test
    public void testSize() {
        SearchTree<Integer> tree = new SemiSplayTree<>(7);
        for (int i = 0; i < 100; i++) {
            Assert.assertEquals(i, tree.size());
            Assert.assertTrue(tree.add(i));
        }
        for (int i = 0; i < 100; i++) {
            Assert.assertEquals(100-i, tree.size());
            Assert.assertTrue(tree.remove(i));
        }
        Assert.assertEquals(0, tree.size());
    }
}
