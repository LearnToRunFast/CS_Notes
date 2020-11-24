[toc]

# Shell

## Shebang

```bash
#!/usr/bin/env python
```

By indicate the first line of this, we tell the shell the default Interpretor for this script.

## Checking Scripts

```bash
shellcheck mcd.sh	
```

## Symbol

```bash
missing:~$ 
```

`~` short for home dirctory

The `$` tells you that you are not the root user (more on that later).

`#` tell you that you are the root user.

`$PATH` refer to environment path, any path inside it can be directly executed.

## Navigation

`cd` change dirctory

`.` stand for current dictory

`..` stand for previous dictory

`-` prev visited dictory

## Common Commands

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

## Permission

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

## Redirection

`>` as output to, eg `echo hello > 1.txt`, the output of `echo hello` will store in `1.txt`

`2>` redirect the `stderr`, eg `2>&1` stderr to stdout

`&>/dev/null` stdout and stderr to (null)

`<` as input to, eg `cat < 1.txt`, `cat` will displau content of `1.txt`

`|` convert output stream from previous command to input stream of next command, eg `echo Hello | > 1.txt` 

`>>` append instead overwrite

## Assignment

`foo=bar` assign `bar` to variable `foo`, `echo $foo` will output `bar`

> **_NOTE:_** `foo = bar` will no work

`foo=$(pwd)` store the pwd result into foo

##  Quotation

` ''` echo 'value is $foo' will treat `$foo` as normal string

`""` echo 'value is $foo' will output the value of `$foo`

"``" will execute the content inside it

## Function

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

## Output

`/dev/null` A magic place to write the useless thing to it, it will automatically discard it.

## Globbing

`*` match everything

`?` single character

`a.{1,2,3}` will expand to a1 a2 a3

`{foo, bar}/{a..j}` expand to fooa to fooj and bara to barj

