import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Utente } from '../module/utente.interface';
import { environment } from 'src/environments/environment';
import { Observable } from 'rxjs';
import { Favorite } from '../module/favorite.interface';
import { BehaviorSubject } from 'rxjs';


@Injectable({
  providedIn: 'root',
})
export class UserService {
  baseUrl = environment.baseUrl;
  private userSubject = new BehaviorSubject<Partial<Utente> | null>(null);
  currentUser$: Observable <Partial<Utente> | null> = this.userSubject.asObservable();

  constructor(private http: HttpClient) {}

  initializeLoginStatus(): void {
    this.getCurrentUser().subscribe((status) => {
      this.userSubject.next(status);
    });
  }

  setUser(data: Partial<Utente> | null) {
    this.userSubject.next(data);
  }

  getUser(): Observable<Partial<Utente> | null> {
    return this.userSubject.asObservable();
  }

  createUser(user: Partial<Utente>) {
    return this.http.post<Utente>(this.baseUrl + 'users', user);
  }

  deleteUser(id: string) {
    return this.http.delete<Utente>(this.baseUrl + `users/${id}`);
  }

  getUsers() {
    return this.http.get<Utente[]>(this.baseUrl + 'users');
  }

  getSingleUsers(id: string) {
    return this.http.get<Utente>(this.baseUrl + `users/${id}`);
  }

  getCurrentUser(): Observable<any> {
    return this.http.get<any>(this.baseUrl + 'users/current');
  }

  getOwnFavorites(id: string) {
    return this.http.get<Favorite>(this.baseUrl + `users/${id}/favorites`);
  }
}
