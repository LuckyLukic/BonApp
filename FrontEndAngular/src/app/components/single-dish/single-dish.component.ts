import { Component, OnInit } from '@angular/core';
import { MenuService } from 'src/app/service/menu.service';
import { Dish } from 'src/app/module/dish.interface';
import { ActivatedRoute } from '@angular/router';
import { Router } from '@angular/router';
import { CartService } from 'src/app/service/cart.service';
import { Utente } from 'src/app/module/utente.interface';
import { UserService } from 'src/app/service/utente.service';
import { tap } from 'rxjs';


@Component({
  selector: 'app-single-dish',
  templateUrl: './single-dish.component.html',
  styleUrls: ['./single-dish.component.scss']
})
export class SingleDishComponent implements OnInit {

  dish!: Dish;
  myId!: string;
  returnUrl: string;
  dishesList: Dish[] = [];
  utente!: Partial<Utente>;
  productsInOrder: Dish[] = [];


  constructor(private dishes: MenuService,
              private route: ActivatedRoute,
              private router:Router,
              private cartSrv: CartService,
              private userSrv: UserService  )
               {

                const navigation = this.router.getCurrentNavigation();
  this.returnUrl = navigation?.extras.state? (navigation.extras.state as any).returnUrl : '/';

               }

  ngOnInit(): void {
    if(this.route.snapshot.paramMap.get('id')) {
      this.myId = this.route.snapshot.paramMap.get('id')!
      this.dishes.getDishDetail(this.myId).subscribe((data:Dish)=> {
        this.dish = data

      })
    }

    this.userSrv.initializeLoginStatus()
    this.userSrv.getCurrentUser().subscribe((_utente) => {
      this.utente = _utente;
        if (this.utente && this.utente.id) {

          this.getProductsInCart(this.utente.id);
        }
    });

    this.dishes.getMenu().subscribe((allDishes: Dish) => {
      this.dishesList = allDishes.content;
    });

  }



    ingredientiNames(): string {
      return this.dish?.ingredienti?.map(ingrediente => ingrediente.nome).join(', ') || '';
    }


    addToCart(itemId:string): void {
      if(this.utente) {
      this.cartSrv.addToCart(this.utente.id!, itemId, 1).pipe(
        tap(() => {
          this.productsInOrder = [];
        }),
        tap(() => {
          this.getProductsInCart(this.utente.id!);
        })
      ).subscribe();
      } else {
        alert ("Per aggiungere un prodotto al carrello devi essere loggato")
      }
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


  getItemCount(itemId: string): number {
    return  this.productsInOrder.filter(item => item.id === itemId).length;

   }

   isItemInArray(itemId: string): boolean {
    return this.productsInOrder.some(item => item.id! === itemId);
  }



    goBack() {

      this.router.navigateByUrl(this.returnUrl);
    }

}




