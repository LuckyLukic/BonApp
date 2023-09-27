import { Component, OnInit } from '@angular/core';
import { Utente } from 'src/app/module/utente.interface';
import { FormBuilder, Validators} from '@angular/forms';
import { UserService } from 'src/app/service/utente.service';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/service/auth.service';
import { FormGroup } from '@angular/forms';


@Component({
  selector: 'app-update',
  templateUrl: './update.component.html',
  styleUrls: ['./update.component.scss']
})
export class UpdateComponent implements OnInit {

  constructor(
    private builder: FormBuilder,
    private route: Router,
    private userSrv: UserService,
    private authSrv: AuthService

  ) { }

utente!: Utente
newUser!: Utente;
registerForm!: FormGroup;


ngOnInit(): void {

  this.userSrv.getCurrentUser().subscribe((_utente) => {
    this.utente = _utente;
    this.initializeForm();
})

}

initializeForm() {
  this.registerForm = this.builder.group({

      username: this.utente.surname,
      name: this.utente.name,
      surname: this.utente.surname,
      email: this.utente.email,

    indirizzo: this.builder.group({
      cap: this.utente.indirizzo.cap,
      civico: this.utente.indirizzo.civico,
      localita: this.utente.indirizzo.localita,
      via: this.utente.indirizzo.via,
      comune: this.utente.indirizzo.comune,
      provincia: this.utente.indirizzo.provincia
    }),
  });
}



onSubmit() {

  this.newUser = this.registerForm.value as Utente;
  console.log("Utente", this.utente)
  console.log("onSUBMIT", this.utente.id)



  this.authSrv.updateUser(this.utente.id!, this.newUser)
    .subscribe(
      () => {
        // You can use a service or a simple alert to show a success message
        alert('Update successful');
        this.route.navigate([`/ownprofile/${this.utente.id}`]);
      },
      (error) => {
        console.error('Update failed', error);
        // Handle the error here, maybe show the user a message
      }
    );
}

}
