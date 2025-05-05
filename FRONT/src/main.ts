import { bootstrapApplication } from '@angular/platform-browser';
import { provideRouter } from '@angular/router';
import {
  provideHttpClient,
  withFetch,
  withInterceptors,
  withXsrfConfiguration
} from '@angular/common/http';

import { AppComponent } from './app/app.component';
import { routes } from './app/app.routes';
import { authInterceptor } from './app/auth.interceptor';

bootstrapApplication(AppComponent, {
  providers: [
    provideRouter(routes),
    provideHttpClient(withFetch()),

    provideHttpClient(
      // 1) register your authInterceptor for cookies
      withInterceptors([ authInterceptor ]),
      // 2) enable Angular's XSRF support to read the cookie and add the header
      withXsrfConfiguration({
        cookieName: 'XSRF-TOKEN', // Spring’s default cookie name
        headerName: 'X-XSRF-TOKEN' // Spring’s default header name
      })
    ),
  ]
})
.catch(err => console.error(err));
