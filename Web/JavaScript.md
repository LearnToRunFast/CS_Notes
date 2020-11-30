[toc]



# Javascript

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

## Class Setter

```javascript
const language = {
  set current(name) {
    this.log.push(name);
  },
  log: []
};
language.current = 'EN';
language.current = 'FA';

console.log(language.log);
// expected output: Array ["EN", "FA"]
```

## Prototypes

Prototypes are the mechanism by which JavaScript objects inherit features from one another.

In JavaScript, every objects come with attributes called `__proto__` which refers to that object's prototype property.

Protoypes of Array in JavaScript:

```js
Array.prototype  // are the list of functions that you can access it from any object of array

const arr = [1,2,3]
arr.push(4) // push is defined in Array.prototype

// we can add method to Array object
Array.prototype.yell = function() {
  console.log("yell at you")
}
// now you can access it
arr.yell();
```

## New Keyword

```js
function Car(make, model, year) {
  this.make = make;
  this.model = model;
  this.year = year;
}
Car.prototype.getModel = function() {
  const { model } = this; // deconstruct
  return model; 
}
// the steps to new keyword
// 1. Creates a blank, plain JavScript object
// 2. link (sets the constructor of) this object to another object
// 3. passes the newly created object from step 1 as the this context
// 4. return this if the function doesn't return it's own object
const newCar = new Car("China", "01", "2020");

```

## JavaScript Class

```js
class Car {
  // constrcutor will run for every new keyword
  constructor(make, model , year) {
    this.make = make;
    this.model = model;
    this.year = year;
  }
  getModel() {
    return this.model;
  }
}
```

## Super and Extend

```js
class Pet {
  constructor(name, age) {
    this.name = name;
    this.age = age;
  }
  ear() {
    return `${this.name} is eating`; 
  }
}
// will extend the __proto__ from it's parent
class Cat extends Pet {
  constructor(name, age, livesLeft = 9) {
    // will call the parent constructor
    super(name, age);
    this.livesLeft = livesLeft;
  }
	meow() {
    return "MEOWWWW!";
  }
}

```

# Document Object Model(DOM)

The `Document` object is the entry point of DOM. It contains representations of all the content on a page, plus tons of useful methods and properties.

## Document

### Selection

#### Select by Id

```javascript
document.getElementById("item_id");
```

#### Select by Tag Name

```javascript
document.getElementsByTagName("img");
```

#### Select by Class Name

```javascript
document.getElementsByClassName("item_id");
```

### Query Selector

#### Selector

```javascript
document.querySelector('#banner'); // first id with banner
document.querySelector('.square'); // first class name with square
document.querySelector('img:nth-of-type(2)'); // second element of image
document.querySelector('a[title="java"]');
document.querySelectorAll('p a'); 

console.dir() // to see all the attributes
```

#### Attribute

```javascript
document.querySelector('p').innerHTML
document.querySelector('p').innerText
```

#### Style

```javascript
document.querySelector('p').style.color // change style
```

#### ClassList

```javascript
document.querySelector('p').classList.add("class1");
document.querySelector('p').classList.remove("class1");
document.querySelector('p').classList.toggle("class1"); // if has class1 remove it, else add it
```

#### Parent, Children and Sibling

```javascript
let a = document.querySelector('p');
let parent = a.parentElement;
let children = a.children;
let prevSibling = a.previousElementSibling; // actual element
prevSibling = a.previousSibling; //DOM node

let nextSibling = a.nextElementSibling; // actual element
nextSibling = a.nextSibling; // DOM node
```

### Create New Element

#### Child

```javascript
let img = document.createElement('img');
img.src = "http://...";
img.classList.add('square');

// add to as child
// way 1 to add to document
document.body.appendChild(img);

// way 2
ParentNode.prepend(); //add as first child
ParentNode.append(); //add as last child
```

#### Sibing

```javascript
let h2 = document.createElement('h2');
let h1 = document.querySelector('h1');
// position can be
// 'beforebegin': Before the targetElement itself.
// 'afterbegin': Just inside the targetElement, before its first child.
// 'beforeend': Just inside the targetElement, after its last child.
// 'afterend': After the targetElement itself.
h1.insertAdjacentElement(position, h2);

// way 2
h1.after(h2);
h1.before(h2);
```

## Window

The stype value of the html could not be found in document(the value queryis empty). We could use window object to access it.

```javascript
window.getComputedStyle(h1).color
```

## Event

### Event Listener

```javascript
btn.addEventListener("click", function() {}, {once: true}); // can add multiple callback
```

### Click Event

```javascript
btn.addEventListener("click", function(event) { //event object is come with javascript
  console.log(event)
});
```

### Key Event

```javascript
btn.addEventListener("keydown", function(event) { //event object is come with javascript
  console.log(event.key);  // value of the key value
  console.log(event.code); // postion of the key
});
```

### Form Event

```javascript
const form = document.querySelector("#form");
form.addEventListener("submit", function (event) {
  event.preventDefault(); // prevent default action of the submit form action
   // username here is  the name attribute value of the element inside the form.
  console.log(this.elements.username.value);
})
```

### Change Event

```javascript
const input = document.querySelector('input');
input.addEventListener('change', function (e) {
  	// only fire when lose focus on cursor.
})
```

### Input Event

```javascript
const input = document.querySelector('input');
input.addEventListener('input', function (e) {
  	 console.log(input.value);
  	// every letter change counts
})
```

### Event Bubbling

The event will be bublle up to parent level and trigger same type parent event if any.To prevent such bahaviour, we use `stopPropagation()` to stop it.

```javascript
input.addEventListener('input', function (event) {
	event.stopPropagation();
})
```

### Event Delegation

Elements were created dynamically by javascript does not have any **event listener**.To solve this issue, we can implement the event listener at their parent level and accessing them by `event.target`.

```javascript
// container is the parent of tag li
container.addEventListener('click', function(event) {
  // ensure the type is the one you are trying to remove
   event.target.nodeName === "LI" && event.target.remove();
})
```

## Promises

To reduce the complexity of callback function.

```javascript
const delayedColorChange = (color, delay) => {
  return new Promise((resolve, reject) => {
    setTimeout(() => {
      document.body.style.backgroundColor = color;
      resolve();
    }, delay);
  });
}

delayedColorChange('red', 1000)
	.then(() => delayedColorChange('orange', 1000))
	.then(() => {
  		return delayedColorChange('blue', 1000)}) // this is same as one line return
	.then(() => delayedColorChange('yellow', 1000))		
```

## Async & Await

### Async

Async function will always return a promise.

```javascript
// any error happened inside an async function will result to promise rejected status.
const login = async (username, password) => {
  if (!username || !password) throw "Miss Credentials";
  if (password == "correctPassword") return "Welcome";
  throw "Invalid Password"
}

login("fhjdksh", "correctPassword")
	.then(msg => {
  	console.log("Logged In");
	})
	.catch(err => {
  	console.log("error");
	})
```

### Await

Wait for promise to be resolved.

```javascript
async function rainbow() {
	await delayedColorChange('red', 1000);
	await delayedColorChange('orange', 1000);
  return "Done";
}
```

## AJAX

```javascript
fetch('http://...')
	.then(res => {
		return res.json(); // promise object
	})
	.then(data => {
		console.(data);
	})
	.catch(e => {
		
	})


// second way
const fetchPrice = async () => {
  const res = await fetch("http://...");
  const data = await.res.json();
  console.log(data.ticker.price);
}
```

## Axios

```javascript
const getJoke = async () => {
  const config = { params: {q: "searchTerm"}, headers : { Accept: 'application/json'}};
  const res = await axios.get("https://...");
  console.log(res.data.joke);
}
```

# Node JS

## Program Argument

```js
const args = process.argv;
// first argument is the node path
// second argument is current script location
// third onwards are the arguments passed in.
```

## Module

```js
// DIY math library
const add = (x, y) => x + y;
const PI = 3.14159;
const sqaure = x => x * x;
module.exports.add = add;
module.exports.PI = PI;
module.exports.square = square;
exports.add = add; // shortcut
// other js file can use the DIY math library on top by
const math = require("./math"); // assume the math library located on same folder.
```

### Index.js

Index.js is the main file or entry file for Node JS.

## NPM

Node library management tool.

### Some Fun Libraries

- Colors: Color your text
- Figlet: Making a 2D text

### Create a Package.json

```zsh
npm init
```

### ^ and ~ in Package.json

- `~version` **“Approximately equivalent to version”**, will update you to all future patch versions, without incrementing the minor version. `~1.2.3` will use releases from 1.2.3 to <1.3.0.

- `^version` **“Compatible with version”**, will update you to all future minor/patch versions, without incrementing the major version. `^2.3.4` will use releases from 2.3.4 to <3.0.0.

### Install Dependencies

Install the dependencies of the project base on package.json file

```zsh
npm install
```

### Auto Restart Server

```zsh
npm install nodemon
nodemob <node_app_name>
```

## Express

```js
const express = require("express")
const app = express()

// general method
// this method will run for every request
app.use((req, res) => {
  console.log("...")
  console.dir(req)
})

// set view engine
app.set('view engine', 'ejs')

// root route
app.get('/', (req, res) => {
  res.send("This is home page")
})

// match route
app.get("/r/:subreddit", (req, res) => {
  const { subreddit } = req.params;
  res.send("this is match any thing with /r/...")
})

// query string
app.get("/search", (req, res) => {
	const { q } = req.query;
  res.send("the query string is ${q}")
})

app.listen(8080, () => {
  console.log("Listening on port 8080")
})


```

### Express Middleware

```js
// app.use will be called for every request
app.use((req, res, next) => {
	consolo.log("this is my first middleware");
  // now you can access req.requestTime for every request
  req.requestTime = Date.now();
  console.log(req.method, req.path);
  
  // if we dont return here, it will continue to run after invocation of next function
  // next will be the routing method
	return next();
})
```

#### Specify Route with Middleware

```js
// this will get called for every same path
app.get('/dogs', (req, res, next) => {
  consolo.log("this will only run with same path")
})
```

#### 404 Path

```js
// this should be place just before app.listen
app.use((req, res) => {
  res.status(404).send("NOT FOUND!");
})
```

#### Multiple Callback

```js
const verifyPassword = (req, res, next) => {
  	const { password } = req.query;
  if (password === 'realpassword') {
    next();
  }
  res.send("sorry, you have to enter correct passwordd");
}
const secondCallBack = (req, res) => {
  res.send("My secrect is ...");
}
// now the next() in verify password refer to secondCallBack
// the secondCallBack will only can be run if there is next() execution on firstCallBack
app.get('/secrect', verifyPassword, secondCallBack);
```



## Templating With EJS

```js
const express = require("express")
const path = require("path")
const app = express()

app.set('view engine', 'ejs')
// relative path of template
app.set('views', path.join(__dirname,'/views'))

// enable access parammeter at req.body
app.use(express.urlencoded({extended: true}))
app.use(express.json())

// with this, now template can access the file inside public folder
app.use(express.static(path.join(__dirname,'/public'))) //static resource

app.get('/', (req, res) => {
  res.render('home') // home located at ./views/home.js
})

app.get('/rand', (req, res) => {
  const num = Math.floor(Math.random() * 10 + 1)
  res.render('random', {num}) // short form
  res.render('random', {num: num}) // pass data to template
})
```

### Template

```ejs
<!--ejs syntax -->

<!-- render -->
<%= num %>  

<!-- if else -->
 <% if (num % 2 === 0) { %>
 <h2> This is Even</h2>
<% } else { %>
<h2>this is odd</h2>
<% } %>

<!-- ternary -->
<h3>That number is: <%= num%2 === 0 ? 'even' : 'odd' %></h3>

<!-- for -->
<% for(let cat of cats) { %>
<li><%= cat %></li>
<% } %>

<!-- include partial template(reusable parts) -->
<%- include('partials/head') %>
```

## Form

### REST API

General routes for frontend to deal with REST API

| Name    | Path               | Verb   | Purpose                            |
| ------- | ------------------ | ------ | ---------------------------------- |
| Index   | /comments          | GET    | Display all comments               |
| New     | /comments/new      | GET    | Form to create new comment         |
| Create  | /comments          | POST   | Creates new comment on server      |
| Show    | /comments/:id      | GET    | Details for one specific comment   |
| Edit    | /comments/:id/edit | HET    | Form to edit specific comment      |
| Update  | /comments/:id      | PATCH  | Updates specific comment on server |
| Destory | /comments/:id      | DELETE | Delete specific item on server     |

### Form With Update

```js
// HTML form only support method of get and post
// to overcome it, use method-override package
var methodOverride = require('method-override')
var app = express()
app.use(methodOverride('_method'))

// on html
<form method="post" action"/comments/<%=comment.id%>?_method=PATCH">
```





