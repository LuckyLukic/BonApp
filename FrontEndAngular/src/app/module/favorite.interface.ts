import { Dish } from "./dish.interface";

export interface Favorite {
  content: Favorite[];
    prodotto: Dish
    favoriteCount: number;
    userId?: string | null | undefined;
    id?: string
}
