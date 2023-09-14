import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Reviews } from '../module/reviews.interface';
import { environment } from 'src/environments/environment';
@Injectable({
  providedIn: 'root'
})
export class ReviewsService {

  url = environment.baseUrl

  constructor(private http : HttpClient) { }

  getAllReviews () {
    return this.http.get<Reviews>(this.url+`reviews`)
  }
}
