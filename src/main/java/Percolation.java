import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final int n;
    private final WeightedQuickUnionUF weightedQuickUnionUF;
    private final int topIndex;
    private final int bottomIndex;
    private final boolean[][] grid; // n-by-n grid

    // create n-by-n grid, with all sites blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Grid size must be greater than 0");
        }

        this.n = n;
        final int indexes = n * n + 2;
        this.weightedQuickUnionUF = new WeightedQuickUnionUF(indexes);
        this.topIndex = indexes - 2;
        this.bottomIndex = indexes - 1;
        this.grid = new boolean[n][n];
        for (int i = 0; i < n; i++) {
            // top row
            weightedQuickUnionUF.union(topIndex, i);
            // bottom row
            weightedQuickUnionUF.union(bottomIndex, n * (n - 1) + i);
        }
    }

    // open site (row i, column j) if it is not open already
    public void open(int i, int j) {
        validate(i, j);

        if (!isOpen(i, j)) {
            grid[i - 1][j - 1] = true;

            for (final Direction direction : Direction.values()) {
                unionByDirection(i, j, gridToIndex(i, j), direction);
            }
        }
    }

    // is site (row i, column j) open?
    public boolean isOpen(int i, int j) {
        validate(i, j);

        return grid[i - 1][j - 1];
    }

    // is site (row i, column j) full?
    public boolean isFull(int i, int j) {
        validate(i, j);

        return isOpen(i, j) && weightedQuickUnionUF.connected(topIndex, gridToIndex(i, j));
    }

    // does the system percolate?
    public boolean percolates() {
        return weightedQuickUnionUF.connected(topIndex, bottomIndex);
    }

    private void validate(int i, int j) {
        if (i > n || j > n || i < 0 || j < 0) {
            throw new IndexOutOfBoundsException("input not in grid bounds");
        }
    }

    private enum Direction {
        LEFT, ABOVE, RIGHT, BELOW
    }

    private void unionByDirection(int i, int j, int index, Direction direction) {
        switch (direction) {
            case LEFT:
                i--;
                break;
            case ABOVE:
                j--;
                break;
            case RIGHT:
                i++;
                break;
            case BELOW:
                j++;
                break;
        }

        if (i > 0 && j > 0 && i <= n && j <= n && isOpen(i, j)) {
            weightedQuickUnionUF.union(gridToIndex(i, j), index);
        }
    }

    private int gridToIndex(int i, int j) {
        return (i - 1) * n + j - 1;
    }
}
