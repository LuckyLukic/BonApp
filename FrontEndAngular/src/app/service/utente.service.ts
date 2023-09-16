import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Utente } from '../module/utente.interface';
import { environment } from 'src/environments/environment';
import { Observable } from 'rxjs';
import { OwnFavorite } from '../module/own-favorite.interface';

import { Dish } from '../module/dish.interface';
import { Favorite } from '../module/favorite.interface';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  baseUrl = environment.baseUrl

  constructor(private http: HttpClient) { }

  createUser(user:Partial<Utente>) {
    return this.http.post<Utente>(this.baseUrl+"users", user)
  }

  deleteUser(id:string) {
    return this.http.delete<Utente>(this.baseUrl+`users/${id}`)
  }

  getUsers() {
    return this.http.get<Utente[]>(this.baseUrl+"users")
  }

  getSingleUsers(id:string) {
    return this.http.get<Utente>(this.baseUrl+`users/${id}`)
  }

  getCurrentUser():Observable <any> {
    return this.http.get<any>(this.baseUrl+"users/current")
  }

  getOwnFavorites(id:string) {
    return this.http.get<Favorite>(this.baseUrl+`users/${id}/favorites`)
  }
}
