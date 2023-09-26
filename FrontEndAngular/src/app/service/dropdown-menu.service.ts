import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { Dish } from '../module/dish.interface';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class DropdownMenuService {

  url = environment.baseUrl
  private selectedCategorySubject = new BehaviorSubject<Dish[]>([]);
  selectedCategory$ = this.selectedCategorySubject.asObservable();
  private products: Dish[] = [];

  constructor(
              private http: HttpClient
  ) {
    this.getMenu().subscribe((data:Dish) =>{
      this.products = data.content;
    });
  }

  filterByCategory(category: string) {
    const filteredProducts = this.products.filter(product => product.categoria === category);
    this.selectedCategorySubject.next(filteredProducts);
  }


  getCategory(): void {
    this.getMenu().subscribe((data: Dish) => {
      this.selectedCategorySubject.next(data.content);
    })
  }

  getMenu() {
    return this.http.get<Dish>(this.url + "prodotti");
  }
}
