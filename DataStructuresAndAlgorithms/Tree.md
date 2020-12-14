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

### Insertion

Example of order 5 B-tree,

1. 2 <=  No. children of root <= 5
2. 

## B+ Tree

| B+ Tree                                     | B Tree                                      |
| ------------------------------------------- | ------------------------------------------- |
| Non-leaf node only contains key information | All nodes contains key information and data |
| A link association between leaf node        | No association between leaf node            |
| Lead node only contains data                | All nodes contains key information and data |

![索引](Asserts/BTree/20160202205105560.png)