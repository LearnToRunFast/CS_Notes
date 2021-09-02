package main

import "fmt"

func merge(nums *[]int, temp *[]int, l, mid, r int) {
	i, j := l, mid+1
	for k := l; k <= r; k++ {
		if i > mid {
			(*temp)[k] = (*nums)[j]
			j++
		} else if j > r {
			(*temp)[k] = (*nums)[i]
			i++
		} else if (*nums)[i] <= (*nums)[j] {
			(*temp)[k] = (*nums)[i]
			i++
		} else {
			(*temp)[k] = (*nums)[j]
			j++
		}
	}
	for k := l; k <= r; k++ {
		(*nums)[k] = (*temp)[k]
	}
}
func sort(nums *[]int, temp *[]int, l, r int) {
	if l >= r {
		return
	}
	mid := l + (r-l)/2
	sort(nums, temp, l, mid)
	sort(nums, temp, mid+1, r)
	if (*nums)[mid] <= (*nums)[mid+1] {
		return
	}
	merge(nums, temp, l, mid, r)
}
func main() {
	nums := []int{9, 1, 1, 3, 5, 100}
	n := len(nums)
	temp := make([]int, n)
	sort(&nums, &temp, 0, n-1)
	fmt.Printf("%v", nums)
}
