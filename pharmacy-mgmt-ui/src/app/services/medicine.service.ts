import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
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

  loadTotalMedicines(): Observable<Medicine[]> {
    return this.http.get<Medicine[]>(`${this.baseUrl}medicines`);
  }

  loadAvailableMedicines(): Observable<Medicine[]> {
    return this.http.get<Medicine[]>(`${this.baseUrl}medicines/available`);
  }

  loadExpiredMedicines(): Observable<Medicine[]> {
    return this.http.get<Medicine[]>(`${this.baseUrl}medicines/expired`);
  }

  loadOutOfStockMedicines(): Observable<Medicine[]> {
    return this.http.get<Medicine[]>(`${this.baseUrl}medicines/outOfStock`);
  }

  loadMedicinesByName(medicineName: string) {
    return this.http.get<Medicine[]>(`${this.baseUrl}medicines/byMedicineName`,{ params: { medicineName } });
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
    return this.http.get<Medicine[]>(`${this.baseUrl}medicines/searchName`,{ params: { medicineOrBatchNumber } });
  }
}
