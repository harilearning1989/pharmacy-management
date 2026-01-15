import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { Customer } from "../models/customer";
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class CustomerService {

  private baseUrl = environment.apiBaseUrl;

  constructor(private http: HttpClient) {
  }

  getCustomers(): Observable<Customer[]> {
    return this.http.get<Customer[]>(`${this.baseUrl}customers`);
  }

  // Add customer
  addCustomer(customer: Customer): Observable<Customer> {
    return this.http.post<Customer>(`${this.baseUrl}customers`, customer);
  }

  deleteCustomer(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }

  searchByName(name: string): Observable<Customer> {
    return this.http.get<Customer>(`${this.baseUrl}/search/name/${name}`);
  }

  searchCustomerByName(name: string): Observable<Customer[]> {
    return this.http.get<Customer[]>(
      `${this.baseUrl}/search?name=${name}`
    );
  }

  searchCustomerByPhone(phone: string): Observable<Customer[]> {
    return this.http.get<Customer[]>(
      `${this.baseUrl}/search?phone=${phone}`
    );
  }

  searchCustomers(name: string) {
    return this.http.get<Customer[]>(`${this.baseUrl}customers/searchName`,
      { params: { name } }
    );
  }

  searchByPhone(phone: string) {
    return this.http.get<Customer[]>(`${this.baseUrl}customers/searchPhone`,
      { params: { phone } }
    );
  }

  getCustomerCount(): Observable<number> {
    return this.http.get<number>(`${this.baseUrl}customers/count`);
  }

}
