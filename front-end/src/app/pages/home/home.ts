import { Component, ChangeDetectionStrategy } from '@angular/core';
import { Header } from './component/header/header';

@Component({
  selector: 'app-home',
  // é necessário importar o RouterLink, sem ele o routerLink no html não funciona
  imports: [Header],
  templateUrl: './home.html',
  changeDetection: ChangeDetectionStrategy.Eager,
  styleUrl: './home.css',
})
export class Home {
  
}
