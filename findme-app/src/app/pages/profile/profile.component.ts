import { Component, OnInit } from '@angular/core';
import { ProfileService } from '../../services/profile.service';
import { Profile } from '../../model/profile';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss'],
  imports: [CommonModule]
})
export class ProfileComponent implements OnInit {

  profile: Profile | null = null;
  error: string | null = null;

  constructor(private profileService: ProfileService, private router: Router) {}
  
  ngOnInit(): void {
    this.profileService.getProfile().subscribe({
      next: (data: Profile) => this.profile = data,
      error: (err) => {
        console.error(err);
        this.error = 'Ошибка загрузки профиля';
      }
    });
  }

  startGame(): void {
    this.router.navigate(['/game-settings']);
  }

  goToRating(): void {
    this.router.navigate(['/rating']);
  }

  logout(): void {
    localStorage.removeItem('token');
    this.router.navigate(['/']);
  }

  goToFaq(): void {
    this.router.navigate(['/faq']);
  }
}
