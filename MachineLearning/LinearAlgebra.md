[toc]

# Linear Algebra

## Matrix

$$
A_{m,n} = 
\begin{pmatrix}
a_{1,1} & a_{1,2} & \cdots & a_{1,n} \\
a_{2,1} & a_{2,2} & \cdots & a_{2,n} \\
\vdots  & \vdots  & \ddots & \vdots  \\
a_{m,1} & a_{m,2} & \cdots & a_{m,n} 
\end{pmatrix}
$$

The matrix A has `m` rows and `n` columns.

$A_{i, j}$ Refers to the value of  `i` row and `j` column in matrix $A_{m,n}$

### Vector

Vector is a special form of matrix with `n` rows  and 1 column
$$
y_{n} = 
\begin{pmatrix}
a_{1} \\
a_{2} \\
\vdots \\
a_{n}  
\end{pmatrix}
$$
$y_i$ Refers to $i^{th}$ row element.

### Addition

Addition only allowed with the same dimension matrix.
$$
\begin{bmatrix} 
a & b \\
c & d 
\end{bmatrix}
+
\begin{bmatrix} 
e & f \\
h & i
\end{bmatrix}
=
\begin{bmatrix} 
a + e & b + f \\
c + h & d + i
\end{bmatrix}
$$

### Multiplication

#### Scalar Multiplication

$$
3 \times
\begin{bmatrix} 
2 & 4 \\
1 & 6
\end{bmatrix}
=
\begin{bmatrix} 
\color{green}6 & \color{green}12 \\
\color{green}3 & \color{green}18
\end{bmatrix}
=
\begin{bmatrix} 
2 & 4 \\
1 & 6
\end{bmatrix}

\times 3
\\[4ex]
\begin{bmatrix} 
2 & 4 \\
1 & 6
\end{bmatrix}
/\textcolor{green}{3}
=
\begin{bmatrix} 
2 & 4 \\
1 & 6
\end{bmatrix} \times \textcolor{green}{\frac{1}{3}}
$$

#### Matrix Multiplication
$$
A_{m,n} \times B_{n,o} = C_{m,o}
$$
The $i^{th}$column of the matrix C is obtained by multiplying A with the  $i^{th}$ column of B.

A `m` x `n` Matrix multiple `n` x `o` matrix will be result in `m`x `o` matrix.
$$
\begin{bmatrix} 
1 & 3 & 2 \\
4 & 0 & 1
\end{bmatrix}
\times
\begin{bmatrix} 
1 & 3 \\
0 & 1 \\
5 & 2
\end{bmatrix}
=
\begin{bmatrix} 
\color{green}11 & \color{blue}10 \\
\color{green}9 & \color{blue} 14
\end{bmatrix}
\\[6ex]
\begin{bmatrix} 
1 & 3 & 2 \\
4 & 0 & 1
\end{bmatrix}
\times
\begin{bmatrix} 
1 \\
0 \\
5
\end{bmatrix}
=
\begin{bmatrix} 
\color{green}1 \times 1 + 3 \times 0 + 2 \times 5 \\
\color{green}4 \times 1 + 0 \times 0 + 1 \times 5
\end{bmatrix}
=
\begin{bmatrix} 
\color{green}11 \\
\color{green}9
\end{bmatrix}
\\[6ex]
\begin{bmatrix} 
1 & 3 & 2 \\
4 & 0 & 1
\end{bmatrix}
\times
\begin{bmatrix} 
3 \\
1 \\
2
\end{bmatrix}
=
\begin{bmatrix} 
\color{blue}1 \times 3 + 3 \times 1 + 2 \times 2 \\
\color{blue}4 \times 3 + 0 \times 1 + 1 \times 2
\end{bmatrix}
=
\begin{bmatrix} 
\color{blue}10 \\
\color{blue}14
\end{bmatrix}
$$

##### Commutative

Matrix Multiplication is not commutative.
$$
A \times B \neq B \times A
$$

##### Associative

Matrix Multiplication is associative.
$$
A \times (B \times C) = (A \times B) \times C
$$

### Identity Matrix

Denoted $I$ (or $I_{n\times n}$)
$$
\begin{bmatrix} 
1 & 0 \\
0 & 1
\end{bmatrix}
~~~~~~~~~~~~
\begin{bmatrix} 
1 & 0 & 0 \\
0 & 1 & 0 \\
0 & 0 & 1
\end{bmatrix}
~~~~~~~~~~~~
\begin{bmatrix} 
1 & 0 & 0 & 0 \\
0 & 1 & 0 & 0 \\
0 & 0 & 1 & 0 \\
0 & 0 & 0 & 1
\end{bmatrix}
$$
For any matrix A,
$$
A_{m\times n} \cdot I_{n\times n} = I_{m\times m} \cdot A_{m\times n} = A_{m\times n}
$$

#### Matrix Inverse

If A is an `m x m` matrix, and if it has an `inverse`. Matrix does not has an inverse called `singular` matrix.
$$
A\cdot A^{-1} = A^{-1} \cdot A = I
$$

### Matrix Transpose

$$
A=
\begin{bmatrix} 
1 & 2 & 0 \\
3 & 5 & 9
\end{bmatrix}
~~~~~~~~
B= A^T
\begin{bmatrix} 
1 & 3 \\
2 & 5 \\
0 & 9
\end{bmatrix}
$$

Let A be an `m x n` matrix, and let B = $A^T$. 

Then B is an `n x m` matrix, and $B_{ij} = A_{ji}$

