import { Routes } from '@angular/router';
import { Home } from './pages/home/home';
import { Register } from './pages/register/register';
import { Login } from './pages/login/login';
import { Cart } from './pages/cart/cart';

export const routes: Routes = [
    { path: '', component: Home, title: "main page" },
    { path: 'register', component: Register, title:"register page" },
    { path: 'login', component: Login, title: "login page" },
    { path: 'cart', component: Cart, title: "cart page" }
];
