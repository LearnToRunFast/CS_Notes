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

