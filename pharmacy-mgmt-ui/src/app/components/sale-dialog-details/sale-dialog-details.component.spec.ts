import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SaleDialogDetailsComponent } from './sale-dialog-details.component';

describe('SaleDialogDetailsComponent', () => {
  let component: SaleDialogDetailsComponent;
  let fixture: ComponentFixture<SaleDialogDetailsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SaleDialogDetailsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SaleDialogDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
