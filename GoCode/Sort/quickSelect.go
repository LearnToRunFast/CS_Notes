package main

import (
	"fmt"
	"math/rand"
	"time"
)

func shuffle(nums *[]int) {
	rand.Seed(time.Now().UnixNano())
	for i := range *nums {
		r := rand.Intn(i + 1)
		(*nums)[i], (*nums)[r] = (*nums)[r], (*nums)[i]
	}
}
func partition(nums *[]int, lo, hi int) (int, int) {
	lt, i, gt := lo, lo+1, hi
	v := (*nums)[lo]
	for i <= gt {
		if (*nums)[i] > v {
			(*nums)[gt], (*nums)[i] = (*nums)[i], (*nums)[gt]
			gt--
		} else if (*nums)[i] < v {
			(*nums)[lt], (*nums)[i] = (*nums)[i], (*nums)[lt]
			lt++
			i++
		} else {
			i++
		}
	}
	return lt, gt
}
func quickSelect(nums *[]int, k int) {
	n := len(*nums)
	if k < 0 || k >= n {
		return
	}
	shuffle(nums)
	lo, hi := 0, n-1
	for lo < hi {
		lt, gt := partition(nums, lo, hi)
		fmt.Printf("lt %v, gt %v, array after single partition %v\n", lt, gt, *nums)
		if k > gt {
			lo = gt + 1
		} else if k < lt {
			hi = lt - 1
		} else {
			return
		}
	}
	return
}
func main() {
	nums := []int{9, 7, 3, 5, 6, 8, 5, 4, 5, 5}
	n := len(nums)
	mid := n / 2
	fmt.Printf("Mid index is %v\n", mid)
	fmt.Printf("orinal array is %v\n", nums)
	quickSelect(&nums, mid)
	fmt.Printf("final array %v", nums)
}
