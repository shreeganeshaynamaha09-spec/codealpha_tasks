import { Injectable, PLATFORM_ID, Inject } from '@angular/core';
import { isPlatformBrowser } from '@angular/common';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private currentUser: any = null;
  private isBrowser: boolean;

  constructor(
    private router: Router,
    @Inject(PLATFORM_ID) private platformId: object
  ) {
    this.isBrowser = isPlatformBrowser(this.platformId);

    // Only access sessionStorage if in a browser
    if (this.isBrowser) {
      const user = sessionStorage.getItem('currentUser');
      if (user) {
        this.currentUser = JSON.parse(user);
      }
    }
  }

  login(user: any): void {
    this.currentUser = user;
    // Only access sessionStorage if in a browser
    if (this.isBrowser) {
      sessionStorage.setItem('currentUser', JSON.stringify(user));
    }
  }

  logout(): void {
    this.currentUser = null;
    // Only access sessionStorage if in a browser
    if (this.isBrowser) {
      sessionStorage.removeItem('currentUser');
    }
    this.router.navigate(['/login']);
  }

  isLoggedIn(): boolean {
    return this.currentUser !== null;
  }

  getCurrentUser(): any {
    return this.currentUser;
  }
  
  getUserId(): number | null {
    return this.currentUser ? this.currentUser.id : null;
  }

  getUserRole(): string | null {
    return this.currentUser ? this.currentUser.role : null;
  }
}

