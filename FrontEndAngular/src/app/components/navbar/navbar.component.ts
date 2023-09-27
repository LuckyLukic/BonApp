import { Component, OnInit, } from '@angular/core';
import { AuthService } from 'src/app/service/auth.service';
import { UserService } from 'src/app/service/utente.service';
import { Utente } from 'src/app/module/utente.interface';
import { CartService } from 'src/app/service/cart.service';
import { Subscription } from 'rxjs';
import { Router } from '@angular/router';
import { DropdownMenuService } from 'src/app/service/dropdown-menu.service';
import { MenuService } from 'src/app/service/menu.service';
import { FormControl } from '@angular/forms';
import { debounceTime, distinctUntilChanged } from 'rxjs/operators';





@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss'],
})
export class NavbarComponent implements OnInit {

  utente!: Partial<Utente>| null;
  productsInOrder: any[] = [];
  private cartSubscription?: Subscription;
  private subscription!: Subscription;
  itemsInCart!: number;
  searchControl = new FormControl();

  constructor(private authSrv: AuthService,
              private userSrv: UserService,
              private cartSrv: CartService,
              private router: Router,
              private ddMenuSrv: DropdownMenuService,
              private menuSrv: MenuService) {

   }


  ngOnInit(): void {



    this.subscription = this.userSrv.currentUser$.subscribe(_utente =>{
     this.utente = _utente;
      this.subscribeToCartItemList();
      this.numberItemsInCart();
    })

    this.searchControl.valueChanges.pipe(
      debounceTime(300),
      distinctUntilChanged()
    ).subscribe(value => {
      if (value) {
        this.menuSrv.searchByPartialName(value)
          .subscribe(results => {
            this.menuSrv.setSearchResults(results.content);
          });
      } else {
        this.menuSrv.setSearchResults([]);
      }
    });

  }




  private subscribeToCartItemList(): void {
    this.cartSubscription = this.cartSrv.cartItemList$.subscribe(
      cartItemList => {
        this.productsInOrder = cartItemList;
        console.log("NAVBAR", this.productsInOrder);
      }
    );
  }


  filterCategory(category: string) {
    this.ddMenuSrv.filterByCategory(category);
  }


  numberItemsInCart():void {
    for (let i of this.utente!.singleOrders!) {
      if (i.status==="IN_CART") {
        this.productsInOrder = i.prodotti
      }
    }
  }

  onBonappClick() {
    this.ddMenuSrv.resetCategory();
    this.router.navigate(['/']);
  }

  logout() {
    this.authSrv.logout();
    this.utente = null;
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }


}


