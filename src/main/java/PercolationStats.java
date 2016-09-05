import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private int trials;
    private double[] thresholdByTrail;

    // perform trials independent experiments on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("n and trials must be greater than 0");
        }

        this.trials = trials;
        this.thresholdByTrail = new double[trials];
        for (int k = 0; k < trials; k++) {
            final Percolation percolation = new Percolation(n);
            do {
                final int i = StdRandom.uniform(n) + 1;
                final int j = StdRandom.uniform(n) + 1;
                percolation.open(i, j);
            } while (!percolation.percolates());

            // optimize this?
            int open = 0;
            for (int i = 1; i <= n; i++) {
                for (int j = 1; j <= n; j++) {
                    if (percolation.isOpen(i, j)) {
                        open++;
                    }
                }
            }

            thresholdByTrail[k] = (double) open / (n * n);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
         return StdStats.mean(thresholdByTrail);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(thresholdByTrail);
    }

    // low  endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - 1.96 * stddev() / Math.sqrt(this.trials);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + 1.96 * stddev() / Math.sqrt(this.trials);
    }

    public static void main(String[] args) {
        // sbt "run 200 100"
        final PercolationStats percolationStats = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        StdOut.println("mean                    = " + percolationStats.mean());
        StdOut.println("stddev                  = " + percolationStats.stddev());
        StdOut.println("95% confidence interval = " + percolationStats.confidenceLo() + ", " + percolationStats.confidenceHi());
    }
}
