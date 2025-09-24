import { Component, OnInit } from '@angular/core';
import { ApiService } from '../../services/api.service';
import { CommonModule, CurrencyPipe } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../services/AuthService';

@Component({
  selector: 'app-user-dashboard',
  standalone: true,
  imports: [CommonModule, FormsModule, CurrencyPipe],
  templateUrl: './user-dashboard.component.html',
  styleUrl: './user-dashboard.component.css'
})
export class UserDashboardComponent implements OnInit {
  
  activeTab: string = 'view';
  myBookings: any[] = [];
  searchRequest = { checkIn: '', checkOut: '' };
  availabilityResults: any[] = [];
  
  // THE FIX IS HERE: Declare the userName property
  userName: string = 'Guest'; // Set a default value

  constructor(private apiService: ApiService, private authService: AuthService) { }

  ngOnInit(): void {
    // Fetch the current user's name from the AuthService
    const currentUser = this.authService.getCurrentUser();
    if (currentUser) {
      this.userName = currentUser.name;
    }
    this.loadMyBookings();
  }

  switchTab(tab: string): void {
    this.activeTab = tab;
  }

  loadMyBookings(): void {
    const userId = this.authService.getUserId();
    if (userId) {
      this.apiService.getMyBookings(userId).subscribe(data => {
        this.myBookings = data;
      });
    }
  }

  searchAvailability(): void {
    if (!this.searchRequest.checkIn || !this.searchRequest.checkOut) {
      alert('Please select both check-in and check-out dates.');
      return;
    }
    this.apiService.searchAvailability(
      this.searchRequest.checkIn,
      this.searchRequest.checkOut
    ).subscribe(results => {
      this.availabilityResults = results;
    });
  }

  bookCategory(category: string): void {
    const userId = this.authService.getUserId();
    if (!userId) {
      alert('You must be logged in to book.');
      return;
    }
    
    const bookingDetails = {
      userId: userId,
      category: category,
      checkInDate: this.searchRequest.checkIn,
      checkOutDate: this.searchRequest.checkOut
    };

    this.apiService.bookCategory(bookingDetails).subscribe({
      next: response => {
        alert('Booking request sent successfully! Your request is PENDING admin approval.');
        this.availabilityResults = [];
        this.switchTab('view');
        this.loadMyBookings();
      },
      error: error => {
        alert('Booking request failed. The selected category may no longer be available.');
        console.error(error);
      }
    });
  }
}

