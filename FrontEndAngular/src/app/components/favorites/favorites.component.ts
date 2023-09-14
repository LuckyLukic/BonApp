import { Component, OnInit } from '@angular/core';
import { Utente } from 'src/app/module/utente.interface';
import { MenuService } from 'src/app/service/menu.service';
import { AuthService } from "src/app/service/auth.service";
import { Favorite } from 'src/app/module/favorite.interface';
import { Dish } from 'src/app/module/dish.interface';
import { AuthData } from 'src/app/module/auth-data.interface';
@Component({
  selector: 'app-favorites',
  templateUrl: './favorites.component.html',
  styleUrls: ['./favorites.component.scss']
})
export class FavoritesComponent implements OnInit {

  utente!: Partial<Utente>;
  favoriteDishes!: Favorite[];
  menu!: Dish[];
  preferedDishList: Dish[] = [];
  constructor(private dishes: MenuService, private authSrv: AuthService) { }

  ngOnInit(): void {
    this.authSrv.user$.subscribe((_utente) => {
      if (_utente) {
        this.utente = _utente;

        this.favoriti(this.utente.id!);
      }
    });
  }

  favoriti(id: string): void {
    this.dishes.getFavorites(id).subscribe((data: Favorite[]) => {
      this.favoriteDishes = data;
      for (let dish of this.favoriteDishes) {
        this.dishes.getDishDetail(dish.dishId).subscribe((data: Dish) => {
          this.preferedDishList.push(data);
        });
      }
    });
  }

  removeFavorite(id:string):void {
    const realId = this.findId(id)

    if(realId) {
    this.dishes.removeFavorite(realId!).subscribe()
    location.reload()

  }
  }

  findId(id:string): string | null {

    const myDish = this.favoriteDishes.find(element =>
      element.dishId===id);
      return myDish?.id!

    }

  }

