# Regular Expression

## Common patterns

`^` Match start of the line

`$` Match end of the line

`.` match any character

`*` zero or more of the preceding match

`?` one of the precceding match

`+` one or more of the preceeding match

`{}` quantifier, `a{3}` match aaa, `a{1,3}` more one and no more than 3 times.

`[abc]` any one character of `a` , `b` , `c`

`[^a]` not a

`(RX1|RX2)` capture group, match either something that matches `RX1` or `RX2`

`\1` or `$1` (depending on language) Accessing first group(first surrounded by `()`) match result in the regular expression

`\<, \>`: start-of-word and end-of-word respectively