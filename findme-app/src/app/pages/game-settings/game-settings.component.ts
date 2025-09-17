import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MatSelectModule } from '@angular/material/select';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatOptionModule } from '@angular/material/core';
import { jwtDecode } from 'jwt-decode';
import { LocationService } from '../../services/game-settings.service';
import { provideAnimations } from '@angular/platform-browser/animations';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Location } from '@angular/common';
import { Router } from '@angular/router';
import { GameService } from '../../services/game.service';
declare const ymaps: any;

@Component({
  selector: 'app-game-settings',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    MatSelectModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatOptionModule
  ],
  providers: [provideAnimations()],
  templateUrl: './game-settings.component.html',
  styleUrls: ['./game-settings.component.scss']
})
export class GameSettingsComponent implements OnInit {
  selectedLocation: string = '';
  locations: string[] = [];
  private location = inject(Location);
  role: string = '';
  lat: number | null = null;
  lng: number | null = null;
  name: string = '';
  constructor(private snackBar: MatSnackBar, private router: Router, private gameService: GameService) {}

  private locationService = inject(LocationService);

  ngOnInit(): void {
    const jwtToken = localStorage.getItem('token');

    this.locationService.getLocationNames().subscribe({
      next: data => {
        this.locations = data;
      },
      error: err => console.error('Failed to fetch locations:', err)
    });

    if (jwtToken) {
      try {
        const decoded: any = jwtDecode(jwtToken);
        this.role = decoded?.role || '';
      } catch (error) {
        console.error('Error decoding JWT:', error);
      }
    }
  }

  addLocation(): void {
    if (this.lat !== null && this.lng !== null && this.name) {
      const coords: [number, number] = [this.lat, this.lng];
      
      const waitForYMaps = () => {
        if (typeof ymaps === 'undefined') {
          setTimeout(waitForYMaps, 100);
        } else {
          ymaps.ready(() => {
            ymaps.panorama.locate(coords).done((panoramas: any[]) => {
              if (panoramas.length > 0) {
                const newLocation = {
                  lat: this.lat!,
                  lng: this.lng!,
                  name: this.name
                };
                
                this.locationService.addLocation(newLocation).subscribe({
                  next: response => {
                    console.log('Локация успешно добавлена:', response);
                    this.snackBar.open('Локация успешно добавлена', 'Закрыть', {
                      duration: 3000,
                      panelClass: ['snackbar-success']
                    });
                    
                    this.locationService.getLocationNames().subscribe({
                      next: data => this.locations = data
                    });
                    
                    this.lat = null;
                    this.lng = null;
                    this.name = '';
                  },
                  error: err => {
                    console.error('Ошибка при добавлении локации:', err);
                    this.snackBar.open('Ошибка при добавлении локации', 'Закрыть', {
                      duration: 3000,
                      panelClass: ['warn-snackbar']
                    });
                  }
                });
              } else {
                this.snackBar.open('Панорама не найдена для указанных координат', 'Закрыть', {
                  duration: 3000,
                  panelClass: ['warn-snackbar']
                });
              }
            }).fail(() => {
              this.snackBar.open('Ошибка при проверке панорамы', 'Закрыть', {
                duration: 3000,
                panelClass: ['warn-snackbar']
              });
            });
          });
        }
      };
  
      waitForYMaps();
    } else {
      this.snackBar.open('Все поля обязательны для заполнения', 'Закрыть', {
        duration: 3000, 
        panelClass: ['warn-snackbar']
      });
    }
  }

  startGame(): void {
    if (this.selectedLocation) {
      this.router.navigate(['/game'], {
        queryParams: { name: this.selectedLocation }
      });
    } else {
      this.snackBar.open('Выберите локацию для начала игры', 'Закрыть', {
        duration: 3000,
        panelClass: ['warn-snackbar']
      });
    }
  }
  
  goBack(): void {
    this.location.back();
  }
}
