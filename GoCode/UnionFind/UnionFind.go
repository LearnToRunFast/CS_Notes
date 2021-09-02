package main

import "fmt"

// go version of UF.java by Robert Sedgewick and Kevin Wayne
type UF struct {
	parent []int  // parent[i] = parent of i
	rank   []byte // rank[i] = rank of subtree rooted at i (never more than 31)
	count  int    // number of components
}

// find root of p
func (uf *UF) find(p int) int {
	for p != uf.parent[p] {
		uf.parent[p] = uf.parent[uf.parent[p]] // path compression by halving
		p = uf.parent[p]
	}
	return p
}
func (uf *UF) union(p, q int) {
	rootP := uf.find(p)
	rootQ := uf.find(q)
	if rootP == rootQ {
		// p and q are already in the same component
		return
	}
	// make root of smaller rank point to root of larger rank
	if uf.rank[rootP] < uf.rank[rootQ] {
		uf.parent[rootP] = rootQ
	} else if uf.rank[rootP] > uf.rank[rootQ] {
		uf.parent[rootQ] = rootP
	} else {
		// same rank; do tie-breaking:
		uf.parent[rootQ] = rootP
		uf.rank[rootP]++
	}
	uf.count--
}
func (uf *UF) connected(p, q int) bool {
	return uf.find(p) == uf.find(q)
}

func New(n int) *UF {
	if n < 0 {
		return nil
	}
	count := n
	parent := make([]int, n)
	rank := make([]byte, n)
	for i := 0; i < n; i++ {
		parent[i] = i
	}
	return &UF{parent, rank, count}
}

func main() {
	uf := New(10)
	fmt.Printf("1<->2:%v\n", uf.connected(1, 2))
	uf.union(1, 2)
	fmt.Printf("1<->2:%v\n", uf.connected(1, 2))
	uf.union(3, 4)
	fmt.Printf("3<->4:%v\n", uf.connected(3, 4))
	fmt.Printf("1<->4:%v\n", uf.connected(1, 4))
	uf.union(1, 3)
	fmt.Printf("1<->4:%v\n", uf.connected(1, 4))
}
