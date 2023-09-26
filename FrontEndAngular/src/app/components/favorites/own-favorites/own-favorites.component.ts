import { Component, OnInit } from '@angular/core';
import { Favorite } from 'src/app/module/favorite.interface';
import { MenuService } from 'src/app/service/menu.service';
import { AuthService } from 'src/app/service/auth.service';
import { Utente } from 'src/app/module/utente.interface';
import { Dish } from 'src/app/module/dish.interface';
import { UserService } from 'src/app/service/utente.service';
import { ActivatedRoute, ParamMap } from '@angular/router';
import { CartService } from 'src/app/service/cart.service';
import { Subscription } from 'rxjs';


@Component({
  selector: 'app-own-favorites',
  templateUrl: './own-favorites.component.html',
  styleUrls: ['./own-favorites.component.scss']
})
export class OwnFavoritesComponent implements OnInit {


  favoriteDishes: Favorite[] = [];
  productsInOrder: Dish[] = [];
  private subscription!: Subscription;
  utente!: Partial<Utente> | null;


  constructor( private dishes: MenuService,private authSrv: AuthService, private userSrv: UserService, private cartSrv: CartService) { }

  ngOnInit(): void {

    this.userSrv.initializeLoginStatus()
    this.subscription = this.userSrv.currentUser$.subscribe(utente => {
      this.utente = utente;
      if (this.utente && this.utente.id) {
        this.getProductsInCart(this.utente.id!);
        this.getOwnFavorites(this.utente.id!)
    }
  });



      }




  getProductsInCart(userId:string): void {
    this.cartSrv.getProductsInOrder(userId).subscribe ((data: Dish[])=> {
      this.productsInOrder = data;
      console.log("Products in order", this.productsInOrder);
    },
    error => {
      console.error('Error fetching products in cart', error);
    });
}




  removeFavorite(dishId: string): void {
    if (this.utente && this.utente.id) {
      this.dishes.removeFavorite(this.utente.id, dishId).subscribe(response =>{
        this.favoriteDishes = this.favoriteDishes.filter(item => item.prodotto.id !== dishId);
    }, error => {
      console.error('Error removing favorite', error);
      });
    }
  }

  getOwnFavorites(id: string): void {
    this.userSrv.getOwnFavorites(id).subscribe(
      (favorites: Favorite) => {
        console.log('Favorites:', favorites);
        this.favoriteDishes = favorites.content;
      },
      error => {
        console.error('Error fetching favorite dishes:', error);
      }
    );
  }

    findId(id:string): string | null {

      const myDish = this.favoriteDishes.find(element =>
        element.id===id);
        return myDish?.id!

    }

    getIngredientName(ingredient: any): string {
      return (ingredient as any).nome;
    }

    trackByFunction(index: number, item: any): any {
      return item.prodotto.id;
    }

}


