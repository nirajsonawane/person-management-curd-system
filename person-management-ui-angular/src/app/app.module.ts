import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import { AppComponent } from './app.component';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatTabsModule} from '@angular/material/tabs';
import { AppRoutingModule } from './app-routing.module';
import { PersonComponent } from './person/person.component';
import { PersonEditComponent } from './person-edit/person-edit.component';

import { HttpClientModule } from '@angular/common/http';
import {MatListModule} from '@angular/material/list';
import {MatTableModule} from '@angular/material/table';
import { FormsModule } from '@angular/forms';
import {MatCardModule} from '@angular/material/card';
import {MatFormFieldModule } from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';

import {MatSelectModule} from '@angular/material/select';
import {MatButtonModule} from '@angular/material/button';
import { ReactiveFormsModule } from '@angular/forms';
import {MatGridListModule} from '@angular/material/grid-list';
import {MatIconModule} from '@angular/material/icon';
import {MatPaginatorModule} from '@angular/material/paginator';
import {MatSnackBarModule} from '@angular/material/snack-bar'



@NgModule({
  declarations: [
    AppComponent,
    PersonComponent,
    PersonEditComponent
  ],
  imports: [
    BrowserModule,
    MatToolbarModule,
    MatTabsModule,
    BrowserAnimationsModule,
    AppRoutingModule,

    BrowserModule,
    MatToolbarModule,
    MatTabsModule,
    MatListModule,
    MatTableModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatButtonModule,
    MatGridListModule,
    
    MatIconModule,
   
    MatPaginatorModule,
    MatSnackBarModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    HttpClientModule,
    ReactiveFormsModule,     
    FormsModule

  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
