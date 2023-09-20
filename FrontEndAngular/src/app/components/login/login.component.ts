import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/service/auth.service';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  constructor(private authSrv: AuthService, private route: Router) { }

  ngOnInit(): void {
  }

  accedi(form: NgForm) {

    this.authSrv.login(form.value).subscribe(
      () => {  // This is the success callback
        alert('Login Effettuato!');
        this.route.navigate(['/']);
      },
      (error) => {  // This is the error callback
        alert('Login errato');
        console.error(error);
        this.route.navigate(['/login']);
      }
    );
  }

}
