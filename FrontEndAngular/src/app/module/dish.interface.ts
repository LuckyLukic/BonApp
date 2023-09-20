import { Categoria } from "./categoria";
import { Ingredienti } from "./ingredienti";

export interface Dish {
  "content": Dish[];
  "nome" : string;
  "descrizione" : string;
  "prezzo" : number;
  "categoria" : Categoria;
  "ingredienti" : Ingredienti[];
  "imgUrl" : String;
  "count"?: number;
  "id"? : string

}
