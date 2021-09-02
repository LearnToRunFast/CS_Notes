package main

import (
	"fmt"
	"math/rand"
	"time"
)

type quickSort struct {
}

func (q *quickSort) sort(nums *[]int, lo, hi int) {
	if hi <= lo {
		return
	}
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
	q.sort(nums, lo, lt-1)
	q.sort(nums, gt+1, hi)
}
func shuffle(nums *[]int) {
	rand.Seed(time.Now().UnixNano())
	for i := range *nums {
		r := rand.Intn(i + 1)
		(*nums)[i], (*nums)[r] = (*nums)[r], (*nums)[i]
	}
}
func (q *quickSort) quickSort(data []int) {
	shuffle(&data)
	q.sort(&data, 0, len(data)-1)
}
func main() {
	nums := []int{4, 2, 9, 3, 1, 10, 6, 8, 7, 5}
	q := &quickSort{}
	q.quickSort(nums)
	fmt.Println(nums)
}
