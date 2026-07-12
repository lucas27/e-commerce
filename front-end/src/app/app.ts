<<<<<<< HEAD
import { Component, signal, ChangeDetectionStrategy } from '@angular/core';
=======
import { Component, signal } from '@angular/core';
>>>>>>> 80db5a919bccb7a906d62d2f8bd811b2b1754c4e
import { RouterOutlet, RouterLink } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.html',
  styleUrl: './app.css',
  // o RouterOutlet serve para renderizar o componente dentro html.
  // ou seja, onde tiver <router-outlet></router-outlet> vai ser renderizado o componente
<<<<<<< HEAD
  changeDetection: ChangeDetectionStrategy.Eager,
  imports: [RouterOutlet],
=======
  imports: [RouterOutlet]
>>>>>>> 80db5a919bccb7a906d62d2f8bd811b2b1754c4e
})
export class App {
  // protected readonly title = signal('front-end');
}
