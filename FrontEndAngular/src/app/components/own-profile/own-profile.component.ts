import { Component, OnInit } from '@angular/core';
import { Utente } from 'src/app/module/utente.interface';
import { Router } from '@angular/router';
import { UserService } from 'src/app/service/utente.service';
import { ActivatedRoute, ParamMap } from '@angular/router';
import { AuthService } from 'src/app/service/auth.service';

@Component({
  selector: 'app-own-profile',
  templateUrl: './own-profile.component.html',
  styleUrls: ['./own-profile.component.scss']
})
export class OwnProfileComponent implements OnInit {

  utenti!: Utente [];
  utente!: Utente;

  constructor(private route: ActivatedRoute, private userSrv: UserService, private authSrv: AuthService, private router: Router) { }

  ngOnInit(): void {
    this.route.paramMap.subscribe((data: ParamMap) => {
      const singleId = data.get('id')!;
      this.userSrv.getSingleUsers(singleId).subscribe((data: Utente) => {
        this.utente = data

    })
  })

}

deleteUser(id:string):void {
  this.userSrv.deleteUser(id).subscribe()
  this.authSrv.logout()
  this.router.navigate(['/']);

}
}
