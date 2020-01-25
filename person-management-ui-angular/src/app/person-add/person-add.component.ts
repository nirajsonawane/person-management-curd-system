import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Subscription } from 'rxjs';
import { ActivatedRoute } from '@angular/router';
import { CommonService } from '../common.service';
import { PersonService } from '../person.service';

export interface PersonForCreation {
  first_name: string;
  last_name: string;
  age: number;
  id: number;
  favourite_colour: string;
  hobby : string;
}
@Component({
  selector: 'app-person-add',
  templateUrl: './person-add.component.html',
  styleUrls: ['./person-add.component.css']
})
export class PersonAddComponent implements OnInit {

  public personForm: FormGroup;
  sub: Subscription;
  personFromDB: any = {};

  constructor(
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

  }

  public hasError = (controlName:string, errorName:string) => {
    return this.personForm.controls[controlName].hasError(errorName);
    
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
      
      this.service.insert(person).subscribe(result => {
        this.commonService.openSnackBar("Persion Saved", "");
       this.commonService.gotoPerson();
      }, error => this.commonService.openSnackBar("Error While saving Data", ""));

    }
  }

}
