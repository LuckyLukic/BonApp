import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/service/utente.service';
import { Utente } from 'src/app/module/utente.interface';
import { Router } from '@angular/router';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.scss']
})
export class UsersComponent implements OnInit {

  utenti!: Utente [];
  utente!: Utente;

  constructor(private userSrv: UserService, private route: Router) { }

  ngOnInit(): void {
    this.userSrv.getUsers().subscribe((data: Utente[]) => {
      this.utenti = data
    });
  }



}
