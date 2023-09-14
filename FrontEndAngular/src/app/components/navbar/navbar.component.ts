import { Component, OnInit } from '@angular/core';
import { AuthData } from 'src/app/module/auth-data.interface';
import { AuthService } from 'src/app/service/auth.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent implements OnInit {

  utente!: AuthData | null;

  constructor(private authSrv: AuthService) { }

  ngOnInit(): void {
    this.authSrv.user$.subscribe((_utente) => {
      console.log("Received userlogin: ", _utente);
      this.utente = _utente;
    })
  }

  logout() {
    this.authSrv.logout();
  }

}

