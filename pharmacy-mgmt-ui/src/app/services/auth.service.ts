import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private baseUrl = environment.apiBaseUrl;

  constructor(private http: HttpClient) {}

  login(data: any) {
    return this.http.post<any>(`${this.baseUrl}user/login`, data);
  }

  register(data: any) {
    return this.http.post<any>(`${this.baseUrl}user/register`, data);
  }
}
