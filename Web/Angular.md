[toc]



# Angular

**Installation**:`npm install -g @angular/cli`

**Start a Project**:`ng new <project_name>`

**Run project**:`ng serve`

**Project Running Flow**: `main.ts`  -> `app.module.ts` -> `app.component.ts`

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

Use `{{}}`

#### Property Binding

Use `[]`, place a property inside `[]`.

Eg. `[disabled]="!allowNewServer"`

### React to User Event

#### Event Buiding

Use `()`, 

`$event` get access to event data, the data is on `(<HTMLInputElement>event.target).value`

Eg `(click)="onCreateServer()"`

### Two Way Binding

For Two-Way-Binding to work, need to enable the `ngModel directive` This is doen by adding `FormsModule` to the `imports[]` array in the AppModule, and import `{ FormsModule } from @angular/forms` at top.

Use `[(ngModel)]` 

Eg. `[(ngModel)]="serverName"`

## Directives

Directives are instructions in the DOM.

### Structure Directives

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

#### ngStyle

Change style dynamically.

`[ngStyle]="{backgroundColor: getColor()}"`

#### ngClass

Change class dynamically.

`[ngClass]="{online: status == 'online'}"`

