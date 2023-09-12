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

// const route: Route[] = [
//   {
//     path: "",
//     component: HomeComponent
//   },
//   {
//     path: "movies/:id",
//     component: SingleMovieComponent,
//     canActivate: [AuthGuardGuard]
//   },
//   {path: "users",
//   component: UsersComponent, children:[
//     {path:':id', component: SingleUserComponent}
//   ]
//   },
//   {path: "register",
//    component: RegisterComponent
//   },
//   {
//     path: "login",
//     component: LoginComponent
//   },
//   {
//     path: "ownprofile/:id",
//     component: OwnProfileComponent,
//     canActivate: [AuthGuardGuard],
//   },
//   {
//     path: "favorites/:id",
//     component: FavoritesComponent,
//     canActivate: [AuthGuardGuard]
//   },
//   {
//     path: "**",
//     component: Error404Component
//   }

// ]


@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    LoginComponent,
    RegisterComponent,
    HomeComponent,
  ],
  imports: [
    BrowserModule,
    // RouterModule.forRoot(route),
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
