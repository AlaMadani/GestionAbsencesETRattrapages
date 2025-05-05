import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { BrowserModule } from '@angular/platform-browser';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-sidebar-teacher',
  imports: [RouterLink],
 standalone: true,
  templateUrl: './sidebar-teacher.component.html',
  styleUrl: './sidebar-teacher.component.css'
})
export class SidebarTeacherComponent {
 // Variable pour contrôler l'état ouvert/fermé de la sidebar
 isOpen: boolean = false;

 // Méthode pour basculer l'état
 toggleSidebar(): void {
   this.isOpen = !this.isOpen;
   console.log('Sidebar state:', this.isOpen ? 'open' : 'closed');
 }
}
