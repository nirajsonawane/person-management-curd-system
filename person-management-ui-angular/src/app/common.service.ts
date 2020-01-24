import { Injectable } from '@angular/core';
import { MatSnackBarVerticalPosition, MatSnackBar, MatSnackBarConfig } from '@angular/material';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class CommonService {

  verticalPosition: MatSnackBarVerticalPosition = 'top';
  actionButtonLabel: string = 'Retry';

  constructor(private snackBar: MatSnackBar, private router: Router) {
    this.snackBar=snackBar;
   }

  public openSnackBar(message: string, action: string) {
    let config = new MatSnackBarConfig();
    config.verticalPosition = this.verticalPosition;
    config.duration=2000;
    this.snackBar.open(message, action ? this.actionButtonLabel : undefined, config);    
  }

  
  public gotoPerson() {
    this.router.navigate(['/person']);
  }
}
