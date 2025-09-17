import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
  imports: [ReactiveFormsModule, CommonModule],
})
export class LoginComponent {
  loginForm: FormGroup;
  isSubmitting = false;
  errorMessage = '';

  constructor(private fb: FormBuilder, private authService: AuthService, private router: Router) {
    this.loginForm = this.fb.group({
      username: ['', [Validators.required, Validators.minLength(3)]],
      password: ['', [Validators.required, Validators.minLength(6)]]
    });
  }

  onLogin(): void {
    if (this.loginForm.invalid) {
      return;
    }
  
    this.isSubmitting = true;
    const { username, password } = this.loginForm.value;
  
    this.authService.login({ username, password })
      .subscribe({
        next: (res) => {
          console.log('Успешный вход:', res);
          localStorage.setItem('token', res.token);
          this.router.navigate(['/profile']);
        },
        error: (err) => {
          if (err.status === 401) {
            this.errorMessage = 'Неверный логин или пароль';
          } else if (err.error?.message) {
            this.errorMessage = err.error.message;
          } else {
            this.errorMessage = 'Произошла ошибка при входе. Попробуйте позже.';
          }
        }
      });
  }

  goBack(): void {
    this.router.navigate(['/']);
  }
}
