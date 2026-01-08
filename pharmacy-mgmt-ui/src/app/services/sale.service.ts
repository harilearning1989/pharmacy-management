import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {SalePayload} from "../models/sale-payload";
import {Observable} from "rxjs";
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class SaleService {

private baseUrl = environment.apiBaseUrl;

  constructor(private http: HttpClient) {}

  saveSale(payload: SalePayload): Observable<any> {
    return this.http.post(`${this.baseUrl}sales`, payload);
  }
}
