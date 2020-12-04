[toc]

# Database

There are mainly two types of databases.

| SQL Databases                                                | NO-SQL Databases                                             |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| Structured Query Language databases are relational databases. We pre-define a schema of tables before we intert anything | NoSQL database do not use SQL. There are many types of no-sql databases. Include document, key-value, and graph stores. |

## Mongo DB

**MongoDB** is a cross-platform **document-oriented** database program. Collection is similar to the table on SQL.

### Some Shortcuts

`cmd + k` Clear the windows(For Mac only)

`show dbs` Show to available databases.

`use <db_name>` create or use the database

`db` Current selected database.

### Insert

`db.collection.insert()`

`db.collection.insertOne()`

`db.collection.insertMany()`

### Find

`db.collection.find(query, projection)` return back a cursor to the selected document.

`db.collection.findOne(query, project)` return back the selected document.

### Find Nested Attribute

`db.coolection.find({'firstLevel.secondLevel': attribute_value})`

### Update

`db.collection.upadteOne({name : '...'}, {$set: {age: 4}})`

`db.collection.updateMany()`

### Delete

`db.collection.deleteOne()`



### JSON VS BSON

#### JSON

JavaScript Object Notation, more commonly known as JSON.JavaScript objects are simple associative containers, wherein a string key is mapped to a value (which can be a number, string, function, or even another object). This simple language trait allowed JavaScript objects to be represented remarkably simply in text:

```json
{
 "id": 1,
 "name": "Joe"
 "age": 18
}
```

There are several issues that make JSON less than ideal for usage inside of a database.

1. JSON is a text-based format, and text parsing is very slow
2. JSON’s readable format is far from space-efficient, another database concern
3. JSON only supports a limited number of basic data types

#### BSON

BSON simply stands for “Binary JSON,” and that’s exactly what it was invented to be. BSON’s binary structure encodes type and length information, which allows it to be parsed much more quickly.

|              | **JSON**                       | **BSON**                                                     |
| ------------ | ------------------------------ | ------------------------------------------------------------ |
| Encoding     | UTF-8 String                   | Binary                                                       |
| Data Support | String, Boolean, Number, Array | String, Boolean, Number (Integer, Float, Long, Decimal128...), Array, Date, Raw Binary |
| Readability  | Human and Machine              | Machine Only                                                 |

## Mongoose

```js
const mongoose = require('mongoose');
mongoose.connect('mongodb://localhost:27017/movieApp', {useNewUrlParser:true, useUnifiedTopology: true})
				.then(() => {
  				console.log("connection open");
				})
				.catch(err => {
  				console.log(err);
				})
```

### Schema

```js
// schema 1
const movieSchema = new mongoose.Schema({
	title: {
    type: String,
    required: true,
    maxlength:20
  },
  year: {
    type: Number,
    required: false,
    min:[0, "the year must be greater than 0"] // second argument is validattion msg
  },
  categories: [String],
  isShowing: {
    type: Boolean,
    default: false
  }
});
// schema 2
const movieSchema = new mongoose.Schema({
  title: String,
  year: Number,
  score: Number,
  rating: String
});
```

### Schema Function

#### Instance Function

```js
// adding instance function to schema
movieSchema.methods.updateYear = function (year) {
  this.year = year;
  console.log(`- from ${this.title}`);
  return this.save();
}
```

#### Static Function

```js
// adding static function to schema
movieSchema.statics.nowShowing = function () {
  return this.updateMany({}, {isShowing: true});
}
```

#### Virtual

```js
// virtual
personSchema.virtual('fullName').get(function() {
  return `${this.firstName} ${this.lastName}`;
})
```

### Model

```js
// model
const Movie = mongoose.model('Movie', movieSchema);
```

### Insert

```js
// movie object
const movie = new Movie({title: "name", year:1986, score:9.2, rating: "good"});
// insert
movie.save();
// insert
Movie.insertMany([
  {}, //movie object 1
  {} // movie object 2
])
```

### Find

```js
// find
Movie.find({rating: 'PG-13'}). then(data => console.log(data))
```

### Pre and Post Middleware

```js
var personSchema;
personSchema.pre('save', async function() {
  console.log("before save");
})
personSchema.post('save', async function() {
  console.log("after save");
})
```

