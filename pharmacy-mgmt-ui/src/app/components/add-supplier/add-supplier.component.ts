import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { SupplierDto } from 'src/app/models/supplier-dto';
import { SupplierService } from 'src/app/services/supplier.service';

@Component({
  selector: 'app-add-supplier',
  templateUrl: './add-supplier.component.html',
  styleUrls: ['./add-supplier.component.scss']
})
export class AddSupplierComponent implements OnInit {

  @Input() supplier?: SupplierDto;
  @Input() isEditMode = false;
  supplierForm!: FormGroup;
  submitting = false;

  ngOnInit(): void {
    this.buildForm();

    if (this.isEditMode && this.supplier) {
      this.supplierForm.patchValue(this.supplier);
      this.supplierForm.get('supplierCode')?.disable(); // optional
    }

    if (this.isEditMode && this.supplier) {
      this.supplierForm.patchValue({
        supplierCode: this.supplier.supplierCode,
        supplierName: this.supplier.supplierName,
        contactPerson: this.supplier.contactPerson,
        phone: this.supplier.phone,
        email: this.supplier.email,
        gstNumber: this.supplier.gstNumber,
        drugLicenseNumber: this.supplier.drugLicenseNumber,
        bankName: this.supplier.bankName,
        bankAccountNumber: this.supplier.bankAccountNumber,
        ifscCode: this.supplier.ifscCode
      });

      this.supplierForm.get('supplierCode')?.disable(); // 🔒 usually immutable
    }
  }

  constructor(
    private fb: FormBuilder,
    private supplierService: SupplierService,
    public activeModal: NgbActiveModal
  ) {
  }

  submit() {
    if (this.supplierForm.invalid) {
      this.supplierForm.markAllAsTouched();
      return;
    }

    this.submitting = true;

    const payload = this.supplierForm.getRawValue(); // includes disabled fields

    if (this.isEditMode && this.supplier?.id) {
      this.supplierService.updateSupplier(this.supplier.id, payload).subscribe({
        next: () => {
          this.submitting = false;
          this.activeModal.close(true);
        },
        error: () => this.submitting = false
      });
    } else {
      this.supplierService.createSupplier(payload).subscribe({
        next: () => {
          this.submitting = false;
          this.activeModal.close(true);
        },
        error: () => this.submitting = false
      });
    }
  }


  cancel(): void {
    this.activeModal.dismiss();
  }

  isInvalid(controlName: string): boolean {
    const control = this.supplierForm.get(controlName);
    return !!(control && control.touched && control.invalid);
  }

  private buildForm(): void {
    this.supplierForm = this.fb.group({
      supplierCode: [
        '',
        [
          Validators.required,
          Validators.minLength(3),
          Validators.maxLength(20)
        ]
      ],
      supplierName: [
        '',
        [
          Validators.required,
          Validators.minLength(3),
          Validators.maxLength(100)
        ]
      ],
      contactPerson: [
        '',
        [
          Validators.maxLength(100)
        ]
      ],
      phone: [
        '',
        [
          Validators.pattern(/^[6-9]\d{9}$/)
        ]
      ],
      email: [
        '',
        [
          Validators.email,
          Validators.maxLength(100)
        ]
      ],
      gstNumber: [
        '',
        [
          Validators.pattern(/^[0-9]{2}[A-Z]{5}[0-9]{4}[A-Z]{1}[1-9A-Z]{1}Z[0-9A-Z]{1}$/)
        ]
      ],
      drugLicenseNumber: [
        '',
        [
          Validators.maxLength(50)
        ]
      ],
      bankName: [
        '',
        [
          Validators.maxLength(100)
        ]
      ],
      bankAccountNumber: [
        '',
        [
          Validators.pattern(/^[0-9]{9,18}$/)
        ]
      ],
      ifscCode: [
        '',
        [
          Validators.pattern(/^[A-Z]{4}0[A-Z0-9]{6}$/)
        ]
      ]
    });
  }

  /*
  {
  "supplierCode": "SUP-001",
  "supplierName": "Apollo Pharma Distributors",
  "contactPerson": "Ramesh Kumar",
  "phone": "9876543210",
  "email": "ramesh.kumar@apollopharma.com",
  "gstNumber": "29ABCDE1234F1Z5",
  "drugLicenseNumber": "DL-KA-2023-45897",
  "bankName": "HDFC Bank",
  "bankAccountNumber": "50200012345678",
  "ifscCode": "HDFC0001234"
}
  */

}
