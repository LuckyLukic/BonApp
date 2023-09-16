import { Component, OnInit } from '@angular/core';
import { Favorite } from 'src/app/module/favorite.interface';
import { MenuService } from 'src/app/service/menu.service';
import { AuthService } from 'src/app/service/auth.service';
import { Utente } from 'src/app/module/utente.interface';
import { Dish } from 'src/app/module/dish.interface';
import { UserService } from 'src/app/service/utente.service';
import { ActivatedRoute, ParamMap } from '@angular/router';
import { OwnFavorite } from 'src/app/module/own-favorite.interface';

@Component({
  selector: 'app-own-favorites',
  templateUrl: './own-favorites.component.html',
  styleUrls: ['./own-favorites.component.scss']
})
export class OwnFavoritesComponent implements OnInit {

  utente!: Partial<Utente>;
  favoriteDishes: Favorite[] = [];


  constructor(private route: ActivatedRoute, private dishes: MenuService,private authSrv: AuthService, private userSrv: UserService) { }

  ngOnInit(): void {

    this.route.params.subscribe(params => {
      if (params) {
        const userId = params['id'];
        if (userId) {

        this.userSrv.getCurrentUser().subscribe((_utente) => {
        this.utente = _utente;

        this.getOwnFavorites(this.utente.id!)
        console.log("OWN FAVORITES" + this.favoriteDishes)
      })


    }
  }
})

  }




  removeFavorite(dishId: string): void {
    if (this.utente && this.utente.id) {
      this.dishes.removeFavorite(this.utente.id, dishId).subscribe(response =>{
        this.getOwnFavorites(this.utente.id!);
      }, error => {
        console.error('Error adding favorite', error);
      });
    }
  }

  getOwnFavorites(id: string): void {
    this.userSrv.getOwnFavorites(id).subscribe(
      (favorites: Favorite) => {
        console.log('Favorites:', favorites);
        this.favoriteDishes = favorites.content;
      },
      error => {
        console.error('Error fetching favorite dishes:', error);
      }
    );
  }

    findId(id:string): string | null {

      const myDish = this.favoriteDishes.find(element =>
        element.id===id);
        return myDish?.id!

    }

    getIngredientName(ingredient: any): string {
      return (ingredient as any).nome;
    }

}


