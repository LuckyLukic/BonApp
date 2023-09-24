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
  review!: Reviews;
  starRating:number = 0;

  ngOnInit(): void {

    this.initializeForm();

    this.userSrv.getCurrentUser().subscribe((_utente) => {

      this.utente = _utente;
      this.reviewForm.patchValue({
        username: this.utente.surname,

    });
  },
  (error) => {
    console.error('Error retrieving user:', error);
    // Handle the error accordingly
  }
);

}



  initializeForm() {
    this.reviewForm = this.builder.group({

      username: [''],
      date: [this.date.getFullYear()],
      title: ['', Validators.required],
      comment: ['', Validators.required],
      rating: ['', Validators.required]

    });
  }


    onSubmit() {
      this.reviewForm.patchValue({ rating: this.starRating });

      if (this.reviewForm.get('rating')?.errors) {
        console.error('Rating has errors:', this.reviewForm.get('rating')?.errors);
        return;
      }

      console.log('Form Status:', this.reviewForm.status);
    this.review = this.reviewForm.value as Reviews;
    console.log("Valori", this.review)
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





