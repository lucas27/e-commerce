import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CartItens } from './cart-itens';

describe('CartItens', () => {
  let component: CartItens;
  let fixture: ComponentFixture<CartItens>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CartItens],
    }).compileComponents();

    fixture = TestBed.createComponent(CartItens);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
