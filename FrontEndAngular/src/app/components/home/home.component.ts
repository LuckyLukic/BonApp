import { Component, OnInit } from '@angular/core';
import { Dish } from 'src/app/module/dish.interface';
import { MenuService } from 'src/app/service/menu.service';
import { Utente } from 'src/app/module/utente.interface';
import { Favorite } from 'src/app/module/favorite.interface';
import { CartService } from 'src/app/service/cart.service';
import { UserService } from 'src/app/service/utente.service';
import { tap } from 'rxjs';
import { Router, NavigationEnd } from '@angular/router';
import { Subscription } from 'rxjs';
import { DropdownMenuService } from 'src/app/service/dropdown-menu.service';
import { filter } from 'rxjs';
import { StripeService } from 'src/app/service/stripe.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {
  dishesList: Dish[] = [];

  utente!: Partial<Utente> | null;
  favoriteDishes!: Favorite[];
  singleDish!:Favorite;
  productsInOrder: Dish[] = [];
  private subscription!: Subscription;


  constructor(
    private dishes: MenuService,
    private userSrv:UserService,
    private cartSrv:CartService,
    private ddMenuSrv: DropdownMenuService,
    private router:Router


  ) {}

  ngOnInit(): void {

    this.dishes.searchResults$.subscribe(results => {
      this.dishesList = results.length > 0 ? results : [];
    });


    this.userSrv.initializeLoginStatus()

    this.subscription = this.userSrv.currentUser$.subscribe(utente => {
      this.utente = utente;
        if (this.utente && this.utente.id) {
          this.favoriti(this.utente.id);
          this.getProductsInCart(this.utente.id);

        }
        });

        this.subscription.add(
          this.router.events.pipe(
            filter(event => event instanceof NavigationEnd)
          ).subscribe(() => {
            this.loadAllProducts();
          })
        );

        this.subscription.add(
          this.ddMenuSrv.selectedCategory$.subscribe(
            (selectedDishes: Dish[]) => {
              if (selectedDishes.length > 0) {
                this.dishesList = selectedDishes;
              } else {
                this.loadAllProducts();
              }
            }
          )
        );

        if (this.dishesList.length === 0) {
          this.loadAllProducts();
        }
   }

   loadAllProducts() {
    this.dishes.getMenu().subscribe((allDishes: Dish) => {
      this.dishesList = allDishes.content;
    },
    error => console.error('Error fetching all dishes', error)
  );
}

  getProductsInCart(userId: string): void {
    this.cartSrv.getProductsInOrder(userId).subscribe((data: any[]) => {
      this.productsInOrder = data;
      this.cartSrv.setCartItemList(this.productsInOrder);
    },
    error => {
      console.error('Error fetching products in cart', error);
    });
  }


   favoriti(id: string): void {
     this.userSrv.getOwnFavorites(id).subscribe((data: Favorite) => {
       this.favoriteDishes = data.content;
     });
  }

   checkMatch(id:string): boolean {

     return this.favoriteDishes.some((element) => element.prodotto.id===id)

   }

   addFavorite(dishId: string): void {
    if (this.utente && this.utente.id) {
      this.dishes.addFavorite(this.utente.id, dishId).subscribe(response => {
        console.log('Favorite added', response);

        this.favoriti(this.utente!.id!);

      }, error => {
        console.error('Error adding favorite', error);
      });
    }
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

  findId(id: string): string | null {
    const myDish = this.favoriteDishes.find((element) => element.prodotto.id === id);
    return myDish?.id ?? null;
  }

  addToCart(itemId:string): void {
    if(this.utente) {
    this.cartSrv.addToCart(this.utente.id!, itemId, 1).pipe(
      tap(() => {
        this.productsInOrder = [];
      }),
      tap(() => {
        this.getProductsInCart(this.utente!.id!);
      })
    ).subscribe();
    } else {
      alert ("Per aggiungere un prodotto al carrello devi essere loggato")
    }
  }

  removeFromCart(itemId:string): void {
    this.cartSrv.removeFromCart(this.utente!.id!, itemId, 1).pipe(
      tap(() => {
        this.productsInOrder = [];
      }),
      tap(() => {
        this.getProductsInCart(this.utente!.id!);
      })
    ).subscribe();
  }

  isItemInArray(itemId: string): boolean {
    return this.productsInOrder.some(item => item.id! === itemId);
  }

  getItemCount(itemId: string): number {
   return  this.productsInOrder.filter(item => item.id === itemId).length;

  }


ngOnDestroy(): void {

  this.subscription.unsubscribe();
}

}






