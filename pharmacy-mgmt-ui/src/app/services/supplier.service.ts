import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { SupplierDto } from '../models/supplier-dto';

@Injectable({
  providedIn: 'root'
})
export class SupplierService {

  private baseUrl = environment.apiBaseUrl;

  constructor(private http: HttpClient) { }

  /** Create a new supplier */
  createSupplier(dto: SupplierDto): Observable<SupplierDto> {
    return this.http.post<SupplierDto>(`${this.baseUrl}suppliers`, dto);
  }

  /** Get supplier by ID */
  getSupplierById(id: number): Observable<SupplierDto> {
    return this.http.get<SupplierDto>(`${this.baseUrl}suppliers/${id}`);
  }

  /** Get all suppliers */
  getAllSuppliers(): Observable<SupplierDto[]> {
    return this.http.get<SupplierDto[]>(`${this.baseUrl}suppliers`);
  }

  /** Update supplier */
  updateSupplier(id: number, dto: SupplierDto): Observable<SupplierDto> {
    return this.http.patch<SupplierDto>(`${this.baseUrl}suppliers/${id}`, dto);
  }

  /** Delete supplier */
  deleteSupplier(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}suppliers/${id}`);
  }
}
