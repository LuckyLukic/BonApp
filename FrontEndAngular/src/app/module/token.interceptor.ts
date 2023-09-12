import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor
} from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from '../service/auth.service';
import { take, switchMap } from 'rxjs';

@Injectable()
export class TokenInterceptor implements HttpInterceptor {

  constructor(private authSrv: AuthService) {}

  nuovaRichiesta!: HttpRequest<any>

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    return this.authSrv.user$.pipe(
      take(1),
      switchMap((utente => {
        if (!utente) {
          console.log(`richiesta originaria: ${request}`);
          console.log(`Richiesta modificata: ${this.nuovaRichiesta}`);
          return next.handle(request);
        }

        this.nuovaRichiesta = request.clone ({
          headers: request.headers.set(
          'Authorization',
          `Bearer  ${utente.accessToken}`
          ),
        });

        console.log(`richiesta originaria: ${request}`);
        console.log(`Richiesta modificata: ${this.nuovaRichiesta}`);
        return next.handle(this.nuovaRichiesta);
      })
    )
    )
  }
}
