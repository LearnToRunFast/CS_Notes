
import java.util.Iterator;
import java.util.NoSuchElementException;

// this code was taken from princeton algo course
public class ResizingArrayStack<T>  implements Iterable<T> {

    private static final int INIT_CAPACITY = 8;

    private T[] a; 
    private int n;

    public ResizingArrayStack() {
        a = (T[]) new Object[INIT_CAPACITY];
        n = 0;
    }

    public boolean isEmpty() {
        return n == 0;
    }

    public int size() {
        return n;
    }


    private void resize(int capacity) {
        assert capacity >= n;

        T[] copy = (T[]) new Object[capacity];
        for (int i = 0; i < n; i++) {
            copy[i] = a[i];
        }
        a = copy;

    }

    public void push(T item) {
        if (n == a.length) resize(2 * a.length);
        a[n++] = T;                           
    }


    public T pop() {
        if (isEmpty()) throw new NoSuchElementException("Stack underflow");
        n--;
        T item = a[n];
        a[n] = null;   // to avoid loitering

        // shrink size of array if necessary
        if (n > 0 && n == a.length / 4) resize(a.length / 2);
        return item;
    }

    public T peek() {
        if (isEmpty()) throw new NoSuchElementException("Stack underflow");
        return a[n - 1];
    }


    public Iterator<T> iterator() {
        return new ReverseArrayIterator();
    }

    // an iterator, doesn't implement remove() since it's optional
    private class ReverseArrayIterator implements Iterator<T> {
        private int i;

        public ReverseArrayIterator() {
            i = n - 1;
        }

        public boolean hasNext() {
            return i >= 0;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public T next() {
            if (!hasNext()) throw new NoSuchElementException();
            return a[i--];
        }
    }

    public static void main(String[] args) {
        ResizingArrayStack<String> stack = new ResizingArrayStack<String>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (!item.equals("-")) stack.push(item);
            else if (!stack.isEmpty()) StdOut.print(stack.pop() + " ");
        }
        StdOut.println("(" + stack.size() + " left on stack)");
    }
}

