import { Component, OnInit } from '@angular/core';
import { Reviews } from 'src/app/module/reviews.interface';
import { Utente } from 'src/app/module/utente.interface';
import { AuthService } from 'src/app/service/auth.service';
import { ReviewsService } from 'src/app/service/reviews.service';
import { Subscription } from 'rxjs';
import { UserService } from 'src/app/service/utente.service';


@Component({
  selector: 'app-reviews',
  templateUrl: './reviews.component.html',
  styleUrls: ['./reviews.component.scss']
})
export class ReviewsComponent implements OnInit {

  reviewList!: Reviews[];
  utente!: Partial<Utente> | null;
  private subscription!: Subscription;

  constructor(private userSrv : UserService, private revSrv : ReviewsService) { }

  ngOnInit(): void {

    this.userSrv.initializeLoginStatus()
    this.subscription = this.userSrv.currentUser$.subscribe(utente => {
      this.utente = utente;
    });

   this.revSrv.getAllReviews().subscribe((allReviews: Reviews) => {
    this.reviewList = allReviews.content;
   })
}

ngOnDestroy(): void {

  this.subscription.unsubscribe();
}
}


