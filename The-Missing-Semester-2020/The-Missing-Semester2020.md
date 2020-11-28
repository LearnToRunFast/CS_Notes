[toc]

# MIT: missing-semesters(2020)

## Shell

### Environment Path

The environment path stores all the runnable command path of the system. Eg. `echo` is in the environment path so that we can use it directly inside the shell.

To find out the location, type `which echo`.

#### Environment Variable $PATH

All environment paths is stored inside a global variable called `PATH` and all the paths are seperated by `:`. To see the content of the it by typing `$PATH`.

#### Add Path

```bash
export PATH=$PATH:/place/with/the/file # add the new path to the tail of PATH
# or
export PATH=/place/with/the/file:$PATH # add the new path to the head of PATH
```

### Shebang

```sh
#!/usr/bin/env zsh #more dynamic
#!/usr/bin/zsh  #Actual path
```

By indicate the first line of this, we tell the shell the default Interpretor for this script.

### Checking Scripts

```bash
shellcheck mcd.sh	
```

### Symbol

```bash
missing:~$ 
```

`~` short for home dirctory

The `$` tells you that you are not the root user (more on that later).

`#` tell you that you are the root user.

`$PATH` refer to environment path, any path inside it can be directly executed.

### Navigation

`cd` change dirctory

`.` stand for current dictory

`..` stand for previous dictory

`-` prev visited dictory

### Common Commands

`man` instruction manual page, eg `man ls` will show you how to use `ls`

`date` show the current datetime

`echo` Like print in other language

`which` tell you the path of the your argument, eg `which echo` will tell you the path of echo program is located.

`pwd` current working dictory in full path.

`ls` list files on current dirctory

- `ls -l` all files with more detail(permissions)
- `ls -al` all files include hidden files.

`cat` read file and output it

`diff` check different between two files

`Ctrl + l` clear the terminal

### Permission

```bash
missing:~$ ls -l /home
drwxr-xr-x 1 missing  users  4096 Jun 15  2019 missing
```

`d` stand for dictory.

`r` read permission

`w` write permission

`x` execute permission

`-` no permission

```
rwx r-x r-x
```

The next 9 letters break down into three parts, they are corresponding to perssmision for

`owner` rwx

`group` r-x

`any one else` r-x

### Change Permission

`chmod` change permission of the file, applying multiple rules can be seperated by `,`.

- `+` add permission, eg `chmod +x ./1.txt` add execution permission for `1.txt` to all user
- `-` remove permission
- `=` recall previous permission and apply current rules.
- `-R`  apply to subfolders
- `a` all user, eg `chomod a+x ./1.txt` apply the rule to all user
- `u` file owner
- `g` group of the owner
- `o` anyone else

### Redirection

`>` as output to, eg `echo hello > 1.txt`, the output of `echo hello` will store in `1.txt`

`2>` redirect the `stderr`, eg `2>&1` stderr to stdout

`&>/dev/null` stdout and stderr to (null)

`<` as input to, eg `cat < 1.txt`, `cat` will displau content of `1.txt`

`|` convert output stream from previous command to input stream of next command, eg `echo Hello | > 1.txt` 

`>>` append instead overwrite

### Assignment

`foo=bar` assign `bar` to variable `foo`, `echo $foo` will output `bar`

> **_NOTE:_** `foo = bar` will no work

`foo=$(pwd)` store the pwd result into foo

###  Quotation

` ''` echo 'value is $foo' will treat `$foo` as normal string

`""` echo 'value is $foo' will output the value of `$foo`

"``" will execute the content inside it

### Function

### Arguments And Special Variables

`$0` refer to file name

`$1-$9` first argument to 9th argument

`$#` number of arguments applied

`$_` last argument of previous command 

`$?` exit status of previous task

`!!` previous command

`$*` All arguments

`$@` All arguments, starting from first

`$$` current pid

```bash
# in mcd.sh
mcd() {
	mkdir -p "$1"
	cd "$1"
}
# in terminal
source mcd.sh
# now you can execute the function on shell
mcd test
```

### Output

`/dev/null` A magic place to write the useless thing to it, it will automatically discard it.

### Globbing

`*` match everything

`[]`set of wildcard, eg  `ls 0[123].txt` will search for `01.txt` `02.txt` and so on.

`?` single character

`a.{1,2,3}` will expand to a1 a2 a3

`{foo, bar}/{a..j}` expand to fooa to fooj and bara to barj

## Using Output As Arguments

`rm` will accept arguments only, so how to delete all the files in the current directory.

`ls | xargs rm` will delete the files in the current directory.

## Vim

### Vim Modes

- **Normal**: for moving around a file and making edits
- **Insert**: for inserting text
- **Replace**: for replacing text
- **Visual** (plain, line, or block): for selecting blocks of text
- **Command-line**: for running a command

### Basic Operation

##### Comand Line

`:q` close current window

`:w` save current window

`:wq` save and quit current window

`:wq!` force save and quit

`:e {name of file}` open file for editing

`:ls` show open buffers

`:help {topic}` open help

- ``:help :w` opens help for the `:w` command

- `:help w` opens help for the `w` movement

#### Mode Operation

`i` Insert mode.

`Esc` exit insert mode and enter normal mode.

`:` In normal mode with `:` will enter command line mode.

- 

#### Navigation

`h` `j` `k` `l` corresponding to left, down, up and right

##### Move by word

`w` next beginning of the word 

`b` previous beginning of the word

`e` next end of the word

##### Move by line

`0` beginning of the line

`^` fist non-blank character of the line

`$` end of the line

##### Move by Screen

`H` top of screen

`M` middle of screen

`L` bottom of screen

##### Move by Scrolling

`Ctrl-b` scroll up one page

`Ctrl-f` scroll down one page

`Ctrl-u` scroll up half page

`Ctrl-d` scroll down half page

##### Move by File

`gg` beginning of the gile

`G` end of the file

#####  Move by Find and Search

Find: `f{character}`, `t{character}`, `F{character}`, `T{character}`

- find/to forward/backward {character} on the current line

- `,` / `;` for navigating matches

Search: `/{regex}`, `?{regex}`

- search forward/backward
-  `n` / `N` for navigating matches

#### Insert

`i` insert mode

`I` to current line of non blank(head) and enter intert mode

`a` insert mode after current cursor

`A` insert at the last area of current line

`o` insert to next line

`O` insert to previous line

#### Modifiers

You can use `d` instead of `c` here

`ca(` cut all the stuff inside the parentheses(included)

`ci(` cut all the stuff inside the parentheses(non-inclued)

`ciw` cut the word of current cursor

#### Copy

`y` copy

`yy` copy current line

#### Delete

`dd` delete current line

`s` delete current character and insert mode.

`x` delete current character

#### Paste

`P` paste before cursor(Cat)

`p` paste after cursor

#### Replacement

##### Flags

`/g` apply it to all

`/i` case insensitive

`/c` confirmation with replacement

`:s/old/new` replace first old with new in current line

`:s/old/new/g` replace all olds with new in current line

`%s/old/new/g` replace domain become global instead of current line

`%s/^/new/gc` add new to the head of every lines and ask for confirmation.

`%s/a$/new/gi` replace ending with `a or A` with new in the document.

#### Others

`%` corresponding item, eg match `()`

`u` redo

`Ctrl-R` undo

``` `` ``` the backquote go back to column of previous cursor

`''` go back to line of the previous cursor

#### Indentation

`<<` indent left.  `>>` indent right

#### Multi Windows

`:vs %` open current doc at second window

`:vs <filepath>` open the new window at side

`:sv <filepath>` open the new window in bottom

`ctrl-w b`   previous window

`ctrl-w w`   next window

`ctrl-w h`   left window

`ctrl-w l`   right window

`ctrl-w j`   bottom window

`ctrl-w k`   top window

#### Visual Mode

`v` start virsual mode at current cursor

`V` start it at linewise

