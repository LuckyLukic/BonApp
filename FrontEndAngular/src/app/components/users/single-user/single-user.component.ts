import { Component, Input, OnInit } from '@angular/core';
import { Utente } from 'src/app/module/utente.interface';
import { ActivatedRoute, ParamMap } from '@angular/router';
import { UserService } from 'src/app/service/utente.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-single-user',
  templateUrl: './single-user.component.html',
  styleUrls: ['./single-user.component.scss']
})
export class SingleUserComponent implements OnInit {

  singleUtente!: Utente;
  singleId!: string;

  constructor(private route: ActivatedRoute, private userSrv: UserService, private router: Router) { }

  ngOnInit(): void {
    this.route.paramMap.subscribe((data: ParamMap) => {
      this.singleId = data.get('id')!;
      this.userSrv.getSingleUsers(this.singleId).subscribe((data: Utente) => {
        this.singleUtente = data
      })
    })
  }
  onSubmit() {
    this.userSrv.deleteUser(this.singleId).subscribe()
    this.router.navigate(['/users'])
    };


}
