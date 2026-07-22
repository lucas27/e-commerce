import { TestBed } from '@angular/core/testing';

import { ValidateRegisterForm } from './validate-register-form';

describe('ValidateRegisterForm', () => {
  let service: ValidateRegisterForm;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ValidateRegisterForm);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
