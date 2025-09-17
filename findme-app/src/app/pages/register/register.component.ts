import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { RegistrationService } from '../../services/registration.service';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss'],
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule, MatSnackBarModule]
})
export class RegisterComponent {
  registerForm: FormGroup;
  isSubmitting = false;
  errorMessage = '';

  constructor(
    private fb: FormBuilder,
    private registrationService: RegistrationService,
    private router: Router,
    private snackBar: MatSnackBar
  ) {
    this.registerForm = this.fb.group({
      username: ['', [Validators.required, Validators.minLength(3)]],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]]
    });
  }

  onSubmit(): void {
    if (this.registerForm.invalid || this.isSubmitting) {
      return;
    }

    this.isSubmitting = true;
    this.errorMessage = '';

    const { username, email, password } = this.registerForm.value;

    this.registrationService.register(username, email, password).subscribe({
      next: () => {
        this.isSubmitting = false;

        this.snackBar.open('Успешная регистрация. Перенаправляем на авторизацию...', '', {
          duration: 3000,
          panelClass: ['snackbar-success']
        });

        setTimeout(() => {
          this.router.navigate(['/login']);
        }, 3000);
      },
      error: (err: any) => {
        const status = err?.status;
        if (status === 200) {
          this.isSubmitting = false;

          this.snackBar.open('Успешная регистрация. Перенаправляем на авторизацию...', '', {
            duration: 3000,
            panelClass: ['snackbar-success']
          });

          setTimeout(() => {
            this.router.navigate(['/login']);
          }, 3000);
          return;
        }

        this.isSubmitting = false;
        const message = err?.message || 'Неизвестная ошибка';
        this.errorMessage = `Ошибка ${status ?? '???'}: ${message}`;

        console.error('Ошибка регистрации:', {
          status,
          message,
          fullError: err
        });
      },
    });
  }

  goBack(): void {
    this.router.navigate(['/']);
  }
}
