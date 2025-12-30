import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Customer} from "../models/customer";

@Injectable({
  providedIn: 'root'
})
export class CustomerService {

  private baseUrl = '/api/medicines'; // Replace with your API endpoint

  constructor(private http: HttpClient) {
  }

  getCustomers(): Observable<Customer[]> {
    return this.http.get<Customer[]>(this.baseUrl);
  }

  deleteCustomer(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }

  searchByName(name: string): Observable<Customer> {
    return this.http.get<Customer>(`${this.baseUrl}/search/name/${name}`);
  }

  searchByPhone(phone: string): Observable<Customer> {
    return this.http.get<Customer>(`${this.baseUrl}/search/phone/${phone}`);
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

  searchCustomers(payload: any) {
    return this.http.post('/api/customers/search', payload);
  }


}
