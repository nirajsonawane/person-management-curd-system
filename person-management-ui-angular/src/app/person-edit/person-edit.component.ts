import { Component, OnInit } from '@angular/core';
import { Location } from '@angular/common';

import { FormGroup, Validators, FormControl } from '@angular/forms';
import { Subscription } from 'rxjs';
import { ActivatedRoute } from '@angular/router';
import { PersonService } from '../person.service';
import { CommonService } from '../common.service';

export interface PersonForCreation {
  first_name: string;
  last_name: string;
  age: number;
  id: number;
  favourite_colour: string;
  hobby : string;
}
@Component({
  selector: 'app-person-edit',
  templateUrl: './person-edit.component.html',
  styleUrls: ['./person-edit.component.css']
})


export class PersonEditComponent implements OnInit {

  public personForm: FormGroup;
  sub: Subscription;
  personFromDB: any = {};

  constructor(private route: ActivatedRoute,
    private location: Location,
    private service: PersonService,
    private commonService: CommonService) { }

 
    ngOnInit() {
      
      this.personForm = new FormGroup({
        first_name: new FormControl('', [Validators.required, Validators.maxLength(60)]),
        last_name: new FormControl(''),
        age: new FormControl(''),
        id: new FormControl(''),
        hobby: new FormControl(''),
        favourite_colour: new FormControl(''),
      });
 
 
 
      this.sub = this.route.params.subscribe(params => {
        const id = params['id'];
        if (id) {
          this.service.get(id).subscribe((patient: any) => {
            if (patient) {
              console.log('patient found in database');
              this.personFromDB = patient;
            } else {
              console.log(`patient with id '${id}' not found, returning to list`);
              //this.commonService.gotoPatient();
            }
          });
        }
      });
    }

    public createPerson = (personValue) => {

      if (this.personForm.valid) {
        console.log(this.personForm)
       
        let person: PersonForCreation = {
          first_name: personValue.first_name,
          last_name: personValue.last_name,
          age: personValue.age,          
          id: personValue.id,
          hobby: JSON.parse('["'+personValue.hobby+'"]') ,
          favourite_colour: personValue.favourite_colour,
        }
        console.log(person)
        this.service.save(person).subscribe(result => {
          this.commonService.openSnackBar("Person Updated", "");
         this.commonService.gotoPerson();
        }, error => this.commonService.openSnackBar("Error While saving Data", ""));
  
      }
    }
    
    
}
