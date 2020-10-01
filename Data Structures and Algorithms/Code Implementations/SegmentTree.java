import java.util.*;

class SegmentTree {

    private int[] tree, arr; //segment tree and original array.
    private int n;  // size of original arr

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

    private void buildSumTree() {
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

    private void updateSum(int j, int newValue) {
        if (j < 0 || j > n - 1) {
            return;
        }

        int diff = newValue - arr[j];
        
        arr[j] = newValue;

        updateSumHelper(1, j, diff, 0, n - 1);
    }

    private int getSumHelper(int i, int ql, int qr, int l, int r) {

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

    private int getSum(int ql, int qr) {
        if (ql > qr || ql < 0 || qr > n - 1) {
            return -1;
        }

        return getSumHelper(1, ql, qr, 0, n - 1);
    }

    public SegmentTree(int[] a) {
        n = a.length;
        tree = new int[2 * n];
        arr = a;
    }

    private static void testing(int i, int j, int expcted, int actual) {
        boolean correctAns = (expcted == actual);
        String result = correctAns ? "Correct" : "Wrong";
        System.out.println("Sum from " + i + " to " + j + ", the answer is " + result);

        if (!correctAns) {
            System.out.println("Expected: " + expcted + " but got " + actual);
        }
    }
    public static void main(String[] args) {
        int[] arr = new int[] {9, -2, 7, 3, 0, -1, -8};
        SegmentTree st = new SegmentTree(arr);
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
    }
}
