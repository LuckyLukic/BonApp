import { Component, OnInit } from '@angular/core';
import { environment } from 'src/environments/environment';
import { AuthService } from 'src/app/service/auth.service';
import { Dish } from 'src/app/module/dish.interface';
import { MenuService } from 'src/app/service/menu.service';
//import { Utente } from 'src/app/module/utente.interface';


@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  menu!: Dish[];
  // poster = environment.posterUrl;
  // utente!: Partial<Utente>;
  // favoriteMovies!: Favourite[];
  // singleMovie!:Favourite;

  constructor(
    private dishes: MenuService,
    private authSrv: AuthService
  ) {}

  ngOnInit(): void {

    // this.authSrv.user$.subscribe((_utente) => {
    //   if (_utente) {
    //     this.utente = _utente.user;

    //     this.favoriti(this.utente.id!)
    //   }
    // });

    this.dishes.getMenu().subscribe((allDishes: Dish[]) => {
      this.menu = allDishes;

    });
  }

  // favoriti(id:any):void {
  //   this.movies.getFavourites(id).subscribe((data:Favourite[]) => {
  //     this.favoriteMovies = data
  //   })
  // }

  // checkMatch(id:number): boolean {

  //     return this.favoriteMovies.some((element) => element.movieId===id)

  //   }

  // addFavorite(movieId: number):void {
  //   const favorite = {
  //     movieId: movieId,
  //     userId: this.utente.id,
  //   };

  //   this.movies.addFavourite(favorite).subscribe((() =>{
  //     this.favoriti(this.utente.id)
  //   }));
  // }

  // removeFavorite(id:number):void {
  //   const realId = this.findId(id)

  //   if(realId) {
  //   this.movies.removeFavorite(realId!).subscribe(() =>{
  //     this.favoriti(this.utente.id)
  //   })
  // }
  // }

  // findId(id:number): number | null {

  //   const myMovie = this.favoriteMovies.find(element =>
  //     element.movieId===id);
  //     return myMovie?.id!

  //   }
  // }
}

