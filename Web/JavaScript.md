

# Javascript && DOM

## This

```javascript
const person = {
  firstName: "Viggo",
  lastName: "Mortensen",
  // this will return correct result as 'this' refer to person who created function
  fullName: function() { 
    return `${this.firstName} ${this.lastName}`;
  },
  // in this case,  'this' will refer to window object.
  fullName1: () => { 
    return `${this.firstName} ${this.lastName}`;
  },
    // in this case,  'this' will refer to person object.
  shoutName: function() {
    setTimeout(()=> {
      console.log(this);
      console.log(this.fullName())
    }, 3000);
  }
  
  // in this case,  'this' will refer to window object.
  shoutName: function() {
    setTimeout(function() {
      console.log(this);
      console.log(this.fullName())
    }, 3000);
  }
}
```

## Default Params & Spread

### Default Params

```java
function person(firstName, lastName="joe"){}
```

### Spread

```javascript
// with function
let nums = [1,2,3,4,5,6];
Math.max(nums) // will output NaN
Math.max(...nums) // output 6, ... convert array into individual num, like unpack in python

// with array
const nums1 = [1, 2, 3];
const num2 = [4,5, 6];

const num3 = [...nums1, ...num2] // make a copy and copy over


// with object
const person = {
  name: "abc",
  age: 18
}

const person1 = {
  name: "hbc",
	hobit: "movie"
}

const newPerson = {...person, ...person1} // 'hbc' will overwrite 'abc'

const ojb = {...[2,3,5,6]} // convert array into obj

const obj1 = {..."HELLO"} // convert string into obj

const newPerson1 = {...person, id: 1, hobit: "sing"} // new person with extra attribute
```

## Rest Params

In, Javascript argument object holds all the arguments pass through function.

```javascript
function sum(...nums) {
  return nums.reduce((total, n) => total + n);
}

```

## Array, Object, Function Param Destructuring 

### Array Destructuring

```javascript
const nums = [1, 2, 3];
const [first, second] = nums;
const [first, second, thid] = nums;
const[first, ...everyElse] = nums;
```

### Object Destructuring

```javascript
const user = {
  email: '111@gmail.com',
  username: "joe",
  password: '123'
}
const {email, username} = user; // with exact name
const  {email :myEmailName} = user; // myEmailName = '111@gmail.com'

```

### Function Param Destructuring

```javascript
const user = {
  firstName: "John",
  lastName: "snow"
}

function fullName(user) {
  // normal way 1
  console.log(`${user.firstName} ${user.lastName}`);
  
  // normal way 2
  const {firstName, lastName} = user;
  console.log(`${firstName} ${lastName}`)
}

// destructuring
function fullName({firstName, lastName}) {
  console.log(`${firstName} ${lastName}`)
}
```

# DOM