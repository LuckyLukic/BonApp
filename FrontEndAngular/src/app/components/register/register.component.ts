import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators, AbstractControl} from '@angular/forms';
import { Registrazione } from 'src/app/module/registrazione.interface';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/service/auth.service';
import { catchError } from 'rxjs/operators';
import { of } from 'rxjs';



@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})

export class RegisterComponent implements OnInit {

  constructor(
    private builder: FormBuilder,
    private authSrv: AuthService,
    private route: Router
  ) {}

  pattern = '^(?=.*[A-Z])(?=.*[!@#$%^&*])(?=.*[0-9]).{8,}$';
  newUser!: Registrazione;

  registerForm = this.builder.group({
    newUserPayload: this.builder.group({
      username: ['', [Validators.required, Validators.minLength(8)]],
      name: ['', Validators.required],
      surname: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.pattern(this.pattern)]],
      confirmPassword: ['', Validators.required],
    }, { validators: this.passwordMatchValidator }),
    newIndirizzoPayload: this.builder.group({
      cap: ['', Validators.required],
      civico: ['', Validators.required],
      localita: ['', Validators.required],
      via: ['', Validators.required],
      comune: ['', Validators.required],
      provincia: ['', Validators.required],
    }),
  });



  ngOnInit(): void {}

    passwordMatchValidator(control: AbstractControl) {
    const password = control.get('password');
    const confirmPassword = control.get('confirmPassword');

    return password && confirmPassword && password.value !== confirmPassword.value
      ?  { passwordMismatch: true }
      : null;
  }

  onSubmit() {

  this.newUser = this.registerForm.value as Registrazione;


  this.authSrv.registra(this.newUser)
    .pipe(
      catchError((error) => {
        if (error.status === 400) {
          alert('Email already registered');
          return of(null);
        }
        throw error;
      })
    )
    .subscribe(() => {
      alert('You are successfully registered!');
      this.registerForm.reset();
      this.route.navigate(['/login']);
    });
  }


}


