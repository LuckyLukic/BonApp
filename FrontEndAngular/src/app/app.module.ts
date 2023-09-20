import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { ReactiveFormsModule } from '@angular/forms';
import { RouterModule, Route } from '@angular/router';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http'  //
import { AuthGuardGuard } from './service/auth-guard.guard';
import { TokenInterceptor } from './module/token.interceptor';
import { FormsModule } from '@angular/forms';
import { AppComponent } from './app.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import { HomeComponent } from './components/home/home.component';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import { FavoritesComponent } from './components/favorites/favorites.component';
import { SingleDishComponent } from './components/single-dish/single-dish.component';
import { ReviewsComponent } from './components/reviews/reviews.component';
import { OwnProfileComponent } from './components/own-profile/own-profile.component';
import { SingleUserComponent } from './components/users/single-user/single-user.component';
import { UsersComponent } from './components/users/users.component';
import { OwnFavoritesComponent } from './components/favorites/own-favorites/own-favorites.component';
import { OwnReviewsComponent } from './components/reviews/own-reviews/own-reviews.component';
import { CartComponent } from './components/cart/cart.component';
import { UpdateComponent } from './components/own-profile/update/update.component';
import { RateUsComponent } from './components/reviews/rate-us/rate-us.component';

 const route: Route[] = [
   {
     path: "",
     component: HomeComponent
   },
  {
    path: "dishes/:id",
    component: SingleDishComponent,
  },
  {path: "users",
  component: UsersComponent, children:[
    {path:':id', component: SingleUserComponent}
  ]
  },
   {path: "register",
    component: RegisterComponent
   },
   {
     path: "login",
     component: LoginComponent
   },
  {
      path: 'ownprofile/:id',
      component: OwnProfileComponent,
      canActivate: [AuthGuardGuard],
    },
    {
      path: 'ownprofile/:id/update',
      component: UpdateComponent,
      canActivate: [AuthGuardGuard],
    },

   {
     path: "top-favoriti",
     component: FavoritesComponent,

   },
   {
    path: "favorites/:id",
    component: OwnFavoritesComponent,
    canActivate: [AuthGuardGuard]
   },
   {
    path: "all-reviews",
    component: ReviewsComponent,

  },
  {
    path: "own-reviews/:id",
    component: OwnReviewsComponent,
    canActivate: [AuthGuardGuard]

  },
  {
   path: "rate-us/:id",
   component: RateUsComponent,
   canActivate: [AuthGuardGuard]
  },
  {
    path: "cart/:id",
    component: CartComponent,
    canActivate: [AuthGuardGuard]

  }

//   {
//     path: "**",
//     component: Error404Component
//   }

 ]


@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    LoginComponent,
    RegisterComponent,
    HomeComponent,
    FavoritesComponent,
    ReviewsComponent,
    OwnProfileComponent,
    UsersComponent,
    OwnFavoritesComponent,
    OwnReviewsComponent,
    CartComponent,
    UpdateComponent,
    RateUsComponent,


  ],
  imports: [
    BrowserModule,
    RouterModule.forRoot(route),
    HttpClientModule,
    ReactiveFormsModule,
    FormsModule
  ],
  providers: [ {provide: HTTP_INTERCEPTORS,
    useClass: TokenInterceptor,
    multi: true
  }],
  bootstrap: [AppComponent]
})
export class AppModule { }
