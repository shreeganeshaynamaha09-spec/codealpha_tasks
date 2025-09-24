import { Component, OnInit } from '@angular/core';
import { ApiService } from '../../services/api.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-admin-dashboard',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './admin-dashboard.component.html',
  styleUrl: './admin-dashboard.component.css'
})
export class AdminDashboardComponent implements OnInit {

  activeTab: string = 'bookings';
  pendingBookings: any[] = [];
  allRooms: any[] = [];
  allBookings: any[] = [];
  
  newRoom: any = {
    roomNumber: '',
    category: 'STANDARD',
    pricePerNight: null,
    status: 'AVAILABLE'
  };

  constructor(private apiService: ApiService) {}

  ngOnInit(): void {
    this.loadInitialData();
  }

  loadInitialData(): void {
    this.apiService.getAllRooms().subscribe({
      next: (rooms) => {
        this.allRooms = rooms;
        // Now that rooms are loaded, load booking data
        this.loadPendingBookings();
        this.loadAllBookings();
      },
      error: (error) => console.error('Error fetching rooms:', error)
    });
  }
  
  switchTab(tab: string): void {
    this.activeTab = tab;
  }

  loadPendingBookings(): void {
    this.apiService.getPendingBookings().subscribe({
      next: (data) => {
        this.pendingBookings = data.map((booking: any) => ({ ...booking, selectedRoomId: null }));
      },
      error: (error) => console.error('Error fetching pending bookings:', error)
    });
  }

  loadAllBookings(): void {
    this.apiService.getAllBookings().subscribe({
      next: (data) => this.allBookings = data,
      error: (error) => console.error('Error fetching all bookings:', error)
    });
  }

  // --- Booking Management ---

  getAvailableRoomsForBooking(booking: any): any[] {
    return this.allRooms.filter(room => 
      room.category === booking.requestedCategory && room.status === 'AVAILABLE'
    );
  }

  confirmBooking(bookingId: number, roomId: number | null): void {
    if (!roomId) {
      alert('Please select a room to assign.');
      return;
    }
    this.apiService.confirmBooking(bookingId, roomId).subscribe({
      next: () => {
        alert('Booking confirmed and room assigned successfully!');
        this.loadInitialData(); // Reload all data
      },
      error: (error) => {
        alert(`Failed to confirm booking: ${error.error.message || 'Server error'}`);
        console.error('Error confirming booking:', error);
      }
    });
  }

  rejectBooking(bookingId: number): void {
    if (confirm('Are you sure you want to reject this booking?')) {
      this.apiService.rejectBooking(bookingId).subscribe({
        next: () => {
          alert('Booking rejected successfully.');
          this.loadInitialData();
        },
        error: (error) => {
          alert('Failed to reject booking.');
          console.error('Error rejecting booking:', error);
        }
      });
    }
  }

  // --- Room Management ---

  addRoom(): void {
    this.apiService.addRoom(this.newRoom).subscribe({
      next: () => {
        alert('Room added successfully!');
        this.loadInitialData();
        this.newRoom = { roomNumber: '', category: 'STANDARD', pricePerNight: null, status: 'AVAILABLE' };
      },
      error: (error) => {
        alert('Failed to add room.');
        console.error('Error adding room:', error)
      }
    });
  }

  editRoom(room: any): void {
    const newPrice = prompt('Enter new price per night:', room.pricePerNight);
    const newStatus = prompt('Enter new status (AVAILABLE, MAINTENANCE):', room.status);

    if (newPrice && newStatus) {
      const updatedRoom = { ...room, pricePerNight: newPrice, status: newStatus };
      this.apiService.updateRoom(room.id, updatedRoom).subscribe({
        next: () => {
          alert('Room updated successfully!');
          this.loadInitialData();
        },
        error: (error) => console.error('Error updating room:', error)
      });
    }
  }

  deleteRoom(roomId: number): void {
    if (confirm('Are you sure you want to delete this room?')) {
      this.apiService.deleteRoom(roomId).subscribe({
        next: () => {
          alert('Room deleted successfully!');
          this.loadInitialData();
        },
        error: (error) => {
          alert('Failed to delete room.');
          console.error('Error deleting room:', error);
        }
      });
    }
  }
}

