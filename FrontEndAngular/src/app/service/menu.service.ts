import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient } from '@angular/common/http';
import { Dish } from '../module/dish.interface';
import { Favorite } from '../module/favorite.interface';

@Injectable({
  providedIn: 'root'
})
export class MenuService {

  url = environment.baseUrl

  constructor(private http: HttpClient) { }

  getMenu() {
    return this.http.get<Dish>(this.url+"prodotti")

  }

  getDishDetail (id:string) {
    return this.http.get<Dish>(this.url+`prodotti/${id}`)

  }

  getFavorites (userId:string) {
    return this.http.get<Favorite[]>(this.url+`userId=${userId}/favorites`)
    }

  getTopFavorites () {
    return this.http.get<Favorite>(this.url+`users/top-favorites`)
  }

  addFavorite(userId: string, dishId: string) {
      return this.http.post(this.url + `users/${userId}/add-favorite/${dishId}`, {});
    }


    removeFavorite(userId: string, dishId: string) {
      return this.http.delete(this.url + `users/${userId}/remove-favorite/${dishId}`);
    }

}

