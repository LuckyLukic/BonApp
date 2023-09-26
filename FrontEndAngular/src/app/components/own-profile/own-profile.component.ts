import { Component, OnInit } from '@angular/core';
import { Utente } from 'src/app/module/utente.interface';
import { Router } from '@angular/router';
import { UserService } from 'src/app/service/utente.service';
import { ActivatedRoute, ParamMap } from '@angular/router';
import { AuthService } from 'src/app/service/auth.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-own-profile',
  templateUrl: './own-profile.component.html',
  styleUrls: ['./own-profile.component.scss']
})
export class OwnProfileComponent implements OnInit {

  utente!: Partial <Utente> | null;
  private subscription!: Subscription;

  constructor(private route: ActivatedRoute, private userSrv: UserService, private authSrv: AuthService, private router: Router) { }

  ngOnInit(): void {
    this.userSrv.initializeLoginStatus()
    this.subscription = this.userSrv.currentUser$.subscribe(utente => {
      this.utente = utente;

    })
}

deleteUser(id:string):void {
  this.userSrv.deleteUser(id).subscribe()
  this.authSrv.logout()
  this.router.navigate(['/']);

}

ngOnDestroy(): void {
  this.subscription.unsubscribe();
}
}
