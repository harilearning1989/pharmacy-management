import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MedicineNameDetailComponent } from './medicine-name-detail.component';

describe('MedicineNameDetailComponent', () => {
  let component: MedicineNameDetailComponent;
  let fixture: ComponentFixture<MedicineNameDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MedicineNameDetailComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MedicineNameDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
