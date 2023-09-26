import { Component, OnInit } from '@angular/core';
import { OrdineSingolo } from 'src/app/module/ordine-singolo.interface';
import { Utente } from 'src/app/module/utente.interface';
import { ActivatedRoute, ParamMap } from '@angular/router';
import { UserService } from 'src/app/service/utente.service';
import { OrdiniService } from 'src/app/service/ordini.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-own-orders',
  templateUrl: './own-orders.component.html',
  styleUrls: ['./own-orders.component.scss']
})
export class OwnOrdersComponent implements OnInit {

  utente!: Partial<Utente> | null;
  ownOrders!: OrdineSingolo[];
  private subscription!: Subscription;

  constructor(private route: ActivatedRoute, private userSrv: UserService, private ordineSrv: OrdiniService) { }

  ngOnInit(): void {




    this.userSrv.initializeLoginStatus()
    this.subscription = this.userSrv.currentUser$.subscribe(utente => {
      this.utente = utente;


        this.getAllCompletedOrder(this.utente!.id!)
        console.log(" OwnOrders", this.ownOrders)

})

}

getAllCompletedOrder(userId: string) {
  this.ordineSrv.getAllCompletedOrder(userId).subscribe((response : OrdineSingolo[])=>{
    this.ownOrders = response;
    console.log("Assigned to this.ownOrders: ", this.ownOrders);
  });
}


// get prodottiNames(): string {
//     return this.ownOrder      (pro =>  {}.join(', ') || '';
// }


}


