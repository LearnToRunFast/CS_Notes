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
  providers: [],
  bootstrap: [AppComponent] // tell bootsrap to run the component when compile
})
export class AppModule { }
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



### Two Way Binding

For Two-Way-Binding to work, need to enable the `ngModel directive` This is doen by adding `FormsModule` to the `imports[]` array in the AppModule, and import `{ FormsModule } from @angular/forms` at top.

Use `[(ngModel)]` 

Eg. `[(ngModel)]="serverName"`

## Directives

Directives are instructions in the DOM.

### Structure Directives

Adds or removes HTML elements

### Ng-if

`*ngIf="isReal"`

```ts
<p *ngIf="isReal"; else notReal">
<ng-template #notReal>
<p></P>
</ng-template>
```

### Ng-for

`*ngFor="let server of servers; let i = index"`

### Attribute Directives

Changes the properties of the HTML element it gets applied to.

#### ngStyle

Change style dynamically.

`[ngStyle]="{backgroundColor: getColor()}"`

#### ngClass

Change class dynamically.

`[ngClass]="{online: status == 'online'}"`

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

