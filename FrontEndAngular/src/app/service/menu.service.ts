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

  addFavorite (favorite: Favorite) {
    return this.http.post(this.url+`users/add-favorites`, favorite)
    }

  removeFavorite (favoriteId:string) {
    return this.http.delete(this.url+`favorites/${favoriteId}`)
  }

}

