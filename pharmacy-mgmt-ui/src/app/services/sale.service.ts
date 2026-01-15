import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { SalePayload } from "../models/sale-payload";
import { Observable } from "rxjs";
import { environment } from 'src/environments/environment';
import { SaleHistory } from '../models/sale-history';
import { SaleHistoryDetail } from '../models/sale-history-detail';

@Injectable({
  providedIn: 'root'
})
export class SaleService {

  private baseUrl = environment.apiBaseUrl;

  constructor(private http: HttpClient) { }

  saveSale(payload: SalePayload): Observable<any> {
    return this.http.post(`${this.baseUrl}sales`, payload);
  }

  loadAllSales(): Observable<SaleHistory[]> {
    return this.http.get<SaleHistory[]>(`${this.baseUrl}sales/history/all`);
  }

  loadSaleDetailsBySaleId(saleId: number): Observable<SaleHistoryDetail> {
    return this.http.get<SaleHistoryDetail>(
      `${this.baseUrl}sales/history/saleById?saleId=${saleId}`
    );
  }

  emailInvoice(id: number) {
    return this.http.post(`/api/sales/${id}/email-invoice`, {});
  }

  getSoldMedicineCount(): Observable<number> {
    return this.http.get<number>(`${this.baseUrl}sales/history/count`);
  }
}
