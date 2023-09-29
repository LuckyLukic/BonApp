import { Component, OnInit } from '@angular/core';
import { Reviews } from 'src/app/module/reviews.interface';
import { Utente } from 'src/app/module/utente.interface';
import { ReviewsService } from 'src/app/service/reviews.service';
import { ActivatedRoute, ParamMap } from '@angular/router';
import { UserService } from 'src/app/service/utente.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-own-reviews',
  templateUrl: './own-reviews.component.html',
  styleUrls: ['./own-reviews.component.scss'],
})
export class OwnReviewsComponent implements OnInit {
  utente!: Partial<Utente> | null;
  ownReviewsList!: Reviews[];
  private subscription!: Subscription;

  constructor(
    private revService: ReviewsService,
    private route: ActivatedRoute,
    private userSrv: UserService
  ) {}

  ngOnInit(): void {
    this.userSrv.initializeLoginStatus();
    this.subscription = this.userSrv.currentUser$.subscribe((utente) => {
      this.utente = utente;

      if (this.utente && this.utente.id) {
        console.log(' Utente' + this.utente);

        this.getOwnReviews(this.utente.id!);
      }
    });
  }

  modalConfirmWithdraw(): void {
    const okModal = document.getElementById('modalConfirmWithdraw');

      okModal!.classList.add('show');
      okModal!.style.display = 'block';
  }

  closeModal():void {
    const okModal = document.getElementById('modalConfirmWithdraw');

      okModal!.classList.remove('show');
      okModal!.style.display = 'none';

  }

  getOwnReviews(id: string): void {
    this.revService.getOwnReviews(id).subscribe(
      (element: Reviews) => {
        this.ownReviewsList = element.content;
        console.log('Reviews', this.ownReviewsList);
      },
      (error) => {
        console.error('Error fetching favorite dishes:', error);
      }
    );
  }

  removeOwnReview(reviewId: string): void {
    if (this.utente && this.utente.id) {
      this.revService.deleteOwnReview(this.utente.id, reviewId).subscribe(
        (response) => {
          alert('Recensione rimossa correttamente');
          this.getOwnReviews(this.utente!.id!); // Refresh the list
        },
        (error) => {
          console.error('Error deleting this review', error);
          this.getOwnReviews(this.utente!.id!); // Still refresh the list even if an error occurs
        }
      );
    }
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }
}
