import { Categoria } from "./categoria";

export interface Dish {
  "content": Dish[];
  "nome" : string;
  "descrizione" : string;
  "prezzo" : number;
  "categoria" : Categoria;
  "ingredienti" : String[];
  "imgUrl" : String;
  "id" : string

}
