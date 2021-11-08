# CSS trick

## CSS Design Pattern

### Even Columns

```scss
.even-columms {
  display: flex;
  /* 1em gap for all direction */
  gap:1em;
}
.even-columns > * {
  /* now evey columns will have same width */
  flex-basis: 100%
}
```

### Grid-ish

```scss
.grid-ish {
	display:flex;
	flex-wrap:wrap;
}

.grid-ish > * {
  /* each item will be 10em, if not enough
    room, it will wrap to next row*/
	flex: 1 1 10em;
}
```

### Content and sidebar

```scss
.content-sidebar {
  display: flex;
  flex-wrap: wrap;
}
/* this can be side bar,
it will become top nav bar if got enough space
*/
.content-sidebar > *:nth-child(1) {
  flex: 1 1 30%;
  min-width: 15ch;
}
/* this is main content */
.content-sidebar > *:nth-child(2) {
  flex: 1 1 70%;
  min-width: 30ch;
}
```

### Apply Margin If it has left Siblings

```scss
.container > * + * {
	margin-left:2rem;
}
```

