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

  getProductsInCart(userId:string): void {
    this.cartSrv.getProductsInOrder(userId).subscribe ((data: Dish[])=> {
      this.productsInOrder = data;
      this.cartSrv.setCartItemList(data);
      console.log("CARRELLO", this.productsInOrder);
    },
    error => {
      console.error('Error fetching products in cart', error);
    });
}

// addToCart(itemId:string): void {
//   this.cartSrv.addToCart(this.utente.id!, itemId, 1).pipe(
//     tap(() => {
//       this.productsInOrder = [];
//     }),
//     tap(() => {
//       this.getProductsInCart(this.utente.id!);
//     })
//   ).subscribe();
// }
addToCart(itemId: string): void {
  this.cartSrv.addToCart(this.utente.id!, itemId, 1).pipe(
    switchMap(() => {
      this.productsInOrder = [];
      return this.cartSrv.getProductsInOrder(this.utente.id!);
    })
  ).subscribe(
    (data: Dish[]) => {
      this.productsInOrder = data;
      this.cartSrv.setCartItemList(data);
      console.log("CARRELLO", this.productsInOrder);
    },
    error => {
      console.error('Error updating cart', error);
    }
  );
}

removeFromCart(itemId:string): void {
  this.cartSrv.removeFromCart(this.utente.id!, itemId, 1).pipe(
    tap(() => {
      this.productsInOrder = [];
    }),
    tap(() => {
      this.getProductsInCart(this.utente.id!);
    })
  ).subscribe();
}


// getNumberOfSameProducts(itemId: string): void {
//   this.cartSrv.addToCart(this.utente.id!, itemId, 1).pipe(
//     switchMap(() => this.cartSrv.getNumberOfSameProducts(this.utente.id!, itemId)),
//     catchError(error => {
//       console.error('Error in getNumberOfSameProducts:', error);
//       return EMPTY;
//     })
//   ).subscribe(response => {
//     const product = this.productsInOrder.find(p => p.id === itemId);
//     if (product) {
//       product.count = +response;
//     }
//   });
// }

// updateProductCountAfterRemoval(itemId: string): void {
//   this.cartSrv.removeFromCart(this.utente.id!, itemId, 1).pipe(
//     switchMap(() => this.cartSrv.getProductsInOrder(this.utente.id!))
//   ).subscribe(updatedProductsInOrder => {
//     this.productsInOrder = updatedProductsInOrder;
//     const product = this.productsInOrder.find(p => p.id === itemId);
//     if (product && product.count && product.count > 0) {
//       product.count -= 1; // decrease the count property of the product
//     }
//   }, error => {
//     console.error('Error in updateProductCountAfterRemoval:', error);
//   });
// }

getOrdineInCart(userId:string):void {
  this.cartSrv.getOrdineWithIncart(userId).subscribe((data:OrdineSingolo) => {
    this.ordineInCart = data;
    console.log("ORDINESINGOLO", data)
  })
}

}


