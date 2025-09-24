import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ApiService {

  private baseUrl = 'http://localhost:8080/api';

  constructor(private http: HttpClient) { }

  // --- Auth Endpoints ---
  registerUser(userDetails: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/auth/register`, userDetails);
  }

  loginUser(credentials: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/auth/login`, credentials);
  }

  // --- User-Facing Reservation Endpoints ---
  searchAvailability(checkIn: string, checkOut: string): Observable<any> {
    return this.http.get(`${this.baseUrl}/reservations/search-availability?checkIn=${checkIn}&checkOut=${checkOut}`);
  }

  bookCategory(bookingDetails: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/reservations/book`, bookingDetails);
  }

  getMyBookings(userId: number): Observable<any> {
    return this.http.get(`${this.baseUrl}/reservations/my-bookings/${userId}`);
  }

  // --- Admin Endpoints ---
  getPendingBookings(): Observable<any> {
    return this.http.get(`${this.baseUrl}/admin/pending-bookings`);
  }

  getAllBookings(): Observable<any> {
    return this.http.get(`${this.baseUrl}/admin/bookings/all`);
  }

  confirmBooking(bookingId: number, roomId: number): Observable<any> {
    return this.http.put(`${this.baseUrl}/admin/bookings/${bookingId}/confirm/${roomId}`, {});
  }

  rejectBooking(bookingId: number): Observable<any> {
    return this.http.put(`${this.baseUrl}/admin/bookings/${bookingId}/reject`, {});
  }
  
  getAllRooms(): Observable<any> {
    return this.http.get(`${this.baseUrl}/admin/rooms`);
  }

  addRoom(roomDetails: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/admin/rooms`, roomDetails);
  }

  updateRoom(roomId: number, roomDetails: any): Observable<any> {
    return this.http.put(`${this.baseUrl}/admin/rooms/${roomId}`, roomDetails);
  }

  deleteRoom(roomId: number): Observable<any> {
    return this.http.delete(`${this.baseUrl}/admin/rooms/${roomId}`);
  }
}

