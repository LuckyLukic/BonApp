

export interface Utente {

  "username" : string | null | undefined;
  "name" : string | null | undefined;
  "surname" : string | null | undefined;
  "email" : string | null | undefined;
  "indirizzo" : {  "via" : string | null | undefined;
                   "civico" : string | null | undefined;
                   "localita": string | null | undefined;
                   "cap": string | null | undefined;
                   "comune" : string | null | undefined;
                   "provincia" : string | null | undefined;
                  }
  "password" : string | null | undefined;
  "id": number | null | undefined;

}
