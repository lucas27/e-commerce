import { Component } from '@angular/core';

// ver static, readonly
interface User {
  login: string;
  password: string;
}
// interface tem que está acima do @component
@Component({
  selector: 'app-login',
  imports: [],
  templateUrl: './login.html',
  styleUrl: './login.css',
})


export class Login {
  accountLogin: User | null = null;
  
  getValue(email:string, password:string) {
    this.accountLogin = {
      login: email,
      password: password
    }
    console.log(this.accountLogin);
  };
}
