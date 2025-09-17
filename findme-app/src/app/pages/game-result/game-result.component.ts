import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { DecimalPipe } from '@angular/common';
import { GameResultService } from '../../services/game-result.service';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
declare const ymaps: any;

@Component({
  selector: 'app-game-result',
  templateUrl: './game-result.component.html',
  styleUrls: ['./game-result.component.scss'],
  standalone: true,
  imports: [CommonModule, DecimalPipe, HttpClientModule, FormsModule]
})
export class GameResultComponent implements OnInit {
  trueLat!: number;
  trueLng!: number;
  userGuessLat!: number;
  userGuessLng!: number;
  score: number = 0;
  distance: number = 0;
  guessMap: any;
  originPlacemark: any;
  guessPlacemark: any;
  line: any;
  userId!: number;
  selectedRating: number = 0;
  selectedIssue: string = '';
  comment: string = '';
  issues: { id: number, name: string }[] = [];
  showIssueError: boolean = false;

  constructor(private route: ActivatedRoute, private router: Router, private gameResultService: GameResultService, private snackBar: MatSnackBar) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.trueLat = +params['trueLat'];
      this.trueLng = +params['trueLng'];
      this.userGuessLat = +params['userGuessLat'];
      this.userGuessLng = +params['userGuessLng'];
      const token = localStorage.getItem('token');
      if (token) {
        this.userId = this.getUserIdFromToken(token);
      }
      if (this.trueLat && this.trueLng && this.userGuessLat && this.userGuessLng) {
        this.calculateScore();
        this.loadMap();
      }
    });

    this.gameResultService.getGameErrors().subscribe((data: { id: number, name: string }[]) => {
      this.issues = data;
    });
  }

  private getUserIdFromToken(token: string): number {
    const payload = this.decodeJWT(token);
    return payload.user_id;
  }

  private decodeJWT(token: string): any {
    const parts = token.split('.');
    if (parts.length !== 3) {
      throw new Error('Invalid token format');
    }
    const payload = parts[1];
    const decodedPayload = atob(payload);
    return JSON.parse(decodedPayload);
  }

  private loadMap(): void {
    const waitForYMaps = () => {
      if (typeof ymaps === 'undefined') {
        setTimeout(waitForYMaps, 100);
      } else {
        ymaps.ready(() => {
          this.guessMap = new ymaps.Map('guess-map', {
            center: [this.trueLat, this.trueLng],
            zoom: 12,
            controls: ['zoomControl']
          });

          this.originPlacemark = new ymaps.Placemark([this.trueLat, this.trueLng], {
            balloonContent: 'Исходная точка'
          }, {
            iconColor: 'red'
          });

          this.guessPlacemark = new ymaps.Placemark([this.userGuessLat, this.userGuessLng], {
            balloonContent: 'Точка пользователя'
          }, {
            iconColor: 'green'
          });

          this.line = new ymaps.GeoObject({
            geometry: {
              type: 'LineString',
              coordinates: [
                [this.trueLat, this.trueLng],
                [this.userGuessLat, this.userGuessLng]
              ]
            },
            properties: {
              balloonContent: 'Линия между точками'
            }
          });

          this.guessMap.geoObjects.add(this.originPlacemark);
          this.guessMap.geoObjects.add(this.guessPlacemark);
          this.guessMap.geoObjects.add(this.line);

          this.guessMap.setBounds(this.guessMap.geoObjects.getBounds(), { checkZoomRange: true });
        });
      }
    };

    waitForYMaps();
  }

  private calculateScore(): void {
    const R = 6371;
    const dLat = this.degreesToRadians(this.userGuessLat - this.trueLat);
    const dLng = this.degreesToRadians(this.userGuessLng - this.trueLng);
    const a =
      Math.sin(dLat / 2) * Math.sin(dLat / 2) +
      Math.cos(this.degreesToRadians(this.trueLat)) * Math.cos(this.degreesToRadians(this.userGuessLat)) *
      Math.sin(dLng / 2) * Math.sin(dLng / 2);
    const c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    const distanceInKm = R * c;

    this.distance = distanceInKm;
    this.score = Math.max(0, Math.round(5000 - (distanceInKm * 1000)));
    this.gameResultService.updateScore(this.score)?.subscribe();
    this.gameResultService.saveGame(this.userId, this.userGuessLat, this.userGuessLng, this.trueLat, this.trueLng, this.score)?.subscribe();
  }

  private degreesToRadians(degrees: number): number {
    return degrees * (Math.PI / 180);
  }

  setRating(rating: number): void {
    this.selectedRating = rating;
    if (rating > 2) {
      this.selectedIssue = '';
    }
  }

  submitFeedback(): void {
    this.showIssueError = false;

    if (this.selectedRating === undefined || this.selectedRating === null) {
      this.snackBar.open('Пожалуйста, выберите оценку', 'Закрыть', {
        duration: 3000,
        panelClass: ['warn-snackbar']
      });
      return;
    }

    if (this.selectedRating <= 2 && !this.selectedIssue) {
      this.showIssueError = true;
      this.snackBar.open('Пожалуйста, укажите проблему', 'Закрыть', {
        duration: 3000,
        panelClass: ['warn-snackbar']
      });
      return;
    }
    console.log('Выбранная проблема:', this.selectedIssue);

    const feedback = {
      userId: this.userId,
      rating: this.selectedRating,
      problem: this.selectedRating <= 2 ? this.selectedIssue : null,
      description: this.comment.length === 0 ? null : this.comment,
    };


    console.log('Отправка отзыва:', feedback);

    this.gameResultService.sendFeedback(feedback).subscribe({
      next: () => {
        this.snackBar.open('Спасибо за ваш отзыв!', 'Закрыть', {
          duration: 2000,
          panelClass: ['snackbar-success']
        });
        this.onProfile();
      },
      error: (err) => {
        this.snackBar.open('Ошибка при отправке отзыва', 'Закрыть', {
          duration: 3000,
          panelClass: ['snackbar-error']
        });
        console.error('Ошибка отправки отзыва:', err);
      }
    });

    this.selectedRating = 0;
    this.selectedIssue = '';
    this.comment = '';
  }



  onPlayAgain(): void {
    this.router.navigate(['/game-settings']);
  }

  onProfile(): void {
    const token = localStorage.getItem('token');
    if (token) {
      this.router.navigate(['/profile']);
    } else {
      this.router.navigate(['/login']);
    }
  }
}
