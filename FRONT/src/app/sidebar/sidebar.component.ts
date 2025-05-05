import { Component, OnInit } from '@angular/core';
import { RouterLink } from '@angular/router';
import { AuthService } from '../auth.service'; // Adjust path as needed
import { CommonModule } from '@angular/common';
@Component({
  selector: 'app-sidebar',
  standalone: true,
  imports: [RouterLink,CommonModule],
  templateUrl: './sidebar.component.html',
  styleUrl: './sidebar.component.css'
})
export class SidebarComponent implements OnInit {
  nom: string | null = null;
  prenom: string | null = null;

  constructor(public authService: AuthService) {}

  ngOnInit(): void {
    this.authService.nom$.subscribe(nom => {
      this.nom = nom;
      console.log('Nom updated:', nom);
    });
    this.authService.prenom$.subscribe(prenom => {
      this.prenom = prenom;
      console.log('Prenom updated:', prenom);
    });
    // Always fetch profile to ensure fresh data
    this.authService.fetchProfile().subscribe({
      next: () => console.log('Profile fetch successful'),
      error: (err) => console.error('Profile fetch failed:', err)
    });
  }
}