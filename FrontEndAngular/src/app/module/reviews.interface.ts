import { Utente } from "./utente.interface";

export interface Reviews {

  content: Reviews[];
  title: string;
  comment: string;
  user: Utente
  rating: number;


}
