import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../auth.service';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-res-slider',
  templateUrl: './res-slider.component.html',
  styleUrls: ['./res-slider.component.css'],
  standalone: true,
  imports: [RouterLink]
})
export class ResSliderComponent {
  constructor(private authService: AuthService, private router: Router) {}

  logout() {
    this.authService.logout().subscribe({
      next: () => {
        this.router.navigate(['/home']);
      },
      error: (err) => {
        console.error('Logout failed:', err);
        // Optionally handle error, but still navigate
        this.router.navigate(['/home']);
      }
    });
  }
}