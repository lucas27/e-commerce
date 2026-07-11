import { Component } from '@angular/core';
import { LucideCircleUser, LucideLock, LucideLockKeyholeOpen, LucideUser } from '@lucide/angular';
import { RouterLink } from "@angular/router";
import { ValidationMessage } from "./component/validation-message/validation-message"

// ver static, readonly
interface User {
  login: string;
  password: string;
}
// interface tem que está acima do @component
@Component({
  selector: 'app-login',
  imports: [LucideLock, LucideUser, LucideCircleUser, LucideLockKeyholeOpen, RouterLink, ValidationMessage],
  // template: '<svg lucideLock></svg>',
  templateUrl: './login.html',
  styleUrl: './login.css',
})

export class Login {
  private accountLogin: User | null = null;
  protected viewPassword: boolean = false;
  protected viewMessageError: string = '';
  protected messageError: string = '';

  changeVisibility(){
    this.viewPassword = !this.viewPassword;
    console.log(this.viewPassword);
  };

  getValue(email:string, password:string) {
    if(!email.includes("@gmail.com") || email.trim() === "") {
      this.viewMessageError = "login";
      this.messageError = "digite um email válido";
      throw new Error("digite um email válido");
    } else if(password.length < 8 || password.trim() === "") {
      this.viewMessageError = "password";
      this.messageError = "A senha tem que ter mais de 8 caracteres";
      throw new Error("A senha tem que ter mais de 8 caracteres");
    } else {
      this.viewMessageError = '';
      this.messageError = '';
    }

    this.accountLogin = {
      login: email,
      password: password
    }
    console.log(this.accountLogin);
  };
}
