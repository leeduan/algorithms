import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class RandomizedQueueTest {

    @Test
    public void testRandomizedQueueEnqueue() {
        RandomizedQueue<Integer> randomizedQueue = new RandomizedQueue<>();

        assertTrue(randomizedQueue.isEmpty());
        assertTrue(randomizedQueue.size() == 0);
        randomizedQueue.enqueue(5);
        assertFalse(randomizedQueue.isEmpty());
        assertTrue(randomizedQueue.size() == 1);
        randomizedQueue.enqueue(8);
        assertFalse(randomizedQueue.isEmpty());
        assertTrue(randomizedQueue.size() == 2);

        assertTrue(Arrays.asList(5, 8).contains(randomizedQueue.dequeue()));
    }

    @Test
    public void testRandomizedQueueDequeue() {
        RandomizedQueue<Integer> randomizedQueue = new RandomizedQueue<>();
        randomizedQueue.enqueue(5);
        randomizedQueue.enqueue(8);
        randomizedQueue.enqueue(232);
        assertTrue(randomizedQueue.size() == 3);

        assertTrue(Arrays.asList(5, 8, 232).contains(randomizedQueue.dequeue()));
        assertTrue(randomizedQueue.size() == 2);
        assertTrue(Arrays.asList(5, 8, 232).contains(randomizedQueue.dequeue()));
        assertTrue(randomizedQueue.size() == 1);
        assertTrue(Arrays.asList(5, 8, 232).contains(randomizedQueue.dequeue()));
        assertTrue(randomizedQueue.size() == 0);
        assertTrue(randomizedQueue.isEmpty());
    }

    @Test
    public void testRandomizedQueueSample() {
        RandomizedQueue<Integer> randomizedQueue = new RandomizedQueue<>();
        randomizedQueue.enqueue(5);
        randomizedQueue.enqueue(8);
        randomizedQueue.enqueue(232);

        assertTrue(Arrays.asList(5, 8, 232).contains(randomizedQueue.sample()));
        assertTrue(randomizedQueue.size() == 3);
        assertTrue(Arrays.asList(5, 8, 232).contains(randomizedQueue.sample()));
        assertTrue(Arrays.asList(5, 8, 232).contains(randomizedQueue.sample()));
        assertTrue(randomizedQueue.size() == 3);
    }

    @Test
    public void testIterator() {
        RandomizedQueue<Integer> randomizedQueue = new RandomizedQueue<>();
        randomizedQueue.enqueue(5);
        randomizedQueue.enqueue(8);
        randomizedQueue.enqueue(232);

        List<Integer> integers = new ArrayList<>();
        Iterator<Integer> it = randomizedQueue.iterator();
        while (it.hasNext()) {
            integers.add(it.next());
        }

        assertTrue(integers.size() == 3);
        integers.containsAll(Arrays.asList(5, 8, 232));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testIteratorRemove() {
        new RandomizedQueue<>().iterator().remove();
    }

    @Test(expected = NoSuchElementException.class)
    public void testIteratorEmptyNext() {
        Iterator it = new RandomizedQueue<>().iterator();
        assertFalse(it.hasNext());
        it.next();
    }
}
