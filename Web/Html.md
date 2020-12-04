# Hypertext Markup Language(HTML)

[toc]

## What is HTML

Hypertext Markup Language is the standard markup language for documents designed to be displayed in a web browser. 

Use **Emmet** to speed up the html development.

## Basic Structure

A standard **HTML** structure consists one `html`tag which include `head` and `body` tags inside it

```html
<!DOCTYPE html> <!-- Document type -->
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
</head>
<body>
    
</body>
</html>
```

## Common Tags

1. `<h1>` to `<h6>` are header tag for the HTML.
2. `<p>` Display text in one block (paragraph)
3. `<ol>` and `<ul` are stand for order list and unorder list
   - `<li>` stand for list which is always inside `<ol>` or `<ul>`
4. `<a>`Anchor element, used to create hyperlink
   - `<a href="home.html">Home</a>`, `href` called attributes for `<a>` which usually stores a local location(relateve path) or website link. Clicking on Home will redirect me to **home.html**
5. `<img>`Image tag, display image from local machine or from website.
   - `<img src="example.png" alt="this is example image">`, `src` will indicates the path for this image(either local or from website).Content of `alt` will displayed on the html when image path is unavailable.
6. `<hr>`stands for Horizontal Rule. Draw a line in between two elements.
7. `<br>`stands for Line Break. Break the current text and start a new line.
8. `<sub>` and `<sup>` stand for subscript and superyscript respectively.
   - b<sup>c</sup><sub>a</sub> where `a` is a subscript and `c` is a superscript

## HTML Block and Inline Elements

Every HTML element has a default display value, depending on what type of element it is.

There are two display values: block and inline.

## Block-level Elements

A block-level element always starts on a new line and takes up the full width available (stretches out to the left and right as far as it can).

Here are the block-level elements in HTML:

```
<p>		<dd>		<dl>		<dt>		<hr>		<li>		<ol>		<ul>		<div>		<nav>		<pre>	<form>		<main>		<aside>		<table>		<tfoot>		<video>		<canvas>		<figure>		<footer>		<header>		<section>		<address> 	<article>		<h1>-<h6>		<fieldset>				    <noscript>		<blockquote>		<figcaption>	
```

## Inline Elements

An inline element does not start on a new line and it only takes up as much width as necessary.**An inline element cannot contain a block-level element!**

Here are the inline elements in HTML:

```
<a>		<b>		<i>		<q>		<br>		<em>		<tt>		<bdo>		<dfn>		<big>		<img>		<kbd>		<map>	<sub>		<sup>		<var>		<abbr>		<cite>			<code>		<samp>		<span>		<time>		<input>		<label>		<small>		<button>		<object>		<output>		<strong>		<select>	<acronym>			<textarea>		<script>
```

## HTML Entities

Some characters are reserved in HTML. Reserved characters in HTML must be replaced with HTML entities. 

Character entities are used to display reserved characters in HTML.

Some useful HTML character entities:

| Result | Description                        | Entity Name | Entity Number |
| :----- | :--------------------------------- | :---------- | :------------ |
|        | non-breaking space                 | \&nbsp;     | \&#160;       |
| <      | less than                          | \&lt;       | \&#60;        |
| >      | greater than                       | \&gt;       | \&#62;        |
| &      | ampersand                          | \&amp;      | \&#38;        |
| "      | double quotation mark              | \&quot;     | \&#34;        |
| '      | single quotation mark (apostrophe) | \&apos;     | \&#39;        |
| ¢      | cent                               | \&cent;     | \&#162;       |
| £      | pound                              | \&pound;    | \&#163;       |
| ¥      | yen                                | \&yen;      | \&#165;       |
| €      | euro                               | \&euro;     | \&#8364;      |
| ©      | copyright                          | \&copy;     | \&#169;       |
| ®      | registered trademark               | \&reg;      | \&#174;       |

## Semantic Markup

A semantic element clearly describes its meaning to both the browser and the developer to improve readerbility and accessbility.

Examples of **non-semantic** elements: `<div>` and `<span>` - Tells nothing about its content.

Examples of **semantic** elements: `<form>`, `<table>`, and `<article>` - Clearly defines its content.

In HTML there are some semantic elements that can be used to define different parts of a web page :  

| Tag        | Description                                                  |
| :--------- | :----------------------------------------------------------- |
| \<article> | Defines independent, self-contained content                  |
| \<aside>   | Defines content aside from the page content                  |
| \<details> | Defines additional details that the user can view or hide    |
| \<figure>  | Defines a caption for a \<figure> element                    |
| \<figure>  | Specifies self-contained content, like illustrations, diagrams, photos, code listings, etc. |
| \<footer>  | Defines a footer for a document or section                   |
| \<header>  | Specifies a header for a document or section                 |
| \<main>    | Specifies the main content of a document                     |
| \<mark>    | Defines marked/highlighted text                              |
| \<nav>     | Defines navigation links                                     |
| \<section> | Defines a section in a document                              |
| \<summary> | Defines a visible heading for a \<details> element           |
| \<time>    | Defines a date/time                                          |

![HTML Semantic Elements](Asserts/img_sem_elements-20201023175832740.gif)

## HTML Table

Tables `<table> </table>`are structured sets of data, made up of rows and columns.They can be a great way of displaying data clearly.

| Tag         | Description                                                  |
| :---------- | :----------------------------------------------------------- |
| \<table>    | Defines a table                                              |
| \<th>       | Defines a header cell in a table                             |
| \<tr>       | Defines a row in a table                                     |
| \<td>       | Defines a cell in a table                                    |
| \<caption>  | Defines a table caption                                      |
| \<colgroup> | Specifies a group of one or more columns in a table for formatting |
| \<col>      | Specifies column properties for each column within a \<colgroup> element |
| \<thead>    | Groups the header content in a table                         |
| \<tbody>    | Groups the body content in a table                           |
| \<tfoot>    | Groups the footer content in a table                         |

```html
<table>
  <thead>
    <tr> 
      <th></th>
    </tr>
  </thead>
  <tbody>
    <tr> 
      <td> </td>
    </tr>
  </tbody>
</table>
```

### Attributes

1. `rowspan`, `<th rowspan="2"`specifies row take up 2 row length.
2. `copspan`,`<th colspan="2"` specifies column take up 2 column length.

## HTML Form

An HTML form `<form> </form>`  is used to collect user input. It is a container for different types of input elements, such as: text fields, checkboxes, radio buttons, submit buttons, etc.

### Attributes

1. `action`,`<form action="/search/">` decides the destination after submit of the input.

### The \<input> Element

The HTML `<input>` element is the most used form element.

An `<input>` element can be displayed in many ways, depending on the `type` attribute.

Here are some examples:

| Type                     | Description                                                  |
| :----------------------- | :----------------------------------------------------------- |
| \<input type="text">     | Displays a single-line text input field                      |
| \<input type="radio">    | Displays a radio button (for selecting one of many choices)  |
| \<input type="checkbox"> | Displays a checkbox (for selecting zero or more of many choices) |
| \<input type="submit">   | Displays a submit button (for submitting the form)           |
| \<input type="button">   | Displays a clickable button                                  |
| \<input type="password"> | Display a text input field for password                      |

#### Grouping Radio Input

If the name attibute of radio have the same values, they are by default belong to one group.

#### Default Checked Input 

`<input type="checkbox" name="agree" id="agree" checked>` Simply put checked at tail.

#### Default Selected Input 

```html
<select name="meal" id="meal">
  <option value="fish" selected>Fish</optional>
  <option value="steak">Steak</option>
</select>
```

#### Range Input Example

```html
<section>
	<label for="price">Price:</label>
  <input type="range" id="price" min="1" max="100" value="70" name="price" step="2">
</section>
```

#### Attributes

1. `Placeholder`, `<intput placeholder="Enter your text">` messages Hint for user.
2. `type`,` <input  type="text"> ` indicates the type of the input.
3. `name`, `<input name="cheese">` indicates the name of the input, it use inside the query.
4. `id`,`<input id="cheese">` indicates the id of the input and the id must be **unique** within the page.
5. `value`,`<input value="value">` indicates the value of the input, especially useful to **distinguish which radio input was submitted**.

### The \<label> Element

The HTML `<label> </label>` element represents a caption for an item in a user interface.

#### Attributes

1. `for`,`<label for="cheese">` indicates the label is belongs to one element with **id=cheese**

### The \<button> Element

The HTML `<button> </button>` element represents a clickable button.

#### Attributes

1. `type`, `<button type="button">` indicates type of the button, as **button** type will not submit the form

## Class Attribute

You can specify one element with two classes separated with a space and use `.firstClassName.secondClassName` to in CSS to style it.

