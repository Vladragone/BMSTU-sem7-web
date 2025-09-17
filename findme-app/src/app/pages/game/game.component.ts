import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { GameService, GameLocation } from '../../services/game.service';
import { Router } from '@angular/router';
import { jwtDecode } from 'jwt-decode';
import { CommonModule } from '@angular/common';
declare const ymaps: any;

@Component({
  selector: 'app-game',
  templateUrl: './game.component.html',
  styleUrls: ['./game.component.scss'],
  imports: [CommonModule]
})
export class GameComponent implements OnInit {
  trueLat!: number;
  trueLng!: number;
  userGuessLat?: number;
  userGuessLng?: number;
  guessMap: any;
  guessPlacemark: any;
  role: string = '';

  constructor(
    private route: ActivatedRoute,
    private gameService: GameService,
    private router: Router
  ) {}

  ngOnInit(): void {
    const jwtToken = localStorage.getItem('token');
    if (jwtToken) {
      try {
        const decoded: any = jwtDecode(jwtToken);
        this.role = decoded?.role || '';
      } catch (error) {
        console.error('Ошибка декодирования JWT:', error);
      }
    }
    const name = this.route.snapshot.queryParamMap.get('name');
    if (name) {
      this.gameService.getRandomLocation(name).subscribe({
        next: (location: GameLocation) => {
          this.trueLat = location.lat;
          this.trueLng = location.lng;
          this.loadPanorama(location.lat, location.lng);
          this.loadMap();
        },
        error: err => {
          console.error('Ошибка загрузки локации:', err);
        }
      });
    }
  }

  private loadPanorama(lat: number, lng: number): void {
    const waitForYMaps = () => {
      if (typeof ymaps === 'undefined') {
        setTimeout(waitForYMaps, 100);
      } else {
        ymaps.ready(() => {
          ymaps.panorama.locate([lat, lng]).done((panoramas: any[]) => {
            if (panoramas.length > 0) {
              new ymaps.panorama.Player('panorama', panoramas[0], {
                direction: [0, 0],
                span: [90, 45]
              });
            } else {
              alert('Панорама не найдена для этой локации.');
            }
          });
        });
      }
    };

    waitForYMaps();
  }

  private loadMap(): void {
    const waitForYMaps = () => {
      if (typeof ymaps === 'undefined') {
        setTimeout(waitForYMaps, 100);
      } else {
        ymaps.ready(() => {
          this.guessMap = new ymaps.Map('guess-map', {
            center: [55.75, 37.62],
            zoom: 3,
            controls: ['zoomControl']
          });

          this.guessMap.events.add('click', (e: any) => {
            const coords = e.get('coords');
            this.userGuessLat = coords[0];
            this.userGuessLng = coords[1];

            if (this.guessPlacemark) {
              this.guessPlacemark.geometry.setCoordinates(coords);
            } else {
              this.guessPlacemark = new ymaps.Placemark(coords, {}, {
                preset: 'islands#redIcon'
              });
              this.guessMap.geoObjects.add(this.guessPlacemark);
            }
          });
        });
      }
    };

    waitForYMaps();
  }

  onFind(): void {
    this.router.navigate(['/game-result'], {
      queryParams: {
        trueLat: this.trueLat,
        trueLng: this.trueLng,
        userGuessLat: this.userGuessLat,
        userGuessLng: this.userGuessLng
      }
    });
  }
  
  onStressTest(): void {
    this.router.navigate(['/stress']);
  }
}
