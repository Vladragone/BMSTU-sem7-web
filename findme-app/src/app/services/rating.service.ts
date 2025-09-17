import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { RatingResponse } from '../model/rating-response';
import { environment } from '../../env/env';

@Injectable({
  providedIn: 'root'
})
export class RatingService {
  private apiUrl = `${environment.apiBaseUrl}/api/rating/top`;

  constructor(private http: HttpClient) {}

  getRating(sortBy: 'points' | 'games'): Observable<RatingResponse> {
    const token = localStorage.getItem('token') || '';
    const headers = new HttpHeaders().set('Authorization', token);
    const params = new HttpParams().set('sortBy', sortBy);
    return this.http.get<RatingResponse>(this.apiUrl, { headers, params });
  }
}
