import { Component } from '@angular/core';
import { ApiService } from '../../services/api.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent {

  userDetails = {
    name: '',
    email: '',
    password: ''
  };

  message: string = '';

  constructor(
    private apiService: ApiService,
    private router: Router
  ) {}

  registerUser(): void {
    this.apiService.registerUser(this.userDetails).subscribe(
      (response) => {
        console.log('User registered successfully:', response);
        this.message = 'Registration successful! Redirecting to login...';
        // Redirect to the login page after a short delay
        setTimeout(() => {
          this.router.navigate(['/login']);
        }, 2000);
      },
      (error) => {
        console.error('Error during registration:', error);
        this.message = 'Registration failed. Please try again.';
      }
    );
  }
}