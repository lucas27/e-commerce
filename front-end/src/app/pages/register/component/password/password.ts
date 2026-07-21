import { Component, EventEmitter, Output } from '@angular/core';
import { LucideEye, LucideEyeOff } from '@lucide/angular';

@Component({
  selector: 'app-password',
  imports: [
    LucideEye,
    LucideEyeOff, 
  ],
  templateUrl: './password.html',
})
export class Password {
  @Output() protected password = new EventEmitter<string>();

  protected viewPassword: boolean = false;
  
  protected changePasswordVisibility = (): void => {
    this.viewPassword = !this.viewPassword;
    
    this.password.emit(this.viewPassword ? 'text' : 'password' );
  };
}
