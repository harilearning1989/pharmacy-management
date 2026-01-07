import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {SalePayload} from "../models/sale-payload";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class SaleService {

  private API_URL = 'http://localhost:8080/api/sales';

  constructor(private http: HttpClient) {}

  saveSale(payload: SalePayload): Observable<any> {
    return this.http.post(this.API_URL, payload);
  }
}
