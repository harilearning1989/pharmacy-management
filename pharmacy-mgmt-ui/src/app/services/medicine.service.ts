import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from "@angular/common/http";
import { Observable } from "rxjs";
import { Medicine } from "../models/medicine";
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class MedicineService {

  private baseUrl = environment.apiBaseUrl;

  constructor(private http: HttpClient) {
  }

  getMedicines(page: number, size: number): Observable<{ content: Medicine[], totalElements: number }> {
    let params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());
    return this.http.get<{ content: Medicine[], totalElements: number }>(this.baseUrl, { params });
  }

  deleteMedicine(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }

  uploadCSV(file: File): Observable<void> {
    const formData = new FormData();
    formData.append('file', file);
    return this.http.post<void>(`${this.baseUrl}/upload`, formData);
  }

  searchMedicineByNameOrBatchNumber(medicineOrBatchNumber: string) {
    return this.http.get<Medicine[]>(`${this.baseUrl}medicines/searchName`,
      { params: { medicineOrBatchNumber } }
    );
  }
}
