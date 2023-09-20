import { ChangeDetectionStrategy, Component, OnInit } from '@angular/core';
import { CartService } from 'src/app/service/cart.service';
import { Dish } from 'src/app/module/dish.interface';
import { Utente } from 'src/app/module/utente.interface';
import { UserService } from 'src/app/service/utente.service';
import { tap } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { EMPTY } from 'rxjs';
import { switchMap } from 'rxjs/operators';
import { OrdineSingolo } from 'src/app/module/ordine-singolo.interface';



@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.scss'],
  changeDetection: ChangeDetectionStrategy.Default
})
export class CartComponent implements OnInit {
  ordineInCart!: OrdineSingolo;
  productsInOrder: Dish[] = [];
  utente!: Utente

  // productCount: number = 1;

  constructor(private userSrv: UserService, private cartSrv: CartService ) { }

  ngOnInit(): void {

    this.userSrv.getCurrentUser().subscribe((_utente) => {
      this.utente = _utente;
      if (this.utente && this.utente.id) {
        this.getProductsInCart(this.utente.id);
        this.getOrdineInCart(this.utente.id!);

      }


  })

  }

  getProductsInCart(userId: string): void {
    this.cartSrv.getProductsInOrder(userId).subscribe((data: any[]) => {
      this.productsInOrder = this.transformProdottiList(data);
      this.cartSrv.setCartItemList(this.productsInOrder);
      console.log(this.productsInOrder);
    },
    error => {
      console.error('Error fetching products in cart', error);
    });
  }

  private transformProdottiList(response: any[]): Dish[] {
    // Step 1: Create a map of all unique Dish objects by their id
    const dishMap: { [id: string]: Dish } = {};
    response.forEach((dish: Dish | string) => {
      if (typeof dish !== 'string' && dish.id) {
        dishMap[dish.id] = dish;
      }
    });
    // Step 2: Create a new list of Dishes, replacing string ids with Dish objects from the map
    const dishList = response.map((dish: Dish | string) => {
      if (typeof dish === 'string') {
        return dishMap[dish] || { productId: dish };
      } else {
        return dish;
      }
    });
    return dishList;
  }


addToCart(itemId: string): void {
  this.cartSrv.addToCart(this.utente.id!, itemId, 1).pipe(
    tap(() => {
      this.productsInOrder = [];
    }),
    tap(() => {
      this.getProductsInCart(this.utente.id!);
    }),
    tap(() => {
      this.getOrdineInCart(this.utente.id!);
    }),

  ).subscribe();
}

removeFromCart(itemId:string): void {
  this.cartSrv.removeFromCart(this.utente.id!, itemId, 1).pipe(
    tap(() => {
      this.productsInOrder = [];
    }),
    tap(() => {
      this.getProductsInCart(this.utente.id!);
    }),
    tap(() => {
      this.getOrdineInCart(this.utente.id!);
    }),

  ).subscribe();
}


getOrdineInCart(userId:string):void {
  this.cartSrv.getOrdineWithIncart(userId).subscribe((data:OrdineSingolo[]) => {
    this.ordineInCart = data[0];
    console.log("ORDINESINGOLO", data)
  })
}


getGroupedProducts(): (Dish & { count: number })[] {
  const groupedProductsMap: { [id: string]: Dish & { count: number } } = {};

  this.productsInOrder.forEach((dish) => {
    if (dish.id) {
      if (!groupedProductsMap[dish.id]) {
        groupedProductsMap[dish.id] = { ...dish, count: 1 };
      } else {
        groupedProductsMap[dish.id].count += 1;
      }
    }
  });

  // Convert the map back to an array
  return Object.values(groupedProductsMap);
}

}


