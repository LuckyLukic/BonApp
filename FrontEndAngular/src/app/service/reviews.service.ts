import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Reviews } from '../module/reviews.interface';
import { environment } from 'src/environments/environment';
import { Observable } from 'rxjs';
import { HttpParams } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ReviewsService {

  url = environment.baseUrl

  constructor(private http : HttpClient) { }

  getAllReviews (page: number, size: number, sortBy: string): Observable<Reviews> {
    const params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString())
      .set('sortBy', sortBy);
    return this.http.get<Reviews>(this.url+"reviews", {params})
  }

  getOwnReviews (id:string) {
    console.log('User ID:', id);
    return this.http.get<Reviews>(this.url+`users/${id}/getOwnReviews`)
  }

  deleteOwnReview (userId:string, reviewId:string): Observable<any> {
    return this.http.delete<any>(this.url+`users/${userId}/delete-own-review/${reviewId}`)
  }

  saveOwnReview (userId:string, review:Reviews) {
    return this.http.post(this.url+`users/${userId}/new-review`, review)
  }
}
