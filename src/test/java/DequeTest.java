import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class DequeTest {

    @Test
    public void testDequeAddFirst() {
        Deque<Integer> deque = new Deque<>();

        assertTrue(deque.isEmpty());
        assertTrue(deque.size() == 0);
        deque.addFirst(5);
        assertFalse(deque.isEmpty());
        assertTrue(deque.size() == 1);
        deque.addFirst(8);
        assertFalse(deque.isEmpty());
        assertTrue(deque.size() == 2);
    }

    @Test
    public void testDequeRemoveFirst() {
        Deque<Integer> deque = new Deque<>();
        deque.addFirst(5);
        deque.addFirst(8);

        assertTrue(deque.size() == 2);
        assertTrue(deque.removeFirst() == 8);
        assertTrue(deque.size() == 1);
        assertTrue(deque.removeFirst() == 5);
        assertTrue(deque.size() == 0);
        assertTrue(deque.isEmpty());
    }

    @Test
    public void testDequeAddLast() {
        Deque<Integer> deque = new Deque<>();

        assertTrue(deque.isEmpty());
        assertTrue(deque.size() == 0);
        deque.addLast(5);
        assertFalse(deque.isEmpty());
        assertTrue(deque.size() == 1);
        deque.addLast(8);
        assertFalse(deque.isEmpty());
        assertTrue(deque.size() == 2);
    }

    @Test
    public void testDequeRemoveLast() {
        Deque<Integer> deque = new Deque<>();
        deque.addFirst(5);
        deque.addFirst(8);

        assertTrue(deque.size() == 2);
        assertTrue(deque.removeLast() == 5);
        assertTrue(deque.size() == 1);
        assertTrue(deque.removeLast() == 8);
        assertTrue(deque.size() == 0);
        assertTrue(deque.isEmpty());
    }

    @Test
    public void testIterator() {
        Deque<Integer> deque = new Deque<>();
        deque.addLast(5);
        deque.addLast(8);

        List<Integer> integers = new ArrayList<>();
        Iterator<Integer> it = deque.iterator();
        while (it.hasNext()) {
            integers.add(it.next());
        }

        assertTrue(integers.size() == 2);
        assertTrue(integers.get(0) == 5);
        assertTrue(integers.get(1) == 8);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testIteratorRemove() {
        new Deque<>().iterator().remove();
    }

    @Test(expected = NoSuchElementException.class)
    public void testIteratorEmptyNext() {
        Iterator it = new Deque<>().iterator();
        assertFalse(it.hasNext());
        it.next();
    }
}
