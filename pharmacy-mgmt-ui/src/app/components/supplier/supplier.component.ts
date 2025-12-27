import { Component, OnInit } from '@angular/core';
import {Customer} from "../../models/customer";
import {CustomerService} from "../../services/customer.service";

@Component({
  selector: 'app-supplier',
  templateUrl: './supplier.component.html',
  styleUrls: ['./supplier.component.scss']
})
export class SupplierComponent implements OnInit {


  customers: Customer[] = [];
  searchText: string = '';

  constructor(private customerService: CustomerService) {
  }

  ngOnInit() {
    //this.loadCustomers();
    this.loadMockCustomer();
  }

  loadCustomers() {
    this.customerService.getCustomers().subscribe(res => {
      this.customers = res;
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

    return this.customers.filter(cust =>
      cust.name.toLowerCase().includes(text) ||
      cust.email.toLowerCase().includes(text) ||
      cust.phone.toLowerCase().includes(text) ||
      cust.gender.toLowerCase().includes(text)
    );
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
