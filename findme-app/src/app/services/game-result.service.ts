import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../env/env';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class GameResultService {
  private apiUrl = `${environment.apiBaseUrl}/api/profile/score`;
  private saveGameUrl = `${environment.apiBaseUrl}/api/save-game`;
  private gameErrorsUrl = `${environment.apiBaseUrl}/api/game-errors`;
  private feedbackUrl = `${environment.apiBaseUrl}/api/feedback`;

  constructor(private http: HttpClient) {}

  updateScore(score: number): Observable<any> {
    const token = localStorage.getItem('token');
    if (!token) {
      return throwError(() => new Error('Token is missing'));
    }

    const headers = new HttpHeaders().set('Authorization', token);
    const body = { score };

    return this.http.put(this.apiUrl, body, { headers }).pipe(
      catchError((error) => {
        console.error('Error updating score', error);
        return throwError(() => error);
      })
    );
  }

  saveGame(userId: number, userLat: number, userLng: number, correctLat: number, correctLng: number, earnedScore: number): Observable<any> {
    const token = localStorage.getItem('token');
    if (!token) {
      return throwError(() => new Error('Token is missing'));
    }

    const headers = new HttpHeaders().set('Authorization', token);
    const body = { userId, userLat, userLng, correctLat, correctLng, earnedScore };

    return this.http.post(this.saveGameUrl, body, { headers }).pipe(
      catchError((error) => {
        console.error('Error saving game', error);
        return throwError(() => error);
      })
    );
  }

  getGameErrors(): Observable<{ id: number; name: string }[]> {
    return this.http.get<{ id: number; name: string }[]>(this.gameErrorsUrl).pipe(
      catchError((error) => {
        console.error('Error fetching game errors', error);
        return throwError(() => error);
      })
    );
  }

  sendFeedback(feedback: any): Observable<any> {
    const token = localStorage.getItem('token');
    if (!token) {
      return throwError(() => new Error('Token is missing'));
    }

    const headers = new HttpHeaders().set('Authorization', token);
    return this.http.post(this.feedbackUrl, feedback, { headers }).pipe(
      catchError((error) => {
        console.error('Error sending feedback', error);
        return throwError(() => error);
      })
    );
  }
}
