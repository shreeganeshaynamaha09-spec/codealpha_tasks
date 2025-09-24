import { Component } from '@angular/core';
import { ApiService } from '../../services/api.service';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-landing',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './landing.component.html',
  styleUrl: './landing.component.css'
})
export class LandingComponent {

  searchRequest = {
    checkIn: '',
    checkOut: ''
  };
  availabilityResults: any[] = [];

  constructor(private apiService: ApiService, private router: Router) {}

  searchAvailability(): void {
    this.apiService.searchAvailability(
      this.searchRequest.checkIn,
      this.searchRequest.checkOut
    ).subscribe(
      (results) => {
        this.availabilityResults = results;
      },
      (error) => {
        console.error('Error searching for rooms:', error);
        this.availabilityResults = [];
      }
    );
  }

  // This method navigates to the login page
  redirectToLogin(): void {
    this.router.navigate(['/login']);
  }

  // This method navigates to the register page
  redirectToRegister(): void {
    this.router.navigate(['/register']);
  }
}

