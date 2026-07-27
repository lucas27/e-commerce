import { Component } from '@angular/core';
import { LucideShoppingCart } from '@lucide/angular';
import { CartItens } from './component/cart-itens/cart-itens';

@Component({
  selector: 'app-cart',
  imports: [LucideShoppingCart, CartItens],
  templateUrl: './cart.html',
  styleUrl: './cart.css',
})
export class Cart {
  protected qtdProducts = 0;
}
