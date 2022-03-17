import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
public class PercolationStats {
    private static final double CONFIDENCE_95 = 1.96;
    private final double[] threshold;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0) throw new IllegalArgumentException("number of sites is illegal");
        if (trials <= 0) throw new IllegalArgumentException("number of sites is illegal");
        threshold = new double[trials];

        for (int i = 0; i < threshold.length; i++) {
            Percolation percs = new Percolation(n);
            while (!percs.percolates()) {
                int row = StdRandom.uniform(1, n + 1);
                int col = StdRandom.uniform(1, n + 1);
                percs.open(row, col);
            }
            threshold[i] = ((double) percs.numberOfOpenSites()) / ((double) n * n);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        double mean = StdStats.mean(threshold);
        return mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        double stddev = StdStats.stddev(threshold);
        return stddev;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        double low = mean() - CONFIDENCE_95 * Math.sqrt(stddev()) / Math.sqrt(threshold.length);
        return low;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        double high = mean() + CONFIDENCE_95 * Math.sqrt(stddev()) / Math.sqrt(threshold.length);
        return high;
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);
        PercolationStats stats = new PercolationStats(n, t);
        System.out.println("mean                    = " + stats.mean());
        System.out.println("stddev                  = " + stats.stddev());
        System.out.println("95% confidence interval = [" + stats.confidenceLo() + ", " + stats.confidenceHi() + "]");
//        StdOut.println("mean                    = " + stats.mean());
//        StdOut.println("stddev                  = " + stats.stddev());
//        StdOut.println("95% confidence interval = [" + stats.confidenceLo() + ", " + stats.confidenceHi() + "]");

    }

}
