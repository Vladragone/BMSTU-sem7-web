import { Component, OnInit } from '@angular/core';
import { RatingResponse } from '../../model/rating-response';
import { RatingService } from '../../services/rating.service';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
@Component({
  selector: 'app-rating',
  templateUrl: './rating.component.html',
  styleUrls: ['./rating.component.scss'],
  imports: [CommonModule]
})
export class RatingComponent implements OnInit {
  ratingData: RatingResponse | null = null;
  sortBy: 'points' | 'games' = 'points';

  constructor(private ratingService: RatingService, private router: Router) {}

  ngOnInit(): void {
    this.fetchRating();
  }

  fetchRating(): void {
    this.ratingService.getRating(this.sortBy).subscribe({
      next: (data) => this.ratingData = data,
      error: (err) => console.error('Ошибка при загрузке рейтинга', err)
    });
  }

  onSortChange(): void {
    this.sortBy = this.sortBy === 'points' ? 'games' : 'points';
    this.fetchRating();
  }

  startGame(): void {
    this.router.navigate(['/game-settings']);
  }

  goToProfile(): void {
    this.router.navigate(['/profile']);
  }

  logout(): void {
    localStorage.removeItem('token');
    this.router.navigate(['/']);
  }

  changeImg(): void {
    const body = document.body;
    const current = body.style.backgroundImage;
    if (current.includes('world.png')) {
      body.style.backgroundImage = "url('/assets/saxur.png')";
    } else {
      body.style.backgroundImage = "url('/assets/world.png')";
    }
  }
}
