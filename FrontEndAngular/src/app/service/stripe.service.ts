import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient } from '@angular/common/http';
import { Stripe, loadStripe } from '@stripe/stripe-js';

@Injectable({
  providedIn: 'root'
})
export class StripeService {

  private stripePromise: Promise<Stripe | null>;
  private baseURL = `${environment.baseUrl}stripe`;

  constructor(private http:HttpClient) {
      this.stripePromise = loadStripe(
      'pk_test_51NsjSxE5ojrJW2GI0sgliGXngCD37A8RqdnvCwJZxTTj6XMypwh46ECBPI7JqT1z6OsDI07tHAW8Ta30baJwR12G00BVRViucI');
  }

      async getStripe(): Promise<Stripe> {
        const stripe = await this.stripePromise;
        if (!stripe) {
          throw new Error('Failed to load Stripe.');
        }
        return stripe;
      }

      async redirectToCheckout(totalPrice: number) {

        const session = await this.http
          .get<any>(`${this.baseURL}/create-checkout-session/${totalPrice}`)
          .toPromise();
        const stripe = await this.getStripe();
        const { error } = await stripe.redirectToCheckout({
          sessionId: session.id,
        });

        if (error) {
          console.error(error);
        }
      }
}
