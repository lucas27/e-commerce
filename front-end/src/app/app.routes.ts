import { Routes } from '@angular/router';
import { Register } from './pages/register/register';
import { Login } from './pages/login/login';
import { Cart } from './pages/cart/cart';
import { Home } from './pages/home/home';
Home

export const routes: Routes = [
    { path: '', loadComponent: () => import('./pages/home/home').then(component => component.Home), title: "main page" },
    { path: 'register', component: Register, title:"register page" },
    { path: 'login', component: Login, title: "login page" },
    { path: 'cart', component: Cart, title: "cart page" },
    // { path: 'create', title: "create itens"},
    { path: '**', redirectTo: ''}
];
