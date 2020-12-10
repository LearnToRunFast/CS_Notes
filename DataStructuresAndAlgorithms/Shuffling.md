# Shuffling

## Knuth Shuffle

For an array `A` of size `n`

1. Loop through `A` from `0` to `n - 1`
2. For every item in index $i$ , generate one random index $r$.
3. Swap item for index $i$ and index $r$

```java
public class Knuth { 

    // this class should not be instantiated
    private Knuth() { }

    /**
     * Rearranges an array of objects in uniformly random order
     * (under the assumption that {@code Math.random()} generates independent
     * and uniformly distributed numbers between 0 and 1).
     * @param a the array to be shuffled
     */
    public static void shuffle(Object[] a) {
        int n = a.length;
        for (int i = 0; i < n; i++) {
            // choose index uniformly in [0, i]
            int r = (int) (Math.random() * (i + 1));
            swap(a, i, r);
        }
    }

    /**
     * Rearranges an array of objects in uniformly random order
     * (under the assumption that {@code Math.random()} generates independent
     * and uniformly distributed numbers between 0 and 1).
     * @param a the array to be shuffled
     */
    public static void shuffleAlternate(Object[] a) {
        int n = a.length;
        for (int i = 0; i < n; i++) {
            // choose index uniformly in [i, n-1]
            int r = i + (int) (Math.random() * (n - i));
						swap(a, i, r);
        }
    }
      
    private static void swap(Object[] a, int i, int j) {
        Object temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }
}
```