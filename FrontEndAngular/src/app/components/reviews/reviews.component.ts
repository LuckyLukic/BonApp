import { Component, OnInit } from '@angular/core';
import { Reviews } from 'src/app/module/reviews.interface';
import { Utente } from 'src/app/module/utente.interface';
import { AuthService } from 'src/app/service/auth.service';
import { ReviewsService } from 'src/app/service/reviews.service';

@Component({
  selector: 'app-reviews',
  templateUrl: './reviews.component.html',
  styleUrls: ['./reviews.component.scss']
})
export class ReviewsComponent implements OnInit {

  reviewList!: Reviews[];
  utente!: Utente;

  constructor(private authSrv : AuthService, private revSrv : ReviewsService) { }

  ngOnInit(): void {

    this.authSrv.user$.subscribe((_utente) => {
      if (_utente) {
        this.utente = _utente;
  }
});
   this.revSrv.getAllReviews().subscribe((allReviews: Reviews) => {
    this.reviewList = allReviews.content;
   })
}
}
