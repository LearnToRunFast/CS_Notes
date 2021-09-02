package main

import "fmt"

type Item struct {
	value string
	index int
}

type IndexMinHeap struct {
	size  int
	items []Item
}

func (h *IndexMinHeap) Len() int {
	return h.size
}
func (h *IndexMinHeap) sink(i int) {
	for 2*i <= h.size {
		j := 2 * i
		if j < h.size && h.items[j+1].value < h.items[j].value {
			j++
		}
		if h.items[i].value < h.items[j].value {
			break
		}
		h.items[i], h.items[j] = h.items[j], h.items[i]
		i = j
	}
}
func (h *IndexMinHeap) swim(i int) {
	for i > 1 && h.items[i/2].value > h.items[i].value {
		h.items[i], h.items[i/2] = h.items[i/2], h.items[i]
		i /= 2
	}
}
func (h *IndexMinHeap) Insert(value string) {
	h.size++
	h.items = append(h.items, Item{value, h.size})
	h.swim(h.size)
}
func (h *IndexMinHeap) Extract() Item {
	item := h.items[1]
	h.items[1] = h.items[h.size]
	h.items = h.items[:h.size]
	h.size--
	h.sink(1)
	return item
}
func main() {
	strings := []string{"it", "was", "the", "best", "of", "times", "it", "was", "the", "worst"}
	h := &IndexMinHeap{0, make([]Item, 1)}
	for _, s := range strings {
		h.Insert(s)
	}
	for h.Len() > 0 {
		item := h.Extract()
		fmt.Printf("index %v Value: %v\n", item.index, item.value)
	}

}
