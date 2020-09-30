# Bit Related Knowledge

## Type of Bit Reprensentation 

#### One's Complement(unsign integer)

There is no negative value for one's complement.

Example:  x = **0000 1101**

The corresponding integer value is $ 13 = 2^3 + 2^2 + 2^0 = 8 + 4 + 1 $ $ \sum_{\forall i}{x_i^{2}} $

#### Two's Complement(sign integer)

what is the value of x = **1111 1111** in two's complement ? 

n = 8,  the $8^{th}$ bit is sign bit.

x = **0111 1111** $-$ **1000 0000** $=>$ $(2^{n - 1}- 1) - 2^{n - 1} $  $= -1$

OR

x = ~(**1111 1111** - 1) = ~(**1111 1110**) = (**0000 0001**) = - 1

one's complement of x: **~x**

Example:  x = **0111 1111**  then ~x = **1000 0000**,

x + ~x $=$ **1111 1111** $= -1 =>$ **-​x ​=  ~x + 1**

where -x = **1000 0001**

## Binary and Hexadecimal Reprensentation

| Decimal | Hex  | Binary | Decimal | Hex  | Binary |
| :-----: | :--: | :----: | :-----: | :--: | :----: |
|    0    |  0   |  0000  |    8    |  8   |  1000  |
|    1    |  1   |  0001  |    9    |  9   |  1001  |
|    2    |  2   |  0010  |   10    |  A   |  1010  |
|    3    |  3   |  0011  |   11    |  B   |  1011  |
|    4    |  4   |  0100  |   12    |  C   |  1100  |
|    5    |  5   |  0101  |   13    |  D   |  1101  |
|    6    |  6   |  0110  |   14    |  E   |  1110  |
|    7    |  7   |  0111  |   15    |  F   |  1111  |

## Bitwise Operators

| Operator | Description                                       |
| -------- | ------------------------------------------------- |
| &        | AND                                               |
| \|       | OR                                                |
| ^        | XOR(exclusive OR) (Same bits 0, different bits 1) |
| ~        | NOT(one's complement)                             |
| <<       | shift left                                        |
| >>       | Shift right                                       |

Example: A = **1011 0011** , B = **01101001**

A & B = **0010 0001**		~A = **0100 1100**		A | B = **1111 1011**		A^B = **1101 1010**		A >> 3 = **0001 0110**		A << 2 = **1100 1100**

## Bit Manipulation Example

#### 1. Clear the $K^{th}$ Bit

Idea: shift, complement, and AND

**y = ~(1 << k) \& x**		**~(1<<k) gives only $K^{th}$ is 0, any value AND with 0 is 0.**

#### 2. Toggle the $K^{th}$ Bit

Idea: shift, XOR

**y = x ^ (1 << k)**		

#### 3. Extract a Bit field

Idea: Mask and shift

**(x & mask) >> shift**

Example: x = **1010 1101**		mask = **1111 0000**		x & mask = **1010 0000**		>> 4 (shift left by 4) = **1010**

#### 4. Set a Bit field

Idea: Invert mask to clear and OR the shifted value

**x = (x & ~mask)  | (y << shift)**		y is the value you want to set  

**x = (x & ~mask)  | ((y << shift) & mask)**		AND the mask to prevent garbage value of y affect the final value of x

Example: x = **1010 1101**		y = **0000 0011**		mask = **1111 0000**

~mask = **0000 1111**		x & ~mask = **0000 1101**		set replacement field into all 0s

y << 4 = **0011 0000**		(x & ~mask)  | (y << shift) = **0011 1101**

#### 5. Swap value without temporary value

x = x ^ y

y = x ^ y

x = x ^ y

Example: x = **1011 1101**		y = **0010 1110**		

x = x ^ y = **1001 0011**		y = x ^ y = **1011 1101**		x = x ^ y = **0010 1110**

why is works:

XOR is it's own inverse	(x ^ y) ^ y => x

Performace:

Poor at exploiting **Instruction-level parallelism(ILP)** (The code can not be run at parallel)

#### 6. Minumum of Two Integers 

If use normal if-else or ternary expression(? :) 

**Performance:** A mispredicted branch empties the processor pipeline

**Caveat:** The compuler is usually smart enough to optimize away the unpredictable branch, but maybe not.

Bit way: minimum **r = y ^ ((x ^ y) & - (x < y))** 		（Branchless,  slower than branch version if complier optimised the code)

Why it works:	In C, True = 1 and False = 0

**If  x < y:**

then **- (x < y) => -1** which is all 1's in two's complement representation. Therefore,  **(x ^ y) & -1 => (x ^ y)** and we know

**y ^ (x ^ y) => x**, so **r = y ^ ((x ^ y) & - (x < y)) = x **.

**If x >= y**:

then **-(x < y) => 0**. Therefore, **(x ^ y) & 0 => 0**, so **r = y ^ ((x ^ y) & - (x < y)) = y ^ 0  = y** .

#### 7. Modular Addition

r = (x + y) % n 	(division is expensive unless by a power of 2)

Better version:

z = x + y

r = ( z < n)  ? z : z - n

Branchless version:

z = x + y

r = z - (n & - (z >= n))		if **(z >= n)** then **z - n** else **z**

#### 8. Round up to a power of 2

Compute $2^{\lceil \lg n \rceil}$

int n; (32Bit)

--n;		(Bit $\lceil \lg n \rceil - 1$ must be set in order to propagate right bits into 1s)

n |= n >> 1;

n |= n >> 2;

n |= n >> 4;

n |= n >> 8;

n |= n >> 16;

++n; 		(Set bit $\lceil \lg n \rceil$)

Example: n = **0101 0000**		--n = **0100 1111**		n |= n >> 1 = **0110 1111**		...  n = **0111 1111**		++n = **1000 0000**

#### 9. Least-Significant

Compute the mask of the least-significant 1 in word x.

r = x & (- x)

Example: x = **0101 0000**	-x = ~x + 1 = **1011 0000**	r = x & (- x) = **0001 0000**

why is works:

The binary representation of **-x is (~x + 1)**

#### 10. Queens Problem

3 bit vectors of size n,  2n - 1 and 2n - 1 (there are 2n -1 possible diagonals)

Placing a queen in column c is not safe if **down & (1 << c )** is nonzero (check specific column has 1)  where down is size n vector.

Placing a queen in row r and column c is not safe if **left & (1 << (r + c))** is nonzero.

Placing a queen in row r and column c is not safe if **right & (1 << ( n -1 -r +c))** is nonzero.

#### 11. Population Count 1

Count the number of 1 bits in a word x

`for (r = 0; x != 0; ++r) {`

`x &= x - 1`		(clear  least significant bit)

`}`

Worse case: O(n) where n is length of the bits.

#### 12. Population Count 2

Create a `int count[256]` which store number of 1's of each 8-bit word

`int count[256] = {0, 1, 1, 2, 1, 2, 2, 3, 1, ..., 8} `

`for (int r = 0; x != 0; x >>= 8) {`

​	`r += count[x & 0xFF]`

`}`

Performance: Memory access

#### 13. Population Count 3

```java
// Create masks
M5 = ~((-1) << 32); 		//0 power of 32, 1 power of 32.
M4 = M5 ^ (M5 << 16);		// (0 power of 16, 1 power of 16) power of 2
M3 = M4 ^ (M4 << 8);		// (0 power of 8, 1 power of 8) power of 4
M2 = M3 ^ (M3 << 4);		// (0000 1111) power of 8
M1 = M2 ^ (M2 << 2);		// (0011) power of 16
M0 = M1 ^ (M1 << 1);		// (01) power of 32

// Compute popcount
x = ((x >> 1) & M0) + (x & M0);  //avoid overflow
x = ((x >> 2) & M1) + (x & M1);	 //avoid overflow
x = ((x >> 4) & x) + M2;
x = ((x >> 8) & x) + M3;
x = ((x >> 16) & x) + M4;
x = ((x >> 32) & x) + M5;
```

