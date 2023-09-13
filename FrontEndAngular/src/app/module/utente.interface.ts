

export interface Utente {

  newUserPayload: {
    "username": string | null,
    "name": string | null,
    "surname": string | null,
    "email": string | null,
    "password": string | null,
    "id"?: number | null
    };

  newIndirizzoPayload: {
    "cap": string | null,
    "civico": string | null,
    "localita": string | null,
    "via": string | null,
    "comune": string | null,
    "provincia": string | null,
    "id"?: number | null
  };

}