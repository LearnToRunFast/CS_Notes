# Java

## Equals

```java
// use final here to make sure it will not be inherited (it would violate symmetry if it can be inherited)
public final class Date implements Cpmparable<Date> {
	private final int month;
  private final int day;
  private final int year;
  
  // must be Object here
  public boolean equals(Object y) {
    if (y == this) return true;
    if (y == null) return false; // must check not null
    if (y.getClass() != this.getClass()) return false;
    
    Date that = (Date) y;
    
    if (this.day != that.day || this.month != that.month ||
        this.year != that.year) {
      return false;
    }
    return true;
  }
}
```

