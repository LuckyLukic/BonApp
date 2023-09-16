import { Dish } from "./dish.interface";

export interface OrdineSingolo {
  id? : string;
  prodotti: Dish[];
  totalPrice: number;
  dataOrdine: string;
  oraOrdine: string
}
