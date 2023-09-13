import { Component, OnInit } from '@angular/core';
import { AuthData } from 'src/app/module/auth-data.interface';
import { AuthService } from 'src/app/service/auth.service';
import { Dish } from 'src/app/module/dish.interface';
import { MenuService } from 'src/app/service/menu.service';
import { Utente } from 'src/app/module/utente.interface';
import { Favorite } from 'src/app/module/favorite.interface';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {
  dishesList!: Dish[];

  utente!: Partial<Utente>;
  favoriteDishes!: Favorite[];
  singleDish!:Favorite;

  constructor(
    private dishes: MenuService,
    private authSrv: AuthService
  ) {}

  ngOnInit(): void {

    this.authSrv.user$.subscribe((_utente) => {
      if (_utente) {
        this.utente = _utente.user;

        this.favoriti(this.utente.newIndirizzoPayload?.id)
      }
    });

    this.dishes.getMenu().subscribe((allDishes) => {
      this.dishesList = allDishes.content;
    });
  }

  favoriti(id: number): void {
    this.dishes.getFavorites(id).subscribe((data: Favorite[]) => {
      this.favoriteDishes = data;
    });
  }

  checkMatch(id: number): boolean {
    return this.favoriteDishes.some((element) => element.dishId === id);
  }

  addFavorite(dishId: number): void {
    const favorite = {
      dishId: dishId,
      userId: this.utente?.newUserPayload?.id
    }

    if (favorite.userId) {
      this.dishes.addFavorite(favorite).subscribe(() => {
        this.favoriti(favorite.userId!);
      });
    }
  }

  removeFavorite(id: number): void {
    const realId = this.findId(id);

    if (realId) {
      this.dishes.removeFavorite(realId).subscribe(() => {
        this.favoriti(this.utente.newUserPayload?.id);
      });
    }
  }

  findId(id: number): number | null {
    const myDish = this.favoriteDishes.find((element) => element.dishId === id);
    return myDish?.id ?? null;
  }
}




