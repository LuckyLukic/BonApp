import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Utente } from 'src/app/module/utente.interface';
import { UserService } from 'src/app/service/utente.service'
import { Reviews } from 'src/app/module/reviews.interface';
import { ReviewsService } from 'src/app/service/reviews.service';
import { Router } from '@angular/router';


@Component({
  selector: 'app-rate-us',
  templateUrl: './rate-us.component.html',
  styleUrls: ['./rate-us.component.scss']
})
export class RateUsComponent implements OnInit {

  constructor(
    private userSrv: UserService,
    private builder: FormBuilder,
    private revSrv: ReviewsService,
    private route: Router
    ) { }

  utente!: Utente
  reviewForm!: FormGroup;
  date = new Date;
  review!: Reviews

  ngOnInit(): void {

    this.userSrv.getCurrentUser().subscribe((_utente) => {
      this.utente = _utente;
      this.initializeForm();

    })
  }



  initializeForm() {
    this.reviewForm = this.builder.group({

      username: this.utente.surname,
      date: this.date.getFullYear(),
      title: ['', Validators.required],
      comment: ['', Validators.required],
      rating: [0, Validators.required]

    });
  }



  onStarClick(rating: number) {
    this.reviewForm.patchValue({ rating });
  }


  onSubmit() {
    this.review = this.reviewForm.value as Reviews;

    this.revSrv.saveOwnReview(this.utente.id!, this.review).
      subscribe(
        () =>{
        alert('Review saved')
        this.route.navigate(['/']);
      },
      (error) => {
        alert('Review not saved!')
        console.error('Review failed', error);
    }
      );
  }
}




