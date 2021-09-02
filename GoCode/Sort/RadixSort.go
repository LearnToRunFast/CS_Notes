package main

import (
	"fmt"
	"strconv"
)

const backetSize = 10

func getMax(nums []int) int {
	max := 0
	for _, v := range nums {
		if v > max {
			max = v
		}
	}
	return max
}

// Radix Sort
func radixSort(nums []int) []int {

	maxNum := getMax(nums)
	n := len(nums)
	currVal := 1
	newNums := make([]int, n, n)

	// Loop until we reach the largest significant digit
	for ; maxNum >= currVal; currVal *= 10 {

		fmt.Println("\tSorting: "+strconv.Itoa(currVal)+"'s place", nums)

		bucket := make([]int, backetSize, backetSize)
		for _, v := range nums {
			bucket[(v/currVal)%10]++
		}

		for i := 1; i < backetSize; i++ {
			bucket[i] += bucket[i-1]
		}

		for i := n - 1; i >= 0; i-- {
			pos := (nums[i] / currVal) % 10
			bucket[pos]--
			newNums[bucket[pos]] = nums[i]
		}

		copy(nums, newNums)

		fmt.Println("\tBucket: ", bucket)
	}

	return nums
}

func main() {

	unsortedList := []int{10, 2, 303, 4021, 293, 1, 0, 429, 480, 92, 2999, 14, 100000000000}
	fmt.Println("Unsorted List:", unsortedList)
	sortedList := radixSort(unsortedList)
	fmt.Println("Sorted List:", sortedList)
}
