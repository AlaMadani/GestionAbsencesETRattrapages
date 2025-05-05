import { Component, OnInit } from '@angular/core';
import { AdminService } from '../admin.service';
import { EnseignantDTO } from '../enseignant.model';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-enseignants-list',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './enseignants-list.component.html',
  styleUrls: ['./enseignants-list.component.css']
})
export class EnseignantsListComponent implements OnInit {
  enseignants: EnseignantDTO[] = [];
  filteredEnseignants: EnseignantDTO[] = [];
  searchName: string = '';
  loading = true;
  error: string | null = null;

  constructor(private adminService: AdminService) {}

  ngOnInit() {
    this.adminService.getAllEnseignants().subscribe({
      next: (list) => {
        this.enseignants = list;
        this.filteredEnseignants = list; // Initialize filteredEnseignants
        this.loading = false;
      },
      error: (err) => {
        this.error = 'Impossible de charger la liste des enseignants.';
        console.error('Error loading enseignants:', err);
        this.loading = false;
      }
    });
  }

  applySearch() {
    const searchLower = this.searchName.toLowerCase().trim();
    this.filteredEnseignants = this.enseignants.filter((enseignant) => {
      const fullName = `${enseignant.prenom} ${enseignant.nom}`.toLowerCase();
      return fullName.includes(searchLower);
    });
  }
}