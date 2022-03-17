import edu.princeton.cs.algs4.WeightedQuickUnionUF;

// Comments are good
public class Percolation {
    private static final byte OPEN = (byte) 1;
    private static final byte CONNECTED_TO_BOTTOM = (byte) 2;
    private static final byte CONNECTED_TO_TOP = (byte) 4;

    private final int numberOfSites;
    //    private boolean[] openSites;
    private int nOpenedSites;
    private final WeightedQuickUnionUF grid;
    private byte[] siteStatus;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException("number of sites is illegal");
        numberOfSites = n + 2; // plus two virtual
        grid = new WeightedQuickUnionUF(n * n + 2);
        siteStatus = new byte[n * n + 2];
        siteStatus[0] = (OPEN | CONNECTED_TO_TOP);
        siteStatus[n * n + 1] = (OPEN | CONNECTED_TO_BOTTOM);
        for (int i = 1; i <= n; i++) {
            grid.union(0, i); // plus two virtual
//            grid.union(n * n + 1, xyTo1D(n, i)); // plus two virtual
            siteStatus[i] = (CONNECTED_TO_TOP);
            siteStatus[xyTo1D(n, i)] = (CONNECTED_TO_BOTTOM);
        }
//        openSites = new boolean[n * n + 2];
//        openSites[0] = true;
//        openSites[n * n + 1] = true;


    }

    private int xyTo1D(int row, int col) {
        if (row == numberOfSites - 1) return (numberOfSites - 2) * (numberOfSites - 2) + 1;
        else if (row == 0) return 0;
        else return col + (row - 1) * (numberOfSites - 2);
    }


    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row < 1 || row > numberOfSites - 2) throw new IllegalArgumentException("number of rows is illegal");
        if (col < 1 || col > numberOfSites - 2) throw new IllegalArgumentException("number of cols is illegal");

        int idOfSite0 = xyTo1D(row, col);
        int top = xyTo1D(row - 1, col);
        int bottom = xyTo1D(row + 1, col);
        int left = xyTo1D(row, col - 1);
        int right = xyTo1D(row, col + 1);
        if (!isOpen(row, col)) {

//            openSites[idOfSite0] = true;
//            if (row > 1 && isOpen(row - 1, col)) {
//                grid.union(idOfSite0, top);
//            }
//            if (row < numberOfSites - 2 && isOpen(row + 1, col)) {
//                grid.union(idOfSite0, bottom);
//            } else if (row == numberOfSites - 2) grid.union(idOfSite0, bottom);
//            if (col > 1 && isOpen(row, col - 1)) {
//                grid.union(idOfSite0, left);
//            }
//            if (col < numberOfSites - 2 && isOpen(row, col + 1)) {
//                grid.union(idOfSite0, right);
//            }

            nOpenedSites++;

            siteStatus[idOfSite0] = (byte) (OPEN | siteStatus[grid.find(idOfSite0)]);
            byte topStatus = 0;
            byte botStatus = 0;
            byte lefStatus = 0;
            byte rigStatus = 0;

            if (row > 1 && isOpen(row - 1, col)) {
                grid.union(idOfSite0, top);
                topStatus = (byte) (siteStatus[idOfSite0] | siteStatus[top] | siteStatus[grid.find(top)]);
            }
            if ((row < numberOfSites - 2 && isOpen(row + 1, col)) || row == numberOfSites - 2) {
                grid.union(idOfSite0, bottom);
                botStatus = (byte) (siteStatus[idOfSite0] | siteStatus[bottom] | siteStatus[grid.find(bottom)]);
            }
//            if (row == numberOfSites - 2) {
//                grid.union(idOfSite0, bottom);
//                botStatus = (byte) (siteStatus[idOfSite0] | siteStatus[bottom] | siteStatus[grid.find(bottom)]);
//            }
            if (col > 1 && isOpen(row, col - 1)) {
                grid.union(idOfSite0, left);
                lefStatus = (byte) (siteStatus[idOfSite0] | siteStatus[left] | siteStatus[grid.find(left)]);
            }
            if (col < numberOfSites - 2 && isOpen(row, col + 1)) {
                grid.union(idOfSite0, right);
                rigStatus = (byte) (siteStatus[idOfSite0] | siteStatus[right] | siteStatus[grid.find(right)]);
            }
            siteStatus[idOfSite0] = (byte) (siteStatus[idOfSite0] | topStatus | botStatus | lefStatus | rigStatus);

            if (row > 1 && isOpen(row - 1, col)) {
                siteStatus[top] = (byte) (siteStatus[idOfSite0] | siteStatus[top] | siteStatus[grid.find(top)]);
            }
            if ((row < numberOfSites - 2 && isOpen(row + 1, col)) || row == numberOfSites - 2) {
                siteStatus[bottom] = (byte) (siteStatus[idOfSite0] | siteStatus[bottom] | siteStatus[grid.find(bottom)]);
            }
            if (col > 1 && isOpen(row, col - 1)) {
                siteStatus[left] = (byte) (siteStatus[idOfSite0] | siteStatus[left] | siteStatus[grid.find(left)]);
            }
            if (col < numberOfSites - 2 && isOpen(row, col + 1)) {
                siteStatus[right] = (byte) (siteStatus[idOfSite0] | siteStatus[right] | siteStatus[grid.find(right)]);
            }

            siteStatus[grid.find(idOfSite0)] = (byte) (siteStatus[idOfSite0] | siteStatus[grid.find(idOfSite0)]);
            siteStatus[idOfSite0] = (byte) ((siteStatus[idOfSite0]) | siteStatus[grid.find(idOfSite0)]);


        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row < 1 || row > numberOfSites - 2) throw new IllegalArgumentException("number of rows is illegal");
        if (col < 1 || col > numberOfSites - 2) throw new IllegalArgumentException("number of cols is illegal");
//        return openSites[xyTo1D(row, col)];
//        if (row==2 && col == 5 ) System.out.print(siteStatus[xyTo1D(2, 5)] + "||" + (siteStatus[xyTo1D(2, 5)] == (OPEN)));
        return siteStatus[xyTo1D(row, col)] == (OPEN)
                || siteStatus[xyTo1D(row, col)] == (OPEN | CONNECTED_TO_TOP)
                || siteStatus[xyTo1D(row, col)] == (OPEN | CONNECTED_TO_BOTTOM)
                || siteStatus[xyTo1D(row, col)] == (OPEN | CONNECTED_TO_BOTTOM | CONNECTED_TO_TOP);
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row < 1 || row > numberOfSites - 2) throw new IllegalArgumentException("number of rows is illegal");
        if (col < 1 || col > numberOfSites - 2) throw new IllegalArgumentException("number of cols is illegal");
        boolean touchesTop = grid.find(xyTo1D(row, col)) == grid.find(xyTo1D(1, col));
//        return isOpen(row, col) && touchesTop;
        return siteStatus[xyTo1D(row, col)] == (CONNECTED_TO_TOP | OPEN) || (isOpen(row, col) && touchesTop);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return nOpenedSites;
    }

    // does the system percolate?
    public boolean percolates() {
//        System.out.print(grid.find(xyTo1D(1, numberOfSites - 2)));
        return grid.find(xyTo1D(numberOfSites - 1, numberOfSites - 2)) == grid.find(xyTo1D(1, numberOfSites - 2));
    }

    // test client (optional)
    public static void main(String[] args) {
//        StdOut.println("good");
    }
}
