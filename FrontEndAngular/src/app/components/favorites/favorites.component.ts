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

  user!: AuthData | null;
  favoriteDishes!: Favorite[];
  menu!: Dish[];
  preferedDishList: Dish[] = [];
  constructor(private dishes: MenuService, private authSrv: AuthService) { }

  ngOnInit(): void {
    this.authSrv.user$.subscribe((userData) => {
      if (userData) {
        this.user = userData; // Update the user property with the correct user data
      if (this.user?.user?.id !== null) {
        this.favoriti(this.user.user.id);
      }
    }
    });
  }

  favoriti(id: number): void {
    this.dishes.getFavorites(id).subscribe((data: Favorite[]) => {
      this.favoriteDishes = data;
      for (let dish of this.favoriteDishes) {
        this.dishes.getDishDetail(dish.dishId).subscribe((data: Dish) => {
          this.preferedDishList.push(data);
        });
      }
    });
  }

  removeFavorite(id:number):void {
    const realId = this.findId(id)

    if(realId) {
    this.dishes.removeFavorite(realId!).subscribe()
    location.reload()

  }
  }

  findId(id:number): number | null {

    const myDish = this.favoriteDishes.find(element =>
      element.dishId===id);
      return myDish?.id!

    }

  }

