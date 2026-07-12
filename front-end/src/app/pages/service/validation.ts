import { Injectable, Service } from '@angular/core';

// versão antiga do angular
// @Injectable({
//   providedIn: 'root',
// })
type account = {
    email: string,
    password: string
}

interface error {
    viewMessageError: string,
    messageError: string
}

@Service()
export class Validation {
    validationAccount({email, password}: account): error {
        if (!email.includes('@gmail.com') || email.trim() === '') {
            return { viewMessageError: 'login', messageError: 'digite um email válido' };
        
        } else if (password.length < 8 || password.trim() === '') {
        
            return { viewMessageError: 'password', messageError: 'A senha tem que ter mais de 8 caracteres' };
        } 
        return { viewMessageError: '', messageError: '' };
    } 
}
