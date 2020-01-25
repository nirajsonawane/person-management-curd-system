import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PersonComponent } from './person/person.component';
import { RouterModule, Routes } from '@angular/router';
import { PersonEditComponent } from './person-edit/person-edit.component';
import { PersonAddComponent } from './person-add/person-add.component';
import { AboutAppComponent } from './about-app/about-app.component';

const routes: Routes = [
  { path: '', redirectTo: '/person', pathMatch: 'full' },
  { path: 'person', component:  PersonComponent},
  { path: 'person-edit/:id', component:  PersonEditComponent},
  { path: 'person-add', component:  PersonAddComponent},
  { path: 'aboutApp', component:  AboutAppComponent},
  
  
];
export const appRouting = RouterModule.forRoot(routes);

@NgModule({
  imports: [
    RouterModule.forRoot(routes),
    CommonModule
  ],
  exports: [ RouterModule ],
  declarations: []
})

export class AppRoutingModule { }
