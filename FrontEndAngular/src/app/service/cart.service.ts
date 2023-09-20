import { Injectable } from '@angular/core';
import { HttpBackend, HttpClient } from '@angular/common/http';
import { Dish } from '../module/dish.interface';
import { environment } from 'src/environments/environment';
import { Observable } from 'rxjs';
import { BehaviorSubject } from 'rxjs';
import { OrdineSingolo } from '../module/ordine-singolo.interface';

@Injectable({
  providedIn: 'root'
})
export class CartService {
  url = environment.baseUrl;
  private cartItemListSource = new BehaviorSubject<Dish[]>([]);
  cartItemList$ = this.cartItemListSource.asObservable();
  //public productList = new BehaviorSubject<any[]>([]);

  constructor(private http: HttpClient) { }

  setCartItemList(items: Dish[]): void {
    this.cartItemListSource.next(items);
  }
  // getProdct(): Observable<any[]> {
  //   return this.productList.asObservable();
  // }

  // setProduct(product:any){
  //   this.cartItemList.push(...product);
  //   this.productList.next(product)
  // }

  // addToArrayCart(product :any){
  //   this.cartItemList.push(product);
  //   this.productList.next(this.cartItemList);
  //   this.getTotalPrice();
  // }

  // getTotalPrice(){
  //   let grandTotal = 0;
  //   this.cartItemList.map((a:any) => {
  //     grandTotal += a.total;
  //   })
  //   }

  //   removeCartItem(product: any){
  //     this.cartItemList.map((a:any, index:any)=>{
  //       if(product.id=== a.id){
  //         this.cartItemList.splice(index,1);
  //       }
  //       })
  //     }

  //   removeAllCart() {
  //     this.cartItemList = []
  //     this.productList.next(this.cartItemList)
  //   }



  addToCart(userId: string, dishId: string, qty: number): Observable<any> { // Specified return type
    return this.http.post(this.url + `users/${userId}/add-to-cart/${dishId}?quantity=${qty}`, {});
  }

  removeFromCart(userId: string, dishId: string, qty: number): Observable<any> { // Specified return type
    return this.http.delete(this.url + `users/${userId}/remove-from-cart/${dishId}?quantity=${qty}`);
  }

  getProductsInOrder(userId: string): Observable<Dish[]> {
    return this.http.get<Dish[]>(this.url + `users/${userId}/cart/products`);
  }

  // getNumberOfSameProducts(userId: string, productId: string): Observable<any> { // Specified return type
  //   return this.http.get(this.url + `users/${userId}/product-quantity?productId=${productId}`);
  // }

  getOrdineWithIncart(userId:string): Observable<any> {
    return this.http.get(this.url+`users/${userId}/ordine-singolo-incart`)
  }

}
