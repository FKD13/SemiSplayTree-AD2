package semisplay;

import org.junit.Assert;
import org.junit.Test;

import java.util.Iterator;
import java.util.Random;

public class SemiSplayTreeTest {

    private Random rg = new Random();

    @Test
    public void testAdd() {
        SearchTree<Integer> tree = new SemiSplayTree<>(8);
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

    @Test
    public void testIterator() {
        SearchTree<Integer> tree = new SemiSplayTree<>(7);
        for (int i = 0; i < 100; i++) {
            Assert.assertTrue(tree.add(i));
        }
        Iterator<Integer> iterator = tree.iterator();
        int n = 0;
        while (iterator.hasNext()) {
            Assert.assertEquals(n, (long) iterator.next());
            n++;
        }
    }

    @Test
    public void testSplaySize() {
        for (int i = 1; i < 100; i++) {
            SearchTree<Integer> tree = new SemiSplayTree<>(i);
            for (int j = 0; j < 10000; j++) {
                Assert.assertTrue(tree.add(j));
            }
        }
        for (int i = 1; i < 100; i++) {
            SearchTree<Integer> tree = new SemiSplayTree<>(i);
            for (int j = 0; j < 10000; j++) {
                Assert.assertTrue(tree.add(rg.nextInt()));
            }
        }
    }

    @Test
    public void testIteratorRandom() {
        for (int i = 0; i < 100; i++) {
            SearchTree<Integer> tree = new SemiSplayTree<>(7);
            for (int j = 0; j < 1000*i; j++) {
                tree.add(rg.nextInt());
            }
            Iterator<Integer> iterator = tree.iterator();
            if (iterator.hasNext()) {
                int previous = iterator.next();
                while (iterator.hasNext()) {
                    int cur = iterator.next();
                    Assert.assertTrue(previous <= cur);
                    previous = cur;
                }
            }
        }
    }

    @Test
    public void testDepth() {
        SearchTree<Integer> tree = new SemiSplayTree<>(100);
        for (int i = 0; i < 99; i++) {
            tree.add(i);
            Assert.assertEquals(i, tree.depth());
        }
        tree.add(100);
        Assert.assertTrue(100 > tree.depth());
    }

    @Test
    public void testElementMovesUp() {
        for (int i = 0; i < 100; i++) {

            for (int j = 0; j < 10000; j++) {

            }
        }
    }
}
