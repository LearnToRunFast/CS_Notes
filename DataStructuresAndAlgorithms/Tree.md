[toc]

# Tree

A **tree** is a widely used abstract data type that simulates a hierarchical tree structure, with a root value and subtrees of children with a parent node, represented as a set of linked nodes.

| ![Tree](Asserts/BTree/220px-Tree_(computer_science).svg.png) |
| :----------------------------------------------------------: |
| `Root` is circled in red<br />Each single circle called node<br />9 is `parent` of 4<br />so 4 is `child` of 9 |

Normal tree can be any forms as long as it follows the structures of any node inside the tree can be access via `root`.

## Binary Tree

A tree whose elements have at most 2 children is called a `binary tree`. Since each element in a binary tree can have only 2 children, we typically name them the left and right child.

### Binary Search Tree

Binary search tree is a special type of binary tree which has following properties:

1. The value of the key of the left sub-tree is less than the value of its parent (root) node's key.
2. The value of the key of the right sub-tree is greater than or equal to the value of its parent (root) node's key.
3. The left and right subtree must also be a binary search tree.

| ![Binary Tree1](Asserts/BTree/20160202203355523.png) | ![Binary Tree 2](Asserts/BTree/20160202203448944.png) |
| :--------------------------------------------------: | :---------------------------------------------------: |
|                 Binary Search Tree 1                 |                 Binary Search Tree 2                  |

Both of them are binary tree, but you can see that if we need to do [[binary search]] on those tree, we will get `O(n)` for **Binary Search Tree 2 **  which is not what we want. To deal with such edge cases,  [[#Balanced Binary Search Tree]] was introduced to deal with such bad performance.

##  Balanced Binary Search Tree

A Balanced Binary Search Tree is a special type of  [[#Binary Search Tree]] that automatically keeps its height (maximal number of levels below the root) small during insertions and deletions.

### AVL Tree

AVL tree is a self-balancing Binary Search Tree (BST) where satisfy all the characteristics of [[#Binary Search Tree]] and the difference between heights of left and right subtrees cannot be more than one for all nodes.

| ![ICS 46 Spring 2020, Notes and Examples: AVL Trees](Asserts/BTree/AVLVsNonAVL.png) |
| :----------------------------------------------------------: |
| The difference between heights of left and right of the highlighted nodes is more than 1 |

#### UnBalance Situation

AVL tree may lose balance when we delete or insert node into the tree. It can be summaries into four type of scenarios:![balance](Asserts/BTree/20160202203648148.png)

1. **LeftLeft**: the `left child` of the root has height different of 2 causing the unbalance(it's `left child` is taller than right child)
2. **LeftRight**: the `left child` of the root has height different of 2 causing the unbalance(it's `right child` is taller than left child)
3. **RightLeft**: the `right child` of the root has height different of 2 causing the unbalance(it's `left child` is taller than left child)
4. **RightRight**: the `right child` of the root has height different of 2 causing the unbalance(it's `right child` is taller than left child)

#### Rebalancing

For the four types of unbalance situation, we need four ways to restore the balance state.

**LeftLeft**:

![索引](Asserts/BTree/20160202204113994.png)

1. Saving the right child(`Y`) of the left child(`k1`) of the root
2. Assign root(`k2`) as it's(`k1`) right child
3. Assign root(`k2`)'s left to the saved right child(`Y`)

**RightRight**:![索引](Asserts/BTree/20160202204207963.png)

1. Saving the left child(`Y`) of the right child(`k2`) of the root(`k1`)
2. Assign root(`k1`) as it's(`k2`) left child
3. Assign root(`k1`)'s right to the saved left child(`Y`)

**LeftRight**: 

![索引](Asserts/BTree/20160202204257369.png)

1. Do a **RR** on left child(`k1`) of the root
2. Do a **LL** on root(`k3`)

**RightLeft**:

![索引](Asserts/BTree/20160202204331073.png)

1. Do a **LL** on right child(`k3`) of the root
2. Do a **RR** on root(`k1`)

#### Implementation

// TODO (Java version)

### 2-3 Search Tree

A 2-3 search tree is a tree that is either **empty** or

- A 2-node, with one key (and associated value) and two links,
  a left link to a 2-3 search tree with smaller keys, and a right
  link to a 2-3 search tree with larger keys
- A 3-node, with two keys (and associated values) and three
  links, a left link to a 2-3 search tree with smaller keys, a middle link to a 2-3 search tree with keys between the node’s keys, and a right link to a 2-3 search tree with larger keys

As usual, we refer to a link to an empty tree as a null link.

2-3 search tree serves as the general idea for red black tree and B tree later.



![image-20201214161043176](Asserts/Tree/image-20201214161043176.png)

### Left Lean Red Black Tree

Define red-black BSTs as BSTs having red and black links and satisfying the following three restrictions:

- Red links lean left.
- No node has two red links connected to it.
- The tree has perfect black balance: every path from the root to a null link has the
  same number of black links.

We can treat Left-leaning red black BST as 2-3 tree as showing below:

![image-20201214164014509](Asserts/Tree/image-20201214164014509.png)

#### Representation

```java
private static final boolean RED = true;
private static final boolean BLACK = false;

private class Node {
  Key key;
  Value val;
  Node left, right;
  boolean color;
}

private boolean isRed(Node x) {
  if (x == null) return false;
  return x.color == RED;
}
```

#### Search

Search in red black tree is same as normal BST.

```java
public Value get(Key key) {
  Node x = root;
  while (x != null) {
    int cmp = key.compareTo(x.key);
    if (cmp < 0) x = x.left;
    else if (cmp > 0) x = x.right;
    else return x.val;
  }
  return null;
}
```

#### Rotation

To preserve the property of red black tree during insertion, we need to make sure the red link is always left leaning.

##### Rotate Left

| ![image-20201214165326645](Asserts/Tree/image-20201214165326645.png) | ![image-20201214170135737](Asserts/Tree/image-20201214170135737.png) |
| :----------------------------------------------------------: | :----------------------------------------------------------: |
|                  **Right-leaning red link**                  |                  **Left-leaning red link**                   |

```java
private Node rotateLeft(Node h) {
  assert isRed(h.right);
  Node x = h.right;
  h.right = x.left;
  x.left = h;
  x.color = h.color;
  h.color = RED;
  return x;
}

```

##### Rotate Right

| ![image-20201214170640931](Asserts/Tree/image-20201214170640931.png) | ![image-20201214170657611](Asserts/Tree/image-20201214170657611.png) |
| :----------------------------------------------------------: | :----------------------------------------------------------: |
|                  **Left-leaning red link**                   |                  **Right-leaning red link**                  |

```java
private Node rotateRight(Node h) {
  assert isRed(h.left);
  Node x = h.left;
  h.left = x.right;
  x.right = h;
  x.color = h.color;
  h.color = RED;
  return x;
}
```



#### Flip Color

Flip the color, change 4-node into 3-node.

| ![image-20201214171623978](Asserts/Tree/image-20201214171623978.png) | ![image-20201214171633035](Asserts/Tree/image-20201214171633035.png) |
| :----------------------------------------------------------: | :----------------------------------------------------------: |
|                          **4-node**                          |                          **3-node**                          |

```java
private void flipColors(Node h) {
  assert !isRed(h);
  assert isRed(h.left);
  assert isRed(h.right);
  h.color = RED;
  h.left.color = BLACK;
  h.right.color = BLACK;
}
```

#### Insertion

##### Insert into a single node tree

1. If the new node is less than current node, then it's fine

   ![image-20201214172143840](Asserts/Tree/image-20201214172143840.png)

2. If the new node is larger than current node(located at right side), do a rotate left.

   ![image-20201214172204882](Asserts/Tree/image-20201214172204882.png)

##### Insert into 2-node tree

```java
private Node put(Node h, Key key, Value val) {
  if (h == null) return new Node(key, val, RED);
  if cmp = key.compareTo(h.key);
  if (cmp < 0) h.left = put(h.left, key, val);
  else if (cmp > 0) h.right = put(h.right, key, val);
  else h.val = val;
  
  if (isRed(h.right) && !isRed(h.left)) h =rotateLeft(h);
  if (isRed(h.left) && isRed(h.left.left) h = rotateRight(h);
  if (isRed(h.left) && isRed(h.right)) flipColors(h);
  
}
```

#### Complete Code

```java
import java.util.NoSuchElementException;

public class RedBlackBST<Key extends Comparable<Key>, Value> {

    private static final boolean RED   = true;
    private static final boolean BLACK = false;

    private Node root;     // root of the BST
    // BST helper node data type
    private class Node {
        private Key key;           // key
        private Value val;         // associated data
        private Node left, right;  // links to left and right subtrees
        private boolean color;     // color of parent link
        private int size;          // subtree count

        public Node(Key key, Value val, boolean color, int size) {
            this.key = key;
            this.val = val;
            this.color = color;
            this.size = size;
        }
    }
  
    public RedBlackBST() {}

    // is node x red; false if x is null ?
    private boolean isRed(Node x) {
        if (x == null) return false;
        return x.color == RED;
    }

    // number of node in subtree rooted at x; 0 if x is null
    private int size(Node x) {
        if (x == null) return 0;
        return x.size;
    } 

    public int size() {
        return size(root);
    }

    public boolean isEmpty() {
        return root == null;
    }

    public Value get(Key key) {
        if (key == null) {
          throw new IllegalArgumentException("argument to get() is null");
        }
        return get(root, key);
    }

    // value associated with the given key in subtree rooted at x; null if no such key
    private Value get(Node x, Key key) {
        while (x != null) {
            int cmp = key.compareTo(x.key);
            if      (cmp < 0) x = x.left;
            else if (cmp > 0) x = x.right;
            else              return x.val;
        }
        return null;
    }

    public boolean contains(Key key) {
        return get(key) != null;
    }
  
    public void put(Key key, Value val) {
        if (key == null) throw new IllegalArgumentException("first argument to put() is null");
        if (val == null) {
            delete(key);
            return;
        }

        root = put(root, key, val);
        root.color = BLACK;
        // assert check();
    }

    // insert the key-value pair in the subtree rooted at h
    private Node put(Node h, Key key, Value val) { 
        if (h == null) return new Node(key, val, RED, 1);

        int cmp = key.compareTo(h.key);
        if      (cmp < 0) h.left  = put(h.left,  key, val); 
        else if (cmp > 0) h.right = put(h.right, key, val); 
        else              h.val   = val;

        // fix-up any right-leaning links
        if (isRed(h.right) && !isRed(h.left))      h = rotateLeft(h);
        if (isRed(h.left)  &&  isRed(h.left.left)) h = rotateRight(h);
        if (isRed(h.left)  &&  isRed(h.right))     flipColors(h);
        h.size = size(h.left) + size(h.right) + 1;

        return h;
    }

    public void deleteMin() {
        if (isEmpty()) throw new NoSuchElementException("BST underflow");

        // if both children of root are black, set root to red
        if (!isRed(root.left) && !isRed(root.right))
            root.color = RED;

        root = deleteMin(root);
        if (!isEmpty()) root.color = BLACK;
        // assert check();
    }

    // delete the key-value pair with the minimum key rooted at h
    private Node deleteMin(Node h) { 
        if (h.left == null)
            return null;

        if (!isRed(h.left) && !isRed(h.left.left))
            h = moveRedLeft(h);

        h.left = deleteMin(h.left);
        return balance(h);
    }

    public void deleteMax() {
        if (isEmpty()) throw new NoSuchElementException("BST underflow");

        // if both children of root are black, set root to red
        if (!isRed(root.left) && !isRed(root.right))
            root.color = RED;

        root = deleteMax(root);
        if (!isEmpty()) root.color = BLACK;
        // assert check();
    }

    // delete the key-value pair with the maximum key rooted at h
    private Node deleteMax(Node h) { 
        if (isRed(h.left))
            h = rotateRight(h);

        if (h.right == null)
            return null;

        if (!isRed(h.right) && !isRed(h.right.left))
            h = moveRedRight(h);

        h.right = deleteMax(h.right);

        return balance(h);
    }

    public void delete(Key key) { 
        if (key == null) throw new IllegalArgumentException("argument to delete() is null");
        if (!contains(key)) return;

        // if both children of root are black, set root to red
        if (!isRed(root.left) && !isRed(root.right))
            root.color = RED;

        root = delete(root, key);
        if (!isEmpty()) root.color = BLACK;
        // assert check();
    }

    // delete the key-value pair with the given key rooted at h
    private Node delete(Node h, Key key) { 
        // assert get(h, key) != null;

        if (key.compareTo(h.key) < 0)  {
            if (!isRed(h.left) && !isRed(h.left.left))
                h = moveRedLeft(h);
            h.left = delete(h.left, key);
        }
        else {
            if (isRed(h.left))
                h = rotateRight(h);
            if (key.compareTo(h.key) == 0 && (h.right == null))
                return null;
            if (!isRed(h.right) && !isRed(h.right.left))
                h = moveRedRight(h);
            if (key.compareTo(h.key) == 0) {
                Node x = min(h.right);
                h.key = x.key;
                h.val = x.val;
                // h.val = get(h.right, min(h.right).key);
                // h.key = min(h.right).key;
                h.right = deleteMin(h.right);
            }
            else h.right = delete(h.right, key);
        }
        return balance(h);
    }

    // make a left-leaning link lean to the right
    private Node rotateRight(Node h) {
        // assert (h != null) && isRed(h.left);
        Node x = h.left;
        h.left = x.right;
        x.right = h;
        x.color = x.right.color;
        x.right.color = RED;
        x.size = h.size;
        h.size = size(h.left) + size(h.right) + 1;
        return x;
    }

    // make a right-leaning link lean to the left
    private Node rotateLeft(Node h) {
        // assert (h != null) && isRed(h.right);
        Node x = h.right;
        h.right = x.left;
        x.left = h;
        x.color = x.left.color;
        x.left.color = RED;
        x.size = h.size;
        h.size = size(h.left) + size(h.right) + 1;
        return x;
    }

    // flip the colors of a node and its two children
    private void flipColors(Node h) {
        // h must have opposite color of its two children
        // assert (h != null) && (h.left != null) && (h.right != null);
        // assert (!isRed(h) &&  isRed(h.left) &&  isRed(h.right))
        //    || (isRed(h)  && !isRed(h.left) && !isRed(h.right));
        h.color = !h.color;
        h.left.color = !h.left.color;
        h.right.color = !h.right.color;
    }

    // Assuming that h is red and both h.left and h.left.left
    // are black, make h.left or one of its children red.
    private Node moveRedLeft(Node h) {
        // assert (h != null);
        // assert isRed(h) && !isRed(h.left) && !isRed(h.left.left);

        flipColors(h);
        if (isRed(h.right.left)) { 
            h.right = rotateRight(h.right);
            h = rotateLeft(h);
            flipColors(h);
        }
        return h;
    }

    // Assuming that h is red and both h.right and h.right.left
    // are black, make h.right or one of its children red.
    private Node moveRedRight(Node h) {
        // assert (h != null);
        // assert isRed(h) && !isRed(h.right) && !isRed(h.right.left);
        flipColors(h);
        if (isRed(h.left.left)) { 
            h = rotateRight(h);
            flipColors(h);
        }
        return h;
    }

    // restore red-black tree invariant
    private Node balance(Node h) {
        // assert (h != null);

        if (isRed(h.right))                      h = rotateLeft(h);
        if (isRed(h.left) && isRed(h.left.left)) h = rotateRight(h);
        if (isRed(h.left) && isRed(h.right))     flipColors(h);

        h.size = size(h.left) + size(h.right) + 1;
        return h;
    }

    public int height() {
        return height(root);
    }
    private int height(Node x) {
        if (x == null) return -1;
        return 1 + Math.max(height(x.left), height(x.right));
    }

    public Key min() {
        if (isEmpty()) throw new NoSuchElementException("calls min() with empty symbol table");
        return min(root).key;
    } 

    // the smallest key in subtree rooted at x; null if no such key
    private Node min(Node x) { 
        // assert x != null;
        if (x.left == null) return x; 
        else                return min(x.left); 
    } 

    public Key max() {
        if (isEmpty()) throw new NoSuchElementException("calls max() with empty symbol table");
        return max(root).key;
    } 

    // the largest key in the subtree rooted at x; null if no such key
    private Node max(Node x) { 
        // assert x != null;
        if (x.right == null) return x; 
        else                 return max(x.right); 
    } 

    public Key floor(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to floor() is null");
        if (isEmpty()) throw new NoSuchElementException("calls floor() with empty symbol table");
        Node x = floor(root, key);
        if (x == null) throw new NoSuchElementException("argument to floor() is too small");
        else           return x.key;
    }    

    // the largest key in the subtree rooted at x less than or equal to the given key
    private Node floor(Node x, Key key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp == 0) return x;
        if (cmp < 0)  return floor(x.left, key);
        Node t = floor(x.right, key);
        if (t != null) return t; 
        else           return x;
    }

    public Key ceiling(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to ceiling() is null");
        if (isEmpty()) throw new NoSuchElementException("calls ceiling() with empty symbol table");
        Node x = ceiling(root, key);
        if (x == null) throw new NoSuchElementException("argument to ceiling() is too small");
        else           return x.key;  
    }

    // the smallest key in the subtree rooted at x greater than or equal to the given key
    private Node ceiling(Node x, Key key) {  
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp == 0) return x;
        if (cmp > 0)  return ceiling(x.right, key);
        Node t = ceiling(x.left, key);
        if (t != null) return t; 
        else           return x;
    }

    public Key select(int rank) {
        if (rank < 0 || rank >= size()) {
            throw new IllegalArgumentException("argument to select() is invalid: " + rank);
        }
        return select(root, rank);
    }

    // Return key in BST rooted at x of given rank.
    // Precondition: rank is in legal range.
    private Key select(Node x, int rank) {
        if (x == null) return null;
        int leftSize = size(x.left);
        if      (leftSize > rank) return select(x.left,  rank);
        else if (leftSize < rank) return select(x.right, rank - leftSize - 1); 
        else                      return x.key;
    }

    public int rank(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to rank() is null");
        return rank(key, root);
    } 

    // number of keys less than key in the subtree rooted at x
    private int rank(Key key, Node x) {
        if (x == null) return 0; 
        int cmp = key.compareTo(x.key); 
        if      (cmp < 0) return rank(key, x.left); 
        else if (cmp > 0) return 1 + size(x.left) + rank(key, x.right); 
        else              return size(x.left); 
    } 

    public Iterable<Key> keys() {
        if (isEmpty()) return new Queue<Key>();
        return keys(min(), max());
    }

    public Iterable<Key> keys(Key lo, Key hi) {
        if (lo == null) throw new IllegalArgumentException("first argument to keys() is null");
        if (hi == null) throw new IllegalArgumentException("second argument to keys() is null");

        Queue<Key> queue = new Queue<Key>();
        // if (isEmpty() || lo.compareTo(hi) > 0) return queue;
        keys(root, queue, lo, hi);
        return queue;
    } 

    // add the keys between lo and hi in the subtree rooted at x
    // to the queue
    private void keys(Node x, Queue<Key> queue, Key lo, Key hi) { 
        if (x == null) return; 
        int cmplo = lo.compareTo(x.key); 
        int cmphi = hi.compareTo(x.key); 
        if (cmplo < 0) keys(x.left, queue, lo, hi); 
        if (cmplo <= 0 && cmphi >= 0) queue.enqueue(x.key); 
        if (cmphi > 0) keys(x.right, queue, lo, hi); 
    } 

    public int size(Key lo, Key hi) {
        if (lo == null) throw new IllegalArgumentException("first argument to size() is null");
        if (hi == null) throw new IllegalArgumentException("second argument to size() is null");

        if (lo.compareTo(hi) > 0) return 0;
        if (contains(hi)) return rank(hi) - rank(lo) + 1;
        else              return rank(hi) - rank(lo);
    }

    private boolean check() {
        if (!isBST())            StdOut.println("Not in symmetric order");
        if (!isSizeConsistent()) StdOut.println("Subtree counts not consistent");
        if (!isRankConsistent()) StdOut.println("Ranks not consistent");
        if (!is23())             StdOut.println("Not a 2-3 tree");
        if (!isBalanced())       StdOut.println("Not balanced");
        return isBST() && isSizeConsistent() && isRankConsistent() && is23() && isBalanced();
    }

    // does this binary tree satisfy symmetric order?
    // Note: this test also ensures that data structure is a binary tree since order is strict
    private boolean isBST() {
        return isBST(root, null, null);
    }

    // is the tree rooted at x a BST with all keys strictly between min and max
    // (if min or max is null, treat as empty constraint)
    // Credit: Bob Dondero's elegant solution
    private boolean isBST(Node x, Key min, Key max) {
        if (x == null) return true;
        if (min != null && x.key.compareTo(min) <= 0) return false;
        if (max != null && x.key.compareTo(max) >= 0) return false;
        return isBST(x.left, min, x.key) && isBST(x.right, x.key, max);
    } 

    // are the size fields correct?
    private boolean isSizeConsistent() { return isSizeConsistent(root); }
    private boolean isSizeConsistent(Node x) {
        if (x == null) return true;
        if (x.size != size(x.left) + size(x.right) + 1) return false;
        return isSizeConsistent(x.left) && isSizeConsistent(x.right);
    } 

    // check that ranks are consistent
    private boolean isRankConsistent() {
        for (int i = 0; i < size(); i++)
            if (i != rank(select(i))) return false;
        for (Key key : keys())
            if (key.compareTo(select(rank(key))) != 0) return false;
        return true;
    }

    // Does the tree have no red right links, and at most one (left)
    // red links in a row on any path?
    private boolean is23() { return is23(root); }
    private boolean is23(Node x) {
        if (x == null) return true;
        if (isRed(x.right)) return false;
        if (x != root && isRed(x) && isRed(x.left))
            return false;
        return is23(x.left) && is23(x.right);
    } 

    // do all paths from root to leaf have same number of black edges?
    private boolean isBalanced() { 
        int black = 0;     // number of black links on path from root to min
        Node x = root;
        while (x != null) {
            if (!isRed(x)) black++;
            x = x.left;
        }
        return isBalanced(root, black);
    }

    // does every path from the root to a leaf have the given number of black links?
    private boolean isBalanced(Node x, int black) {
        if (x == null) return black == 0;
        if (!isRed(x)) black--;
        return isBalanced(x.left, black) && isBalanced(x.right, black);
    } 
}
```

## B Tree

**B**-**tree** is a self-balancing **tree** data structure that maintains sorted data and allows searches, sequential access, insertions, and deletions in logarithmic time.

A B-tree of order `m` is a tree which satisfies the following properties:

1. Every node has at most `m` children.
2. Every non-leaf node (except root) has at least `⌈m/2⌉` child nodes.
3. The root has at least two children if it is not a leaf node.
4. A non-leaf node with `k` children contains `k - 1` keys.
5. All leaves appear in the same level and carry no information.

![索引](Asserts/BTree/20160202204827368.png)

### Application

B tree is normally used in different database systems or file systems.

## B+ Tree

| B+ Tree                                     | B Tree                                      |
| ------------------------------------------- | ------------------------------------------- |
| Non-leaf node only contains key information | All nodes contains key information and data |
| A link association between leaf node        | No association between leaf node            |
| Lead node only contains data                | All nodes contains key information and data |

![索引](Asserts/BTree/20160202205105560.png)