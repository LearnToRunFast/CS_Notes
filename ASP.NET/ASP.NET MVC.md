[toc]

# ASP.net MVC

## MVC

### Model

Data Model

```c#
namespace xxx.Models {
  public class Movie {
    public int Id {get; set;}
    public string Name {get; set;}
  }
}
```

### View

HTML markup that display to the user.

### Controller

Resposible to handling HTTP request which contains bunch of ActionResults.

```c#
// code snippet of a movie controller
public class MoviesController : Controller
{
  // int? make year nullable which month become optional
  // references object do not need "?".
  public ActionResult ByReleaseDate(int year, int? month)
  {
     if (!month.HasValue) {
       month = 1;
     }
	}
}
```

## Action

### ActionResult

Generic type. Below are child types of action result.

|        Type        |   Helper Method   |
| :----------------: | :---------------: |
|     ViewResult     |      View()       |
| PartialViewResult  |   PartialView()   |
|   ContentResult    |     Content()     |
|   RedirectResult   |    Redirect()     |
|     JsonResult     |      Json()       |
|     FileResult     |      File()       |
| HttpNotFoundResult |  HttpNotFound()   |
|    EmptyResult     | new EmptyResult() |

### Action Parameters

1. Embedded in the URL:/movie/edit/1
2. In the query string: /movie/edit?id=1
3. In the form data

## Router

Select the right controller to handle a request

```c#
// example of custom route
routes.MapRoute("MoviesByReleaseDate",
               "movies/released/{year}/{month}",
               new {controller = "Movies", action = "ByReleaseDate"}
                // constraint here only 4 digit for year. Regex here.
               new {year = @"\d{4}", month = @"\d{2}"});// @ sign here to avoid double \\
```

### Attribute Routing

(better verion of routing)

```c#
// on ROuteConfig.cs
routes.MapMvcAttributeRoutes();

// on movie Controller
// 4 digits only and range from 1-12
// more constraints eg. min max minlength maxlength int float guid
// google ASP.NET MVC Attribute Route Constraints for more detail
[Route("movies/released/{year}/{month:regex(\\d{4}):range(1, 12)}")]
public ActionResult ByReleaseYear(int year, int month) { ... }
```

#### Passing data to view

1. Normal way

```c#
// On Controller
public ActionResult Random() {
  var movie = new Movie() {
    Name = "Joel";
  }
 return View(movie);
}
// On cshtml
@model xxx.Models.Movie
@{
  ViewBag.Title = "Random";
  Layout = "~/Views/Shared/_Layout.cshtml";
}
<h2>@Model.Name</h2>
```

2. ViewData ans ViewBag (has magic string "Movie", this version is fragile)

```c#
// On Controller
public ActionResult Random() {
  var movie = new Movie() {
    Name = "Joel";
  }
  ViewData["Movie"] = movie; // ViewData version
  ViewBag.Movie = movie; // ViewBag version, is not compile safe
  // accessing controller name, more attribute like "action" or "id"
  var controller = RouteData.Values["controller"]; 
  return View();
}
// On cshtml
@model xxx.Models.Movie
@{
  ViewBag.Title = "Random";
  Layout = "~/Views/Shared/_Layout.cshtml";
}
<h2>@(((Movie) ViewData["Movie"]).Name)</h2> // ViewData version
@ViewBag.Movie // ViewBag version
```

3. TempData: which can be used from current session

```c#
// tempData required session state is turn on
TempData.add("Country", "India");
TempData.Keep()
  
// on post action
TempData.Keep()
```

## ViewModel

Model specifically built for a view.It includes any data and rules specific to that view(usually combine two or more models together).

## Razor sytax

support html and C# on cshtml

```c#
// on cshtml
@*
  comments here
*@
// if-else statement
@if (Model.Customers.Count == 0) {
  <text>No one has rented this movie before. </text>
} else {
  <ul>
    // for loop
  @foreach (va r customer in Model.Customers) {
  	<li>@customer.Name </li>
  }
  </ul>
}

// conditional rendering
@{
  var className = Model.Customers.Count > 5 ? "popular" : null;
}
<h2 class="@className">... </h2>
```

## Partial View

Break View into different small pieces for better maintainability(by convention start naming with a "_").

```c#
// after create a partial view, simple render it by using
@Html.Partial("_NavBar", Model.Movie);// 2nd argument is optional Model passing
```

## Link Creation

There are various ways to create links

1. ```html
   // raw html
   <a href="/Movies/Index">View Movies</a>
   ```

2. ```c#
   // actionLink in htmlhepler class
   @Html.ActionLink("View Movies", "Index", "Movies"); //name , action , model
   
   // if requires paramter
   // remember the null at the end for some reason.
   @Html.ActionLink("View Movies", "Index", "Movies", new {id = 1}, null); 
   ```

## Entity Framework(Oject Relational Mapper)

