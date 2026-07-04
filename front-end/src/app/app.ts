import { Component, signal } from '@angular/core';
import { RouterOutlet, RouterLink } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.html',
  styleUrl: './app.css',
  // o RouterOutlet serve para renderizar o componente dentro html.
  // ou seja, onde tiver <router-outlet></router-outlet> vai ser renderizado o componente
  imports: [RouterOutlet]
})
export class App {
  // protected readonly title = signal('front-end');
}
