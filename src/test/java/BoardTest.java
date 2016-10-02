import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import edu.princeton.cs.algs4.Stack;
import org.junit.Test;

public class BoardTest {
    private final static int[] PUZZLE_04 = new int[]{0, 1, 3, 4, 2, 5, 7, 8, 6};
    private final static int[] HAMMING_MANHATTAN = new int[]{8, 1, 3, 4, 0, 2, 7, 6, 5};

    public static int[][] generateBlock(int[] arr) {
        int n = (int)Math.sqrt(arr.length);

        int[][] blocks = new int[n][n];
        for (int i = 0; i < arr.length; i++) {
            blocks[i / n][i % n] = arr[i];
        }
        return blocks;
    }

    @Test
    public void testDimension() {
        assertTrue(new Board(generateBlock(PUZZLE_04)).dimension() == 3);
    }

    @Test
    public void testHamming() {
        assertTrue(new Board(generateBlock(HAMMING_MANHATTAN)).hamming() == 5);
    }

    @Test
    public void testManhattan() {
        assertTrue(new Board(generateBlock(HAMMING_MANHATTAN)).manhattan() == 10);
    }

    @Test
    public void testIsGoalFalse() {
        assertFalse(new Board(generateBlock(PUZZLE_04)).isGoal());
    }

    @Test
    public void testIsGoalTrue() {
        assertTrue(new Board(generateBlock(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 0})).isGoal());
    }

    @Test
    public void testTwin() {
        // TODO: Write more comprehensive test for twin, which is 'random'.
        assertTrue(new Board(generateBlock(PUZZLE_04)).twin().dimension() == 3);
    }

    @Test
    public void testNeighbors() {
        assertTrue(((Stack) new Board(generateBlock(PUZZLE_04)).neighbors()).size() == 2);
        assertTrue(((Stack) new Board(generateBlock(new int[]{1, 0, 3, 4, 2, 5, 7, 8, 6})).neighbors()).size() == 3);
        assertTrue(((Stack) new Board(generateBlock(new int[]{4, 1, 3, 0, 2, 5, 7, 8, 6})).neighbors()).size() == 3);
        assertTrue(((Stack) new Board(generateBlock(new int[]{1, 2, 3, 4, 0, 5, 7, 8, 6})).neighbors()).size() == 4);
        assertTrue(((Stack) new Board(generateBlock(new int[]{1, 2, 3, 4, 5, 7, 0, 8, 6})).neighbors()).size() == 2);
        assertTrue(((Stack) new Board(generateBlock(new int[]{1, 2, 3, 4, 5, 7, 8, 0, 6})).neighbors()).size() == 3);
        assertTrue(((Stack) new Board(generateBlock(new int[]{1, 2, 3, 4, 5, 7, 8, 6, 0})).neighbors()).size() == 2);
    }
}
