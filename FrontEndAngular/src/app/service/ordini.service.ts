import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { OrdineSingolo } from '../module/ordine-singolo.interface';
import { environment } from 'src/environments/environment';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class OrdiniService {

  url = environment.baseUrl

  constructor(private http: HttpClient) { }

  getAllCompletedOrder(userId:string) {
   return this.http.get<OrdineSingolo[]>(this.url+`users/${userId}/completed`)
  }

}
