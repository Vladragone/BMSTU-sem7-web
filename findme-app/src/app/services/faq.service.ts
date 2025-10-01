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
  private apiUrl = `${environment.apiBaseUrl}/api/v1/faqs`;

  constructor(private http: HttpClient) {}

  getFaqs(): Observable<Faq[]> {
    return this.http.get<Faq[]>(this.apiUrl);
  }

  addFaq(faq: Omit<Faq, 'id'>): Observable<Faq> {
    return this.http.post<Faq>(this.apiUrl, faq);
  }

  getFaqById(id: number): Observable<Faq> {
    return this.http.get<Faq>(`${this.apiUrl}/${id}`);
  }

  updateFaq(id: number, updates: Partial<Faq>): Observable<Faq> {
    return this.http.patch<Faq>(`${this.apiUrl}/${id}`, updates);
  }

  deleteFaq(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
