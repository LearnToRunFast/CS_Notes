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

## Testing

### Test with Junit

Spring Boot projects with versions >= 2.2.0 use JUnit 5 by default.

| Description                                                  | JUnit 4                                                      | JUnit 5                                                      |
| ------------------------------------------------------------ | ------------------------------------------------------------ | ------------------------------------------------------------ |
| Test Annotation Changes                                      | `@Before` `@After` `@BeforeClass` `@AfterClass` `@Ignore`    | `@BeforeEach` `@AfterEach` `@BeforeAll` `@AfterAll` `@Disabled` |
| Use `@ExtendWith` instead of `@RunWith`                      | `@RunWith(SpringJUnit4ClassRunner.class)` `@RunWith(MockitoJUnitRunner.class)` | `@ExtendWith(SpringExtension.class)`  `@ExtendWith(MockitoExtension.class)` |
| Package changes to `org.junit.jupiter`                       | `org.junit.Test;`  `org.junit.Assert.*;`                     | `org.junit.jupiter.api.Test;`  `org.junit.jupiter.api.Assertions.*;` |
| `@RunWith` is NOT needed with `@SpringBootTest`, `@WebMvcTest`, `@DataJpaTest` | `@RunWith(SpringRunner.class)` `@SpringBootTest(classes = DemoApplication.class)` | `@SpringBootTest(classes = DemoApplication.class)`           |

### Test using Mockito

```java
@RunWith(MockitoJUnitRunner.class) // add this to support @mock and @Inject
public class SomeMockTest {
  
  @Mock
  DataService dataServiceMock; // data that need to be mocked
  
  @Inject
  SomeBusinessImpl  businessImpl; // business logic that depend on mocked object 
  
  // with no argument
  @Test
  public void testWithSth() {
    when(dataServiceMock.getData()).thenReturn(new int{15});
    int result = businessImpl.getMax();
    assertEquals(15, result);
  }
  
  // with interface
  //multiple return value
  @Test
  public void testWithSize() {
    List listMock = mock(List.class);
    when(listMock.size()).thenReturn(10).thenReturn(20); //first call return 10, else return 20
    assertEquals(10, listMock.size());
    assertEquals(20, listMock.size());
  }
  
  // one or more argument
  @Test
  public void testWithSize() {
    List listMock = mock(List.class);
    when(listMock.get(0)).thenReturn("someString");
    assertEquals("someString", listMock.get(0));
    assertEquals(null, listMock.get(1));
    
    when(listMock.get(Mockito.anyInt())).thenReturn("someString");
    assertEquals("someString", listMock.get(5));
    assertEquals("someString", listMock.get(10));
    
  }
}
```

## Spring Boot

The goal of spring boot is to 

1. enable building production ready applications quickly
2. provide common non-functional features
   1. embedded servers
   2. metrices
   3. health checks
   4. externalised configuration 

### Controller

```java
@RestController
public class BoosController {
  @GetMapping("/books")
  public List<Book> getAllBooks() {
    return Arrays.asList(new Book(1l, "book name", "author"));
  }
}
```

### Spring Boot Dev Tool

Dev tools help to restart the application when necessary to to avoid restart the application manually.

```xml
<dependency>
  <groupdId>
    org.springframework.boot
  </groupdId>
  <artifactId>
    spring-boot-devtools
  </artifactId>
</dependency>
```

### Aspect-Oriented Programming（AOP)

```java
@Aspect
@Configuration
public class UseAccessAspect {
	// execution(* PACKAGE.*.*(..))
  //* any return type .* any class .* any method (..) any arguments
  @Bofre("execution(* com.aop.springaop.business.*.*(..))")
  public void before(JoinPoint joinPoint) { }
  
  @AfterReturning(value = "execution(* com.aop.springaop.business.*.*(..))",
                 returning = "result")
  public void afterReturning(JoinPoint joinPoint, Object result) {
    
  }
  @AfterThrowing(value="execution(* com.aop.springaop.business.*.*(..))", throwing="exception")
  public void afterThrowing(JoinPoint joinPoint, Exception exception) {
    
  }
  @After(value="execution(* com.aop.springaop.business.*.*(..))")
  public void after(JoinPoint joinPoint) {
    
  }
  
  @Around("execution(* com.aop.springaop.business.*.*(..))")
  public void around(ProceedingJoinPoint joinPoint) throws Throwable {
    long startTime = System.currentTimeMillis();
    
    joinPoint.proceed();
    
    long timeTaken = System.currentTImeMillis() - startTime;
  }
}
```

#### PointCut

```java
public class CommonJoinPointConfig {
  @Pointcut("execution(* com.aop.springaop.business.*.*(..))")
  public void businessLayerExecution(){}
  
  @Pointcut("execution(* com.aop.springaop.data.*.*(..))")
  public void dataLayerExecution(){}
  @Pointcut("com.in28minutes.sprint.aop.springaop.aspect.CommonJoinPointConfig.dataLayerExecution() && com.in28minutes.sprint.aop.springaop.aspect.CommonJoinPointConfig.businessLayerExecution()")
  public void allLayerExecution() {}
  
  @Pointcut("brean(*dao*)")
  public void beanContainingDao(){}
       @Pointcut("within(com.in28minutes.sprint.aop.springaop.data..*)")
  public void dataLayerExecutionWithWithin(){}
}
```

#### Custom Annotation

Assume we have following Repository

```java
@Repository
public class Dao {
  @TrackTime // custom annotation
  public String retrieveSomething() {
    return "Dao";
  }
}
```

Custom Annotation class

```java
// apply in method only
@Target(ElementType.METHOD)
//runtime
@Retention(RetentionPolicy.RUNTIME)
public @interface TrackTime {
  
}
```

Define point cut for custom annotation class

```java
@Pointcut("@annotation(com.in28minutes.sprint.aop.springaop.aspect.TrackTime)")
public void trackTimeAnnotation() {}
```

## Spring JDBC

### H2

Enable console mod for h2

```java
// in application.properties
sprint.h2.console.enabled=true

// on console localhost:8080/h2-console
JDBC URL: jdbc:h2:mem:testdb
username:sa
password
```

#### Create Table

create a `sql` file call `schema.sql` on `src/main/resources`, when the application is launched, it will automatically run the `sql`

```sql
create table person
(
  id integer not null,
  name varchar(255) not null,
  location varchar(255),
  birth_date timestamp,
  primary key(id)
);
```

#### Insert Item

create a `sql` file call `data.sql` on `src/main/resources`, when the application is launched, it will automatically run the `sql`

```sql
INSERT INTO PERSON (ID, NAME, LOCATION, BIRTH_DATE ) 
VALUES(10001,  'Ranga', 'Hyderabad',sysdate());
INSERT INTO PERSON (ID, NAME, LOCATION, BIRTH_DATE ) 
VALUES(10002,  'James', 'New York',sysdate());
INSERT INTO PERSON (ID, NAME, LOCATION, BIRTH_DATE ) 
VALUES(10003,  'Pieter', 'Amsterdam',sysdate());
```

#### Query with DAO

```java
@Repository
public class PersonJdbcDao {
  @Autowired
  JdbcTemplate jdbcTemplate;
  
   // new BeanPropertyRowMapper(Person.class)
  // use this if db column is not match to Person object
	class PersonMapper implements RowMapper<Person> {
		@Override
		public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
      Person person = new Person();
      person.setId(rs.getInt("id"));
      person.setName(rs.getString("name"));
      person.setLocation(rs.getString("location"));
      person.setBirthDate(rs.getTimestamp("birth_date"));
			return person;
		}
	}
  
  public List<Person> findAll() {
    return jdbcTemplate.query("select * from person",
             new PersonMapper());
  }
  
  public Person findById(int id) {
    return jdbcTemplate.queryForObject("select * from person where id=?", 
          new Object[]{id},
          new BeanPropertyRowMapper<Person>(Person.class));
  }

  	public void insert(Person person) {
		jdbcTemplate.update("insert into person(id, birth_date,location, name) values(?,?,?,?)", 
        person.getId(),
				new Timestamp(person.getBirthDate().getTime()),
        person.getLocation(),
        person.getName());
	}

	public void update(Person person) {
		jdbcTemplate.update("Update person set name=?, location=?, birth_date=? where id=?", 
        person.getName(),
				person.getLocation(), 
        new Timestamp(person.getBirthDate().getTime()),
        person.getId());
	}
  
  //return how many row get deleted
	public void deleteById(int id) {
		jdbcTemplate.update("delete from person where id=?", id);
	}
}
```

#### Java Persistence API(JPA)

JPA defines a set of concepts that can be implemented by any tool or framework. While JPA's object-relational mapping (ORM) model was originally based on Hibernate, it has since evolved. Likewise, while JPA was originally intended for use with relational/SQL databases, some JPA implementations have been extended for use with NoSQL datastore. A popular framework that supports JPA with NoSQL is EclipseLink.

##### Entity

```java
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;

@Entity
@NamedQuery(query = "select p from Person p", name = "find_all_persons_query")
// only necessary if the class name is different from db table name, in this case, we can ignore it.
@table(name="person") 
public class Person {

	@Id //primary key
	@GeneratedValue // auto generated value
	private int id;
	// only necessary if the column name is different from db column name, in this case, we can ignore it.
  @column(name="name")
	private String name;

	private String location;

	private Date birthDate;

	public Person(int id, String name, String location, Date birthDate) {
		super();
		this.id = id;
		this.name = name;
		this.location = location;
		this.birthDate = birthDate;
	}

	public Person() {

	}

	public Person(String name, String location, Date birthDate) {
		super();
		this.id = id;
		this.name = name;
		this.location = location;
		this.birthDate = birthDate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	@Override
	public String toString() {
		return String.format("\nPerson [id=%s, name=%s, location=%s, birthDate=%s]", id, name, location, birthDate);
	}

}
```

##### Repository

```java
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

@Repository
// transaction, for multiple queries, you want all of them to be success or fail together
// ideally the transaction is implemented around bussiness services
@Transactional
public class PersonJpaRepository {

	@PersistenceContext
	private EntityManager entityManager;

	public List<Person> findAll() {
		Query query = entityManager.createNamedQuery("find_all_persons_query", Person.class);
		return query.getResultList();
	}

	public Person findById(int id) {
		return entityManager.find(Person.class, id);
	}

	public void insert(Person person) {
		entityManager.merge(person);
	}

	public void update(Person person) {
		entityManager.merge(person);
	}

	public void deleteById(int id) {
		Person person = findById(id);
		entityManager.remove(person);
	}
}
```

##### Repository Interface

We can see that there are still a lot of duplicate codes, to simplify further, we use build-in repository interface

```java
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonSpringDataRepository extends 
  // <Entity, Primary Key>
  JpaRepository<Person, Integer> {
  
}
// other class

public class businessLogic {
  @Autowired
  PersonSpringDataRepository repo;
  repo.findById(1);
  repo.save(new Person(...));
  repo.deleteById(1);
  repo.findAll();
  
}
```

