import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PersonService {

  constructor(private http:HttpClient) {

  }

  getAllPerson(): Observable<any>{
    return this.http.get('//localhost:8080/person');
  }
  
  get(id: string) {
   return this.http.get('//localhost:8080/person' + '/' + id);
 }
 
 delete(id: string) {
   return this.http.delete('//localhost:8080/person' + '/' + id);
 }
 save(person: any): Observable<any> {
    
  console.log("In Save!!!!!!!!!!!!!!!!!!!!!!!!!")
  console.log(person);
  let result: Observable<Object>;    
  console.log("In Save!!!!!!!!!!!!!!!!!!!!!!!!!");
  result = this.http.put('//localhost:8080/person'+'/'+person.id, person);
    console.log("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX" + result);
    return result;
}


}
