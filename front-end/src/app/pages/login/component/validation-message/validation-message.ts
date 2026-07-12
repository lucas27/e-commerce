import { Component, inject, input, Input, ChangeDetectionStrategy } from '@angular/core';
import { Login } from '../../login';

@Component({
  selector: 'app-validation-message',
  changeDetection: ChangeDetectionStrategy.Eager,
  template: '<p class="text-white pl-18">{{message}}</p>',
})
export class ValidationMessage {
  @Input() message: string = '';
}
