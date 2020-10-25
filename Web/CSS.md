[toc]



# Cascading Syle Sheets(CSS)

CSS is a language for describing how documents are presented visually - how they are arranged and styled

**Basic Pattern**:

```css
selector {
  property: value;
}
```

## **Three ways of Using CSS**

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

