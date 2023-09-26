import { Dish } from "./dish.interface";
import { Status } from "./status.enum";


export interface OrdineSingolo {
  id? : string;
  prodotti: Dish[];
  totalPrice: number;
  dataOrdine: Date;
  oraOrdine: Date;
  userId: string|null;
  shippingCost: number;
  status: Status;
}
