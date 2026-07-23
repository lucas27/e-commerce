import { Component, ChangeDetectionStrategy, signal, inject } from '@angular/core';
import { form, FormField, submit } from '@angular/forms/signals';
import { LucideCircleUser } from '@lucide/angular';
import { ValidateRegisterForm } from './service/validate-register-form';
import { ValidationMessage } from '../component/validation-message/validation-message';
import { Password } from './component/password/password';
import axios from 'axios';
import { Router } from '@angular/router';

interface validationRegister {
  name: string;
  email: string;
  password: string;
  againPassword: string;
}

interface signUp {
  name: string,
  email: string,
  password: string
}

@Component({
  selector: 'app-register',
  imports: [
    FormField, 
    LucideCircleUser,
    Password,
    ValidationMessage],
  templateUrl: './register.html',
  changeDetection: ChangeDetectionStrategy.Eager,
  styleUrl: './register.css',
})
export class Register {
  protected ViewPassword:string = "password";

  protected getValueFromComponent = (value: string): void => {
    this.ViewPassword = value;
  };

  private validation = inject(ValidateRegisterForm);
  private route = inject(Router);

  private createAccountModel = signal<validationRegister>({
    name: '',
    email: '',
    password: '',
    againPassword: '' 
  });

  protected createAccountForm = form(this.createAccountModel, (path) => {
    // Para diminuir as linhas de código, 
    // eu cologuei toda lógica de validção dentro do validate-register-form.ts
    this.validation.validation(path);
  });

 async onSubmit(event:Event){
  // onSubmit(){
    event.preventDefault();

    await submit(this.createAccountForm, async (schema) => {
      
      const credentials:signUp = {
        name: schema.name().value(),
        email: schema.email().value(),
        password: schema.password().value() 
      };

      const response = await axios.post('http://localhost:8080/Auth/sign-up', credentials);
      
      if(response.status === 201) {
        this.route.navigate(['/login']);
      }
    });
  }
}
