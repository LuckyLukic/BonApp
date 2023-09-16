import { Component, OnInit } from '@angular/core';
import { AuthData } from 'src/app/module/auth-data.interface';
import { Utente } from 'src/app/module/utente.interface';
import { AuthService } from 'src/app/service/auth.service';
import { UserService } from 'src/app/service/utente.service';
import { Router, NavigationEnd } from '@angular/router';
import { filter } from 'rxjs';


@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent implements OnInit {

  utente!: Partial <Utente> | null

  constructor(private authSrv: AuthService, private userSrv: UserService,private router: Router) { }

  ngOnInit(): void {

    this.userSrv.getCurrentUser().subscribe((_utente) => {

        this.utente = _utente;
      });

      // this.router.events
      // .pipe(
      //   filter(event => event instanceof NavigationEnd)
      // )
      // .subscribe(() => {
      //   this.userSrv.getCurrentUser().subscribe((_utente) => {

      //     this.utente = _utente;
      //   });

      // });

}

  logout() {
    this.authSrv.logout();
    this.utente = null;
  }

}

