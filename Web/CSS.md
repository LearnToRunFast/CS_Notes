[toc]



# Cascading Syle Sheets(CSS)

CSS is a language for describing how documents are presented visually - how they are arranged and styled

**Basic Pattern**:

```css
selector {
  property: value;
}
```

## Three ways of Using CSS

1. Inline style

   - `style="color: blue"`

2. The style element

   - Write `<style> </style> `**element** inside the head.

3. External Stylesheet

   - Write the styles **in a .css file**, and then include the using a \<link> in the head.

     ```html
     <head>
       <link rel="stylesheet" href="my_styles.css">
     </head>
     
     ```

## Color

For the colours, there are few ways to show the color.

1. Use predefined color name
   - `color: red`
2. Use RGB function
   - `color:rgb(0,1,2)` which stand for red = 0, green = 1and blue = 2
3. Use Hexadecimal representation
   - `color: #ff1100` which red=ff, green=11 and blue=00

## Text

```css
h1 {
  text-align: center; /* text alignment */
  font-weight: 400;  /*400 is normal, make text bold or unbold*/
  text-decoration: blue underline; /* one blue underline will show up for the text */
  line-height: 2; /* line space in between of two line */
  letter-spacing: 10px; /* spacing between two letter */
  font-size: 1px; /* font size */
  font-family: serif, Futura; /* change the default font, Futura as backup font*/
}
```

## Selector

### Element Selector

```css
h1, h2 {
 	color: black;
}
```

### ID Selector

```css
#signup { /*sign up is id name */
  background-color: blue;
}
```

### Class Selector

```css
.complete { /*select elements with class of 'complete' */
	background-color: blue;
}
```

### Descendant Selector

```css
li a { /*all a inside li */
  background-color: blue;
}
```

### Adjacent Selector

```css
h1 + p { /* parapgrah after h1 */
  background-color: blue;
}
```

### Direct Child Selector

```css
div > li { /* li direct child(one level down) in the div */
  background-color: blue;
}
```

### Attribute Selector

```css
section[value="post"] { /* attribute 'value' in section is post */
  background-color: blue;
}
/* it also applied to *=, *= means contains. $= end with */
```

### Pseudo Classes

Keyword added to a selector that specifies a special state of the selcted element

- `:active`
- `:checked`
- `:fist`
- `:first-child`
- `:hover`
- `:not()`
- `:nth-child()`
- `:nth-of-type()` 

```css
a:hover {
	color: orange;
}
a:nth-of-type(2n) { /*    even number */
}
```

###  Pseudo Elements

- `::first-letter`
- `::first-line`
- `::selection`
- `::before`
- `::after`

## Overwrite Rules

### Orders

The order of the styles are top to bottom order.

### Specificity

When there is multiple rules could apply to the same element, the more specific selector "wins".

ID > CLASS > ELEMENT, one ID = ten CLASSes = 100 ELEMENTs

**Note**:`!important`  is alway the most weighted style.

### Inheritance

The CSS follows Inheritance rules which rules apply to parent will affect the chilren too.But some of them are not affected by inheritance.

## The CSS Properties

### Border Properties

- `border:` Specify the width | style | color in one line.
  - `border-width` Control the thickness of the border
    - `border-left-width`
  - `border-color` Control the color of the border
    - `border-left-color`
  - `border-style` Control the line style of the border
  - `border-sizing: border-box` Include no extra weight and height for border

- `border-radius: 30%`: Rounded cornor for the border
  - `border-radius:top-left top-right bottom-right bottom-left`

### Padding

The gap area between border and content.

- `padding` : vertical | horizontal,  top | right | bottom | left

### Margin

The gaps between elements.

- `margin` : vertical | horizontal,  top | right | bottom | left

### Display Properties

Allow change the inline to block element and vice verse. By default, the inline the the padding or margin will not work as expected.

- `display: inline` or `display: block`
- `display:inline-block` Helps the inline to take effect with padding and margin. Same to block.

### Alpha Channel

`rgba(255, 255, 255, 0.7)` 0.7 is called alpha channel and is only affect background color.

`#FFFFFFb9` is another way to change the transparent.

### Opacity

Opacaity will change the all the elements become transparent. eg. `opacity: 0.5`

### Position

The position property sets how an element is positioned in a document.

#### Static

The position is static, static is default option.

#### Relative

Use `top`, `left`, `right`, `bottom` move the element.

#### Absolute

The element is removed from the normal document flow, and no space is created for the element in the page layout.

#### Fixed

It similar to 'absolute' to root container, normally use it to create a floating effect. eg a nav bar that can move down when scrolling down.

### Transitions

Use to change the transition delay.

It follows a order: Name | Duration | Timing Function | Delay

- `transition: 3s` Change all
- `transition: background-color 3s` Only background color only

### Transform

Move the element.

`transform: rotate(45deg)`

`transform: scale(0.5)`

`transform: translateX(200px)` Move to right 200px

### Background

`background: center/cover url("")`

- `background-size`
- `background-image`
- `background-position`

### Google Fonts



## CSS Units

### Relative

- **Percentages**
- **em**
  - With font-size, 1em equals the font-size of the parent. 2em is double.
  - With other properties, 1em is equal to the computed font-size of the element itself.
- **rem**: 1rem equals to the font-size of the root element.

## 