import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from '../../env/env';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LocationService {
  private baseUrl = environment.apiBaseUrl;

  constructor(private http: HttpClient) {}

  getLocationNames(): Observable<string[]> {
    return this.http.get<string[]>(`${this.baseUrl}/api/locations`);
  }

  addLocation(location: { lat: number, lng: number, name: string }): Observable<any> {
    const token = localStorage.getItem('token');
    const headers = new HttpHeaders({
      'Authorization': token ? token : '',
      'Content-Type': 'application/json'
    });

    return this.http.post(`${this.baseUrl}/api/admin/locations`, location, { headers });
  }
}
