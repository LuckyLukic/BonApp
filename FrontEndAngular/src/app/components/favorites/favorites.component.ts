import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { MenuService } from 'src/app/service/menu.service';
import { Favorite } from 'src/app/module/favorite.interface';
import { Dish } from 'src/app/module/dish.interface';
import { UserService } from 'src/app/service/utente.service';
import { Subscription } from 'rxjs';
import { Utente } from 'src/app/module/utente.interface';
import { CartService } from 'src/app/service/cart.service';


@Component({
  selector: 'app-favorites',
  templateUrl: './favorites.component.html',
  styleUrls: ['./favorites.component.scss']
})
export class FavoritesComponent implements OnInit {


  topFavoriteDishes: Favorite[] = []
  productsInOrder: Dish[] = [];
  private subscription!: Subscription;
  private cartSubscription?: Subscription;
  utente!: Partial<Utente> | null;

  constructor(
               private dishes: MenuService,
               private router: Router,
               private userSrv: UserService,
               private cartSrv: CartService) { }

  ngOnInit(): void {
    this.userSrv.initializeLoginStatus()
    this.subscription = this.userSrv.currentUser$.subscribe(utente => {
      this.utente = utente;
    });


  this.topFavorite()

  }


  topFavorite():void {
    this.dishes.getTopFavorites().subscribe((allDishes: Favorite) => {

      this.topFavoriteDishes = allDishes.content;
      console.log("List", this.topFavoriteDishes)

  })

  }

  getIngredientName(ingredient: any): string {
    return (ingredient as any).nome;
  }

  navigateToDestination() {

    const currentUrl = this.router.url;
    this.router.navigate(['/dishes/:id'], { state: { returnUrl: currentUrl } })

}

  }



