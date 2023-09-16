import { Dish } from "./dish.interface"

export interface OwnFavorite {

  prodotto: Dish,
  favoriteCount: number,
  userId?: string,
  id?: string
}
