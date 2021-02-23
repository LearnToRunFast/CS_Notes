[toc]



# Shell

## Environment Path

The environment path stores all the runnable command path of the system. Eg. `echo` is in the environment path so that we can use it directly inside the shell.

To find out the location, type `which echo`.

### Environment Variable $PATH

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

`sed` find and replace text

- `sed -E s/old/new` the `-E` will make `sed` support new sytax of regular expression

  `uniq -c` collapse consecutive lines that are the same into a single line, prefixed with a count of the number of occurrences.

`sort -nk1,1` `sort -n` will sort in numeric (instead of lexicographic) order. `-k1,1` means “sort by only the first whitespace-separated column”. The `,n` part says “sort until the `n`th field, where the default is the end of the line. 

`head` `head -n10` take the top 10 lines

`tail` `tail -n5` take the bottom 5 lines

`wc` `wc -l` count the number of lines

`paste` `paste -sd` combine lines (`-s`) by a given single-character delimiter (`-d`; `,` in this case).

#### Awk

`awk` programs take the form of an optional pattern plus a block saying what to do if the pattern matches a given line. 

The default pattern (which we used above) matches all lines. Inside the block, `$0` is set to the entire line’s contents, and `$1` through `$n` are set to the `n`th *field* of that line, when separated by the `awk` field separator (whitespace by default, change with `-F`). Some example list on below

```shell
awk '{print $2}'
```

For every line, print out the second field of the line.

```shell
awk '$1 == 1 && $2 ~ /^c[^ ]*e$/ { print $2 }'
```

If first field equals `1` and second field match the regular expression, print it out.

```shell
BEGIN { rows = 0 }
$1 == 1 && $2 ~ /^c[^ ]*e$/ { rows += $1 }
END { print rows }
```

More detail, look out [here](https://backreference.org/2010/02/10/idiomatic-awk/).

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

### Using Output As Arguments

`rm` will accept arguments only, so how to delete all the files in the current directory.

`ls | xargs rm` will delete the files in the current directory.