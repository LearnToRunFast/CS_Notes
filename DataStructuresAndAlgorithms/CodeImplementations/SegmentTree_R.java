import java.util.*;

class SegmentTree_R {

    private int[] tree, arr; //segment tree and original array.
    private int n;  // size of original arr
    private int[] lazy; // for lazy propagation

    private int left(int i) {
        // left child, index is i * 2
        return i << 1;
    }

    private int right(int i) {
        // right child, index is i * 2 + 1
        return (i << 1) + 1;
    }

    // prevent overflow 
    private int getMid(int l, int r) {
        return l + (r - l) / 2;
    }

    private void error(String msg) {
        System.out.println(msg);
    }

    /*
        Starting of Range sum
    */
    private void buildSumTreeHelper(int i, int l, int r) {
        if (l == r) {
            tree[i] = arr[l];
        } else {
            buildSumTreeHelper(left(i), l, getMid(l, r));
            buildSumTreeHelper(right(i), getMid(l, r) + 1, r);

            // depends on different verion of segment tree(range sum, min, max , update)
            // eg range sum
            tree[i] = tree[left(i)] + tree[right(i)];
        }
    }

    void buildSumTree() {
        buildSumTreeHelper(1, 0, n - 1);
    }
    
    // i is current index, j is index that going to be updated.
    // diff is the value to be updated that along the index j path.
    private void updateSumHelper(int i, int j, int diff, int l, int r) {
        if (j < l || j > r) {
            return;
        }

        tree[i] += diff;
        // as tree[i] != diff was assign before branching, so the condition is l < r instead of  l <= r.
        if (l < r) {
            int mid = getMid(l, r);

            updateSumHelper(left(i), j, diff, l, mid);
            updateSumHelper(right(i), j, diff, mid + 1, r);
        }
    }

    void updateSum(int j, int newValue) {
        if (j < 0 || j > n - 1) {
            return;
        }

        int diff = newValue - arr[j];
        
        arr[j] = newValue;

        updateSumHelper(1, j, diff, 0, n - 1);
    }

    private void lazyMove(int i, int l, int r) {
        if (lazy[i] != 0) {
            tree[i] += (r - l + 1) * lazy[i];

            if (l < r) {
                lazy[left(i)] += lazy[i];
                lazy[right(i)] += lazy[i];
            }
            lazy[i] = 0;
        }
    }

    private int getSumHelper(int i, int ql, int qr, int l, int r) {
        // lazy propagation part
        lazyMove(i, l, r);

        if (ql <= l && qr >= r) {
            return tree[i];
        }

        // completely outside the range
        if (ql > r || qr < l) {
            return 0;
        }
 
        int mid = getMid(l, r);
        
        int leftSum = getSumHelper(left(i), ql, qr, l, mid);
        int rightSum = getSumHelper(right(i), ql, qr, mid + 1, r);
        return leftSum + rightSum;

    }

    int getSum(int ql, int qr) {
        if (ql > qr || ql < 0 || qr > n - 1) {
            error("Invalid input");
            return -1;
        }

        return getSumHelper(1, ql, qr, 0, n - 1);
    }
    /*
        End of Range Sum.

    */


    /* 
        Starting of Range Min
    
    */

    private void buildRMQHelper(int i, int l, int r) {
        if (l == r) {
            tree[i] = arr[l]; 
        } else {
            int mid = getMid(l, r);

            buildRMQHelper(left(i), l, mid);
            buildRMQHelper(right(i), mid + 1, r);
    
            tree[i] = Math.min(tree[left(i)], tree[right(i)]);
        }
    }

    void buildRMQ() {
        buildRMQHelper(1, 0, n - 1);
    }


    private int RMQHelper(int i, int ql, int qr, int l, int r) {
        if (ql <= l && qr >= r) {
            return tree[i];
        }

        if (ql > r || qr < l) {
            return Integer.MAX_VALUE;
        }

        int mid = getMid(l, r);

        int x = RMQHelper(left(i), ql, qr, l, mid);
        int y = RMQHelper(right(i), ql, qr, mid + 1, r);

        return Math.min(x, y);
    }

    int RMQ(int ql, int qr) {
        if (ql < 0 || qr > n - 1 || ql > qr) {
            error("Invalid input");
            return -1;
        }

        return RMQHelper(1, ql, qr, 0, n - 1);
    }
    /* 
        End of Range Min
    
    */

    /*
        Update Range

    */
    private void updateRangeHelper(int i, int ql, int qr, int diff, int l, int r) {
        lazyMove(i, l, r);

        if (l > r || l > qr || r < ql) {
            return;
        }

        // if current l and r is in range of query (ql, qr)
        if (l >= ql && r <= qr) {
            tree[i] += (r - l + 1) * diff;

            // we already calculate the result above, so if  current segment is not leaf,
            // mark the lazy array and postpone the updates.
            if (l < r) {
                lazy[left(i)] += diff;
                lazy[right(i)] += diff;
            }
            return;
        }

        int mid = getMid(l, r);
        updateRangeHelper(left(i), ql, qr, diff, l, mid);
        updateRangeHelper(right(i), ql, qr, diff, mid + 1, r);

        tree[i] = tree[left(i)] + tree[right(i)];
    }

    private void updateRange(int ql, int qr, int diff) {
        updateRangeHelper(1, ql, qr, diff, 0, n - 1);
    }
    public SegmentTree_R(int[] a) {
        n = a.length;
        tree = new int[2 * n];
        lazy = new int[2 * n];
        arr = a;
    }


    // debugging purposes
    private static void testing(int i, int j, int expcted, int actual) {
        boolean correctAns = (expcted == actual);
        String result = correctAns ? "Correct" : "Wrong";
        System.out.println("Sum from " + i + " to " + j + ", the answer is " + result);

        if (!correctAns) {
            System.out.println("Expected: " + expcted + " but got " + actual);
        }
    }

    private int getTreeItem(int i) {
        return tree[i];
    }
    private int getLazyItem(int i) {
        return lazy[i];
    }

    public static void main(String[] args) {
        int[] arr = new int[] {9, -2, 7, 3, 0, -1, -8};
        SegmentTree_R st = new SegmentTree_R(arr);
        st.buildSumTree();

        // testing for getsum
        testing(0, 1, 7, st.getSum(0, 1)); // head
        testing(0, 6, 8, st.getSum(0, 6)); // full
        testing(5, 6, -9, st.getSum(5, 6)); // tail
        testing(3, 4, 3, st.getSum(3, 4)); // head

        //testing for updatesum
        st.updateSum(1, 3);
        testing(0, 1, 12, st.getSum(0, 1)); 
        
        st.updateSum(4, 10);
        testing(0, 6, 23, st.getSum(0, 6)); 

        arr = new int[] {9, -2, 7, 3, 0, -1, -8};
        SegmentTree_R st1 = new SegmentTree_R(arr);
        st1.buildRMQ();
        testing(0, 1, -2, st1.RMQ(0, 1)); // head
        testing(0, 6, -8, st1.RMQ(0, 6)); // full
        testing(5, 6, -8, st1.RMQ(5, 6)); // tail
        testing(3, 4, 0, st1.RMQ(3, 4)); // head

        arr = new int[] {9, -2, 7, 3, 0, -1, -8};
        SegmentTree_R st2 = new SegmentTree_R(arr);
        st2.buildSumTree();

        // testing for lazy propagation
        int prev1 = st2.getSum(0, 2);
        int prev2 = st2.getSum(3, 5);

        st2.updateRange(3, 6, 5);
        testing(0, 2, prev1, st2.getSum(0, 2));
        testing(3, 5, prev2 + 15, st2.getSum(3, 5));
        testing(0, 2, 9, st2.getSum(4, 5));
        testing(0, 2, 4, st2.getSum(5, 5));

    }
}
