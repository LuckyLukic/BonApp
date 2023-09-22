import { Component, OnInit } from '@angular/core';
import { OrdineSingolo } from 'src/app/module/ordine-singolo.interface';
import { Utente } from 'src/app/module/utente.interface';
import { ActivatedRoute, ParamMap } from '@angular/router';
import { UserService } from 'src/app/service/utente.service';
import { OrdiniService } from 'src/app/service/ordini.service';
import { Observable } from 'rxjs';
import {map} from 'rxjs';

@Component({
  selector: 'app-own-orders',
  templateUrl: './own-orders.component.html',
  styleUrls: ['./own-orders.component.scss']
})
export class OwnOrdersComponent implements OnInit {

  utente!: Partial<Utente>;
  ownOrders!: OrdineSingolo[];

  constructor(private route: ActivatedRoute, private userSrv: UserService, private ordineSrv: OrdiniService) { }

  ngOnInit(): void {




      this.userSrv.getCurrentUser().subscribe((_utente) => {
        this.utente = _utente;


        this.getAllCompletedOrder(this.utente.id!)
        console.log(" OwnOrders", this.ownOrders)

})

}

getAllCompletedOrder(userId: string) {
  this.ordineSrv.getAllCompletedOrder(userId).subscribe((response : OrdineSingolo[])=>{
    this.ownOrders = response;
    console.log("Assigned to this.ownOrders: ", this.ownOrders);
  });
}


// transformOrderList(response: any[]): OrdineSingolo[] {
//   console.log("transformOrderList input", response)
//   const originalOrdine = response[0] as OrdineSingolo;

//   const transformedArray = response.map((item: OrdineSingolo | string) => {
//     if (typeof item === 'string') {
//       return {
//         ...originalOrdine,
//         id: item
//       };
//     }
//     return item;
//   });

//   console.log("transformOrderList result", transformedArray);
//   return transformedArray;
// }


}


