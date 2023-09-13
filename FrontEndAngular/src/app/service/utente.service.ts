import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Utente } from '../module/utente.interface';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  baseUrl = environment.baseUrl

  constructor(private http: HttpClient) { }

  createUser(user:Partial<Utente>) {
    return this.http.post<Utente>(this.baseUrl+"users", user)
  }

  deleteUser(id:number) {
    return this.http.delete<Utente>(this.baseUrl+`users/${id}`)
  }

  getUsers() {
    return this.http.get<Utente[]>(this.baseUrl+"users")
  }

  getSingleUsers(id:number) {
    return this.http.get<Utente>(this.baseUrl+`users/${id}`)
  }
}
