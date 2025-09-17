import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-start',
  templateUrl: './start.component.html',
  styleUrls: ['./start.component.scss'],
})
export class StartComponent {
  constructor(private router: Router) {}

  onLogin(): void {
    this.router.navigate(['/login']);
  }

  onRegister(): void {
    this.router.navigate(['/register']);
  }

  onStartGame(): void {
    this.router.navigate(['/game-settings']);
  }

  onAbout(): void {
    this.router.navigate(['/faq']);
  }
}
