import { Component, OnInit } from '@angular/core';
import { AdminService } from '../admin.service';
import { EtudiantDTO } from '../etudiant.model';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-etudiants-list',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './etudiants-list.component.html',
  styleUrls: ['./etudiants-list.component.css']
})
export class EtudiantsListComponent implements OnInit {
  etudiants: EtudiantDTO[] = [];
  filteredEtudiants: EtudiantDTO[] = [];
  searchName: string = '';
  loading = true;
  error: string | null = null;

  constructor(private adminService: AdminService) {}

  ngOnInit() {
    this.adminService.getAllEtudiants().subscribe({
      next: (list) => {
        this.etudiants = list;
        this.filteredEtudiants = list; // Initialize filteredEtudiants
        this.loading = false;
      },
      error: (err) => {
        this.error = 'Impossible de charger la liste des étudiants.';
        console.error('Error loading étudiants:', err);
        this.loading = false;
      }
    });
  }

  applySearch() {
    const searchLower = this.searchName.toLowerCase().trim();
    this.filteredEtudiants = this.etudiants.filter((etudiant) => {
      const fullName = `${etudiant.prenom} ${etudiant.nom}`.toLowerCase();
      return fullName.includes(searchLower);
    });
  }
}