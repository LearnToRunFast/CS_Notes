# Golang

Go is a **static type** language.

Go code is grouped into packages, and packages are grouped into modules. Your package's module specifies the context Go needs to run the code, including the Go version the code is written for and the set of other modules it requires.

## GO CLI

1. `go build` Compiles a bunch of go source code files.
2. `go run` Compiles and executes one or two files.
3. `go fmt` Formats all the code in each file in the current directory.
4. `go install` Compiles and "install" a package.
5. `go get` Downloads the raw source code of other's package.
6. `go test` Runs any tests associated with the current project.

## Package

There are two types of packages.

1. `Executable`: Generates a file that we can run, only`main` is the executable package
2. `Resuable`: Code used as 'helpers'. It's a good place to put reusable logic. Excepted `main`, other self defined package name will be treated as reusable package

```go
// simple go sample

// main is executable package and "main" function must exist
//others are resuable package
package main

import "fmt" // coming from standard library of GO

func main() {
  fmt.Println("Hello!")
}
```

### Creating Packages

Let's create an application that will use a package we will write. Create a folder in `~/src/golang-book` called `chapter11`. Inside that folder create a file called `main.go` which contains this:

```go
package main

import "fmt"
import "golang-book/chapter11/math"

func main() {
  xs := []float64{1,2,3,4}
  avg := math.Average(xs)
  fmt.Println(avg)
}
```

Now create another folder inside of the `chapter11` folder called `math`. Inside of this folder create a file called `math.go` that contains this:

```go
package math

func Average(xs []float64) float64 {
  total := float64(0)
  for _, x := range xs {
    total += x
  }
  return total / float64(len(xs))
}
```

Using a terminal in the `math` folder you just created run `go install`. This will compile the `math.go` program and create a linkable object file: `~/pkg/os_arch/golang-book/chapter11/math.a`.Now go back to the `chapter11` folder and run `go run main.go`. You should see `2.5`. 

### Alias

we  use an alias for an imported math:

```go
import m "golang-book/chapter11/math"

func main() {
  xs := []float64{1,2,3,4}
  avg := m.Average(xs)
  fmt.Println(avg)
}
```

`m` is the alias.

### Public & private

Every function in the packages we've seen start with a capital letter. In Go if something starts with a capital letter that means other packages (and programs) are able to see it. If we had named the function `average` instead of `Average` our `main` program would not have been able to see it.

## Documentation

Go has the ability to automatically generate documentation for packages we write in a similar way to the standard package documentation. In a terminal run this command:

```bash
godoc golang-book/chapter11/math Average
```

You should see information displayed for the function we just wrote. We can improve this documentation by adding a comment before the function:

```go
// Finds the average of a series of numbers
func Average(xs []float64) float64 {
```

If you run `go install` in the `math` folder, then re-run the `godoc` command you should see our comment below the function definition. This documentation is also available in web form by running this command:

```go
godoc -http=":6060"
```

and entering this URL into your browser:

```go
http://localhost:6060/pkg/
```

You should be able to browse through all of the packages installed on your system.

## Variable

### Types

#### Integer

Go's integer types are: `uint8`, `uint16`, `uint32`, `uint64`, `int8`, `int16`, `int32` and `int64.` 8, 16, 32 and 64 tell us how many bits each of the types use. 

`uint` means “unsigned integer” while `int` means “signed integer”.  

Generally if you are working with integers you should just use the `int` type.

**Max and Min**:`math.MaxInt32` and `math.MinInt32`

#### Floating Point Numbers

Go has two floating point types: `float32` and `float64` (also often referred to as single precision and double precision respectively) as well as two additional types for representing complex numbers (numbers with imaginary parts): `complex64` and `complex128`. Generally we should stick with `float64` when working with floating point numbers.

In addition to numbers there are several other values which can be represented: “not a number” (`NaN`, for things like `0/0`) and positive and negative infinity. (`+∞` and `−∞`)

**Max**:`math.MaxFloat32`

#### String

Go strings are made up of individual bytes, usually one for each character. (Characters from other languages like Chinese are represented by more than one byte)

##### String Format

`%d` for decimal

`%b` for binary

`%x` for non-cap hex, `%X` for cap hex, `%#x` with `0x`prefix and non-cap, `%#X` with `0x`prefix and cap

#### Boolean

A boolean value (named after George Boole) is a special 1 bit integer type used to represent true and false (or on and off). Three logical operators are used with boolean values:

| &&   | and  |
| ---- | ---- |
| \|\| | or   |
| !    | not  |

### Declaration

There are few way to declare a variable.

```go
var card string = "a" // specify card is string, but it is not necessary
var card = "a" // which is equal to above , but you can't change card to other type.
card := "a" // all the three assignment are equivalent 

// declare a group of variable
var (
  a = 5
  b = 10
  c = 15
)
```

### Constant

```go
const x string = "Hello World"
```

## Control Structures

### For

```go
// for loop
// every variable declared need to be used inside the loop
// type 1
for i, card := range cards {
  fmt.Println(i, card)
}

// type 2
for i := 0; i < 100; i++ {
  fmt.Printf(i)
}

// inifite loop
for {
  
}
```

### If

```go
if i % 2 == 0 {
  // divisible by 2
} else if i % 3 == 0 {
  // divisible by 3
} else if i % 4 == 0 {
  // divisible by 4
}

// is ok is true then it will print out 
if name, ok := elements["Un"]; ok {
  fmt.Println(name, ok)
}
```

### Switch

```go
switch i {
case 0: fmt.Println("Zero")
case 1: fmt.Println("One")
case 2: fmt.Println("Two")
case 3: fmt.Println("Three")
case 4: fmt.Println("Four")
case 5: fmt.Println("Five")
default: fmt.Println("Unknown Number")
}
```

## Arrays, Slices and Maps

Array has fixed length in Go but slice is an array that can grow and shrink.

### Arrays

```go
// type 1
var x [5]float64
x[0] = 98
x[1] = 93
x[2] = 77
x[3] = 82
x[4] = 83
 
// type 2
cards := []string{"1", "2"} // array declaration 
```

### Slices

```go
x := make([]float64, 5)  // declare slice

//10 represents the capacity of the underlying array which the slice points to
x := make([]float64, 5, 10)

arr := [5]float64{1,2,3,4,5} //array
x := arr[0:5] // slice

// append will create a new array and assign back to cards.
cards = append(cards, "new string") 


slice1 := []int{1,2,3}
slice2 := make([]int, 2)
copy(slice2, slice1)  // slice 2 will only has 1 and 2

cards[0:1] // from 0 up to 1 but not include 1
```

### Maps

A map is an unordered collection of key-value pairs. Also known as an associative array, a hash table or a dictionary, maps are used to look up a value by its associated key. 

```go
var x map[string]int // declare  // key is type of string and value is type of int
elements := map[string]map[string]string // map[map[string]]

x := make(map[string]int) // initialization
x["key"] = 10 // assign

delete(x, 1) // delete 1 from x

name, ok := elements["Un"] // check for existance


```

## Function

```go
// defined return type 
func newFunc() string {}

// defined two return type
func newFunc() (string, string) {}

// name the return value
func f2() (r int) { // it will return 1
	r = 1
	return
}

// different files with same package can call the function without importing
// file 1
func printOne() string {
  return "1"
}

//file 2
func main() {
  fmt.Println(printOne())
}
```

### Variadic Functions

```go
func add(args ...int) int {
  total := 0
  for _, v := range args {
    total += v
  }
  return total
}
func main() {
  fmt.Println(add(1,2,3))
}
```

By using `...` before the type name of the last parameter you can indicate that it takes zero or more of those parameters. In this case we take zero or more `int`s. We invoke the function like any other function except we can pass as many `int`s as we want.

This is precisely how the `fmt.Println` function is implemented:

```go
func Println(a ...interface{}) (n int, err error)
```

The `Println` function takes any number of values of any type.

We can also pass a slice of `int`s by following the slice with `...`:

```go
func main() {
  xs := []int{1,2,3}
  fmt.Println(add(xs...)) // xs... convert xs into individual value
}
func add(x ...int) int {
	sum := 0
	for _, value := range x {
		sum += value
	}
	return sum
}
```

### Function Literals(Lambda)

```go
func() {}
```

### Closure

It is possible to create functions inside of functions:

```go
func main() {
  add := func(x, y int) int {
    return x + y
  }
  fmt.Println(add(1,1))
}
```

One way to use closure is by writing a function which returns another function which can generate a sequence of numbers. For example here's how we might generate all the even numbers:

```go
func makeEvenGenerator() func() uint {
  i := uint(0)
  return func() (ret uint) {
    ret = i
    i += 2
    return
  }
}
func main() {
  nextEven := makeEvenGenerator()
  fmt.Println(nextEven()) // 0
  fmt.Println(nextEven()) // 2
  fmt.Println(nextEven()) // 4
}
```

`makeEvenGenerator` returns a function which generates even numbers. Each time it's called it adds 2 to the local `i` variable which – unlike normal local variables – persists between calls.

### Recursion

```go
func factorial(x uint) uint {
  if x == 0 {
    return 1
  }
  return x * factorial(x-1)
}
```

### Defer, Panic & Recover

Go has a special statement called `defer` which schedules a function call to be run after the function completes. Consider the following example:

```go
package main

import "fmt"

func first() {
  fmt.Println("1st")
}
func second() {
  fmt.Println("2nd")
}
func main() {
  defer second() // this function will only run after
  first()
}
```

`defer` is often used when resources need to be freed in some way. For example when we open a file we need to make sure to close it later. With `defer`:

```go
f, _ := os.Open(filename)
defer f.Close()
```

This has 3 advantages:

1. it keeps our `Close` call near our `Open` call so it's easier to understand
2. (if our function had multiple return statements (perhaps one in an `if` and one in an `else`) `Close` will happen before both of them
3. deferred functions are run even if a run-time panic occurs.

### Panic & Recover

Earlier we created a function that called the `panic` function to cause a run time error. We can handle a run-time panic with the built-in `recover` function. `recover` stops the panic and returns the value that was passed to the call to `panic`. We might be tempted to use it like this:

```go
package main

import "fmt"

func main() {
  panic("PANIC")
  str := recover()
  fmt.Println(str)
}
```

But the call to `recover` will never happen in this case because the call to `panic` immediately stops execution of the function. Instead we have to pair it with `defer`:

```go
package main

import "fmt"

func main() {
  defer func() {
    str := recover()
    fmt.Println(str)
  }()
  panic("PANIC")
}
```

## Pointers

Pointers reference a location in memory where a value is stored rather than the value itself.

### The * and & operators

`*` is also used to “dereference” pointer variables. Dereferencing a pointer gives us access to the value the pointer points to.

`&` operator to find the address of a variable. `&x` returns address of x in type of `*int` if x is type of `int`

### New

Another way to get a pointer is to use the built-in `new` function:

```go
func one(xPtr *int) {
  *xPtr = 1
}
func main() {
  xPtr := new(int)
  one(xPtr)
  fmt.Println(*xPtr) // x is 1
}
```

`new` takes a type as an argument, allocates enough memory to fit a value of that type and returns a pointer to it.

In some programming languages there is a significant difference between using `new` and `&`, with great care being needed to eventually delete anything created with `new`. Go is not like this, it's a garbage collected programming language which means memory is cleaned up automatically when nothing refers to it anymore.

## Structs and Interfaces

### Structs

An easy way to make this program better is to use a struct. A struct is a type which contains named fields. For example we could represent a Circle like this:

```go
// type 1
type Circle struct {
  x float64
  y float64
  r float64
}

// type 2
type Circle struct {
  x, y, r float64
}

// initialization
var c Circle // type 1
c := new(Circle) // type 2

// assignment
c := Circle{x: 0, y: 0, r: 5} // type 1
c := Circle{0, 0, 5} // type 2

// access fiedls by .
c.x = 10
c.y = 5
```

The `type` keyword introduces a new type. It's followed by the name of the type (`Circle`), the keyword `struct` to indicate that we are defining a `struct` type and a list of fields inside of curly braces. Each field has a name and a type. 

#### Receiver

By creating a new type with a function that has a receiver, we are adding a 'method' to any value of that type.

```go
type deck []string // now deck will act like array of string

// any variable that is deck type can access "print" function
func (d *deck) print() { // d deck is the receiver here
  for i, card := range d {
    fmt.Println(i, card)
  }
}
```

#### Embedded Types

A struct's fields usually represent the has-a relationship. For example a `Circle` has a `radius`. Suppose we had a person struct:

```go
type Person struct {
  Name string
}
func (p *Person) Talk() {
  fmt.Println("Hi, my name is", p.Name)
}
```

And we wanted to create a new `Android` struct. We could do this:

```go
type Android struct {
  Person Person
  Model string
}
```

This would work, but we would rather say an Android is a Person, rather than an Android has a Person. Go supports relationships like this by using an embedded type. Also known as anonymous fields, embedded types look like this:

```go
type Android struct {
  Person
  Model string
}
```

We use the type (`Person`) and don't give it a name. When defined this way the `Person` struct can be accessed using the type name:

```go
a := new(Android)
a.Person.Talk()
```

But we can also call any `Person` methods directly on the `Android`:

```go
a := new(Android)
a.Talk()
```

The is-a relationship works this way intuitively: People can talk, an android is a person, therefore an android can talk.

### Interface

**Interface** is a contract, any object implmented the contract methods will be accpted as it is one of the kind.

`bot` is a interface which has a method call `getGreeting`. The `printGreeting` accepts `bot` type as argument, as both `englishBot` and `spanishBot` implements the `getGeeting` method, they considered as some kind of `bot` object.

```go
type bot interface {
	getGreeting() string
}

type englishBot struct {} //as long as englishBot has the interface method
type spanishBot struct {}

func main() {
  eb := engBot{}
  sb := spanBot{}
  
  printGreeting(eb)
  printGreeting(sb)
}

func printGreeting(b bot) {
  fmt.Println(b.getGreeting())
}

func (englishBot) getGreeting() string {
  return "eng"
}
func (spanishBot) getGreeting() string {
  return "span"
}
```

## Concurrency

Making progress run more than one task simultaneously is known as concurrency. Go has rich support for concurrency using goroutines and channels.

### Go Routine

**GO Scheduler** monitors the activities carried out by **Go Routines**. By default Go tries to use one core.

Place a `go` command in front of a function and main routine communicate with child routine  use **Channel**.One **channel** only pass one type of value.

**Note**: Never access same variable from different routines, make a copy and pass it through as parameter.

### Channel

```go
c := make(chan string) // communicate use channel with string type
c <- 5 // send the value '5' into this channel

myNumber <- c // wait for a value to be sent into the channel. when we get one , assign the vlaue to 'myNumber'

```

#### Channel Direction

We can specify a direction on a channel type thus restricting it to either sending or receiving. For example pinger's function signature can be changed to this:

```go
func pinger(c chan<- string)
```

Now `c` can only be sent to. Attempting to receive from c will result in a compiler error. Similarly we can change printer to this:

```go
func printer(c <-chan string)
```

A channel that doesn't have these restrictions is known as bi-directional. A bi-directional channel can be passed to a function that takes send-only or receive-only channels, but the reverse is not true.

### Select

Go has a special statement called `select` which works like a `switch` but for channels:

```go
func main() {
  c1 := make(chan string)
  c2 := make(chan string)

  go func() {
    for {
      c1 <- "from 1"
      time.Sleep(time.Second * 2)
    }
  }()

  go func() {
    for {
      c2 <- "from 2"
      time.Sleep(time.Second * 3)
    }
  }()

  go func() {
    for {
      select {
      case msg1 := <- c1:
        fmt.Println(msg1)
      case msg2 := <- c2:
        fmt.Println(msg2)
      }
    }
  }()

  var input string
  fmt.Scanln(&input)
}
```

This program prints “from 1” every 2 seconds and “from 2” every 3 seconds. `select` picks the first channel that is ready and receives from it (or sends to it). If more than one of the channels are ready then it randomly picks which one to receive from. If none of the channels are ready, the statement blocks until one becomes available.

The `select` statement is often used to implement a timeout:

```go
select {
case msg1 := <- c1:
  fmt.Println("Message 1", msg1)
case msg2 := <- c2:
  fmt.Println("Message 2", msg2)
case <- time.After(time.Second):
  fmt.Println("timeout")
}
```

`time.After` creates a channel and after the given duration will send the current time on it. (we weren't interested in the time so we didn't store it in a variable) We can also specify a `default` case:

```go
select {
case msg1 := <- c1:
  fmt.Println("Message 1", msg1)
case msg2 := <- c2:
  fmt.Println("Message 2", msg2)
case <- time.After(time.Second):
  fmt.Println("timeout")
default:
  fmt.Println("nothing ready")
}
```

The default case happens immediately if none of the channels are ready.

### Buffered Channels

It's also possible to pass a second parameter to the make function when creating a channel:

```go
c := make(chan int, 1)
```

This creates a buffered channel with a capacity of 1. Normally channels are synchronous; both sides of the channel will wait until the other side is ready. A buffered channel is asynchronous; sending or receiving a message will not wait unless the channel is already full.

## Testing

Create a testing with \<name of original Go file>_test.go. And run the test with command `go test`.



## Input / Output

 The `io` package consists of a few functions, but mostly interfaces used in other packages. 

The two main interfaces are `Reader` and `Writer`. `Reader`s support reading via the `Read` method. `Writer`s support writing via the `Write` method. Many functions in Go take `Reader`s or `Writer`s as arguments. For example the `io` package has a `Copy` function which copies data from a `Reader` to a `Writer`:

```go
func Copy(dst Writer, src Reader) (written int64, err error)
```

To read or write to a `[]byte` or a `string` you can use the `Buffer` struct found in the `bytes` package:

```go
var buf bytes.Buffer
buf.Write([]byte("test"))
```

A `Buffer` doesn't have to be initialized and supports both the `Reader` and `Writer` interfaces. You can convert it into a `[]byte` by calling `buf.Bytes()`. If you only need to read from a string you can also use the `strings.NewReader` function which is more efficient than using a buffer.

## Files & Folders

To open a file in Go use the `Open` function from the `os` package. Here is an example of how to read the contents of a file and display them on the terminal:

```go
package main

import (
  "fmt"
  "os"
)

func main() {
  file, err := os.Open("test.txt")
  if err != nil {
    // handle the error here
    return
  }
  defer file.Close()

  // get the file size
  stat, err := file.Stat()
  if err != nil {
    return
  }
  // read the file
  bs := make([]byte, stat.Size())
  _, err = file.Read(bs)
  if err != nil {
    return
  }

  str := string(bs)
  fmt.Println(str)
}
```

We use `defer file.Close()` right after opening the file to make sure the file is closed as soon as the function completes. Reading files is very common, so there's a shorter way to do this:

```go
package main

import (
  "fmt"
  "io/ioutil"
)

func main() {
  bs, err := ioutil.ReadFile("test.txt")
  if err != nil {
    return
  }
  str := string(bs)
  fmt.Println(str)
}
```

Here is how we can create a file:

```go
package main

import (
  "os"
)

func main() {
  file, err := os.Create("test.txt")
  if err != nil {
    // handle the error here
    return
  }
  defer file.Close()

  file.WriteString("test")
}
```

To get the contents of a directory we use the same `os.Open` function but give it a directory path instead of a file name. Then we call the `Readdir` method:

```go
package main

import (
  "fmt"
  "os"
)

func main() {
  dir, err := os.Open(".")
  if err != nil {
    return
  }
  defer dir.Close()

  fileInfos, err := dir.Readdir(-1)
  if err != nil {
    return
  }
  for _, fi := range fileInfos {
    fmt.Println(fi.Name())
  }
}
```

Often we want to recursively walk a folder (read the folder's contents, all the sub-folders, all the sub-sub-folders, …). To make this easier there's a `Walk` function provided in the `path/filepath` package:

```go
package main

import (
  "fmt"
  "os"
  "path/filepath"
)

func main() {
  filepath.Walk(".", func(path string, info os.FileInfo, err error) error {
    fmt.Println(path)
    return nil
  })
}
```

The function you pass to `Walk` is called for every file and folder in the root folder. (in this case `.`)

## Errors

Go has a built-in type for errors that we have already seen (the `error` type). We can create our own errors by using the `New` function in the `errors` package:

```go
package main

import "errors"

func main() {
  err := errors.New("error message")
}
```

## Containers & Sort

In addition to lists and maps Go has several more collections available underneath the container package. We'll take a look at the `container/list` package as an example.

### List

The `container/list` package implements a doubly-linked list. A linked list is a type of data structure that looks like this:

![img](Asserts/100000000000019000000057111AA314.558518910.png)

Each node of the list contains a value (1, 2, or 3 in this case) and a pointer to the next node. Since this is a doubly-linked list each node will also have pointers to the previous node. This list could be created by this program:

```go
package main

import ("fmt" ; "container/list")

func main() {
  var x list.List
  x.PushBack(1)
  x.PushBack(2)
  x.PushBack(3)

  for e := x.Front(); e != nil; e=e.Next() {
    fmt.Println(e.Value.(int))
  }
}
```

The zero value for a `List` is an empty list (a `*List` can also be created using `list.New`). Values are appended to the list using `PushBack`. We loop over each item in the list by getting the first element, and following all the links until we reach nil.

### Sort

The sort package contains functions for sorting arbitrary data. There are several predefined sorting functions (for slices of ints and floats) Here's an example for how to sort your own data:

```go
package main

import ("fmt" ; "sort")

type Person struct {
  Name string
  Age int
}

type ByName []Person

func (this ByName) Len() int {
  return len(this)
}
func (this ByName) Less(i, j int) bool {
  return this[i].Name < this[j].Name
}
func (this ByName) Swap(i, j int) {
  this[i], this[j] = this[j], this[i]
}

func main() {
  kids := []Person{
    {"Jill",9},
    {"Jack",10},
  }
  sort.Sort(ByName(kids))
  fmt.Println(kids)
}
```

The `Sort` function in `sort` takes a `sort.Interface` and sorts it. The `sort.Interface` requires 3 methods: `Len`, `Less` and `Swap`. To define our own sort we create a new type (`ByName`) and make it equivalent to a slice of what we want to sort. We then define the 3 methods.

Sorting our list of people is then as easy as casting the list into our new type. We could also sort by age by doing this:

```go
type ByAge []Person
func (this ByAge) Len() int {
  return len(this)
}
func (this ByAge) Less(i, j int) bool {
  return this[i].Age < this[j].Age
}
func (this ByAge) Swap(i, j int) {
  this[i], this[j] = this[j], this[i]
}
```

## Hashes & Cryptography

A hash function takes a set of data and reduces it to a smaller fixed size. Hashes are frequently used in programming for everything from looking up data to easily detecting changes. Hash functions in Go are broken into two categories: **cryptographic** and **non-cryptographic**.

The **non-cryptographic** hash functions can be found underneath the hash package and include `adler32`, `crc32`, `crc64` and `fnv`. Here's an example using `crc32`:

```go
package main

import (
  "fmt"
  "hash/crc32"
)

func main() {
  h := crc32.NewIEEE()
  h.Write([]byte("test"))
  v := h.Sum32()
  fmt.Println(v)
}
```

The `crc32` hash object implements the `Writer` interface, so we can write bytes to it like any other `Writer`. Once we've written everything we want we call `Sum32()` to return a `uint32`. A common use for `crc32` is to compare two files. If the `Sum32` value for both files is the same, it's highly likely (though not 100% certain) that the files are the same. If the values are different then the files are definitely not the same:

```go
package main

import (
  "fmt"
  "hash/crc32"
  "io/ioutil"
)

func getHash(filename string) (uint32, error) {
  bs, err := ioutil.ReadFile(filename)
  if err != nil {
    return 0, err
  }
  h := crc32.NewIEEE()
  h.Write(bs)
  return h.Sum32(), nil
}

func main() {
  h1, err := getHash("test1.txt")
  if err != nil {
    return
  }
  h2, err := getHash("test2.txt")
  if err != nil {
    return
  }
  fmt.Println(h1, h2, h1 == h2)
}
```

**Cryptographic** hash functions are similar to their **non-cryptographic** counterparts, but they have the added property of being hard to reverse. Given the cryptographic hash of a set of data, it's extremely difficult to determine what made the hash. These hashes are **often used in security applications**.

One common cryptographic hash function is known as SHA-1. Here's how it is used:

```go
package main

import (
  "fmt"
  "crypto/sha1"
)

func main() {
  h := sha1.New()
  h.Write([]byte("test"))
  bs := h.Sum([]byte{})
  fmt.Println(bs)
}
```

This example is very similar to the `crc32` one, because both `crc32` and `sha1` implement the `hash.Hash` interface. The main difference is that whereas `crc32` computes a 32 bit hash, `sha1` computes a 160 bit hash. There is no native type to represent a 160 bit number, so we use a slice of 20 bytes instead.

## Servers

Writing network servers in Go is very easy. We will first take a look at how to create a TCP server:

```go
package main

import (
  "encoding/gob"
  "fmt"
  "net"
)

func server() {
  // listen on a port
  ln, err := net.Listen("tcp", ":9999")
  if err != nil {
    fmt.Println(err)
    return
  }
  for {
    // accept a connection
    c, err := ln.Accept()
    if err != nil {
      fmt.Println(err)
      continue
    }
    // handle the connection
    go handleServerConnection(c)
  }
}

func handleServerConnection(c net.Conn) {
  // receive the message
  var msg string
  err := gob.NewDecoder(c).Decode(&msg)
  if err != nil {
    fmt.Println(err)
  } else {
    fmt.Println("Received", msg)
  }

  c.Close()
}

func client() {
  // connect to the server
  c, err := net.Dial("tcp", "127.0.0.1:9999")
  if err != nil {
    fmt.Println(err)
    return
  }

  // send the message
  msg := "Hello World"
  fmt.Println("Sending", msg)
  err = gob.NewEncoder(c).Encode(msg)
  if err != nil {
    fmt.Println(err)
  }

  c.Close()
}

func main() {
  go server()
  go client()

  var input string
  fmt.Scanln(&input)
}
```

This example uses the `encoding/gob` package which makes it easy to encode Go values so that other Go programs (or the same Go program in this case) can read them. Additional encodings are available in packages underneath `encoding` (like `encoding/json`) as well as in 3rd party packages. (for example we could use `labix.org/v2/mgo/bson` for bson support)

### HTTP

HTTP servers are even easier to setup and use:

```go
package main

import ("net/http" ; "io")

func hello(res http.ResponseWriter, req *http.Request) {
  res.Header().Set(
    "Content-Type",
    "text/html",
  )
  io.WriteString(
    res,
    `<DOCTYPE html>
<html>
  <head>
      <title>Hello World</title>
  </head>
  <body>
      Hello World!
  </body>
</html>`,
  )
}
func main() {
  http.HandleFunc("/hello", hello)
  http.ListenAndServe(":9000", nil)
}
```

`HandleFunc` handles a URL route (`/hello`) by calling the given function. We can also handle static files by using `FileServer`:

```go
http.Handle(
  "/assets/",
  http.StripPrefix(
    "/assets/",
    http.FileServer(http.Dir("assets")),
  ),
)
```

### RPC

The `net/rpc` (remote procedure call) and `net/rpc/jsonrpc` packages provide an easy way to expose methods so they can be invoked over a network. (rather than just in the program running them)

```go
package main

import (
  "fmt"
  "net"
  "net/rpc"
)

type Server struct {}
func (this *Server) Negate(i int64, reply *int64) error {
  *reply = -i
  return nil
}

func server() {
  rpc.Register(new(Server))
  ln, err := net.Listen("tcp", ":9999")
  if err != nil {
    fmt.Println(err)
    return
  }
  for {
    c, err := ln.Accept()
    if err != nil {
      continue
    }
    go rpc.ServeConn(c)
  }
}
func client() {
  c, err := rpc.Dial("tcp", "127.0.0.1:9999")
  if err != nil {
    fmt.Println(err)
    return
  }
  var result int64
  err = c.Call("Server.Negate", int64(999), &result)
  if err != nil {
    fmt.Println(err)
  } else {
    fmt.Println("Server.Negate(999) =", result)
  }
}
func main() {
  go server()
  go client()

  var input string
  fmt.Scanln(&input)
}
```

This program is similar to the TCP example, except now we created an object to hold all the methods we want to expose and we call the `Negate` method from the client. See the documentation in `net/rpc` for more details.

## Parsing Command Line Arguments

When we invoke a command on the terminal it's possible to pass that command arguments. We've seen this with the `go` command:

```
go run myfile.go
```

run and myfile.go are arguments. We can also pass flags to a command:

```
go run -v myfile.go
```

The flag package allows us to parse arguments and flags sent to our program. Here's an example program that generates a number between 0 and 6. We can change the max value by sending a flag (`-max=100`) to the program:

```go
package main

import ("fmt";"flag";"math/rand")

func main() {
  // Define flags
  maxp := flag.Int("max", 6, "the max value")
  // Parse
  flag.Parse()
  // Generate a number between 0 and max
  fmt.Println(rand.Intn(*maxp))
}
```

Any additional non-flag arguments can be retrieved with `flag.Args()` which returns a `[]string`.

## Synchronization Primitives

The preferred way to handle concurrency and synchronization in Go is through goroutines and channels.However, Go does provide more traditional multithreading routines in the `sync` and `sync/atomic` packages.

### Mutexes

A mutex (mutal exclusive lock) locks a section of code to a single thread at a time and is used to protect shared resources from non-atomic operations. Here is an example of a mutex:

```go
package main

import (
  "fmt"
  "sync"
  "time"
)

func main() {
  m := new(sync.Mutex)

  for i := 0; i < 10; i++ {
    go func(i int) {
      m.Lock()
      fmt.Println(i, "start")
      time.Sleep(time.Second)
      fmt.Println(i, "end")
      m.Unlock()
    }(i)
  }

  var input string
  fmt.Scanln(&input)
}
```

When the mutex (`m`) is locked any other attempt to lock it will block until it is unlocked. Great care should be taken when using mutexes or the synchronization primitives provided in the `sync/atomic` package.

Traditional multithreaded programming is difficult; it's easy to make mistakes and those mistakes are hard to find, since they may depend on a very specific, relatively rare, and difficult to reproduce set of circumstances. One of Go's biggest strengths is that the concurrency features it provides are much easier to understand and use properly than threads and locks.