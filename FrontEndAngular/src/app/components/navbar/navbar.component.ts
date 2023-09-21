import { Component, OnInit, } from '@angular/core';
import { AuthService } from 'src/app/service/auth.service';
import { UserService } from 'src/app/service/utente.service';
import { Utente } from 'src/app/module/utente.interface';
import { CartService } from 'src/app/service/cart.service';
import { Subscription } from 'rxjs';
import { Router } from '@angular/router';
import { ActivatedRoute, ParamMap } from '@angular/router';



@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss'],
})
export class NavbarComponent implements OnInit {

  utente!: Partial<Utente> |null;
  productsInOrder: any[] = [];
  private cartSubscription?: Subscription;
  checkUtente!: string | null;
  // singleId!: string | null;

  constructor(private authSrv: AuthService, private userSrv: UserService, private cartSrv: CartService, private route: ActivatedRoute) {

   }




  ngOnInit(): void {

    this.userSrv.getUser().subscribe((_utente) => {
      this.utente = _utente;
      console.log("NAV", _utente)
      this.subscribeToCartItemList();

      // this.route.paramMap.subscribe((data: ParamMap) => {
      //   this.singleId =  data.get('id');

      // })


    });

    this.checkUtente = localStorage.getItem('user');
    console.log("checkUtente", this.checkUtente)

  }

  private subscribeToCartItemList(): void {
    this.cartSubscription = this.cartSrv.cartItemList$.subscribe(
      cartItemList => {
        this.productsInOrder = cartItemList;
        console.log("NAVBAR", this.productsInOrder);
      }
    );
  }


  logout() {
    this.authSrv.logout();
    this.utente = null;
  }
}


