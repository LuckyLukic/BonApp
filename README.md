# BonApp

# Applicazione Fullstack - Shop Online per Ristoranti

## Descrizione
Questa è un'applicazione fullstack per uno shop online dedicato ai ristoranti, che permette agli utenti registrati di visualizzare il menu, i dettagli dei prodotti, i prodotti più amati dagli utenti, leggere e scrivere recensioni, accedere alla propria area personale, aggiungere prodotti al carrello, effettuare pagamenti tramite Stripe e ricevere email post-acquisto per la richiesta di recensioni.

Gli utenti non registrati hanno accesso al menu, ai dettagli dei prodotti, ai prodotti più amati e alle recensioni degli utenti.

## Tecnologie Utilizzate
### Backend
- Java
- Spring Boot
- PostgreSQL

### Frontend
- Angular
- Bootstrap
- CSS/SCSS

## Funzionalità
### Utenti Registrati
- Accesso al menu e dettagli dei prodotti
- Visualizzazione dei prodotti top e delle recensioni degli altri utenti
- Area personale per visualizzare ordini, prodotti favoriti, recensioni e modificare i dati personali
- Possibilità di aggiungere prodotti al carrello
- Checkout tramite Stripe
- Ricezione di email post-acquisto per la richiesta di recensioni con sistema di rating

### Utenti Non Registrati
- Accesso al menu e dettagli dei prodotti
- Visualizzazione dei prodotti top e delle recensioni degli altri utenti

## Installazione e Setup

Per far funzionare l'applicazione localmente, è necessario seguire i seguenti passaggi:

### Prerequisiti
- Java: l'applicazione backend è sviluppata in Java, quindi è necessario averlo installato. Puoi scaricare Java dal [sito ufficiale](https://www.oracle.com/java/technologies/javase-downloads.html).
- Angular: il frontend è sviluppato con Angular, quindi è necessario installare Angular CLI. Puoi farlo con il seguente comando npm (se non hai npm, puoi installarlo da [qui](https://www.npmjs.com/get-npm)):
  ```sh
  npm install -g @angular/cli

IDE: ti suggeriamo di utilizzare Eclipse per il backend e Visual Studio Code per il frontend.
Configurazione
	Backend
	Apri la cartella del backend con Eclipse.
	Assicurati che il CORS sia settato sulla porta 4200.
	Avvia l'applicazione facendo partire il file main della cartella BonApp.
	Apri la cartella del frontend con Visual Studio Code.
ng serve -o
	L'applicazione sarà disponibile all'indirizzo http://localhost:4200.

Assicurati che entrambe le applicazioni (frontend e backend) siano in esecuzione e che comunicano correttamente tra di loro.


## Backend

Il backend dell'applicazione è costruito utilizzando Java e Spring Boot e si basa su sei classi principali, che rappresentano anche le entità dell’applicazione che vengono salvate nel database PostgresSql

### Classi/Entità

#### 1. `User`
Rappresenta un utente dell'applicazione e ha i seguenti attributi:
- `username`: Stringa che rappresenta il nome utente.
- `name`: Stringa che rappresenta il nome dell'utente.
- `surname`: Stringa che rappresenta il cognome dell'utente.
- `email`: Stringa che rappresenta l'indirizzo email dell'utente.
- `indirizzo`: Oggetto `Indirizzo` associato all'utente.
- `password`: Stringa che rappresenta la password dell'utente.
- `role`: Enum che rappresenta il ruolo dell utente: User o Admin
- `singleOrders`: Lista di oggetti `OrdineSingolo` associati all'utente.
- `dataRegistrazione`: Data di registrazione dell'utente.
- `prodottiPreferiti`: Lista di oggetti `Prodotto` preferiti dall'utente.
- `reviews`: Lista di oggetti ‘Review’ per le recensioni lasciate dall'utente.

Relazioni:
- `ManyToOne` con `Indirizzo`.
- `OneToMany` con `OrdineSingolo`.
`ManyToMany` con `Prodotto` (per i prodotti preferiti).


#### 2. `Indirizzo`
Rappresenta l'indirizzo associato a un utente dell'applicazione e ha i seguenti attributi:
- `indirizzo_id`: Identificativo univoco dell'indirizzo.
- `via`: Stringa che rappresenta la via dell'indirizzo.
- `civico`: Numero civico dell'indirizzo.
- `località`: Stringa che rappresenta la località dell'indirizzo.
- `cap`: Codice di avviamento postale.
- `comune`: Stringa che rappresenta il comune dell'indirizzo.
- `provincia`: Stringa che rappresenta la provincia dell'indirizzo.
- `User`: Oggetto `User` associato all'indirizzo.

Relazioni:
`OneToMany` con `User` (diversi utenti possono avere uno stesso indirizzo)


#### 3. `Review`
Rappresenta una recensione lasciata da un utente e ha i seguenti attributi:
- `id`: Identificativo univoco della recensione.
- `title`: Stringa che rappresenta il titolo della recensione.
- `comment`: Stringa che rappresenta il commento lasciato dall'utente.
- `rating`: Valore numerico che rappresenta la valutazione dell'utente.
- `User`: Oggetto `User` che rappresenta l'utente che ha lasciato la recensione.
- `username`: Stringa che rappresenta il nome utente di chi ha lasciato la recensione.
- `reviewDate`: Data in cui la recensione è stata lasciata.

Relazioni:
`ManyToOne` con `User`: ogni recensione è associata a un unico utente, mentre un utente può lasciare più recensioni.

#### 4. `Ingrediente`
Rappresenta un ingrediente che può essere utilizzato in uno o più prodotti. Gli attributi della classe `Ingrediente` sono:
- `id`: Identificativo univoco dell'ingrediente.
- `nome`: Stringa che rappresenta il nome dell'ingrediente.
- `prodotti`: Lista di oggetti `Prodotto` associati all'ingrediente.

Relazioni:
- `ManyToMany` con `Prodotto`: un ingrediente può essere associato a più prodotti e un prodotto può contenere più ingredienti.

#### 5. `Prodotto`
La classe `Prodotto` rappresenta un elemento che può essere acquistato o segnato come preferito dagli utenti nell'applicazione. Gli attributi della classe `Prodotto` sono:
- `id`: Identificativo univoco del prodotto.
- `nome`: Stringa che rappresenta il nome del prodotto.
- `descrizione`: Stringa che rappresenta la descrizione del prodotto.
- `prezzo`: Valore numerico che rappresenta il prezzo del prodotto.
- `imgUrl`: Stringa che rappresenta l'URL dell'immagine del prodotto.
- `categoria`: Enum che rappresenta la categoria del prodotto.
- `ingredienti`: Lista di oggetti `Ingrediente` associati al prodotto.
- `orders`: Lista di oggetti `OrdineSingolo` associati al prodotto.
- `usersFavouriteProducts`: Lista di oggetti `User` che hanno segnato il prodotto come preferito.

Relazioni:
- `ManyToMany` con `Ingrediente`: un prodotto può contenere più ingredienti e un ingrediente può essere parte di più prodotti.
- `ManyToMany` con `OrdineSingolo`: un prodotto può essere parte di più ordini e un ordine può contenere più prodotti.
`ManyToMany` con `User` (tramite `usersFavouriteProducts`): un prodotto può essere segnato come preferito da più utenti e un utente può segnare più prodotti come preferiti.


#### 6. `OrdineSingolo`
La classe `OrdineSingolo` rappresenta un ordine effettuato da un utente, contenente uno o più prodotti. Inoltre rappresenta anche il cart dello shop online. Gli attributi di questa classe sono:
- `id`: Identificativo univoco dell'ordine.
- `user`: Oggetto `User` che rappresenta l'utente che ha effettuato l'ordine.
- `totalPrice`: Valore numerico che rappresenta il prezzo totale dell'ordine.
- `dataOrdine`: Data in cui l'ordine è stato effettuato.
- `oraOrdine`: Ora in cui l'ordine è stato effettuato.
- `status`: Enumerazione `StatusOrdine` che rappresenta lo stato dell'ordine.
- `shippingCost`: Costo della spedizione associato all'ordine.
- `prodotti`: Lista di oggetti `Prodotto` che sono inclusi nell'ordine.

Relazioni:
- `ManyToOne` con `User`: Ogni ordine è associato a un unico utente, mentre un utente può effettuare più ordini.
- `ManyToMany` con `Prodotto`: Un ordine può contenere più prodotti e un prodotto può essere incluso in più ordini.


API Endpoints

1. Registrazione Utente (USER)
Endpoint: POST http://localhost:3001/auth/register
Descrizione: Questo endpoint permette ad un nuovo utente di registrarsi. L'utente deve fornire i propri dati personali e le informazioni di indirizzo.
Body della richiesta:

{
    "newUserPayload": {
        "username": "example",
        "name": "John",
        "surname": "Doe",
        "email": "luca@gmail.com",
        "password": "Infedele1980!"
    },
    "newIndirizzoPayload": {
        "cap": "12345",
        "civico": "10",
        "localita": "Example Town",
        "via": "Example Street",
        "comune": "Example City",
        "provincia": "EX"
    }
}

2. Login Utente
Endpoint: POST http://localhost:3001/auth/login
Descrizione: Questo endpoint permette agli utenti registrati di effettuare il login inserendo email e password. In caso di successo, viene restituito un token di autenticazione.
Body della richiesta:

{
    "email": "luca@gmail.com",
    "password": "Infedele1980!"
}


3. Visualizzare i Top 10 Prodotti Preferiti
Endpoint: GET http://localhost:3001/users/top-favorites
Descrizione: Questo endpoint permette a tutti gli utenti, sia registrati che non, di visualizzare i primi 10 prodotti preferiti dalla comunità. Non è necessario essere autenticati per accedere a queste informazioni.
Parametri della richiesta: Nessuno
Risposta di successo:
Codice: 200 OK
Contenuto:

          
"prodotto": {
                "id": "3d6f9ac1-6489-4620-a992-734000f9c938",
                "nome": "Heavy Duty Marble Knife",
                "descrizione": "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam sed purus at turpis egestas feugiat. Donec commodo hendrerit ex, vitae viverra erat congue ac. Aliquam erat volutpat. ",
                "prezzo": 15.0,
                "imgUrl": "https://www.buttalapasta.it/wp-content/uploads/2022/03/ricette-con-agnello.jpg",
                "categoria": "SECONDO",
                "ingredienti": [
                    {
                        "id": "f2bdfbc5-1586-4ff7-962b-c1d6d5830674",
                        "nome": "Limes"
                    },
                    {
                        "id": "342bf2ad-47dc-4b65-a25c-ab854ad99f1c",
                        "nome": "Chickpea"
                    },
                    {
                        "id": "4d6d64b3-539f-4c45-aa89-4ce765b5f4fe",
                        "nome": "Sweet Potato"
                    }
                ]
            },
            "favoriteCount": 5


4. Visualizzare tutte le Recensioni degli Utenti
Endpoint: GET http://localhost:3001/reviews
Descrizione: Questo endpoint permette sia agli utenti registrati che ai visitatori di visualizzare tutte le recensioni lasciate dagli utenti su vari prodotti. Non è necessario essere autenticati per accedere a queste informazioni.
Parametri della richiesta: Nessuno
Risposta di successo:
Codice: 200 OK
Contenuto:
[
    {
        "id": "a857054f-2097-4131-8a8c-5e9d12266343",
        "title": "et",
        "comment": "Sit magni rerum corrupti nihil. Ut consequuntur omnis rem odit qui. Deserunt adipisci delectus laboriosam autem nam repellendus quia.",
        "rating": 1,
        "user": {
            "id": "96578122-b7da-4eeb-9f1c-121c0adec0cf",
            "username": "gregorio.sorrentino@libero.it",
            "name": "Emidio",
            "surname": "Martino",
            "email": "gregorio.sorrentino@libero.it",
            "indirizzo": {
                "indirizzo_id": "fd2a7d02-0e9e-4e5d-998b-1b5daf99feef",
                "via": "Via Ferretti 362, Appartamento 00",
                "civico": "55",
                "localita": "De Santis a mare",
                "cap": "31126",
                "comune": "San Clodovea laziale",
                "provincia": "Biella"
            },
        }
    },
    // Altre recensioni...
]


5. Visualizzare Tutti i Prodotti
Endpoint: GET http://localhost:3001/prodotti
Descrizione: Questo endpoint permette a tutti gli utenti, sia registrati che non, di visualizzare l’elenco completo dei prodotti disponibili. Non è necessario essere autenticati per accedere a queste informazioni.
Parametri della richiesta: Nessuno
Risposta di successo:
Codice: 200 OK
Contenuto:

[ 
    { "id": “03ad34a7-72e2-4966-bd2f-beea56bff91d",
 "nome": "Rustic Wooden Lamp”,
 "descrizione": "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam sed purus at turpis egestas feugiat. Donec commodo hendrerit ex, vitae viverra erat congue ac. Aliquam erat volutpat. In rhoncus, nunc ut gravida tincidunt”,
 "prezzo": 9.9,
 "imgUrl": “https://www.foodblog.it/wp-content/uploads/2019/12/antipasti-di-natale-e-capodanno.jpg.webp",
 "categoria": “ANTIPASTO",
 "ingredienti": [ 
{ "id": “f2bdfbc5-1586-4ff7-962b-c1d6d5830674",
 "nome": "Limes" },
 { "id": “342bf2ad-47dc-4b65-a25c-ab854ad99f1c",
 "nome": "Chickpea" },
 { "id": “4d6d64b3-539f-4c45-aa89-4ce765b5f4fe",
 "nome": "Sweet Potato" },
 { "id": "2d158521-60c7-4f9c-8d59-2c7865294a8e", "nome": "Brazil Nut" }
 ] 
},
 // Altri prodotti... ] 


6. Ricerca Multipla del Prodotto
Endpoint: GET http://localhost:3001/prodotti/search

Descrizione: Questo endpoint è accessibile a tutti gli utenti, sia loggati che non, e permette di filtrare i prodotti per categoria, come definito nelle enum Categoria, o per prezzo, o per una combinazione di categoria e fascia di prezzo.

Parametri di Query:

categoria (opzionale): Categoria dei prodotti da filtrare. Valori possibili sono definiti nelle enum Categoria.
minPrice (opzionale): Prezzo minimo dei prodotti da filtrare.
maxPrice (opzionale): Prezzo massimo dei prodotti da filtrare.
Esempio di Richiesta:

GET http://localhost:3001/prodotti/search?categoria=PRIMO&minPrice=5&maxPrice=12
Risposta di successo:

Codice: 200 OK
Contenuto:

[
    {
        "id": "095d4956-0255-4dde-b801-c64d2eac3560",
        "nome": "Gorgeous Wool Car",
        // Altri campi del prodotto...
        "ingredienti": [
            {
                "id": "f877fd70-befa-47ec-bf5f-20fe2ca1c950",
                "nome": "Strawberries"
            },
            // Altri ingredienti...
        ]
    },
    {
        "id": "4316deed-0253-4bee-934a-aed5c17b69fd",
        "nome": "Fantastic Marble Bottle",
        // Altri campi del prodotto...
        "ingredienti": [
            {
                "id": "0b13ef68-952c-4aa8-8403-e909c4a25979",
                "nome": "Nasturtium"
            },
            // Altri ingredienti...
        ]
    }
]



7. Cercare Utente per ID
Endpoint: GET http://localhost:3001/users/:userId
Descrizione: Questo endpoint permette agli utenti autenticati di recuperare i propri dati inserendo il proprio ID utente nella richiesta. È necessario essere autenticati per accedere a queste informazioni.
Parametri della richiesta:
userId (nel path): ID dell'utente da cercare.
Risposta di successo:
Codice: 200 OK
Contenuto:

{
    "id": "07fa0876-c06c-43fc-afbb-1bf047e05a74",
    "username": "eliziario.fabbri@gmail.com",
    "name": "Gianmaria",
    "surname": "Grasso",
    // Altri campi dell'utente...
    "reviews": [
        {
            "id": "46e1c78a-85f2-4845-b4f8-c7855ce3cc19",
            "title": "neque",
            // Altri campi della recensione...
        },
        // Altre recensioni...
    ],
    "enabled": true,
    "cart": null
}

8. Aggiornare il Proprio Profilo
Endpoint: PUT http://localhost:3001/users/{UserId}

Descrizione: Questo endpoint è disponibile solo per gli utenti loggati e permette di aggiornare i dettagli del profilo utente, compresi i campi dell'indirizzo. Non è possibile utilizzare questo endpoint per aggiornare la password.

Parametri del Percorso:

UserId: ID dell'utente il cui profilo si vuole aggiornare.
Esempio Body della Richiesta:

{
"username" : "Ciccio",
"name" : "Luca",
"surname" : "Iannice",
"email" : "luca@gmail.com"
}

Risposta di successo:

Codice: 200 OK
Contenuto:

{
  "id": "07fa0876-c06c-43fc-afbb-1bf047e05a74",
  "username": "Ciccio",
  "name": "Luca",
  "surname": "Iannice",
  "email": "luca@gmail.com",
  // Altri campi del profilo utente...
}


9. Aggiungere un Prodotto ai Favoriti
Endpoint: POST http://localhost:3001/users/{userId}/add-favorite/{prodottoId}

Descrizione: Questo endpoint permette agli utenti di aggiungere un prodotto ai loro favoriti. Le variabili userId e prodottoId vengono passate direttamente nel percorso dell'URL, mentre il body della richiesta è vuoto.

Parametri del Percorso:

userId: ID dell'utente che desidera aggiungere il prodotto ai favoriti.
prodottoId: ID del prodotto da aggiungere ai favoriti.
Body della Richiesta:

Vuoto {}
Esempio di Richiesta:

POST http://localhost:3001/users/12345/add-favorites/2852eb7c-04f7-4d30-9735-feebb144940b
Risposta di successo:

Codice: 200 OK
Contenuto:

{
    "productId": "2852eb7c-04f7-4d30-9735-feebb144940b",
    "message": "Product successfully added to favorites"
}


10. Recuperare i propri Favoriti
Endpoint: GET http://localhost:3001/users/{userID}/favorites

Descrizione: Questo endpoint permette agli utenti loggati di recuperare i prodotti che hanno aggiunto ai loro favoriti. La variabile userID viene passata direttamente nel percorso dell'URL.

Parametri del Percorso:

userID: ID dell'utente che desidera recuperare la lista dei prodotti favoriti.
Esempio di Richiesta:

GET http://localhost:3001/users/12345/favorites
Risposta di successo:

Codice: 200 OK
Contenuto:

{
    "content": [
        {
            "prodotto": {
                "id": "2852eb7c-04f7-4d30-9735-feebb144940b",
                "nome": "Enormous Bronze Table",
                "descrizione": "Lorem ipsum dolor...",
                "prezzo": 13.0,
                "imgUrl": "https://www.cucchiaio.it/content/cucchiaio/it/ricette/2009/11/ricetta-spaghetti-carbonara/_jcr_content/header-par/image_single.img.jpg/1617198167116.jpg",
                "categoria": "PRIMO",
                "ingredienti": [
                    {
                        "id": "f877fd70-befa-47ec-bf5f-20fe2ca1c950",
                        "nome": "Strawberries"
                    },
                    {
                        "id": "a3d2bc04-4b7c-44d2-ad69-4bedc82d66a5",
                        "nome": "Monkfish"
                    },
                    {
                        "id": "6da75a95-45df-45de-b786-5fb9d7db2436",
                        "nome": "Macadamia Oil"
                    }
                ]
            },
            "favoriteCount": 0
        }
// ALTRI CAMPI......

    ], 
// ALTRE VOCI....
}


11. Rimuovere un Prodotto dai Propri Favoriti
Endpoint: DELETE http://localhost:3001/users/{userId}/remove-favorite/{prodottoId}

Descrizione: Questo endpoint permette agli utenti loggati di rimuovere un prodotto specifico dalla loro lista di prodotti favoriti. Sia userId che prodottoId vengono passati direttamente nel percorso dell'URL.

Parametri del Percorso:

userId: ID dell'utente che desidera rimuovere un prodotto dai favoriti.
prodottoId: ID del prodotto da rimuovere dalla lista dei favoriti.
Esempio di Richiesta:

DELETE http://localhost:3001/users/12345/remove-favorite/2852eb7c-04f7-4d30-9735-feebb144940b
Risposta di successo:

Codice: 204 NO CONTENT


12. Aggiungere una Propria Review
Endpoint: POST http://localhost:3001/users/{userId}/new-review

Descrizione: Questo endpoint permette agli utenti loggati di aggiungere una nuova recensione. L'ID dell'utente (userId) viene passato direttamente nel percorso dell'URL.

Parametri del Percorso:

userId: ID dell'utente che desidera aggiungere una nuova recensione.
Corpo della Richiesta:

{ 
    "title": "Nuova Review versione 2",
    "comment" : "Lorem Ipsum...",
    "rating" : 4
}

Risposta di Successo:

Codice: 201 Created
Contenuto:

{
    "id": "4060d195-898d-4d7f-98ca-b1f22dd76193",
    "title": "Nuova Review versione 2",
    "comment": "Shakira Shakira Shakira",
    "rating": 4,
    "user": {
        "id": "ae6f11e3-99a4-4143-aa83-0456490e311e",
        "username": "luca@gmail.com",
        "reviews": [
            {
                "id": "4060d195-898d-4d7f-98ca-b1f22dd76193",
                "title": "Nuova Review versione 2",
                "comment": "Lorem Ipsum...",
                "rating": 4,
                "reviewDate": "2023-09-28"
            }
            // ALTRE REVIEWS...
        ]
    }
}


13. Vedere Tutte le Proprie Review
Endpoint: GET http://localhost:3001/users/{userId}/getOwnReviews

Descrizione: Questo endpoint permette agli utenti loggati di visualizzare tutte le proprie recensioni. L'ID dell'utente (userId) viene passato direttamente nel percorso dell'URL.

Parametri del Percorso:

userId: ID dell'utente che desidera visualizzare le proprie recensioni.
Esempio di Richiesta:

GET http://localhost:3001/users/ae6f11e3-99a4-4143-aa83-0456490e311e/getOwnReviews
Risposta di Successo:

Codice: 200 OK
Contenuto:

{
    "reviews": [
        {
            "id": "2e6bf84a-560f-4d95-8712-185422ba5f9d",
            "title": "Prova 1",
            "comment": "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam sed purus at turpis egestas feugiat. Donec commodo hendrerit ex, vitae viverra erat congue ac. Aliquam erat volutpat. In rhoncus, nunc ut gravida tincidunt, est ante scelerisque nisi, quis efficitur dui quam et nunc.",
            "rating": 4,
            "user": "ae6f11e3-99a4-4143-aa83-0456490e311e",
            "username": "luca@gmail.com",
            "reviewDate": "2023-09-27"
        },
        // ALTRI OGGETTI REVIEW...
    ]
}

14. Aggiungere un Prodotto al Carrello
Endpoint: POST http://localhost:3001/users/{userId}/add-to-cart/{productId}?quantity=1

Descrizione: Questo endpoint permette agli utenti loggati di aggiungere una certa quantità di un determinato prodotto al loro carrello. Ogni utente ha sempre uno e un solo carrello con status IN_CART. Se un carrello con status IN_CART non esiste per l'utente, verrà creato automaticamente.

Parametri del Percorso:

userId: ID dell'utente che desidera aggiungere un prodotto al carrello.
productId: ID del prodotto da aggiungere al carrello.
Parametri Query:

quantity: La quantità del prodotto da aggiungere al carrello. Default a 1 se non specificato.
Esempio di Richiesta:

POST http://localhost:3001/users/ae6f11e3-99a4-4143-aa83-0456490e311e/add-to-cart/2852eb7c-04f7-4d30-9735-feebb144940b?quantity=2
Risposta di Successo:

Codice: 200 OK
Contenuto:

{
    "message": "Product added to cart successfully"
}

15. Rimuovere un Prodotto dal Carrello
Endpoint: DELETE http://localhost:3001/users/{userId}/remove-from-cart/{productId}?quantity=1

Descrizione: Questo endpoint permette agli utenti loggati di rimuovere una certa quantità di un determinato prodotto dal loro carrello. Se la quantità del prodotto nel carrello è inferiore o uguale alla quantità specificata, il prodotto viene rimosso completamente dal carrello.

Parametri del Percorso:

userId: ID dell'utente che desidera rimuovere un prodotto dal carrello.
productId: ID del prodotto da rimuovere dal carrello.
Parametri Query:

quantity: La quantità del prodotto da rimuovere dal carrello. Default a 1 se non specificato.
Esempio di Richiesta:

DELETE http://localhost:3001/users/ae6f11e3-99a4-4143-aa83-0456490e311e/add-to-cart/2852eb7c-04f7-4d30-9735-feebb144940b?quantity=2
Risposta di Successo:

Codice: 200 OK


16. Ottenere Tutti i propri Ordini Completati
Endpoint: GET http://localhost:3001/users/{userId}/completed

Descrizione: Questo endpoint permette agli utenti loggati di visualizzare tutti i loro ordini che sono stati completati.

Parametri del Percorso:

userId: ID dell'utente che desidera visualizzare gli ordini completati.
Esempio di Richiesta:

GET http://localhost:3001/users/ae6f11e3-99a4-4143-aa83-0456490e311e/completed
Risposta di Successo:

Codice: 200 OK
Contenuto:

{
    "singleOrders": [
        {
            "id": "59ecdba1-ce48-4edc-919c-9f4a1e03b652",
            "user": "ae6f11e3-99a4-4143-aa83-0456490e311e",
            "totalPrice": 10.0,
            "dataOrdine": "2023-09-27",
            "oraOrdine": "15:33:00.833",
            "status": "COMPLETATO",
            "shippingCost": 2.5,
            "prodotti": [
                {
                    "id": "685f5040-bf4b-4752-aeb6-b086405707d6",
                    "nome": "Enormous Concrete Coat",
                    "descrizione": "Lorem ipsum dolor sit amet, consectetur adipiscing elit...",
                    "prezzo": 5.0,
                    "imgUrl": "https://staticcookist.akamaized.net/.../ricette-contorni-patate-1200x675.jpg",
                    "categoria": "CONTORNO",
                    "ingredienti": [
                        {"id": "d3cafaf5-56ad-4504-8bba-4c93586dfbe5", "nome": "Nutmeg"},
                        {"id": "0fec7460-616f-4daf-a588-e1f513023d36", "nome": "Tempeh"},
                        {"id": "342bf2ad-47dc-4b65-a25c-ab854ad99f1c", "nome": "Chickpea"}
                    ]
                }
            ]
        }
    ]
}

