import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;

public class Subset {
    // echo A B C D E F G H I | sbt 'run 5'
    public static void main(String[] args) {
        final int k = Integer.valueOf(args[0]);
        final RandomizedQueue<String> queue = new RandomizedQueue<>();
        while (queue.size() < k) {
            queue.enqueue(StdIn.readString());
        }

        final Iterator<String> it = queue.iterator();
        while (it.hasNext()) {
            StdOut.println(it.next());
        }
    }
}
