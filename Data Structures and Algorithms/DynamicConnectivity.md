# Dynamic Connectivity

Try to find connection between two objects

## Quick Find

1. Integer array `id[]` of size `N`
2. Interpretation: p and q are connected iff they have the same id

```java
public class QuickFindUF {
    private int[] id;    // id[i] = component identifier of i
    private int count;   // number of components
    public QuickFindUF(int n) {
        count = n;
        id = new int[n];
        for (int i = 0; i < n; i++)
            id[i] = i;
    }

    public int count() {
        return count;
    }

    public boolean connected(int p, int q) {
        return id[p] == id[q];
    }

    public void union(int p, int q) {
        int pId = id[p]; 
        int qId = id[q];  

        // p and q are already in the same component
        if (pId == qId) return;

        for (int i = 0; i < id.length; i++)
            if (id[i] == pID) id[i] = qId;
        count--;
    }
}
```

**Connected**: Check `id[p]= id[q]`.  **O(1)**

**Union**:Merge components containing p and q by setting all `id[p]` with value of `id[q]` **O(n)**

**Problem**: Too slow

## Quick Union

1. Integer array `id[]` of size `N`
2. Interpretation: `id[i]` is parent of i
3. Root of i is `id[id[id[...]]]`

```java
public class QuickUnionUF {
    private int[] parent;  // parent[i] = parent of i
    private int count;     // number of components

    public QuickUnionUF(int n) {
        parent = new int[n];
        count = n;
        for (int i = 0; i < n; i++) {
            parent[i] = i;
        }
    }
  
    public int count() {
        return count;
    }
  
    public int root(int p) {
        // terminate only root = root itself
        while (p != parent[p]) {
             p = parent[p];
        }
        return p;
    }
  
    public boolean connected(int p, int q) {
        return root(p) == root(q);
    }

    public void union(int p, int q) {
        int rootP = root(p);
        int rootQ = root(q);
        if (rootP == rootQ) return;
        parent[rootP] = rootQ; 
        count--;
    }
}
```

**Connected**: Check `root(p)= root(q)`.  **O(n)**

**Union**:Merge components containing p and q by setting the id of p's root to the id of q's root. **O(n)**

**Problem**: The height of the tree may become linear

## Weighted Quick Union with Path Compression

Instead of always connecting p to q, maintain one extra array to keep track the size of p and q and always connect small size to larger size. Compressing the path by point every points in the find parent path to it's root.

```java
public class WeightedQuickUnionUF {
    private int[] parent;   // parent[i] = parent of i
    private int[] size;     // size[i] = number of elements in subtree rooted at i
    private int count;      // number of components

    public WeightedQuickUnionUF(int n) {
        count = n;
        parent = new int[n];
        size = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            size[i] = 1;
        }
    }

    public int count() {
        return count;
    }
  
    public int root(int p) {
        while (p != parent[p]) {
           parent[p] = parent[parent[p]];  // path compression by halving
           p = parent[p];
        }
        return p;
    }

    public boolean connected(int p, int q) {
        return root(p) == root(q);
    }

    public void union(int p, int q) {
        int rootP = root(p);
        int rootQ = root(q);
        if (rootP == rootQ) return;

        // make smaller root point to larger one
        if (size[rootP] < size[rootQ]) {
            parent[rootP] = rootQ;
            size[rootQ] += size[rootP];
        } else {
            parent[rootQ] = rootP;
            size[rootP] += size[rootQ];
        }
        count--;
    }
}
```

 **Connected**: Check `root(p)= root(q)`.  **O(lg n)**

**Union**:Merge components containing p and q by setting the id of p's root to the id of q's root. **O(lg n)**

## Weighted Quick Union by Rank with Path Compression

```java
public class UF {

    private int[] parent;  // parent[i] = parent of i
    private byte[] rank;   // rank[i] = rank of subtree rooted at i (never more than 31)
    private int count;     // number of components

    public UF(int n) {
        if (n < 0) throw new IllegalArgumentException();
        count = n;
        parent = new int[n];
        rank = new byte[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            rank[i] = 0;
        }
    }

    public int root(int p) {
        while (p != parent[p]) {
            parent[p] = parent[parent[p]];    // path compression by halving
            p = parent[p];
        }
        return p;
    }

    public int count() {
        return count;
    }
  
    public boolean connected(int p, int q) {
        return root(p) == root(q);
    }
  
    public void union(int p, int q) {
        int rootP = root(p);
        int rootQ = root(q);
        if (rootP == rootQ) return;

        // make root of smaller rank point to root of larger rank
        if (rank[rootP] < rank[rootQ]) {
          parent[rootP] = rootQ;
        }
        else if (rank[rootP] > rank[rootQ]) {
          parent[rootQ] = rootP;
        } else {
            parent[rootQ] = rootP;
            rank[rootP]++; 
        }
        count--;
    }
}

```

