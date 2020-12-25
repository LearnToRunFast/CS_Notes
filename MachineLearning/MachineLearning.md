[toc]

# Machine Learning

## Type of Machine Learning Algorithms

- Supervised learning
- Unsupervised learning
- Reinforcement learning
- Recommended systems

## Supervised Learning

### Model Representation

We’ll use $x^{(i)}$ to denote the “input” variables, also called input features, and $y^{(i)}$ to denote the “output” or target variable that we are trying to predict. A pair $(x^{(i)}, y^{(i)})$ is called a training example, and the datasets that we’ll be using to learn—a list of m training examples $(x^{(i)}, y^{(i)}); i=1,...,m$ —is called a training set. 

> **_Note_**: The superscript `(i)` in the notation is simply an index into the training set, and has nothing to do with exponentiation. 

We will also use `X` to denote the space of input values, and `Y` to denote the space of output values. In this example, X = Y = ℝ. 

To describe the supervised learning problem slightly more formally, our goal is, given a training set, to learn a function h : X → Y so that h(x) is a “good” predictor for the corresponding value of y. For historical reasons, this function h is called a hypothesis. Seen pictorially, the process is therefore like this:

![image-20201129223314957](Asserts/MachineLearning/image-20201129223314957.png)

When the target variable that we’re trying to predict is continuous, such as in our housing example, we say the learning problem is a `regression problem`. 

When `y` can take on only a small number of discrete values (such as if, given the living area, we wanted to predict if a dwelling is a house or an apartment, say), we call it a `classification problem`.

### Cost Function

We can measure the accuracy of our hypothesis function by using a **cost function**. This takes an average difference (actually a fancier version of an average) of all the results of the hypothesis with inputs from x's and the actual output y's.
$$
J(\theta_0, \theta_1) = \frac{1}{2m}\sum_{i=1}^n(h_0(x^{(i)} - y^{(i)}))^2
$$
To break it apart, it is $\frac{1}{2}\overline{x}$ where $\overline{x}$ is the mean of the squares of $h_0(x_i) - y_i$, or the difference between the predicted value and the actual value.

This function is otherwise called the "Squared error function", or "Mean squared error". The mean is halved $(\frac{1}{2})$ as a convenience for the computation of the gradient descent, as the derivative term of the square function will cancel out the term.The goal is to minimise the $j(\theta_0, \theta_1)$

### Gradient Descent

A algorithm to minimise the cost function.

Repeat until convergence {
$$
\theta_j := ~ \theta_j - \alpha \frac{\partial}{\partial \theta_j} J(\theta_0, \theta_1) ~ (for ~ j = 0 ~ and ~ j = 1)
$$
which equivalent to
$$
\theta_j := ~ \theta_j - \alpha \frac{1}{m}\sum^{m}_{i=1}(h_\theta(x^{(i)}) - y^{(i)})x_j^{(i)}~
~ (for ~ j = 0 ~ and ~ j = 1)
$$
where
$$
\frac{\partial}{\partial \theta_j} J(\theta)=\frac{1}{m}\sum^{m}_{i=1}(h_\theta(x^{(i)}) - y^{(i)})
$$
}

> **_Note:_** All $\theta_i$ need to be updated simultaneously.

Where

1. `:=` means assign
2. $\theta$, learning rate

#### Correct Implementation

Simultaneous update
$$
temp0 := \theta_0 - \alpha \frac{\partial}{\partial \theta_0} J(\theta_0, \theta_1)
\\
temp1 := \theta_1 - \alpha \frac{\partial}{\partial \theta_1} J(\theta_0, \theta_1)
\\
\theta_0 := temp0
\\
\theta_1 := temp1
$$


#### Gradient Descent Intuition

Assume one example
$$
\theta_1 := \theta_1 - \alpha \frac{\part}{\part \theta_1} ~ J(\theta_1)
$$
The $\frac{\part}{\part \theta_1} ~ J(\theta_1)$ represent the slope of the curve, 

If the curve is increasing, then $\frac{\part}{\part \theta_1} ~ J(\theta_1)$ will be positive. Since the curve is increasing , we need to decrease $\theta_1$ to get minimise $\theta_1$

If the curve is decreasing, then $\frac{\part}{\part \theta_1} ~ J(\theta_1)$ will be negative. Since the curve is decreasing , we need to increase $\theta_1$ to get minimise $\theta_1$

### Multivariate Linear Regression

Linear regression with multiple variables is also known as "multivariate linear regression".

We now introduce notation for equations where we can have any number of input variables.

$x^{(i)}_j$:  value of feature $j$ in the $i^{th}$ training example

$x^{(i)}$:  value of features in the $i^{th}$ training example

$m$: the number of training examples

$n$: the number of features

The multivariable form of the hypothesis function accommodating these multiple features is as follows:
$$
h_\theta(x) = \theta_0+\theta_1x_1+\theta_2x_2+\cdots+\theta_nx_n
$$
Using the definition of matrix multiplication, our multivariable hypothesis function can be concisely represented as:
$$
h_\theta(x)=[\theta_0 ~ \theta_1~\cdots~\theta_n]
~
\begin{bmatrix} 
x_0\\
x_1 \\
\vdots\\
x_n
\end{bmatrix}
=\theta^{T}x
$$

### Gradient Descent for Multiple Variables

The gradient descent equation itself is generally the same form; we just have to repeat it for our `n` features:

Repeat until convergence {
$$
\theta_j := ~ \theta_j - \alpha \frac{\partial}{\partial \theta_j} J(\theta_0, \theta_1) ~ (for j = 0, \cdots, n)
$$
which equivalent to
$$
\theta_j := ~ \theta_j - \alpha \frac{1}{m}\sum^{m}_{i=1}(h_\theta(x^{(i)}) - y^{(i)})x_j^{(i)}~
~ (for j = 0, \cdots, n)
$$
}

#### Feature Scaling

We can speed up gradient descent by having each of our input values in roughly the same range. This is because θ will descend quickly on small ranges and slowly on large ranges, and so will oscillate inefficiently down to the optimum when the variables are very uneven.By using
$$
x_i := \frac{x_i-\mu_i}{s_i}
$$
where 

$\mu_i$ is the average of all the values for feature $i$

$s_i$ is the range of values (max - min), or $s_i$ is the standard deviation

#### Debugging

Plot a graph of y-axis represents $min(\theta)$ and x-axis as No. of iterations. The y should be decreasing over iterations and finally converge.

> **_Note:_** If the graph is decreasing and increasing, try to use smaller $\alpha$. where $\alpha$ is learning rate.

#### Learning Rate

If the $\alpha$ (learning rate) is too small: slow convergence.

If $\alpha$ is too large: $J(\theta)$ may not converge.

#### Features and Polynomial Regression

We can improve our features and the form of our hypothesis function in a couple different ways.

We can **combine** multiple features into one. For example, we can combine $x_1$and $x_2$into a new feature $x_3$ by taking $x_1 \cdot x_2$

##### Polynomial Regression

Our hypothesis function need not be linear (a straight line) if that does not fit the data well.

We can **change the behaviour or curve** of our hypothesis function by making it a quadratic, cubic or square root function (or any other form).

Let say we have
$$
h_\theta(x)=\theta_0 + \theta_1x_1 +\theta_2x_1^{2}+\theta_3x_1^3
$$
we can simply convert it to
$$
h_\theta(x)=\theta_0 + \theta_1x_1 +\theta_2x_2+\theta_3x_3
$$
where
$$
x_1^{2}=x_2 ~~~~~~~~~~~and ~~~~~~~~~~~x_1^3 =x_3
$$

> **_Note:_** One important thing to keep in mind is, if you choose your features this way then feature scaling becomes very important.

## Unsupervised Learning

Use Clustering algorithms to group the data together.

