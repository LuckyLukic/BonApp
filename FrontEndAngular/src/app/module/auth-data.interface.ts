import { Role } from "./role.enum";
import { OrdineSingolo } from "./ordine-singolo.interface";
import { Reviews } from "./reviews.interface";
import { Dish } from "./dish.interface";
export interface AuthData {
  accessToken: string;
  user:{
    "id"? : string
    "username": string,
    "name": string,
    "surname": string,
    "email": string,
    "indirizzo" : {
      "cap": string | null,
      "civico": string | null,
      "localita": string | null,
      "via": string | null,
      "comune": string | null,
      "provincia": string | null,
    }
    "password": string;
    "role": Role
    "singleOrders": OrdineSingolo[];
    "dataRegistrazione": string;
    "prodottiPreferiti": Dish[];
    "reviews" : Reviews[];

}
}
