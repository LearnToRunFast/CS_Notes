import java.util.*;

class SegmentTree_I {

    private int[] tree, arr; //segment tree and original array.
    private int n;  // size of original arr

    public SegmentTree_I(int[] a) {
        n = a.length;
        tree = new int[2 * n];
        arr = a;
    }

    private int left(int i) {
        // left child, index is i * 2
        return i << 1;
    }

    private int right(int i) {
        // right child, index is i * 2 + 1
        return (i << 1) + 1;
    }

    private int parent(int i) {
        return i >> 1;
    }

    // the distance of same item between arr and tree is n.
    private int getTreeIndex(int i) {
        return i + n;
    }

    private boolean isOdd(int i) {
        return (i & 1) > 0;
    }

    private void error(String msg) {
        System.out.println(msg);
    }

    private void updateSum(int i, int value) {
        arr[i] = value;
        i = getTreeIndex(i);

        tree[i] = value;

        while(i > 1) {
            tree[parent(i)] = tree[i] + tree[i^1]; 
            i = parent(i);
        }

    }

    private int getSum(int l, int r) {
        int res = 0;
        l = getTreeIndex(l); 
        r = getTreeIndex(r); 

        while(l < r) {

            // another verion
            // [l, r) include l but not r.
            // if (isOdd(l)) res += tree[l++];
            // if (isOdd(r)) res += tree[--r];
            // l = parent(l);
            // r = parent(r);


            // if l is odd
            // l is right child， then the result does not include l's parent
            // l = (l + 1) / 2 move to next child's parent
            // if l is even
            // l is left child which we need to include l's parent but not l
            // l = l / 2 which is l's parent
            if (isOdd(l)) {
                // right child, just add value to result and move on to next value.
                res += tree[l++]; 
            }

            // opposite of l, if r is even the result does not include r's parent
            // vise versa
            if (!isOdd(r)) {
                // left child, just add value to result and move on to next value.
                res += tree[r--]; 
            }
            l = parent(l);
            r = parent(r);
        }

        if (l < 2 * n && l == r) {
            res += tree[l];
        }
        return res; 
    }


    private void build() {
        //start from n copy content to tail of tree(leaf nodes)
        for(int i = 0; i < n; i++) {
            tree[getTreeIndex(i)] = arr[i];
        }

        for(int i = n - 1; i > 0; i--) {
            tree[i] = tree[left(i)] + tree[right(i)];
        }
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

    public static void main(String[] args) {
        int[] arr = new int[] {9, -2, 7, 3, 0, -1, -8};
        SegmentTree_I st = new SegmentTree_I(arr);
        st.build();

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
