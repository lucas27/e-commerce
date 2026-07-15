import { Component, inject, input, Input, ChangeDetectionStrategy, signal } from '@angular/core';

@Component({
  selector: 'app-validation-message',
  changeDetection: ChangeDetectionStrategy.Eager,
  template: '<p class="text-white pl-1">{{message}}</p>',
})
export class ValidationMessage {
  @Input() message: string | undefined = '';
}
