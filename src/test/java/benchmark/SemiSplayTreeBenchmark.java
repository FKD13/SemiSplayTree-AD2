package benchmark;

import org.junit.Test;
import semisplay.SemiSplayTree;

import java.util.Random;

public class SemiSplayTreeBenchmark {

    private Random rg = new Random();
    private final int runs = 100;

    @Test
    public void addBenchmarkWorstCase() {
        System.out.println("Warming up the tree");
        for (int i = 0; i < 100000; i++) {
            SemiSplayTree<Integer> tree = new SemiSplayTree<>(5);
            tree.add(1);
        }
        System.out.println("Starting Worst Case Benchmark (Add)");
        System.out.println("-----------------------------------------------------------");
        int factor = 1000;
        for (int i = 0; i < 100; i+=10) {
            long start = System.currentTimeMillis();
            SemiSplayTree<Integer> tree = new SemiSplayTree<>(7);
            for (int j = 0; j < factor*i; j++) {
                tree.add(j);
            }
            long end = System.currentTimeMillis();
            System.out.println(factor*i + ": Completed in: " + (end - start) + " ms");
        }
    }

    @Test
    public void addBenchmarkRandom() {
        System.out.println("Warming up the tree");
        for (int i = 0; i < 100000; i++) {
            SemiSplayTree<Integer> tree = new SemiSplayTree<>(5);
            tree.add(1);
        }
        System.out.println("Starting Random Benchmark (Add)");
        System.out.println("Printing averages from " + runs + " runs.");
        System.out.println("-----------------------------------------------------------");
        int factor = 1000;
        for (int i = 0; i < 100; i+=10) {
            long[] runTimes = new long[runs];
            for (int r = 0; r < runs; r++) {
                int[] randoms = new int[factor*i];
                for (int j = 0; j < factor*i; j++) {
                    randoms[j] = rg.nextInt();
                }
                long start = System.currentTimeMillis();
                SemiSplayTree<Integer> tree = new SemiSplayTree<>(7);
                for (int j = 0; j < factor*i; j++) {
                    tree.add(randoms[j]);
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
