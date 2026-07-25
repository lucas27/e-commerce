import { Component } from '@angular/core';
import { Account } from './component/account/account';
import { Cart } from './component/cart/cart';
import { Search } from './component/search/search';
import { Category } from './component/category/category';

@Component({
  selector: 'app-header',
  imports: [Account, Cart, Search, Category],
  templateUrl: './header.html',
  styleUrl: './header.css',
})
export class Header {}
