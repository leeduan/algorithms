import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node first;
    private Node last;
    private int size;

    // construct an empty deque
    public Deque() { }

    // is the deque empty?
    public boolean isEmpty() {
        return size() == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new NullPointerException("Cannot add first null item");
        }

        final Node oldFirst = this.first;
        this.first = new Node(item);
        if (isEmpty()) {
            this.last = this.first;
        } else {
            this.first.next = oldFirst;
            oldFirst.prev = this.first;
        }

        size++;
    }

    // add the item to the end
    public void addLast(Item item) {
        if (item == null) {
            throw new NullPointerException("Cannot add last null item");
        }

        final Node oldLast = this.last;
        this.last = new Node(item);
        if (isEmpty()) {
            this.first = this.last;
        } else {
            oldLast.next = this.last;
            this.last.prev = oldLast;
        }

        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("Cannot remove first of empty list");
        }

        final Node oldFirst = this.first;
        this.first = first.next;
        size--;
        return oldFirst.item;
    }

    // remove and return the item from the end
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("Cannot remove last of empty list");
        }

        final Node oldLast = this.last;
        this.last = oldLast.prev;
        size--;
        return oldLast.item;
    }

    // return an iterator over items in order from front to end
    public Iterator<Item> iterator() {
        return new DequeIterator(this.first);
    }

    public static void main(String[] args) { }

    private class Node {
        final Item item;
        Node prev;
        Node next;

        public Node(Item item) {
            this.item = item;
        }
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current;

        public DequeIterator(Node first) {
            this.current = first;
        }

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException("Remove not supported");
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more items to return");
            }

            final Item item = current.item;
            this.current = current.next;
            return item;
        }
    }
}
