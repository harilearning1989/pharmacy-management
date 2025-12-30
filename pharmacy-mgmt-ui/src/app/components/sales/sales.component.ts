import {Component, HostListener, OnInit} from '@angular/core';
import {CustomerService} from "../../services/customer.service";
import {Customer} from "../../models/customer";

@Component({
  selector: 'app-sales',
  templateUrl: './sales.component.html',
  styleUrls: ['./sales.component.scss']
})
export class SalesComponent implements OnInit {

  constructor(private customerService: CustomerService) {
  }

  ngOnInit(): void {
  }

  activeInput: 'name' | 'phone' | null = null;

  // ✅ Close dropdown when clicking outside
  @HostListener('document:click', ['$event'])
  clickOutside(event: Event) {
    const target = event.target as HTMLElement;
    const autocomplete = document.querySelector('.autocomplete-box');
    const inputBox = document.querySelector('input');

    if (autocomplete && inputBox && !autocomplete.contains(target) && !inputBox.contains(target)) {
      this.customers = [];
    }
  }

  allCustomers: Customer[] = [
    {
      id: 1,
      name: 'Amit Kumar',
      email: 'amit.kumar@gmail.com',
      phone: '9876543210',
      gender: 'Male',
      dob: '15-08-1990'
    },
    {
      id: 2,
      name: 'Anil Sharma',
      email: 'anil.sharma@gmail.com',
      phone: '9123456789',
      gender: 'Male',
      dob: '22-01-1988'
    },
    {
      id: 3,
      name: 'Anjali Singh',
      email: 'anjali.singh@gmail.com',
      phone: '9988776655',
      gender: 'Female',
      dob: '10-11-1992'
    },
    {
      id: 4,
      name: 'Rahul Verma',
      email: 'rahul.verma@gmail.com',
      phone: '9012345678',
      gender: 'Male',
      dob: '05-03-1987'
    },
    {
      id: 5,
      name: 'Ravi Patel',
      email: 'ravi.patel@gmail.com',
      phone: '9090909090',
      gender: 'Male',
      dob: '18-07-1991'
    },
    {
      id: 6,
      name: 'Sunita Rao',
      email: 'sunita.rao@gmail.com',
      phone: '9345678123',
      gender: 'Female',
      dob: '30-09-1989'
    },
    {
      id: 7,
      name: 'Suresh Reddy',
      email: 'suresh.reddy@gmail.com',
      phone: '9871203456',
      gender: 'Male',
      dob: '12-12-1985'
    },
    {
      id: 8,
      name: 'Priya Mehta',
      email: 'priya.mehta@gmail.com',
      phone: '9567891234',
      gender: 'Female',
      dob: '25-04-1993'
    },
    {
      id: 9,
      name: 'Pooja Nair',
      email: 'pooja.nair@gmail.com',
      phone: '9786543211',
      gender: 'Female',
      dob: '08-06-1990'
    },
    {
      id: 10,
      name: 'Alisha Khan',
      email: 'alisha.khan@gmail.com',
      phone: '9998887776',
      gender: 'Female',
      dob: '14-02-1994'
    }
  ];


  selectedMedicine = {
    name: 'Paracetamol 500mg',
    batch: 'BATCH123',
    expiry: '15/08/2026',
    mrp: 150,
    stock: 120
  };

  quantity = 1;
  cart: any[] = [];

  subtotal = 0;
  tax = 0;
  grandTotal = 0;

  addToCart() {
    const total = this.selectedMedicine.mrp * this.quantity;
    this.cart.push({
      name: this.selectedMedicine.name,
      batch: this.selectedMedicine.batch,
      expiry: this.selectedMedicine.expiry,
      qty: this.quantity,
      price: this.selectedMedicine.mrp,
      discount: 0,
      total
    });
    this.calculateTotals();
  }

  removeItem(index: number) {
    this.cart.splice(index, 1);
    this.calculateTotals();
  }

  calculateTotals() {
    this.subtotal = this.cart.reduce((sum, i) => sum + i.total, 0);
    this.tax = this.subtotal * 0.05;
    this.grandTotal = this.subtotal + this.tax;
  }

  processSale() {
    alert('Sale processed successfully');
  }

  customers: Customer[] = [];
  searchName: string = '';
  searchPhone: string = '';
  selectedCustomer: Customer | null = null;

  selectCustomer(customer: any) {
    this.selectedCustomer = customer;
    this.searchName = customer.name;
    this.searchPhone = customer.phone;
    this.customers = [];
    this.activeInput = null;
  }

  onNameChange(event: any) {
    this.activeInput = 'name';
    this.searchName = event.target.value.trim();
    this.searchPhone = '';
    this.handleSearch();
  }

  onPhoneChange(event: any) {
    this.activeInput = 'phone';
    this.searchPhone = event.target.value.trim();
    this.searchName = '';
    this.handleSearch();
  }

  handleSearch() {
    const query =
      this.searchName.length >= 3 || this.searchPhone.length >= 3;

    if (!query) {
      this.customers = [];
      return;
    }

    this.searchCustomersApi();
  }

  searchCustomersApi() {
    const payload = {
      name: this.searchName || null,
      phone: this.searchPhone || null
    };

    this.customerService.searchCustomers(payload).subscribe({
      next: (res: any) => {
        this.customers = res.data || [];
      },
      error: () => {
        //this.customers = [];
        this.customers = this.allCustomers.filter(c =>
          c.phone.includes(this.searchPhone))
      }
    });
  }

}
