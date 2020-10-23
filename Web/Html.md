# Hypertext Markup Language(HTML)

## What is HTML

Hypertext Markup Language is the standard markup language for documents designed to be displayed in a web browser. 

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

# HTML Block and Inline Elements

Every HTML element has a default display value, depending on what type of element it is.

There are two display values: block and inline.

## Block-level Elements

A block-level element always starts on a new line and takes up the full width available (stretches out to the left and right as far as it can).

Here are the block-level elements in HTML:

<address> 	<article>		<aside>		<blockquote>		<canvas>		<dd>		<div>		<dl>		<dt><fieldset>		<figcaption>		<figure>		<footer>		<form>		<h1>-<h6>		<header>		<hr><li>		<main>		<nav>		<noscript>		<ol>		<p>		<pre>		<section>		<table>		<tfoot><ul>		<video>

## Inline Elements

An inline element does not start on a new line and it only takes up as much width as necessary.**An inline element cannot contain a block-level element!**

<a>	<abbr><acronym><b><bdo><big><br><button><cite><code><dfn><em><i><img><input><kbd><label><map><object><output><q><samp><script><select><small><span><strong><sub><sup><textarea><time><tt><var>

