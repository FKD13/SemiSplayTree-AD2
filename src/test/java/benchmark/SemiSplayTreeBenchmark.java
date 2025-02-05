package benchmark;

import org.junit.Assert;
import org.junit.Test;
import semisplay.SemiSplayTree;

import java.util.Random;

public class SemiSplayTreeBenchmark {

    private Random rg = new Random();
    private final int runs = 100;

    @Test
    public void addBenchmarkWorstCaseOrdinairyTree() {
        System.out.println("Warming up the tree");
        for (int i = 0; i < 10000000; i++) {
            SemiSplayTree<Integer> tree = new SemiSplayTree<>(5);
            tree.add(1);
        }
        System.out.println("Starting Worst Case Benchmark (For ordinary trees) (Add)");
        System.out.println("Splay size: 63");
        System.out.println("-----------------------------------------------------------");
        int factor = 1000;
        for (int i = 0; i < 100; i+=10) {
            long[] runTimes = new long[runs];
            for (int r = 0; r < runs; r++) {
                long start = System.currentTimeMillis();
                SemiSplayTree<Integer> tree = new SemiSplayTree<>(63);
                for (int j = 0; j < factor*i; j++) {
                    tree.add(j);
                }
                long end = System.currentTimeMillis();
                runTimes[r] = end-start;
            }
            System.out.println(factor*i + ": Completed in: " + average(runTimes) + " ms");
        }
    }

    @Test
    public void addBenchmarkRandom() {
        System.out.println("Warming up the tree");
        for (int i = 0; i < 10000000; i++) {
            SemiSplayTree<Integer> tree = new SemiSplayTree<>(5);
            tree.add(1);
        }
        System.out.println("Starting Random Benchmark (Add)");
        System.out.println("Printing averages from " + runs + " runs.");
        System.out.println("Splay size: 63");
        System.out.println("-----------------------------------------------------------");
        int factor = 1000;
        for (int i = 0; i < 200; i+=10) {
            long[] runTimes = new long[runs];
            for (int r = 0; r < runs; r++) {
                int[] randoms = new int[factor*i];
                for (int j = 0; j < factor*i; j++) {
                    randoms[j] = rg.nextInt();
                }
                long start = System.currentTimeMillis();
                SemiSplayTree<Integer> tree = new SemiSplayTree<>(63);
                for (int j = 0; j < factor*i; j++) {
                    tree.add(randoms[j]);
                }
                long end = System.currentTimeMillis();
                runTimes[r] = end-start;
            }
            System.out.println(factor*i + ": Completed in: " + average(runTimes) + " ms");
        }
    }

    @Test
    public void AllBenchmarkRandom() {
        System.out.println("Warming up the tree");
        for (int i = 0; i < 10000000; i++) {
            SemiSplayTree<Integer> tree = new SemiSplayTree<>(5);
            tree.add(1);
        }
        System.out.println("Starting Random Benchmark (All)");
        System.out.println("Printing averages from " + runs + " runs.");
        System.out.println("Splay size: 63");
        System.out.println("-----------------------------------------------------------");
        int factor = 1000;
        for (int i = 0; i < 100; i+=10) {
            long[] runTimes = new long[runs];
            for (int r = 0; r < runs; r++) {
                int[] randoms = new int[factor*i];
                int[][] randomIndexes = new int[2][factor*i];
                for (int j = 1; j < factor*i; j++) {
                    randoms[j] = rg.nextInt();
                    randomIndexes[0][j] = rg.nextInt(j)+1;
                    randomIndexes[1][j] = rg.nextInt(j)+1;
                }
                long start = System.currentTimeMillis();
                SemiSplayTree<Integer> tree = new SemiSplayTree<>(63);
                for (int j = 1; j < factor*i; j++) {
                    boolean u = false;
                    Assert.assertNotEquals(tree.contains(randoms[j]), tree.add(randoms[j]));
                    Assert.assertTrue(tree.contains(randoms[randomIndexes[0][j]]));
                    Assert.assertFalse(tree.add(randoms[randomIndexes[0][j]]));
                }
                for (int j = 1; j < factor*i; j++) {
                    Assert.assertEquals(tree.contains(randoms[j]), tree.remove(randoms[j]));
                }
                long end = System.currentTimeMillis();
                runTimes[r] = end-start;
            }
            System.out.println(factor*i + ": Completed in: " + average(runTimes) + " ms");
        }
    }

    private long average(long[] numbers) {
        long total = 0;
        for (long l : numbers) {
            total += l;
        }
        return total/numbers.length;
    }
}
