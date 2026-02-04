import { Component, OnInit } from '@angular/core';
import { SupplierService } from 'src/app/services/supplier.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { AddSupplierComponent } from '../add-supplier/add-supplier.component';
import { SupplierDto } from 'src/app/models/supplier-dto';
import { DeleteConfirmationModalComponent } from '../delete-confirmation-modal/delete-confirmation-modal.component';

@Component({
  selector: 'app-supplier',
  templateUrl: './supplier.component.html',
  styleUrls: ['./supplier.component.scss']
})
export class SupplierComponent implements OnInit {

  suppliers: SupplierDto[] = [];
  searchText: string = '';
  sortDirection: 'asc' | 'desc' = 'asc';
  sortColumn: string = '';
  alertMessage: string = '';
  alertType: string = '';

  constructor(private supplierService: SupplierService,
    private ngbModal: NgbModal
  ) {
  }

  ngOnInit() {
    this.getAllSuppliers();
  }

  getAllSuppliers() {
    this.supplierService.getAllSuppliers().subscribe(res => {
      this.suppliers = res;
    });
  }

 openDeleteModal(supplier: SupplierDto): void {
  const modalRef = this.ngbModal.open(DeleteConfirmationModalComponent, {
    centered: true,
    backdrop: 'static'
  });

  modalRef.componentInstance.title = 'Delete Customer';
  modalRef.componentInstance.message =
    `Are you sure you want to delete ${supplier.supplierName}?`;

  modalRef.result.then(
    (confirmed) => {
      if (confirmed) {
        this.deleteSupplier(supplier.id);
      }
    },
    () => {} // dismissed
  );
}

deleteSupplier(id: number): void {
  this.supplierService.deleteSupplier(id).subscribe({
    next: () => {
      this.showSuccess('Supplier deleted successfully');
      this.getAllSuppliers(); // refresh API
    },
    error: () => {
      this.showError('Failed to delete supplier');
    }
  });
}

showSuccess(message: string) {
  this.alertMessage = message;
  this.alertType = 'success';

  setTimeout(() => {
    this.alertMessage = '';
  }, 3000);
}

showError(message: string) {
  this.alertMessage = message;
  this.alertType = 'danger';
  setTimeout(() => {
    this.alertMessage = '';
  }, 3000);
}

  addSupplierModal() {
    const modalRef = this.ngbModal.open(AddSupplierComponent, {
      size: 'lg',
      backdrop: 'static'
    });
    modalRef.result.then(
      (result) => {
        if (result === true) {
          this.getAllSuppliers(); // 🔄 refresh main page
        }
      },
      () => {
        // dismissed — do nothing
      }
    );
  }

  editSupplier(supplier: SupplierDto) {
    const modalRef = this.ngbModal.open(AddSupplierComponent, {
      size: 'lg',
      backdrop: 'static'
    });

    modalRef.componentInstance.supplier = supplier; // 👈 pass data
    modalRef.componentInstance.isEditMode = true;

    modalRef.result.then(
      (result) => {
        if (result === true) {
          this.getAllSuppliers(); // 🔄 refresh list
        }
      },
      () => { }
    );
  }

  filterCustomer() {
    if (!this.searchText) {
      return this.suppliers;
    }

    const text = this.searchText.toLowerCase();

    return this.suppliers.filter(cust =>
      cust.supplierName.toLowerCase().includes(text) ||
      cust.email.toLowerCase().includes(text) ||
      cust.phone.toLowerCase().includes(text)
    );
  }

  sort(column: string) {
    if (this.sortColumn === column) {
      // toggle direction
      this.sortDirection = this.sortDirection === 'asc' ? 'desc' : 'asc';
    } else {
      // new column sort
      this.sortColumn = column;
      this.sortDirection = 'asc';
    }

    this.suppliers.sort((a: any, b: any) => {
      let valueA = a[column];
      let valueB = b[column];

      // Handle string sorting
      if (typeof valueA === 'string') {
        valueA = valueA.toLowerCase();
        valueB = valueB.toLowerCase();
      }

      if (valueA < valueB) return this.sortDirection === 'asc' ? -1 : 1;
      if (valueA > valueB) return this.sortDirection === 'asc' ? 1 : -1;
      return 0;
    });
  }

  getSortIcon(column: string): string {
    if (this.sortColumn !== column) {
      return 'bi-arrow-down-up'; // default
    }
    return this.sortDirection === 'asc'
      ? 'bi-sort-alpha-down'
      : 'bi-sort-alpha-up';
  }

}
