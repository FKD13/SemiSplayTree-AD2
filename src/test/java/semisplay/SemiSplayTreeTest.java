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
    public void testRemove() {
        SearchTree<Integer> tree = new SemiSplayTree<>(7);
        for (int i = 0; i < 100; i++) {
            Assert.assertSame(true, tree.add(i));
        }
        for (int i = 0; i < 100; i++) {
            Assert.assertSame(true, tree.remove(i));
            Assert.assertSame(false, tree.remove(i));
            Assert.assertSame(true, tree.add(i));
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
}
