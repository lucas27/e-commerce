<<<<<<< HEAD
import { Component, ChangeDetectionStrategy } from '@angular/core';
=======
import { Component } from '@angular/core';
>>>>>>> 80db5a919bccb7a906d62d2f8bd811b2b1754c4e
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-home',
<<<<<<< HEAD
  // é necessário importar o RouterLink, sem ele o routerLink no html não funciona
  imports: [RouterLink],
  templateUrl: './home.html',
  changeDetection: ChangeDetectionStrategy.Eager,
=======
  // é necessário importar o RouterLink, sem ele o routerLink no html não funciona 
  imports: [RouterLink],
  templateUrl: './home.html',
>>>>>>> 80db5a919bccb7a906d62d2f8bd811b2b1754c4e
  styleUrl: './home.css',
})
export class Home {}
