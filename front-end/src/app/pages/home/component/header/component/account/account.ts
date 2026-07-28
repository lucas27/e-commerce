import { Component, inject, OnInit, signal } from '@angular/core';
import { RouterLink } from '@angular/router';
import { LucideCircleUser } from '@lucide/angular';
import { AuthService } from '../../../../../../auth/service/auth-service';

@Component({
  selector: 'app-account',
  imports: [LucideCircleUser, RouterLink],
  templateUrl: './account.html',
  styleUrl: './account.css',
})
export class Account implements OnInit{
  protected isLogged = signal(false);
  private auth = inject(AuthService);

  async logout() {
    const logout = await this.auth.logout();
    this.isLogged.set(logout);
  }

  async isValid() {
    const isValid = await this.auth.isValid();
    this.isLogged.set(isValid);
    sessionStorage.setItem('isCall', 'false');
  }

  ngOnInit() {
    const isCall$ = sessionStorage.getItem('isCall');
    if(isCall$?.includes('true') || isCall$ === null) {
      this.isValid();
    }
  }
  
}
