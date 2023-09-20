import { Component, OnInit } from '@angular/core';
import { Favorite } from 'src/app/module/favorite.interface';
import { MenuService } from 'src/app/service/menu.service';
import { AuthService } from 'src/app/service/auth.service';
import { Utente } from 'src/app/module/utente.interface';
import { Dish } from 'src/app/module/dish.interface';
import { UserService } from 'src/app/service/utente.service';
import { ActivatedRoute, ParamMap } from '@angular/router';
import { OwnFavorite } from 'src/app/module/own-favorite.interface';
import { CartService } from 'src/app/service/cart.service';

@Component({
  selector: 'app-own-favorites',
  templateUrl: './own-favorites.component.html',
  styleUrls: ['./own-favorites.component.scss']
})
export class OwnFavoritesComponent implements OnInit {

  utente!: Partial<Utente>;
  favoriteDishes: Favorite[] = [];
  productsInOrder: Dish[] = [];


  constructor(private route: ActivatedRoute, private dishes: MenuService,private authSrv: AuthService, private userSrv: UserService, private cartSrv: CartService) { }

  ngOnInit(): void {

    this.userSrv.getCurrentUser().subscribe((_utente) => {
      this.utente = _utente;
      if (this.utente && this.utente.id) {
        this.getProductsInCart(this.utente.id!);
        this.getOwnFavorites(this.utente.id!)


      }
    })
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

}


