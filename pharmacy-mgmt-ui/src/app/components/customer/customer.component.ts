import { Component, OnInit } from '@angular/core';
import { Customer } from "../../models/customer";
import { CustomerService } from "../../services/customer.service";
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-customer',
  templateUrl: './customer.component.html',
  styleUrls: ['./customer.component.scss']
})
export class CustomerComponent implements OnInit {

  customers: Customer[] = [];
  searchText: string = '';
  sortDirection: 'asc' | 'desc' = 'asc';
  sortColumn: string = '';
  selectedCustomer: Customer = { id: 0, name: '', email: '', phone: '', gender: '', dob: '' };
  customerToDelete: Customer | null = null;
  newCustomer: Customer = { id: 0, name: '', email: '', phone: '', gender: '', dob: '' };
  addCustomerForm!: FormGroup;

  constructor(private customerService: CustomerService,
    private fb: FormBuilder
  ) {
  }

  ngOnInit() {
    this.loadCustomers();
    //this.loadMockCustomer();
    this.initForm();
  }

  loadCustomers() {
    this.customerService.getCustomers().subscribe(res => {
      this.customers = res;
    });
  }

  initForm() {
    this.addCustomerForm = this.fb.group({
      name: ['', [Validators.required, Validators.minLength(3)]],
      email: ['', [Validators.required, Validators.email]],
      phone: ['', [Validators.required, Validators.pattern('^[0-9]{10}$')]],
      gender: ['', Validators.required],
      dob: ['', Validators.required]
    });
  }

  // Add customer
  addCustomer() {
    if (this.addCustomerForm.invalid) {
      this.addCustomerForm.markAllAsTouched();
      return;
    }

    const newCustomer: Customer = {
      id: 0, // backend may generate id
      ...this.addCustomerForm.value
    };

    this.customerService.addCustomer(newCustomer).subscribe({
      next: (createdCustomer) => {
        this.customers.push(createdCustomer);

        // Reset form
        this.addCustomerForm.reset();

        // Close modal
        const modal = document.getElementById('addCustomerModal');
        (window as any).bootstrap.Modal.getInstance(modal)?.hide();
      },
      error: (err) => {
        console.error('Failed to add customer', err);
        alert('Failed to add customer. Please try again.');
      }
    });
  }


  deleteCustomer(id: number) {
    if (confirm('Are you sure you want to delete this medicine?')) {
      this.customerService.deleteCustomer(id).subscribe(() => this.loadCustomers());
    }
  }

  filterCustomer() {
    if (!this.searchText) {
      return this.customers;
    }

    const text = this.searchText.toLowerCase();

    return this.customers.filter((cust: Customer) =>
      cust.name.toLowerCase().includes(text) ||
      cust.email.toLowerCase().includes(text) ||
      cust.phone.toLowerCase().includes(text)
    );
  }

  openEditDialog(customer: Customer) {
    this.selectedCustomer = {
      ...customer,
      gender: customer.gender?.toLowerCase() === 'male' ? 'Male' : 'Female'
    };
  }

  updateCustomer() {
    console.log('Updated Customer:', this.selectedCustomer);

    // Call API or update list here

    // Close modal manually
    const modal = document.getElementById('editCustomerModal');
    const modalInstance = (window as any).bootstrap.Modal.getInstance(modal);
    modalInstance.hide();
  }

  openDeleteModal(customer: Customer) {
    this.customerToDelete = customer;
  }

  deleteCustomerConfirmed() {
    if (this.customerToDelete) {
      this.customers = this.customers.filter(c => c.id !== this.customerToDelete!.id);
      this.customerToDelete = null;

      // Close the modal programmatically
      const modal = document.getElementById('deleteConfirmModal');
      (window as any).bootstrap.Modal.getInstance(modal)?.hide();

      // Optionally call API here
    }
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

    this.customers.sort((a: any, b: any) => {
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


  sortByName() {
    this.sortDirection = this.sortDirection === 'asc' ? 'desc' : 'asc';

    this.customers.sort((a: any, b: any) => {
      const nameA = a.name.toLowerCase();
      const nameB = b.name.toLowerCase();

      if (nameA < nameB) return this.sortDirection === 'asc' ? -1 : 1;
      if (nameA > nameB) return this.sortDirection === 'asc' ? 1 : -1;
      return 0;
    });
  }

  private loadMockCustomer() {
    this.customers = [
      {
        id: 1,
        name: 'Hari',
        email: 'hari.duddukunta@gmail.com',
        gender: 'Male',
        phone: '9494968081',
        dob: '06-04-1989'
      }
    ]
  }
}
