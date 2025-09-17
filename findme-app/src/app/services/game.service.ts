import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { environment } from '../../env/env';
import { Observable } from 'rxjs';

export interface GameLocation {
  id: number;
  name: string;
  lat: number;
  lng: number;
}

@Injectable({
  providedIn: 'root'
})
export class GameService {
  private baseUrl = environment.apiBaseUrl;

  constructor(private http: HttpClient) {}

  getRandomLocation(name: string): Observable<GameLocation> {
    const params = new HttpParams().set('name', name);
    return this.http.get<GameLocation>(`${this.baseUrl}/api/location/random`, { params });
  }
}
