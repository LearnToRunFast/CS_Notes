## Common concept

### ACID

Acid stand for:

- Atomicity
  - Entire transaction must finish or revert the database to old state.
  - Transaction can contains 1 or more queries.
- Consistency
  - Ensure the data to be in correct state.
  - Data integrity must be followed.
- Isolation
  - Concurrent execution safe, perform parallel jobs like a sequential of jobs.
- Durablility
  - Committed transactions to be persisted into non-volatile memory.

### CAP Theorem

CAP says for any distributed system can only provide 2 of these 3 guarantees.

- Consistency
- Availability: server must fulfils the request if not crashed
- Partition Tolerance: system must keep functioning properly in case of network partition.

### Binary Search

#### Binary Search Version 1

```go
func binarySearch(target int) {
  left, right := 0, len(nums) - 1; // [left, right]  include for both
  for (left <= right) {
    mid := left + (right - left) / 2
    if target > nums[mid]{
      left = mid + 1
    } else if target < nums[mid] {
      right = mid - 1
    } else {
      return mid
    }
  }
  return -1
}

```

#### Binary Search Version 2

```go
func binarySearch(target int) {
  left, right := 0, len(nums); // [left, right)  left include, right open
  for (left < right) {
    mid := left + (right - left) / 2
    if target > nums[mid]{
      left = mid + 1
    } else if target < nums[mid] {
      right = mid 
    } else {
      return mid
    }
  }
  return -1
}
```

#### Binary Search Version 3

```go
func binarySearch(target int) {
  left, right := 0, len(nums); // [left, right)  left include, right open
  for (left < right) {
    mid := left + (right - left) / 2
    if target > nums[mid]{
      left = mid + 1
    } else if target < nums[mid] {
      right = mid 
    }
  }
  return left
}
```

