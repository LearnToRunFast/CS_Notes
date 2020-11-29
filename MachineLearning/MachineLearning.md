# Machine Learning

## Type of Machine Learning Algorithms

- Supervised learning
- Unsupervised learning
- Reinforcement learning
- Recommender systems

## Supervised Learning

### Model Reprensentaion

We’ll use $x^{(i)}$ to denote the “input” variables, also called input features, and $y^{(i)}$ to denote the “output” or target variable that we are trying to predict. A pair $(x^{(i)}, y^{(i)})$ is called a training example, and the dataset that we’ll be using to learn—a list of m training examples $(x^{(i)}, y^{(i)}); i=1,...,m$ —is called a training set. 

> **_Note_**: The superscript `(i)` in the notation is simply an index into the training set, and has nothing to do with exponentiation. 

We will also use `X` to denote the space of input values, and `Y` to denote the space of output values. In this example, X = Y = ℝ. 

To describe the supervised learning problem slightly more formally, our goal is, given a training set, to learn a function h : X → Y so that h(x) is a “good” predictor for the corresponding value of y. For historical reasons, this function h is called a hypothesis. Seen pictorially, the process is therefore like this:

![image-20201129223314957](Asserts/MachineLearning/image-20201129223314957.png)

When the target variable that we’re trying to predict is continuous, such as in our housing example, we say the learning problem is a `regression problem`. 

When y can take on only a small number of discrete values (such as if, given the living area, we wanted to predict if a dwelling is a house or an apartment, say), we call it a `classification problem`.

### Cost Function

We can measure the accuracy of our hypothesis function by using a **cost function**. This takes an average difference (actually a fancier version of an average) of all the results of the hypothesis with inputs from x's and the actual output y's.
$$
J(\theta_0, \theta_1) = \frac{1}{2m}\sum_{i=1}^n(h_0(x^{(i)} - y^{(i)}))^2
$$
To break it apart, it is $\frac{1}{2}\overline{x}$ where $\overline{x}$ is the mean of the squares of $h_0(x_i) - y_i$, or the difference between the predicted value and the actual value.

This function is otherwise called the "Squared error function", or "Mean squared error". The mean is halved $(\frac{1}{2})$ as a convenience for the computation of the gradient descent, as the derivative term of the square function will cancel out the term.The goal is to minimize the $j(\theta_0, \theta_1)$

## Unsupervised Learning

Use Clustering algorithms to group the data together.

