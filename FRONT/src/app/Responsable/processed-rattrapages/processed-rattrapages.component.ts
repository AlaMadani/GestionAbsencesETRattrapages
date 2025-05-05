import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { AdminService } from '../admin.service';
import { ProcessedRattrapage } from '../rattrapage.model';
import { CommonModule, NgFor, NgIf, DatePipe } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-processed-rattrapages',
  standalone: true,
  imports: [CommonModule, NgIf, NgFor, DatePipe, FormsModule],
  templateUrl: './processed-rattrapages.component.html',
  styleUrls: ['./processed-rattrapages.component.css']
})
export class ProcessedRattrapagesComponent implements OnInit {
  private baseUrl = '/api';
  rattrapages: ProcessedRattrapage[] = [];
  filteredRattrapages: ProcessedRattrapage[] = [];
  loading = true;
  error: string | null = null;

  // Filter properties
  classes: string[] = [];
  specialites: string[] = [];
  groupes: string[] = [];
  filterClasse: string = '';
  filterSpecialite: string = '';
  filterGroupe: string = '';

  constructor(
    private http: HttpClient,
    private adminService: AdminService
  ) {}

  ngOnInit() {
    // Fetch classes
    this.http.get<string[]>(`${this.baseUrl}/emploi-temps/classes`, { withCredentials: true })
      .subscribe({
        next: (classes) => {
          this.classes = classes;
        },
        error: (err) => {
          this.error = 'Erreur lors du chargement des classes.';
          console.error('Error fetching classes:', err);
        }
      });

    // Fetch processed rattrapages
    this.adminService.getProcessedRattrapages().subscribe({
      next: (list) => {
        this.rattrapages = list;
        this.filteredRattrapages = list; // Initialize filteredRattrapages
        this.loading = false;
      },
      error: (err) => {
        this.error = 'Impossible de charger l’historique des rattrapages traités.';
        console.error('Error loading processed rattrapages:', err);
        this.loading = false;
      }
    });
  }

  loadSpecialites() {
    this.specialites = [];
    this.groupes = [];
    this.filterSpecialite = '';
    this.filterGroupe = '';
    if (this.filterClasse) {
      this.http.get<string[]>(`${this.baseUrl}/emploi-temps/specialites?classe=${this.filterClasse}`, { withCredentials: true })
        .subscribe({
          next: (specialites) => {
            this.specialites = specialites;
          },
          error: (err) => {
            this.error = 'Erreur lors du chargement des spécialités.';
            console.error('Error fetching specialites:', err);
          }
        });
    }
    this.applyFilters();
  }

  loadGroupes() {
    this.groupes = [];
    this.filterGroupe = '';
    if (this.filterClasse && this.filterSpecialite) {
      this.http.get<string[]>(`${this.baseUrl}/emploi-temps/groupes?classe=${this.filterClasse}&specialite=${this.filterSpecialite}`, { withCredentials: true })
        .subscribe({
          next: (groupes) => {
            this.groupes = groupes;
          },
          error: (err) => {
            this.error = 'Erreur lors du chargement des groupes.';
            console.error('Error fetching groupes:', err);
          }
        });
    }
    this.applyFilters();
  }

  applyFilters() {
    this.filteredRattrapages = this.rattrapages.filter((ratt) => {
      const matchesClasse = !this.filterClasse || ratt.classe === this.filterClasse;
      const matchesSpecialite = !this.filterSpecialite || ratt.specialite === this.filterSpecialite;
      const matchesGroupe = !this.filterGroupe || ratt.groupe === this.filterGroupe;
      return matchesClasse && matchesSpecialite && matchesGroupe;
    });
  }
}