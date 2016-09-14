import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] items;
    private int n;

    // construct an empty randomized queue
    public RandomizedQueue() {
        this.items = (Item[]) new Object[1];
    }

    // is the queue empty?
    public boolean isEmpty() {
        return n == 0;
    }

    // return the number of items on the queue
    public int size() {
        return n;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new NullPointerException("Cannot add null item");
        }

        if (n == items.length) {
            resize(2 * items.length);
        }

        items[n++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("Cannot dequeue from empty list");
        }

        final int index = StdRandom.uniform(size());
        final Item item = items[index];
        n--;
        items[index] = items[n]; // swap index and last
        items[n] = null; // removes loitering
        if (n > 0 && n == items.length / 4) {
            resize(items.length / 2);
        }

        return item;
    }

    // return (but do not remove) a random item
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException("Cannot sample from empty list");
        }
        return items[StdRandom.uniform(size())];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator(this.items, this.n);
    }

    private void resize(int capacity) {
        final Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < n; i++) {
            copy[i] = items[i];
        }

        this.items = copy;
    }

    public static void main(String[] args) { }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private final Item[] items;
        private final int[] indexes;
        private int index;

        public RandomizedQueueIterator(Item[] items, int n) {
            this.items = items;
            this.indexes = new int[n];
            for (int i = 0; i < n; i++) {
                indexes[i] = i;
            }

            StdRandom.shuffle(this.indexes);
        }

        public boolean hasNext() {
            return this.indexes.length > this.index;
        }

        public void remove() {
            throw new UnsupportedOperationException("Remove not supported");
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more items to return");
            }

            final Item next = items[this.indexes[this.index]];
            this.index++;
            return next;
        }
    }
}