# Golang

[toc]

Go is a **static type** language.

Go code is grouped into packages, and packages are grouped into modules. Your package's module specifies the context Go needs to run the code, including the Go version the code is written for and the set of other modules it requires.

## GO CLI

1. `go build` Compiles a bunch of go source code files.
2. `go run` Compiles and executes one or two files.
3. `go fmt` Formats all the code in each file in the current directory.
4. `go install` Compiles and "install" a package.
5. `go get` Downloads the raw source code of other's package.
6. `go test` Runs any tests associated with the current project.

## Pacakage

There are two types of packages.

1. `Executable`: Generates a file that we can run, only`main` is the executable package
2. `Resuable`: Code used as 'helpers'. It's a good place to put resuable logic. Excepted `main`, other self defined package name will be treated as resuable package

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

## Testing

Create a testing with \<name of original Go file>_test.go. And run the test with command `go test`.

