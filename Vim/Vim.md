# Vim

## Vim Modes

- **Normal**: for moving around a file and making edits
- **Insert**: for inserting text
- **Replace**: for replacing text
- **Visual** (plain, line, or block): for selecting blocks of text
- **Command-line**: for running a command

## Comand Line

`:q` close current window

`:w` save current window

`:wq` save and quit current window

`:wq!` force save and quit

`:e {name of file}` open file for editing

`:ls` show open buffers

`:help {topic}` open help

- ``:help :w` opens help for the `:w` command

- `:help w` opens help for the `w` movement

## Mode Operation

`i` Insert mode.

`Esc` exit insert mode and enter normal mode.

`:` In normal mode with `:` will enter command line mode.

`v` start virsual mode at current cursor

`V` start virsual mode at linewise

## Navigation

`h` `j` `k` `l` corresponding to left, down, up and right

`*` Move to function declaration or go back

`opt + up arrow` Move current line up to one line

`gd`: find function defination

### Move by word

`w` next beginning of the word 

`b` previous beginning of the word

`e` next end of the word

### Move by line

`0` beginning of the line

`^` fist non-blank character of the line

`$` end of the line

### Move by Screen

`H` top of screen

`M` middle of screen

`L` bottom of screen

### Move by Scrolling

`Ctrl-b` scroll up one page

`Ctrl-f` scroll down one page

`Ctrl-u` scroll up half page

`Ctrl-d` scroll down half page

### Move by File

`gg` beginning of the gile

`G` end of the file

###  Move by Find and Search

Find: `f{character}`, `t{character}`, `F{character}`, `T{character}`

- find/to forward/backward {character} on the current line

- `,` / `;` for navigating matches

Search: `/{regex}`, `?{regex}`

- search forward/backward
- `n` / `N` for navigating matches

## Insertion

`i` insert mode

`I` to current line of non blank(head) and enter intert mode

`a` insert mode after current cursor

`A` insert at the last area of current line

`o` insert to next line

`O` insert to previous line

## Modifiers

You can use `d` instead of `c` here

`ca(` cut all the stuff inside the parentheses(included)

`ci(` cut all the stuff inside the parentheses(non-inclued)

`ciw` cut the word of current cursor

## Copy

`y` copy

`yy` copy current line

## Delete

`dd` delete current line

`s` delete current character and insert mode.

`x` delete current character

## Paste

`P` paste before cursor(Cat)

`p` paste after cursor

## Replacement

### Flags

`/g` apply it to all

`/i` case insensitive

`/c` confirmation with replacement

`:s/old/new` replace first old with new in current line

`:s/old/new/g` replace all olds with new in current line

`%s/old/new/g` replace domain become global instead of current line

`%s/^/new/gc` add new to the head of every lines and ask for confirmation.

`%s/a$/new/gi` replace ending with `a or A` with new in the document.

## Others

`ddp` swap current line with next line

`%` corresponding item, eg match `()`

`u` redo

`Ctrl-R` undo

``` `` ``` the backquote go back to column of previous cursor

`''` go back to line of the previous cursor

### Indentation

`<<` indent left.  `>>` indent right

### Multi Windows

`:vs %` open current doc at second window

`:vs <filepath>` open the new window at side

`:sv <filepath>` open the new window in bottom

`ctrl-w b`   previous window

`ctrl-w w`   next window

`ctrl-w h`   left window

`ctrl-w l`   right window

`ctrl-w j`   bottom window

`ctrl-w k`   top window

