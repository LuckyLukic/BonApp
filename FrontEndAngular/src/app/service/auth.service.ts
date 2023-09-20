import { EnvironmentInjector, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { AuthData } from '../module/auth-data.interface';
import { environment } from 'src/environments/environment';
import { Router } from '@angular/router';
import { Utente } from '../module/utente.interface';
import { BehaviorSubject, throwError } from 'rxjs';
import { tap, catchError } from 'rxjs/operators';
import { JwtHelperService } from '@auth0/angular-jwt';
import { Registrazione } from '../module/registrazione.interface';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  baseURL = environment.baseUrl;
  utente!: AuthData;
  jwtHelper = new JwtHelperService();
  private authSubj = new BehaviorSubject<null | AuthData>(null);
  user$ = this.authSubj.asObservable();
  timeOut: any;

  constructor(private http: HttpClient, private route: Router) {
    this.restore()
  }

  login(data: { email: string; password: string }) {
    return this.http.post<AuthData>(`${this.baseURL}auth/login`, data).pipe(
      tap((data) => {
        console.log(data);
        this.authSubj.next(data);
        this.utente = data;
        console.log(this.utente);
        localStorage.setItem('user', JSON.stringify(data));
      })
    );
  }

  logout() {
    this.authSubj.next(null);
    localStorage.removeItem('user');
    this.route.navigate(['/login'])
  }

  autologout(data: AuthData) {
    const scadenza = this.jwtHelper.getTokenExpirationDate(
      data.accessToken
    ) as Date;

    const tempoScadenza = scadenza.getTime() - new Date().getTime();
    this.timeOut = setTimeout(() => {
      this.logout()
    }, tempoScadenza)
  }

  registra(data: Partial<Registrazione>) {
    return this.http.post(`${this.baseURL}auth/register`, data);
  }

  restore() {
    const utenteLoggato = localStorage.getItem('user');
    if (!utenteLoggato) {return};

    const datiUtente: AuthData = JSON.parse(utenteLoggato!);
    if (this.jwtHelper.isTokenExpired(datiUtente.accessToken)) {
      return
    }
    this.authSubj.next(datiUtente);
  }

  updateUser(userid: string, data: Partial<Utente>) {
    return this.http.put(this.baseURL+`users/+${userid}`, data )
  }
}
