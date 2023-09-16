import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/service/auth.service';
import { Dish } from 'src/app/module/dish.interface';
import { MenuService } from 'src/app/service/menu.service';
import { Utente } from 'src/app/module/utente.interface';
import { Favorite } from 'src/app/module/favorite.interface';
import { delay } from 'rxjs';
import { UserService } from 'src/app/service/utente.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {
  dishesList: Dish[] = [];

  utente!: Partial<Utente>;
  favoriteDishes!: Favorite[];
  singleDish!:Favorite;


  constructor(
    private dishes: MenuService,
    private userSrv:UserService
  ) {}

  ngOnInit(): void {


    this.userSrv.getCurrentUser().subscribe((_utente) => {
        this.utente = _utente;
        if (this.utente && this.utente.id) {
          this.favoriti(this.utente.id);
        }

    });

    this.dishes.getMenu().subscribe((allDishes: Dish) => {
      this.dishesList = allDishes.content;

    });

    console.log(this.utente)
  }


   favoriti(id: string): void {
     this.userSrv.getOwnFavorites(id).subscribe((data: Favorite) => {
       this.favoriteDishes = data.content;
     });
  }

   checkMatch(id:string): boolean {

     return this.favoriteDishes.some((element) => element.prodotto.id===id)

   }

   addFavorite(dishId: string): void {
    if (this.utente && this.utente.id) {
      this.dishes.addFavorite(this.utente.id, dishId).subscribe(response => {
        console.log('Favorite added', response);
        // Refresh the favorites list
        this.favoriti(this.utente.id!);
      }, error => {
        console.error('Error adding favorite', error);
      });
    }
  }

  removeFavorite(dishId: string): void {
    if (this.utente && this.utente.id) {
      this.dishes.removeFavorite(this.utente.id, dishId).subscribe(response =>{
        this.favoriti(this.utente.id!);
      }, error => {
        console.error('Error adding favorite', error);
      });
    }
  }

  findId(id: string): string | null {
    const myDish = this.favoriteDishes.find((element) => element.prodotto.id === id);
    return myDish?.id ?? null;
  }
}




