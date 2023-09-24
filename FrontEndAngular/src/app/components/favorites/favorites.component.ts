import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { MenuService } from 'src/app/service/menu.service';
import { Favorite } from 'src/app/module/favorite.interface';
import { Dish } from 'src/app/module/dish.interface';


@Component({
  selector: 'app-favorites',
  templateUrl: './favorites.component.html',
  styleUrls: ['./favorites.component.scss']
})
export class FavoritesComponent implements OnInit {


  topFavoriteDishes: Favorite[] = []
  productsInOrder: Dish[] = [];

  constructor(
               private dishes: MenuService,
               private router: Router) { }

  ngOnInit(): void {
  this.topFavorite()

  }

  topFavorite():void {
    this.dishes.getTopFavorites().subscribe((allDishes: Favorite) => {

      this.topFavoriteDishes = allDishes.content;
      console.log("List", this.topFavoriteDishes)

  })

  }

  getIngredientName(ingredient: any): string {
    return (ingredient as any).nome;
  }

  navigateToDestination() {

    const currentUrl = this.router.url;
    this.router.navigate(['/dishes/:id'], { state: { returnUrl: currentUrl } })

}

  }



