import { Component, OnInit } from '@angular/core';
import { RouterLink } from '@angular/router';
import { AuthService } from '../auth.service'; // Adjust path as needed
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-sidebar-teacher',
  standalone: true,
  imports: [RouterLink,CommonModule],
  templateUrl: './sidebar-teacher.component.html',
  styleUrl: './sidebar-teacher.component.css'
})
export class SidebarTeacherComponent implements OnInit {
  isOpen: boolean = false;
  nom: string | null = null;
  prenom: string | null = null;

  constructor(private authService: AuthService) {}

  ngOnInit(): void {
    // Subscribe to nom and prenom updates
    this.authService.nom$.subscribe(nom => {
      this.nom = nom;
    });
    this.authService.prenom$.subscribe(prenom => {
      this.prenom = prenom;
    });

    // Fetch profile if not already loaded
    if (!this.authService.nom || !this.authService.prenom) {
      this.authService.fetchProfile().subscribe();
    }
  }

  toggleSidebar(): void {
    this.isOpen = !this.isOpen;
    console.log('Sidebar state:', this.isOpen ? 'open' : 'closed');
  }
}