import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
    private Iterable<Board> solution;
    private boolean solvable;
    private int moves;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) {
            throw new NullPointerException("Initial cannot be null");
        }
        // set defaults
        this.solution = null;
        this.moves = -1;

        // a* calculation
        final MinPQ<SearchNode> minPQ = new MinPQ<>();
        minPQ.insert(new SearchNode(initial, null));

        final MinPQ<SearchNode> minPQTwin = new MinPQ<>();
        minPQTwin.insert(new SearchNode(initial.twin(), null));

        SearchNode node = minPQ.delMin();
        SearchNode nodeTwin = minPQTwin.delMin();

        while (!node.getBoard().isGoal() && !nodeTwin.getBoard().isGoal()) {
            addNeighbors(node, minPQ);
            node = minPQ.delMin();

            addNeighbors(nodeTwin, minPQTwin);
            nodeTwin = minPQTwin.delMin();
        }

        if (node.getBoard().isGoal()) {
            this.moves = node.getMoves();
            this.solvable = true;

            final Stack<Board> s = new Stack<>();
            while (true) {
                s.push(node.getBoard());
                if (node.getPrevNode() != null) {
                    node = node.getPrevNode();
                    continue;
                }
                break;
            }

            this.solution = s;
        }
    }

    private void addNeighbors(SearchNode node, MinPQ<SearchNode> minPQ) {
        for (Board board : node.getBoard().neighbors()) {
            if (node.getPrevNode() == null || !board.equals(node.getPrevNode().getBoard())) {
                minPQ.insert(new SearchNode(board, node));
            }
        }
    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return solvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return moves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        return solution;
    }

    private class SearchNode implements Comparable<SearchNode> {
        private final Board board;
        private final int moves;
        private final SearchNode prevNode;
        private final int manhattan;
        private final int hamming;

        public SearchNode(Board board, SearchNode prevNode) {
            this.board = board;
            this.moves = prevNode == null ? 0 : prevNode.moves + 1;
            this.prevNode = prevNode;
            this.manhattan = board.manhattan() + moves;
            this.hamming = board.hamming() + moves;
        }

        public Board getBoard() {
            return board;
        }

        public int getMoves() {
            return moves;
        }

        public SearchNode getPrevNode() {
            return prevNode;
        }

        @Override
        public int compareTo(SearchNode other) {
            // priority function set to manhattan
            final int m = Integer.compare(manhattan, other.manhattan);
            return m != 0 ? m : Integer.compare(hamming, other.hamming);
        }

        public String toString() {
            return new StringBuilder().append(this.moves + "\n").append(board.toString()).toString();
        }
    }

    // solve a slider puzzle (given below)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                blocks[i][j] = in.readInt();
            }
        }
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable()) {
            StdOut.println("No solution possible");
        } else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution()) {
                StdOut.println(board);
            }
        }
    }
}