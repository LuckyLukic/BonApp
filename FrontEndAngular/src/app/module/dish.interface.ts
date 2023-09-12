import { Categoria } from "./categoria";

export interface Dish {
  "nome" : string;
  "descrizione" : string;
  "prezzo" : number;
  "categoria" : Categoria;
  "ingredienti" : String[];
  "imgUrl" : String;

}
