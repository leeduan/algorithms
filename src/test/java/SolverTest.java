import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class SolverTest {

    public static int[][] generateBlock(int[] arr) {
        int n = (int)Math.sqrt(arr.length);

        int[][] blocks = new int[n][n];
        for (int i = 0; i < arr.length; i++) {
            blocks[i / n][i % n] = arr[i];
        }
        return blocks;
    }

    @Test
    public void testPuzzle01() {
        int[][] block = generateBlock(new int[]{1, 0, 3, 2});
        Solver solver = new Solver(new Board(block));
        assertTrue(solver.moves() == 1);
        assertTrue(solver.isSolvable());
    }

    @Test
    public void testPuzzle02() {
        int[][] block = generateBlock(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20,
                21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46,
                47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 0, 71,
                73, 74, 75, 76, 77, 78, 79, 80, 72});
        Solver solver = new Solver(new Board(block));
        assertTrue(solver.moves() == 2);
        assertTrue(solver.isSolvable());
    }

    @Test
    public void testPuzzle03() {
        int[][] block = generateBlock(new int[]{2, 0, 1, 3});
        Solver solver = new Solver(new Board(block));
        assertTrue(solver.moves() == 3);
        assertTrue(solver.isSolvable());
    }

    @Test
    public void testPuzzle04() {
        int[][] block = generateBlock(new int[]{0, 1, 3, 4, 2, 5, 7, 8, 6});
        Solver solver = new Solver(new Board(block));
        assertTrue(solver.moves() == 4);
        assertTrue(solver.isSolvable());
    }

    @Test
    public void testPuzzle05() {
        int[][] block = generateBlock(new int[]{4, 1, 3, 0, 2, 6, 7, 5, 8});
        Solver solver = new Solver(new Board(block));
        assertTrue(solver.moves() == 5);
        assertTrue(solver.isSolvable());
    }

    @Test
    public void testPuzzle06() {
        int[][] block = generateBlock(new int[]{0, 1, 2, 3, 5, 6, 7, 4, 9, 10, 11, 8, 13, 14, 15, 12});
        Solver solver = new Solver(new Board(block));
        assertTrue(solver.moves() == 6);
        assertTrue(solver.isSolvable());
    }

    @Test
    public void testPuzzle09() {
        int[][] block = generateBlock(new int[]{2, 0, 3, 4, 1, 10, 6, 8, 5, 9, 7, 12, 13, 14, 11, 15});
        Solver solver = new Solver(new Board(block));
        assertTrue(solver.moves() == 9);
        assertTrue(solver.isSolvable());
    }

    // depending on the twin selected,
    @Test
    public void testPuzzle3x3Unsolvable() {
        int[][] block = generateBlock(new int[]{1, 2, 3, 4, 6, 5, 7, 8, 0});
        Solver solver = new Solver(new Board(block));
        assertFalse(solver.isSolvable());
    }

    @Test
    public void testPuzzle3x3Unsolvable1() {
        int[][] block = generateBlock(new int[]{1, 2, 3, 4, 6, 5, 7, 8, 0});
        Solver solver = new Solver(new Board(block));
        assertFalse(solver.isSolvable());
    }
}
