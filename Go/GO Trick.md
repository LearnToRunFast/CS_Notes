# GO Trick

## Tricks

### Convert data state into code state when it makes programs clearer

```go
state := 0
for {
  c := readChar()
  switch state {
    case 0:
    if c != '"' {
      return false
    }
    state = 1
    case 1:
    if c == '"' {
      return true
    }
    if c == '\\' {
      state = 2
    } else {
      state = 1
    }
    case 2:
    state = 1
  }
}

// convert to
if readChar() != '"' {
  return false
}

var c rune
for c != '"' {
  c := readChar()
  if c == '\\' {
    readChar()
  }
}
return true
```

### Use additional goroutines to hold additional code state

```go
// we are not able to covert it to code state
type quoteReader struct {
  state int
}
func (q *quoteReader) Init() {
  q.state = 0
}
func (q *quoteReader) ProcessChar(c rune) Status {
  switch q.state {
    case 0:
    if c != '"' {
      return BadInput
    }
    q.state = 1
    case 1:
    if c == '"' {
      return Success
    }
    if c == '\\' {
      q.state = 2
    } else {
      q.state = 1
    }
    case 2:
    q.state = 1
  }
  return NeedMoreInput
}

// but we can use go routines to hold code state
type quoteReader struct {
  char chan rune
  status chan Status
}
func (q *quoteReader) Init() {
  q.char = make(chan rune)
  q.status = make(chan Status)
  go readString(q.readChar)
  <-q.status // always NeedMoreInput
}
func (q *quoteReader) readChar() int {
  q.status <- NeedMoreInput
  return <-q.char
}
func (q *quoteReader) ProcessChar(c rune) Status {
  q.char <- c
  return <-q.status
}
```

### Know why and when each goroutine will exit

```go
package main
import (
  "net/http"
  _ "net/http/pprof"
)
var c = make(chan int)
func main() {
  for i := 0; i < 100; i++ {
    go f(0x10*i)
  }
  http.ListenAndServe("localhost:8080", nil)
}
func f(x int) {
  g(x+1)
}
func g(x int) {
  h(x+1)
}
func h(x int) {
  c <- 1
  f(x+1)
}
```

### Check for escape

```bash
go build -gcflags="-m"
# more verbose version
go build -gcflags '-m -m' ./1.go
```

#### typically cause variables to escape to the heap:

- **Sending pointers or values containing pointers to channels.** At compile time there’s no way to know which goroutine will receive the data on a channel. Therefore the compiler cannot determine when this data will no longer be referenced.
- **Storing pointers or values containing pointers in a slice.** An example of this is a type like `[]*string`. This always causes the contents of the slice to escape. Even though the backing array of the slice may still be on the stack, the referenced data escapes to the heap.
- **Backing arrays of slices that get reallocated because an** `**append**` **would exceed their capacity.** In cases where the initial size of a slice is known at compile time, it will begin its allocation on the stack. If this slice’s underlying storage must be expanded based on data only known at runtime, it will be allocated on the heap.
- **Calling methods on an interface type.** Method calls on interface types are a *dynamic dispatch —* the actual concrete implementation to use is only determinable at runtime. Consider a variable `r` with an interface type of `io.Reader`. A call to `r.Read(b)` will cause both the value of `r` and the backing array of the byte slice `b` to *escape* and therefore be allocated on the heap.

