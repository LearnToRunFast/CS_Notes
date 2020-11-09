[toc]



# Spring

Sprint is a dependency Injection framework.

## Dependency

### Coupling

Below example is just an example to illustrate the couling issue. In real life, binary search will not dependent on sorting algorithm.

#### High Couling

```java
class BinarySearch {
  
  public int search(int[] arr, int element) {
    // sort the arr.
    SortAlgo sortAlgo = new BubberSort(); // BubberSort is one kind of SortAlgo
    sortAlgo.sort(arr);
    // search part
    // return result
  }
}
```

With the code above, we consider use sort algorithm inside binarySearch class, we said that `BinarySearch` is dependent on `BubberSort`. In future, If we wanna use other other SortAlgo, we need to modify the `BinarySearch` class which is not a good practice. We said `BinarySearch` is **highly coupled** with `BubberSort`.

> **_Note:_** A good practice is we should follow `Open Close Princeple`, Open code for extension and close for modification. We should avoid modify existing class as most as possible.

#### Loose Couling

A better version is instead of hard coding the type inside method, we pass it as constructor argument. In this case, we can avoid modify `BinarySearch` if we decide to use different type of algorithm. See code below for more details.

> **_Note:_** `SortAlgo` here should be an interface, any algorithm classes should `implements` this interface so that they can be used by `BinarySearch` class.

```java
class BinarySearch {
  
  private SortAlgo sortAlgo;
  public binarySearch(SortAlgo sortAlgo) {
    this.sortAlgo = sortAlgo;
  }
  public int search(int[] arr, int element) {
    // sort the arr.
    sortAlgo.sort(arr);
    // search part
    // return result
  }
}
```

## Bean

Spring creates `beans` with `@component` on top of one class which means for every class with `@component`, spring will create one `bean` represent that class.

### Bean Scope

In Spring, a `Bean` has few different scopes.

| Scope     | Description                     |
| --------- | ------------------------------- |
| Singleton | One instance per Spring Context |
| Prototype | New bean whenever requested     |
| request   | One bean per HTTP request       |
| Session   | One bean per HTTP session       |

The default bean scope is `Singleton`. We can use 

1. `@Scope("prototype")`
2. or build-in contstant variable `ConfigurableBeanFactory.SCOPE_PROTOTYPE` 

on top of one class to change the scope of that bean to `prototype` 

## Application Context

Spring will create an application context to manage all the beans.

### Component Scan

Scan the package name for add bean into context.

```java
@SpringBootApplication() == @ComponentScan("Current_Package_name");

@SpringBootApplication()  // include scan for current package
@ComponentScan("Another_Package_name"); // add another scan to current package.
```

### Bean Life Cycle

```java
@PostConstruct
public void PostConstrcut() {} // will be called after construction of the bean

@PreDestroy
public void preDestory() {} // before the instances get removed.
```



## Dependency Injection

with the example provided in other secitons, we can rewrite it use spring.

```java
@Component
class BinarySearch {
  
  @AutoWired
  private SortAlgo sortAlgo;
  public int search(int[] arr, int element) {
    // sort the arr.
    sortAlgo.sort(arr);
    // search part
    // return result
  }
}
```

The `@AutoWired` tell the spring that `BinarySearch` is dependent on `SortAlgo`, we need to make `sortAlgo` class as bean by putting `@Component` on top of that class too.

### Choosing Dependencies

What happened if we have two or more different type of `SortAlgo`. 

1. We can speicfy  the main algorithm that we going to use by using `@primary`.

```java
class BubberSort implements SortAlgo {
	//... implementation here
}
@Primary
class QuickSort implements SortAlgo {
	//... implementation here
}
```

2. We can the algorithm name to specify algorithm.

```java
@Component
class BinarySearch {
  
  @AutoWired
  private SortAlgo quickSort;
	//...
}
```

> **_Note:_** What happen if we use `@Primary` to `BubberSort` and use the naming method to `quickSort`? In this case, `@Primary` has higher priority win the game.

3. Add qualifier. Add a qualifier using `@Qualifier` for both sort algorithms and specify which sort algorithm to use by specify the qualifier.

```java
@Qualifier("quickSort")
class QuickSort implements SortAlgo {
	//... implementation here
}

@Component
class BinarySearch {
  
  @AutoWired
	@Qualifier("quickSort")
  private SortAlgo sortAlgo;
	//...
}
```

### Dependencies Scope

Normally the scope of a dependency will remain `Singleton` if it's dependent class is `Singleton`. To solve this issue, we need to add the proxy setting to the dependency.

```java
@Scope(value=ConfigurableBeanFactory.SCOPE_PROTOYPE,
       proxyMode=ScopedProxyMode.TARGET_CLASS)
```

## Contexts and Dependency Injection(CDI)

```java
@Inject (@Autowired)
@Named (@Component)
@Singleton (Defines a scope of Singleton)
@Qualifier (@Qualifier)
```

## Spring Native

```java
@Configuration
@ComponentScan
public class SpringBasicApplication {
  public static void main(String[] args) {
    ApplicationContext context = new AnnotationCOnfigApplicationContext(SpringBasicApplication.class);
    //...
    ((AnnotationCOnfigApplicationContext) content).close();
  }
}
```

## Read Value From External File

```java
// create a app.properties on resource folder
external.service.url = "http://..."
  
// on dependency class
@value(${external.service.url})
  
// on application context
@PropertySource("classpath:app.properties")
```

## Test with Junit

