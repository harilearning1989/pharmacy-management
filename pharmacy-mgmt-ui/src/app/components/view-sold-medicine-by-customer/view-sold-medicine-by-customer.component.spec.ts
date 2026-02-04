import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewSoldMedicineByCustomerComponent } from './view-sold-medicine-by-customer.component';

describe('ViewSoldMedicineByCustomerComponent', () => {
  let component: ViewSoldMedicineByCustomerComponent;
  let fixture: ComponentFixture<ViewSoldMedicineByCustomerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ViewSoldMedicineByCustomerComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ViewSoldMedicineByCustomerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
