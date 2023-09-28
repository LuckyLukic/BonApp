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
  page = 0;
  pageSize = 10;
  totalPage = 0;

  reviewList!: Reviews[];
  utente!: Partial<Utente> | null;
  private subscription!: Subscription;

  constructor(private userSrv : UserService, private revSrv : ReviewsService) { }

  ngOnInit(): void {

    this.userSrv.initializeLoginStatus()
    this.subscription = this.userSrv.currentUser$.subscribe(utente => {
      this.utente = utente;
    });

   this.allReviews();
}

allReviews() {
  this.revSrv.getAllReviews(this.page, this.pageSize, "reviewDate").subscribe((allReviews: Reviews) => {
  this.reviewList = allReviews.content;
  this.totalPage = allReviews.totalPages

 })
}

previousPage(): void {
  if(this.page>0) {
    this.page--;
    this.allReviews();

  }
}

nextPage(): void {
    if(this.page < this.totalPage - 1) {
    this.page++;
    this.allReviews();
  }
}


ngOnDestroy(): void {

  this.subscription.unsubscribe();
}
}


