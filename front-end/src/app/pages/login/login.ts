import { Component, ChangeDetectionStrategy, inject, signal, Signal } from '@angular/core';
import { LucideCircleUser, LucideLock, LucideLockKeyholeOpen, LucideUser } from '@lucide/angular';
import { Router, RouterLink } from '@angular/router';
import { ValidationMessage } from '../component/validation-message/validation-message';
import { form, FormField, minLength, required, submit, validate } from '@angular/forms/signals';
import axios from 'axios';

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
    FormField
  ],
  templateUrl: './login.html',
  changeDetection: ChangeDetectionStrategy.Eager,
  styleUrl: './login.css',
})
export class Login {
  protected viewPassword: boolean = false;
  private router = inject(Router);

  changeVisibility():void {
    this.viewPassword = !this.viewPassword;
    console.log(this.viewPassword);
  }
  
  loginModel = signal<User>({
    login: '',
    password: ''
  });
  
  loginForm = form(this.loginModel, (schemaPath) => {
    // eu pensei em várias formas de fazer,
    // mas nada fazia sentido em manter o service, 
    // essa foi a melhor forma
    
    validate(schemaPath.login, ({value}) => {
      const text = value();
      if(!text) {
        return {
          kind: 'void', 
          message: 'O campo de login não pode ficar vázio'
        };
      };
      return null;
    })
    
    required(schemaPath.password, {message: 'O campo de senha não pode ficar vázio'});
    minLength(schemaPath.password, 8, {message: 'A senha não pode ser menor que 8 caracteres'})
  });


  onSubmit(event: Event):void {
    event.preventDefault();
    // ele só dispara qunado não há exceção do form, 
    // fazendo com que o formulário não seja disparado
    submit(this.loginForm, async () => {
      const credentials = this.loginModel();
      await axios.post("http://localhost:8080/Auth/sign-in", credentials, {
        withCredentials: true
      });
    })
    this.router.navigate(['/'])
  }
  
  // legacy method
  // getValue(email: string, password: string) {
  //   const verify = this.validation.validationAccount({ email, password });
  //   this.viewMessageError = verify.viewMessageError;
  //   this.messageError = verify.messageError;

  //   if(!this.viewMessageError) {
  //     this.accountLogin = {
  //       login: email,
  //       password: password,
  //     };
  //     console.log(this.accountLogin);

  //   }
  // }
}
