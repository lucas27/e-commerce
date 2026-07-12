<<<<<<< HEAD
import { Component, ChangeDetectionStrategy, inject } from '@angular/core';
import { LucideCircleUser, LucideLock, LucideLockKeyholeOpen, LucideUser } from '@lucide/angular';
import { RouterLink } from '@angular/router';
import { ValidationMessage } from '../component/validation-message/validation-message';
import { Validation } from '../service/validation';

// ver static, readonly
interface User {
  login: string;
  password: string;
}
// interface tem que está acima do @component
@Component({
  selector: 'app-login',
  imports: [
    LucideLock,
    LucideUser,
    LucideCircleUser,
    LucideLockKeyholeOpen,
    RouterLink,
    ValidationMessage,
  ],
  // template: '<svg lucideLock></svg>',
  templateUrl: './login.html',
  changeDetection: ChangeDetectionStrategy.Eager,
  styleUrl: './login.css',
})
export class Login {
  private accountLogin: User | null = null;
  protected viewPassword: boolean = false;
  protected viewMessageError: string = '';
  protected messageError: string = '';

  private validation = inject(Validation);


  changeVisibility() {
    this.viewPassword = !this.viewPassword;
    console.log(this.viewPassword);
  }

  getValue(email: string, password: string) {
    const verify = this.validation.validationAccount({ email, password });
    this.viewMessageError = verify.viewMessageError;
    this.messageError = verify.messageError;

    if(!this.viewMessageError) {
      this.accountLogin = {
        login: email,
        password: password,
      };
      console.log(this.accountLogin);

    }
  }
}
=======
import { Component } from '@angular/core';

@Component({
  selector: 'app-login',
  imports: [],
  templateUrl: './login.html',
  styleUrl: './login.css',
})
export class Login {}
>>>>>>> 80db5a919bccb7a906d62d2f8bd811b2b1754c4e
