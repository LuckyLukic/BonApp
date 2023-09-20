import { Dish } from "./dish.interface";
import { Utente } from "./utente.interface";

export interface OrdineSingolo {
  id? : string;
  prodotti: Dish[];
  totalPrice: number;
  dataOrdine: Date;
  oraOrdine: Date;
  userId: string|null;
  shippingCost: number;
}
