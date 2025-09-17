import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { environment } from '../../env/env';

@Injectable({
  providedIn: 'root'
})
export class RegistrationService {

  private apiUrl = `${environment.apiBaseUrl}/api/register`;

  constructor(private http: HttpClient) {}

  register(username: string, email: string, password: string): Observable<any> {
    const registrationData = { username, email, password };
    return this.http.post(this.apiUrl, registrationData, { observe: 'response' }).pipe(
      catchError((error: HttpErrorResponse) => this.handleError(error))
    );
  }

  private handleError(error: HttpErrorResponse): Observable<never> {
    let message = 'Произошла ошибка при регистрации. Пожалуйста, попробуйте снова.';
    if (error.status === 400) {
      message = 'Логин или почта уже используются';
    }
    return throwError(() => ({
      status: error.status,
      message: message
    }));
  }
}
