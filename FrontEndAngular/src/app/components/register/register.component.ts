import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators} from '@angular/forms';
import { Utente } from 'src/app/module/utente.interface';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/service/auth.service';


@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {
  pattern = '^(?=.*[A-Z])(?=.*[!@#$%^&*])(?=.*[0-9]).{8,}$';
  newUser!: Utente;

  registerForm = this.builder.group({
    username: ['', [Validators.required, Validators.minLength(8)]],
    name: ['', Validators.required],
    surname: ['', Validators.required],
    indirizzo: this.builder.group({
      via: ['', Validators.required],
      civico: ['', Validators.required],
      localita: ['', Validators.required],
      cap: ['', Validators.required],
      comune: ['', Validators.required],
      provincia: ['', Validators.required],
    }),
    email: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required, Validators.pattern(this.pattern)]],
  });

  constructor(
    private builder: FormBuilder,
    private authSrv: AuthService,
    private route: Router
  ) {}

  ngOnInit(): void {}

  onSubmit() {
    this.newUser = this.registerForm.value as Utente;

    try {
      this.authSrv.registra(this.newUser).subscribe(() => {
        this.registerForm.reset();
        this.route.navigate(['/login']);
      }, (error: any) => {
        if (error.status === 400) {
          alert('email already registered');
          this.route.navigate(['/register']);
        }
      });
    } catch (error: any) {
      console.error('An unexpected error occurred', error);
    }
  }
}


