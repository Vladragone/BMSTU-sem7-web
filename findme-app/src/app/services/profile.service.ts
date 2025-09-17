import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Profile } from '../model/profile';

@Injectable({
  providedIn: 'root'
})
export class ProfileService {

  private apiUrl = 'http://localhost:8080/api/profile';

  constructor(private http: HttpClient) {}

  getProfile(): Observable<Profile> {
    const token = localStorage.getItem('token');
    const headers = new HttpHeaders({
      'Authorization': token ?? ''
    });
    return this.http.get<Profile>(this.apiUrl, { headers });
  }
}
