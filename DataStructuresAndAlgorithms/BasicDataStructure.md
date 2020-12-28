# Basic Data Structure

## Stack

First-In-Last-Out(**FILO**) or Last-in-First-Out(**LIFO**). Stack can provides every last item that get pushed into the stack.

### Implementation

[Stack with Java (Implemented with LinkedList)](./CodeImplementations/LinkedListStack.java)

[ResizingArrayStack with Java (Implemented with Resizing Array)](./CodeImplementations/ResizingArrayStack.java)

## Queue

First-In-First-Out(**FIFO**) or Last-in-Last-Out(**LILO**). Queue can provides every first item that get enqueued into the queue.

### Implementation

[Queue with Java (Implemented with LinkedList)](./CodeImplementations/LinkedListQueue.java)

[ResizingArrayStack with Java (Implemented with Resizing Array)](./CodeImplementations/ResizingArrayQueue.java)

## Priority Queue

### Binary Heap

Represent priority queue with binary heap. We use array to represent the binary heap which

1. To ease of calculation, we start the array at index 1 instead of 0.

2. Parent of the node at k is k / 2

3. Children of the node at k are at 2k and 2k + 1

4. For Max Heap, the parent is always larger than it's children.

5. For Min Heap, the parent is always smaller than it's children.

   

### Max  Priority Queue

```java
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;


public class MaxPQ<Key> implements Iterable<Key> {
    private Key[] pq;    // store items at indices 1 to n
    private int n;      // number of items on priority queue
    private Comparator<Key> comparator;  // optional comparator

    public MaxPQ(int initCapacity) {
        pq = (Key[]) new Object[initCapacity + 1];
        n = 0;
    }

    public MaxPQ() {
        this(1);
    }

    public MaxPQ(int initCapacity, Comparator<Key> comparator) {
        this.comparator = comparator;
        pq = (Key[]) new Object[initCapacity + 1];
        n = 0;
    }

    public MaxPQ(Comparator<Key> comparator) {
        this(1, comparator);
    }
  
    public MaxPQ(Key[] keys) {
        n = keys.length;
        pq = (Key[]) new Object[keys.length + 1];
        for (int i = 0; i < n; i++) {
          pq[i + 1] = keys[i];
        }
        // fun fact the n / 2 + 1 to n are leaves    
        for (int k = n / 2; k >= 1; k--) {
          sink(k);
        }
    }
      
    public boolean isEmpty() {
        return n == 0;
    }

    public int size() {
        return n;
    }

    public Key peekMax() {
        if (isEmpty()) throw new NoSuchElementException("Priority queue underflow");
        return pq[1];
    }

    // resize the underlying array to have the given capacity
    private void resize(int capacity) {
        assert capacity > n;
        Key[] temp = (Key[]) new Object[capacity];
        for (int i = 1; i <= n; i++) {
            temp[i] = pq[i];
        }
        pq = temp;
    }

    public void insert(Key x) {
        // double size of array if necessary
        if (n == pq.length - 1) {
          resize(2 * pq.length);
        }
        // add x, and percolate it up to maintain heap invariant
        pq[++n] = x;
        swim(n);
        assert isMaxHeap();
    }

    public Key popMax() {
        if (isEmpty()) throw new NoSuchElementException("Priority queue underflow");
        Key max = pq[1];
        exch(1, n--);
        sink(1);
        pq[n + 1] = null;     // to avoid loitering and help with garbage collection
       // resize to half if it's only one quarter full
        if ((n > 0) && (n == (pq.length - 1) / 4)) {
          resize(pq.length / 2);
        }
        assert isMaxHeap();
        return max;
    }

    private void swim(int k) {
        while (k > 1 && less(k / 2, k)) {
            exch(k, k / 2);
            k = k / 2;
        }
    }

    private void sink(int k) {
        while (2 * k <= n) {
            int j = 2 * k;
            if (j < n && less(j, j + 1)) j++;
            if (!less(k, j)) break;
            exch(k, j);
            k = j;
        }
    }

    private boolean less(int i, int j) {
        if (comparator == null) {
            return ((Comparable<Key>) pq[i]).compareTo(pq[j]) < 0;
        }
        else {
            return comparator.compare(pq[i], pq[j]) < 0;
        }
    }

    private void exch(int i, int j) {
        Key swap = pq[i];
        pq[i] = pq[j];
        pq[j] = swap;
    }

    // is pq[1..n] a max heap?
    private boolean isMaxHeap() {
        for (int i = 1; i <= n; i++) {
            if (pq[i] == null) return false;
        }
        for (int i = n + 1; i < pq.length; i++) {
            if (pq[i] != null) return false;
        }
        if (pq[0] != null) return false;
        return isMaxHeapOrdered(1);
    }

    // is subtree of pq[1..n] rooted at k a max heap?
    private boolean isMaxHeapOrdered(int k) {
        if (k > n) return true;
        int left = 2 * k;
        int right = 2 * k + 1;
        if (left  <= n && less(k, left))  return false;
        if (right <= n && less(k, right)) return false;
        return isMaxHeapOrdered(left) && isMaxHeapOrdered(right);
    }

    public Iterator<Key> iterator() {
        return new HeapIterator();
    }

    private class HeapIterator implements Iterator<Key> {

        // create a new pq
        private MaxPQ<Key> copy;

        // add all items to copy of heap
        // takes linear time since already in heap order so no keys move
        public HeapIterator() {
            if (comparator == null) {
              copy = new MaxPQ<Key>(size());
            } else {
              copy = new MaxPQ<Key>(size(), comparator);
            }                   
            for (int i = 1; i <= n; i++) {
              copy.insert(pq[i]);
            }
        }

        public boolean hasNext()  { 
          return !copy.isEmpty();                     
        }
        public void remove()      { 
          throw new UnsupportedOperationException();  
        }

        public Key next() {
            if (!hasNext()) throw new NoSuchElementException();
            return copy.popMax();
        }
    }
}

```

### Min Priority Heap

```java
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class MinPQ<Key> implements Iterable<Key> {
    private Key[] pq;   // store items at indices 1 to n
    private int n;    // number of items on priority queue
    private Comparator<Key> comparator;  // optional comparator

    public MinPQ(int initCapacity) {
        pq = (Key[]) new Object[initCapacity + 1];
        n = 0;
    }

    public MinPQ() {
        this(1);
    }

    public MinPQ(int initCapacity, Comparator<Key> comparator) {
        this.comparator = comparator;
        pq = (Key[]) new Object[initCapacity + 1];
        n = 0;
    }

    public MinPQ(Comparator<Key> comparator) {
        this(1, comparator);
    }

    public MinPQ(Key[] keys) {
        n = keys.length;
        pq = (Key[]) new Object[keys.length + 1];
        for (int i = 0; i < n; i++)
            pq[i+1] = keys[i];
        for (int k = n/2; k >= 1; k--)
            sink(k);
        assert isMinHeap();
    }
  
    public boolean isEmpty() {
        return n == 0;
    }

    public int size() {
        return n;
    }

    public Key peekMin() {
        if (isEmpty()) {
          throw new NoSuchElementException("Priority queue underflow");
        }
        return pq[1];
    }

    // resize the underlying array to have the given capacity
    private void resize(int capacity) {
        assert capacity > n;
        Key[] temp = (Key[]) new Object[capacity];
        for (int i = 1; i <= n; i++) {
            temp[i] = pq[i];
        }
        pq = temp;
    }

    public void insert(Key x) {
        // double size of array if necessary
        if (n == pq.length - 1) resize(2 * pq.length);

        // add x, and percolate it up to maintain heap invariant
        pq[++n] = x;
        swim(n);
        assert isMinHeap();
    }

    public Key popMin() {
        if (isEmpty()) {
          throw new NoSuchElementException("Priority queue underflow");
        }
        Key min = pq[1];
        exch(1, n--);
        sink(1);
        pq[n + 1] = null;     // to avoid loitering and help with garbage collection
        if ((n > 0) && (n == (pq.length - 1) / 4)) {
          resize(pq.length / 2);
        }
        assert isMinHeap();
        return min;
    }

    private void swim(int k) {
        while (k > 1 && greater(k / 2, k)) {
            exch(k, k / 2);
            k = k / 2;
        }
    }

    private void sink(int k) {
        while (2 * k <= n) {
            int j = 2 * k;
            if (j < n && greater(j, j + 1)) j++;
            if (!greater(k, j)) break;
            exch(k, j);
            k = j;
        }
    }

    private boolean greater(int i, int j) {
        if (comparator == null) {
            return ((Comparable<Key>) pq[i]).compareTo(pq[j]) > 0;
        }
        else {
            return comparator.compare(pq[i], pq[j]) > 0;
        }
    }

    private void exch(int i, int j) {
        Key swap = pq[i];
        pq[i] = pq[j];
        pq[j] = swap;
    }

    // is pq[1..n] a min heap?
    private boolean isMinHeap() {
        for (int i = 1; i <= n; i++) {
            if (pq[i] == null) return false;
        }
        for (int i = n+1; i < pq.length; i++) {
            if (pq[i] != null) return false;
        }
        if (pq[0] != null) return false;
        return isMinHeapOrdered(1);
    }

    // is subtree of pq[1..n] rooted at k a min heap?
    private boolean isMinHeapOrdered(int k) {
        if (k > n) return true;
        int left = 2 * k;
        int right = 2 * k + 1;
        if (left  <= n && greater(k, left))  return false;
        if (right <= n && greater(k, right)) return false;
        return isMinHeapOrdered(left) && isMinHeapOrdered(right);
    }

    public Iterator<Key> iterator() {
        return new HeapIterator();
    }

    private class HeapIterator implements Iterator<Key> {
        // create a new pq
        private MinPQ<Key> copy;

        // add all items to copy of heap
        // takes linear time since already in heap order so no keys move
        public HeapIterator() {
            if (comparator == null) {
              copy = new MinPQ<Key>(size());
            }
            else {
              copy = new MinPQ<Key>(size(), comparator);
            }                   
            for (int i = 1; i <= n; i++) {
              copy.insert(pq[i]);
            }
        }

        public boolean hasNext()  { 
          return !copy.isEmpty();  
        }
        public void remove()      { 
          throw new UnsupportedOperationException();  
        }

        public Key next() {
            if (!hasNext()) throw new NoSuchElementException();
            return copy.popMin();
        }
    }
}
```

### Heap Sort

```java
public class Heap {
    public static void sort(Comparable[] pq) {
      int n = pq.length;
      //construct priority queue
      for (int k = n / 2; k >= 1; k--) {
        sink(pq, k, n);
      }
      while (n > 1) {
        exch(pq, 1, n);
        sink(pq, 1, --n);
      }
      
    }
    private void sink(int k) {
        while (2 * k <= n) {
            int j = 2 * k;
            if (j < n && less(j, j + 1)) j++;
            if (!less(k, j)) break;
            exch(k, j);
            k = j;
        }
    }

    private boolean less(int i, int j) {
        if (comparator == null) {
            return ((Comparable<Key>) pq[i]).compareTo(pq[j]) < 0;
        }
        else {
            return comparator.compare(pq[i], pq[j]) < 0;
        }
    }

    private void exch(int i, int j) {
        Key swap = pq[i];
        pq[i] = pq[j];
        pq[j] = swap;
    }
}
```

## Hash Table

### Hash Function

Standard rule for user-defined types

- Set a prime number as hashcode first(eg. 17) 

- Combine each significant field using the 31x + y rule where x is previous hashcode and y is the particular object hashcode
- if the field is primitive type, use wrapper type hashcode()
- if field is null, return 0
- if field is a reference type, use hashcode()
- if field is an array, apply to each entry.

#### Modular hashing

Hash code gives an `int` between $-2^{31}$ to $-2^{31} - 1$.

Hash function gives an `int` between 0 and M - 1

```java
private int hash(Key key) {
  // since hashcode can be negative, we make sure it's positive
  // and simply use abs will lead to bug if it's -2^31
  return (key.hashCode() & 0x7fffffff) % M)
}
```

### Collision

#### Separate Chaining

Create an array of size M and build Linked List for every index of the array for collision.

- Hash: map key to integer `i` between 0 to M - 1
- Insert: put at front of $i^{th}$ chain
- Search: need to search only $i^{th}$ chain

```java
private static class Node {
  private Object key;
  private Object val;
  private Node next;
  ...
}
public Value get(Key key) {
  int i = hash(key);
  for (Node x = st[i]; x != null; x = x.next) {
    if (key.equals(x.key)) return (Value) x.val;
  }
  return null;
}
```

##### Consequence

Number of probes for search/insert is proportional to N / M

Typical choice: M ~ N / 5,  constant-time operation.

#### Linear Probing

Linear Probing or Open addressing refers to when there is a collision, find next empty slot and put it there.

> **_Note:_** Keep the array at least half full to achieve high performance

