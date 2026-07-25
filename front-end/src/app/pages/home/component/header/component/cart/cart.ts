import { Component } from '@angular/core';
import { LucideShoppingCart } from '@lucide/angular';

@Component({
  selector: 'app-cart',
  imports: [LucideShoppingCart],
  templateUrl: './cart.html',
  styleUrl: './cart.css',
})
export class Cart {}
