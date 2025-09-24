import { Component } from '@angular/core';
import { ApiService } from '../../services/api.service';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../services/AuthService';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  credentials = {
    email: '',
    password: ''
  };

  message: string = '';

  constructor(
    private apiService: ApiService,
    private router: Router,
    private authService: AuthService 
  ) {}

  // ... inside LoginComponent class

loginUser(): void {
    this.apiService.loginUser(this.credentials).subscribe(
      (response) => {
        // THE FIX IS HERE: Save user data and then redirect
        this.authService.login(response);

        // Redirect based on role
        if (response.role === 'ROLE_ADMIN') {
          this.router.navigate(['/admin-dashboard']);
        } else {
          this.router.navigate(['/user-dashboard']);
        }
      },
      (error) => { /* ... error handling ... */ }
    );
  }
}