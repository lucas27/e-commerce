import { Component } from '@angular/core';
import { LucideCircleUser, LucideFileText, LucideLock, LucideLockKeyholeOpen, LucideUser } from '@lucide/angular';
import { RouterLink } from "@angular/router";
import { NgIf } from "../../../../node_modules/@angular/common/types/_common_module-chunk";

// ver static, readonly
interface User {
  login: string;
  password: string;
}
// interface tem que está acima do @component
@Component({
  selector: 'app-login',
  imports: [LucideLock, LucideUser, LucideCircleUser, LucideLockKeyholeOpen, RouterLink],
  // template: '<svg lucideLock></svg>',
  templateUrl: './login.html',
  styleUrl: './login.css',
})


export class Login {
  private accountLogin: User | null = null;
  protected viewPassword: boolean = false;

  changeVisibility(){
    this.viewPassword = !this.viewPassword;
    console.log(this.viewPassword);
  };

  getValue(email:string, password:string) {
    if(!email.includes("@gmail.com") || email.trim() === "") {
      throw new Error("digite um email válido");
    } else if(password.length < 8 || password.trim() === "") {
      throw new Error("A senha tem que ter mais de 8 caracteres");
    }

    this.accountLogin = {
      login: email,
      password: password
    }
    console.log(this.accountLogin);
  };
}
