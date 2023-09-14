import { Component, OnInit } from '@angular/core';
import { MenuService } from 'src/app/service/menu.service';
import { Dish } from 'src/app/module/dish.interface';
import { ActivatedRoute } from '@angular/router';


@Component({
  selector: 'app-single-dish',
  templateUrl: './single-dish.component.html',
  styleUrls: ['./single-dish.component.scss']
})
export class SingleDishComponent implements OnInit {

  dish!: Dish
  myId!: string


  constructor(private dishes: MenuService, private route: ActivatedRoute ) { }

  ngOnInit(): void {
    if(this.route.snapshot.paramMap.get('id')) {
      this.myId = this.route.snapshot.paramMap.get('id')!
      this.dishes.getDishDetail(this.myId).subscribe((data:Dish)=> {
        this.dish = data
        console.log(this.dish)
      })
    }

    }

}

