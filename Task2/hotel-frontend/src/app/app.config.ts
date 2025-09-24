import { ApplicationConfig } from '@angular/core';
import { provideRouter } from '@angular/router';
// Import withFetch
import { provideHttpClient, withFetch } from '@angular/common/http';

import { routes } from './app.routes';

export const appConfig: ApplicationConfig = {
  providers: [
    provideRouter(routes),
    // Add withFetch() here
    provideHttpClient(withFetch()) 
  ]
};