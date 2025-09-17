import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { catchError, throwError } from 'rxjs';
import { Observable } from 'rxjs';

interface LoginRequest {
  username: string;
  password: string;
}

interface LoginResponse {
  message: string;
  token: string;
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = 'http://localhost:8080/api/auth';

  constructor(private http: HttpClient) {}
  login(data: LoginRequest): Observable<LoginResponse> {
    console.log(data);
    return this.http.post<LoginResponse>(`${this.apiUrl}/login`, data).pipe(
      catchError((error) => {
        return throwError(() => error);
      })
    );
  }
}
