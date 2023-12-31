import { ChangeDetectionStrategy, Component, OnInit } from '@angular/core';
import { CartService } from 'src/app/service/cart.service';
import { Dish } from 'src/app/module/dish.interface';
import { Utente } from 'src/app/module/utente.interface';
import { UserService } from 'src/app/service/utente.service';
import { tap } from 'rxjs';
import { OrdineSingolo } from 'src/app/module/ordine-singolo.interface';
import { StripeService } from 'src/app/service/stripe.service';



@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.scss'],
  changeDetection: ChangeDetectionStrategy.Default
})
export class CartComponent implements OnInit {
  ordineInCart!: OrdineSingolo;
  productsInOrder: Dish[] = [];
  utente!: Utente;
  loaded: boolean = false;


  constructor(private userSrv: UserService, private cartSrv: CartService, private stripeService: StripeService ) { }

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
      this.productsInOrder = data;
      this.cartSrv.setCartItemList(this.productsInOrder);
      console.log(this.productsInOrder);
    },
    error => {
      console.error('Error fetching products in cart', error);
    });
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


  return Object.values(groupedProductsMap);
}

checkout():void {
  this.cartSrv.checkout(this.utente.id!, this.ordineInCart.id!).subscribe((response)=>{
  console.log("Ordine Effettuato", response)
  });
}


handlePayment() {

     this.checkout();
     this.stripeService.redirectToCheckout(this.ordineInCart.totalPrice);

}


}


