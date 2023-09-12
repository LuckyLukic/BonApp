import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient } from '@angular/common/http';
import { Dish } from '../module/dish.interface';

@Injectable({
  providedIn: 'root'
})
export class MenuService {

  url = environment.baseUrl

  constructor(private http: HttpClient) { }

  getMenu() {
    return this.http.get<Dish[]>(this.url+"prodotti")

  }

  // getMovieDetail (id:number) {
  //   return this.http.get<Movie>(this.url+`movies-popular/${id}`)

  // }

  // getFavourites (userId:number) {
  //   return this.http.get<Favourite[]>(this.url+`favorites?userId=${userId}`)
  //   }

  // addFavourite (favorite: Favourite) {
  //   return this.http.post(this.url+"favorites", favorite)
  //   }

  // removeFavorite (favoriteId:number) {
  //   return this.http.delete(this.url+`favorites/${favoriteId}`)
  // }
}
