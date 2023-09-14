import { Component, OnInit } from '@angular/core';
import { Utente } from 'src/app/module/utente.interface';
import { MenuService } from 'src/app/service/menu.service';
import { AuthService } from "src/app/service/auth.service";
import { Favorite } from 'src/app/module/favorite.interface';
import { Dish } from 'src/app/module/dish.interface';
import { AuthData } from 'src/app/module/auth-data.interface';
import { Categoria } from 'src/app/module/categoria';
@Component({
  selector: 'app-favorites',
  templateUrl: './favorites.component.html',
  styleUrls: ['./favorites.component.scss']
})
export class FavoritesComponent implements OnInit {


  topFavoriteDishes: Favorite[] = []


  constructor(private dishes: MenuService, private authSrv: AuthService) { }

  ngOnInit(): void {
  this.topFavorite()

  }

  topFavorite():void {
    this.dishes.getTopFavorites().subscribe((allDishes: Favorite) => {

      this.topFavoriteDishes = allDishes.content;

  })

  }

  getIngredientName(ingredient: any): string {
    return (ingredient as any).nome;
  }

  }

