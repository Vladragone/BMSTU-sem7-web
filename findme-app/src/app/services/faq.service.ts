import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../env/env';

export interface Faq {
  id: number;
  question: string;
  answer: string;
  userId: number;
}

@Injectable({
  providedIn: 'root'
})
export class FaqService {
  private apiUrl = `${environment.apiBaseUrl}/api/faq`;

  constructor(private http: HttpClient) {}

  getFaqs(): Observable<Faq[]> {
    return this.http.get<Faq[]>(this.apiUrl);
  }

  addFaq(faq: Omit<Faq, 'id'>): Observable<Faq> {
    return this.http.post<Faq>(this.apiUrl, faq);
  }
}
