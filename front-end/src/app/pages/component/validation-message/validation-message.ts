import { Component, Input, ChangeDetectionStrategy, input } from '@angular/core';
import { ValidationError } from '@angular/forms/signals';

@Component({
  selector: 'app-validation-message',
  changeDetection: ChangeDetectionStrategy.Eager,
  template: `
  @if(isTouched && isInvalid()) {
    @for(errors of error(); track errors) {
      <p class="text-white pl-1">{{errors.message}}</p>
    }
  }
  `,
})
export class ValidationMessage {
  // duas formas de fazer
  @Input() isTouched: boolean = false;

  readonly isInvalid = input.required<boolean>();
  readonly error = input.required<ValidationError.WithFieldTree[]>();
}
