import { Component, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { MatSort } from '@angular/material/sort';
import { MatSnackBar, MatSnackBarConfig, MatSnackBarVerticalPosition } from '@angular/material';
import { PersonService } from '../person.service';
import { CommonService } from '../common.service';


@Component({
  selector: 'app-person',
  templateUrl: './person.component.html',
  styleUrls: ['./person.component.css']
})
export class PersonComponent implements OnInit {

  dataSource = new MatTableDataSource<any>();
  displayedColumns: string[] = ['id', 'first_name', 'last_name', 'age', 'favourite_colour','hobby','update', 'delete'];

  //@ViewChild(MatPaginator) paginator: MatPaginator;
  //@ViewChild(MatSort) sort: MatSort;

  constructor(private personService:PersonService,private commonService : CommonService,private snackBar: MatSnackBar) {
    
    this.personService.getAllPerson().subscribe(data => {
      this.dataSource = new MatTableDataSource(data);;
     // this.dataSource.sort = this.sort;
      //this.dataSource.paginator = this.paginator;
    },
    error => commonService.openSnackBar("Error While getting data from Server!", ""));
    this.snackBar = snackBar;
   };

   refreshData() {
    this.personService.getAllPerson().subscribe(data => {
      this.dataSource = new MatTableDataSource(data);;
    });
  }

   public redirectToDelete = (id: string) => {
    console.log(id);
    this.personService.delete(id).subscribe(result => {
      this.refreshData();
      this.commonService.openSnackBar("Person Deleted", "");
    }, error => this.commonService.openSnackBar("Error While deleting  data", "")
    );

    
    
  }

  ngOnInit() {

  }

}
