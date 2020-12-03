[toc]



# Angular

**Installation**:`npm install -g @angular/cli`

**Start a Project**:`ng new <project_name>`

**Run project**:`ng serve`

**Project Running Flow**: `main.ts`  -> `app.module.ts` -> `app.component.ts`

**Structure**: 

## Component

Create a new Component with **CLI**:`ng generate component <component-name>` or `ng g c <component-name>`

```ts
import { Component } from '@angular/core';

@Component({ // indicate the class Appcomponent is Component
  //selector: '[app-server]'  select attribute instead of element
  //selector: '.app-server'  select by class
  selector: 'app-root', //  select by element
  
  // template: ''  // inline template
  // template: `` // use back quote to have multiple lines template
  templateUrl: './app.component.html', 
  
  // styles: '' // inline style
  styleUrl: ['./app.component.css']
})
export class AppComponent {}
```

## Module

Run `ng generate module MODULE_NAME --routing`

```ts
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';

import { AppComponent } from './app.component';

@NgModule({
  declarations: [  // add new component here
    AppComponent
  ],
  imports: [ // add modules to this module
    BrowserModule,
    FormsModule,
    HttpModule
  ],
  providers: [], // old way to connect modules
  bootstrap: [AppComponent] // tell bootsrap to run the component when compile
})
export class AppModule { }
```

### Module Communication

```ts
// component needed to be used
@NgModule({
	exports: [Component_Name] // Component_Name should be replace by real component name
})

// app module
import  {Module_Name } from "Module_Path"
@NgModule({
	imports: [Module_Name]
})
```

## Data Binding

### Output Data

### String Interpolation

Use `{{age}}`

#### Property Binding

Use `[]`, place a property inside `[]`.

Eg. `[disabled]="!allowNewServer"` The `"!allowNewServer"` coming from ts file

```ts
// parent html
<app-recipe-item 
  *ngFor=let recipeEl of recipes"
		[recipe]="recepeEl"></app-recipe-item>

// child ts
import { Input } from '@angular/core';
export class RecipeItemComponent {
  // @Input() makes child able to receive data from parent.
  @Input() recipe: Recipe; // Recipe is model
}

// child html
<h1>{{recipe.name}}</h1>
```

### React to User Event

#### Event Buiding

Use `()`, 

`$event` get access to event data, the data is on `(<HTMLInputElement>event.target).value`

Eg `(click)="onCreateServer()"`, the `"onCreateServer()"` coming from ts file.

```ts
// child html side
<a hred="#" (click)="onSelect('recipe')"></a>

// child ts side
import { EventEmitter, Output } from '@angular/core';

export class HeaderComponent {
  // @Output() make it's parent able to listen to the event.
  @Output() featureSelected = new EventEmitter<stirng>(); 
  
  onSelect(feature: string) { // trigger function of onclick
    this.featureSelected.emit(feature);
  }
}

// parent html side
<app-header (featureSelected)="onNavigate($event)"></app-header>
<app-recipes *ngIf="loadedFeature === 'recipe'"></app-recipes>


//parent ts side
export class AppComponent {
  loadedFeature = 'recipe';
  onNavigate(feature: string) {
    this.loadedFeature = feature;
  }
}
```

### Two Way Binding(ngModel)

For Two-Way-Binding to work, need to enable the `ngModel directive` This is done by adding `FormsModule` to the `imports[]` array in the AppModule, and import `{ FormsModule } from @angular/forms` at top.

Use `[(ngModel)]` 

Eg. `[(ngModel)]="serverName"`

## Directives

Directives are instructions in the DOM.

### Attribute Directives

Changes the properties of the HTML element it gets applied to.

#### ngStyle

Change style dynamically.

`[ngStyle]="{backgroundColor: getColor()}"`

#### ngClass

Change class dynamically.

##### Single Class

`[ngClass]="{online: status === 'online'}"`， if status is 'online', it will add `online` class to current element.

##### Condition Class

```ts
// html
<li [ngClass]="getClass()"> </li>
// on ts
getClass() {
  if (condition) {
    return "class1";
  }
  if (condition) {
    return "class2";
  }
}
```

##### Multiple Class

```java
// html
<li [ngClass]="getClass()"> </li>
// on ts
getClass() {
  class = []
  if (condition) {
    class.push("class1");
  }
  if (condition) {
    class.push("class2");
  }
  return class;
}
```

#### Custom Attribute Directives

Run `ng generate directive directive_name`

```ts
//first approach
// html
<h1 appClass [backgroundColor]= " 'orange' "> </h1>

// ts
import { Directive, ElementRef, Input } from '@angular/core';

@Directive({
  selector: '[appClass]'
})
export class ClassDirective {
  constructor(private element: ElementRef) { // element here will the element that this directive use to.
  }
  
  @Input() set backgroundColor(color: string) {
    this.element.nativeElement.style.backgroundColor = color;
  }
}

// below approach is equivalent to the approach above

// html
<h1 [appClass]= " {} "> </h1>

// ts
import { Directive, ElementRef, Input } from '@angular/core';

@Directive({
  selector: '[appClass]'
})
export class ClassDirective {
  constructor(private element: ElementRef) { // element here will the element that this directive use to.
  }
  // @Input('appClass') tell the html to look for appClass attributes
  // so the html will be <h1 appClass [appClass]= " 'orange' "> </h1>
  // since appClass and [appClass] having same name we can combine it 
  // <h1 [appClass]= " 'orange' "> </h1>
  @Input('appClass') set classNames(classObj: any) {
    for (let key in classObj) {
      if (classObj[key]) {
				this.element.nativeElement.classList.add(key);
      } else {
        this.element.nativeElement.classList.remove(key);
      }
    }
  }
}
```

### Structure Directives

Adds or removes HTML elements

#### Ng-if

`*ngIf="isReal"`

```ts
<p *ngIf="isReal"; else notReal">
<ng-template #notReal>
<p></P>
</ng-template>
```

#### Ng-for

`*ngFor="let server of servers; let i = index"`

#### Custom Structure Directives

Run `ng generate directive directive_name`

```ts
//first approach
// html
<h1 *appTimes=5> </h1>

// ts
import { Directive, TemplateRef, ViewContainerRef, Input } from '@angular/core';

@Directive({
  selector: '[appTimes]'
})
export class ClassDirective {
  constructor(
  // ref to html element, viewcontainerRef provides more element control.
  	private viewComtainer: ViewContainerRef, 
     // ref to child element
    private templateRef: TemplateRef<any>) {}
  
  @Input('appTimes') set render(times: number) {
    this.viewContainer.clear(); //reset the child elements
    // context object that are variables that can accessed by html
    let contextObj = {
        index: i  
      }
    for (let i = 0; i < times; i++) {
      this.viewContainer.createEmbeddedView(this.templateRef, contextObj);
    }
  }
}
```



## Models

```ts
// blueprint
//ver 1
export class Recipe {
  public name: string;
  public description: string;
  public imagePath: string;
  
	constructor(name: string, desc: string, imagePath: string) {
    this.name = name;
    this.description = desc;
    this.imagePath = imagePath;
  }
}
// ver2
export class Recipe {
	constructor(public name: string, public description: string, public imagePath: string) {
  }
}
```

## CSS

Special selector `:host` to style own component.

`:host:not(:first-of-type)` choose every element except for first one.

```css
# hide the element if it is empty.
div.header:empty {
	display: none;
}
```



## Pipe

Pipe is a value formatter in Angular.

### Currency Pipe

```html
<!--output '$0.26'-->
<p>A: {{a | currency}}</p>
```

### Jason Pipe

```html
<p>With JSON pipe:</p>
<pre>{{object | json}}</pre>
```

### Data Pipe

```html
<div>
   <p>Today is {{today | date}}</p>
   <p>Or if you prefer, {{today | date:'fullDate'}}</p>
   <p>The time is {{today | date:'h:mm a z'}}</p>
 </div>`
```

### Custom Pipe

```ts
ng generate pipe pipe_name

// ts
import {Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'convert'
})

export class ConvertPipe implements PipeTransform {
  transform(value: any, ...args: any[]):  any {
    return null;
  }
}
```

## Routing

Run `ng new comps --routing`

```ts

import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ElementsHomeComponent } from 'elements_path';

const routes: Routes = [
  // change the routing order by edit the imports modules in app.module.ts
  { path: 'elements', component: ElementsHomeComponent },
  { path: '**', component: NotFoundComponent}
];

@NgModule({
  imports : [RouterModule.forChild(routes)],
  exports: [RouterModule]
})  
export class ElementsRoutingModule {}


// app.component.html
[routerLinkActiveOptions]="{ exact: true}" // only active if it is exact match
<a routerLink="/elements" routerLinkActive="active">Elements</a> /// active is a class name  
<a routerLink="/collections" routerLinkActive="active">Collections</a>
<router-outlet></router-outlet>
```

### Routing Children

```ts
const routes: Routes = [
  {
    path: '',
    component: CollectionsHomeComponent,
    children: [
      { path: '', component: Child1Component},
      { path: 'partners', component: Child2Component}
    ]
	}
]
```

## Lazy Loading

```ts
// app routing module
const routes: Routes = [
  // change the routing order by edit the imports modules in app.module.ts
  { path: 'elements', 
   loadChildren: () => 
   		import('./elements/elements.module').then(m => m.ElementsModule)
  }
];


```

## NG-Content

`<ng-content>` will use inside the reuse component

```html
<!-- reuse template -->
<h1>
<ng-content> </ng-content>
</h1>

<!-- main template -->
<app-reuse>
  Hello <!-- now, ng-content will has content Hello -->
</app-reuse>


```

## Modal Issue

**Issue**: If a modal is inside a container with position `relative`, the modal will be displayed centered relative to it's parent which is not what we want.

**Solution**: Make the modal as direct child of the body.

```ts
export class ModalComponent implements OnInit {

  constructor(private el: ElementRef) { }

  ngOnInit() {
    document.body.appendChild(this.el.nativeElement);
  }
}
```

## Lifecycle Hook

| Hook method               | Purpose                                                      | Timing                                                       |
| :------------------------ | :----------------------------------------------------------- | :----------------------------------------------------------- |
| `ngOnChanges()`           | Respond when Angular sets or resets data-bound input properties. The method receives a `SimpleChanges` object of current and previous property values.Note that this happens very frequently, so any operation you perform here impacts performance significantly. See details in [Using change detection hooks](https://angular.io/guide/lifecycle-hooks#onchanges) in this document. | Called before `ngOnInit()`and whenever one or more data-bound input properties change.Note that if your component has no inputs or you use it without providing any inputs, the framework will not call `ngOnChanges()`. |
| `ngOnInit()`              | Initialize the directive or component after Angular first displays the data-bound properties and sets the directive or component's input properties. See details in [Initializing a component or directive](https://angular.io/guide/lifecycle-hooks#oninit) in this document. | Called once, after the first `ngOnChanges()`.                |
| `ngDoCheck()`             | Detect and act upon changes that Angular can't or won't detect on its own. See details and example in [Defining custom change detection](https://angular.io/guide/lifecycle-hooks#docheck) in this document. | Called immediately after `ngOnChanges()` on every change detection run, and immediately after `ngOnInit()` on the first run. |
| `ngAfterContentInit()`    | Respond after Angular projects external content into the component's view, or into the view that a directive is in.See details and example in [Responding to changes in content](https://angular.io/guide/lifecycle-hooks#aftercontent) in this document. | Called *once* after the first `ngDoCheck()`.                 |
| `ngAfterContentChecked()` | Respond after Angular checks the content projected into the directive or component.See details and example in [Responding to projected content changes](https://angular.io/guide/lifecycle-hooks#aftercontent) in this document. | Called after `ngAfterContentInit()` and every subsequent `ngDoCheck()`. |
| `ngAfterViewInit()`       | Respond after Angular initializes the component's views and child views, or the view that contains the directive.See details and example in [Responding to view changes](https://angular.io/guide/lifecycle-hooks#afterview) in this document. | Called *once* after the first `ngAfterContentChecked()`.     |
| `ngAfterViewChecked()`    | Respond after Angular checks the component's views and child views, or the view that contains the directive. | Called after the `ngAfterViewInit()` and every subsequent `ngAfterContentChecked()`. |
| `ngOnDestroy()`           | Cleanup just before Angular destroys the directive or component. Unsubscribe Observables and detach event handlers to avoid memory leaks. See details in [Cleaning up on instance destruction](https://angular.io/guide/lifecycle-hooks#ondestroy) in this document. | Called immediately before Angular destroys the directive or component. |