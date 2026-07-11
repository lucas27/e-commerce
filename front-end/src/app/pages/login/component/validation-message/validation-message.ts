import { Component, inject, input, Input } from '@angular/core';
import { Login } from '../../login';

@Component({
  selector: 'app-validation-message',
  template: '<p class="text-white pl-18">{{message}}</p>',
})
export class ValidationMessage {
  @Input() message: string = "";
}
