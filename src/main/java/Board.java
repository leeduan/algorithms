import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Arrays;

public class Board {
    private final int dimension;
    private final char[] tiles;

    // keep track of location of blank on init
    private int blankI;

    // construct a board from an n-by-n array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {
        if (blocks == null) {
            throw new NullPointerException("Blocks cannot be null");
        }
        if (blocks.length == 0 || blocks.length != blocks[0].length) {
            throw new IllegalArgumentException("Blocks dimension must have equal rows and columns");
        }

        // default values for un-cached
        this.dimension = blocks.length;
        this.tiles = new char[dimension * dimension];
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                final int val = blocks[i][j];
                this.tiles[i * dimension + j] = (char) val;
                if (val == 0) {
                    this.blankI = i * dimension + j;
                }
            }
        }
    }

    // board dimension n
    public int dimension() {
        return dimension;
    }

    // number of blocks out of place
    public int hamming() {
        int hamming = 0;
        for (int i = 0; i < this.tiles.length; i++) {
            final int val = (int) this.tiles[i];
            if (val == 0) {
                continue;
            }
            if (val != (i + 1)) {
                hamming++;
            }
        }

        return hamming;
    }

    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        int manhattan = 0;
        for (int i = 0; i < this.tiles.length; i++) {
            final int tile = (int) this.tiles[i];
            if (tile == 0) {
                continue;
            }
            // row and column of tile at desired index
            final int rowT = (tile - 1) / this.dimension;
            final int columnT = (tile - 1) % this.dimension;
            // row and column currently
            final int row = i / this.dimension;
            final int column = i % this.dimension;

            manhattan += (Math.abs(row - rowT) + Math.abs(column - columnT));
        }

        return manhattan;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }

    // a board that is obtained by exchanging any pair of blocks
    public Board twin() {
        int i1 = -1;
        int i2 = -1;
        // randomly pick indexes that are not 0
        while (i1 < 0) {
            final int index = StdRandom.uniform(this.tiles.length);
            if ((int) this.tiles[index] != 0) {
                i1 = index;
            }
        }
        while (i2 < 0 || i1 == i2) {
            final int index = StdRandom.uniform(this.tiles.length);
            if ((int) this.tiles[index] != 0) {
                i2 = index;
            }
        }

        return buildBoardSwapIndexes(i1, i2);
    }

    // does this board equal y?
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Board board = (Board) o;

        if (dimension != board.dimension) return false;
        return Arrays.equals(tiles, board.tiles);
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        // row and column of blank
        final int row = blankI / this.dimension;
        final int column = blankI % this.dimension;
        final Stack<Board> boards = new Stack<>();

        // left
        if (column - 1 >= 0) {
            boards.push(buildBoardSwapIndexes(blankI, row * dimension + column - 1));
        }

        // right
        if (column + 1 < this.dimension) {
            boards.push(buildBoardSwapIndexes(blankI, row * dimension + column + 1));
        }

        // above
        if (row - 1 >= 0) {
            boards.push(buildBoardSwapIndexes(blankI, (row - 1) * dimension + column));
        }

        // below
        if (row + 1 < this.dimension) {
            boards.push(buildBoardSwapIndexes(blankI, (row + 1) * dimension + column));
        }

        return boards;
    }

    private Board buildBoardSwapIndexes(int i1, int i2) {
        final char[] tilesCopy = Arrays.copyOf(this.tiles, this.tiles.length);

        // swap the chars
        final char i1Char = tilesCopy[i1];
        tilesCopy[i1] = tilesCopy[i2];
        tilesCopy[i2] = i1Char;

        // build the blocks
        final int[][] blocks = new int[this.dimension][this.dimension];
        for (int i = 0; i < tilesCopy.length; i++) {
            blocks[i / this.dimension][i % this.dimension] = tilesCopy[i];
        }

        return new Board(blocks);
    }

    // string representation of this board (in the output format specified below)
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(this.dimension + "\n");
        for (int i = 0; i < this.dimension * this.dimension; i++) {
            s.append(String.format("%2d ", (int) this.tiles[i]));
            if ((i + 1) % this.dimension == 0) {
                s.append("\n");
            }
        }
        return s.toString();
    }

    // unit tests (not graded)
    public static void main(String[] args) { }
}