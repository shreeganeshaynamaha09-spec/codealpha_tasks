// import { Routes } from '@angular/router';

// export const routes: Routes = [];
import { Routes } from '@angular/router';

// Import your page components
import { LandingComponent } from './pages/landing/landing.component';
import { LoginComponent } from './pages/login/login.component';
import { RegisterComponent } from './pages/register/register.component';
import { UserDashboardComponent } from './pages/user-dashboard/user-dashboard.component';
import { AdminDashboardComponent } from './pages/admin-dashboard/admin-dashboard.component';

// Define and export the routes array
export const routes: Routes = [
    { path: '', component: LandingComponent }, // Default page
    { path: 'login', component: LoginComponent },
    { path: 'register', component: RegisterComponent },
    { path: 'user-dashboard', component: UserDashboardComponent },
    { path: 'admin-dashboard', component: AdminDashboardComponent },
];