# Regular Expression

## Regex's Special Characters

### Position Anchors

`^` Match start of the line

`$` Match end of the line

`\<, \>`: start-of-word and end-of-word respectively

### Occurrence Indicators

`*` zero or more of the preceding match

`?` one of the precceding match

`+` one or more of the preceeding match

`{}` quantifier, `a{3}` match aaa, `a{1,3}` more one and no more than 3 times.

### Pattern Indicator

`.` match any character

`[abc]` any one character of `a` , `b` , `c`

`[^a]` not start with a

### Group

`(RX1|RX2)` capture group, match either something that matches `RX1` or `RX2`

`\1` or `$1` (depending on language) Accessing first group(first surrounded by `()`) match result in the regular expression

### Escape Sequences

To match special meanings characters, we need to prepend it with a backslash `\`, known as *escape sequence*. For examples, `\+` matches `"+"`; `\[` matches `"["`; and `\.` matches `"."`.

## Flags

Regex has flags to indicates the global events.

`g`:  Search for global context and does not return after first match

`m`: ^ and $ now match start/end of each lines.

`i`: case insensitive match.

`x`: extemded, ignore whitespace.

`s`: dot . now can match newline.

`U`: make quantifiers lazy.

