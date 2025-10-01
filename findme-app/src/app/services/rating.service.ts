import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { RatingResponse } from '../model/rating-response';
import { environment } from '../../env/env';

@Injectable({
  providedIn: 'root'
})
export class RatingService {
  private apiUrl = `${environment.apiBaseUrl}/api/v1/ratings`;

  constructor(private http: HttpClient) {}

  getRating(sortBy: 'points' | 'games', limit: number = 3): Observable<RatingResponse> {
    const token = localStorage.getItem('token') || '';
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    const params = new HttpParams()
      .set('sortBy', sortBy)
      .set('limit', limit);

    return this.http.get<RatingResponse>(this.apiUrl, { headers, params });
  }
}
