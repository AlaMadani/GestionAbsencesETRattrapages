import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { AuthService } from '../auth.service'; // Adjust path as needed

interface StudentDetails {
  classe: string;
  specialite: string;
  groupe: string;
}

@Component({
  selector: 'app-options',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './options.component.html',
  styleUrls: ['./options.component.css']
})
export class OptionsComponent implements OnInit {
  private baseUrl = '/api';
  studentId: number | null = null;
  selectedClass: string = '';
  selectedSpec: string = '';
  selectedGroup: string = '';
  classes: string[] = [];
  specialites: string[] = [];
  groupes: string[] = [];
  updateMessage: string = '';

  constructor(
    private http: HttpClient,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    // Fetch student ID and current details
    this.authService.userId$.subscribe(id => {
      if (id) {
        this.studentId = id;
        this.fetchStudentDetails(id);
      } else {
        console.error('No studentId found. User not authenticated.');
      }
    });

    // Fetch classes
    this.fetchClasses();
  }

  fetchStudentDetails(studentId: number): void {
    this.http.get<StudentDetails>(`${this.baseUrl}/admin/students/${studentId}/details`, { withCredentials: true })
      .subscribe({
        next: (details) => {
          this.selectedClass = details.classe || '';
          this.selectedSpec = details.specialite || '';
          this.selectedGroup = details.groupe || '';
          // After setting the class, fetch specialites and groupes
          if (this.selectedClass) {
            this.fetchSpecialites();
          }
          if (this.selectedClass && this.selectedSpec) {
            this.fetchGroupes();
          }
        },
        error: (err) => console.error('Error fetching student details', err)
      });
  }

  fetchClasses(): void {
    this.http.get<string[]>(`${this.baseUrl}/emploi-temps/classes`, { withCredentials: true })
      .subscribe({
        next: (classes) => {
          this.classes = classes;
        },
        error: (err) => console.error('Error fetching classes', err)
      });
  }

  fetchSpecialites(): void {
    this.specialites = [];
    this.groupes = [];
    this.selectedSpec = '';
    this.selectedGroup = '';
    if (this.selectedClass) {
      this.http.get<string[]>(`${this.baseUrl}/emploi-temps/specialites?classe=${this.selectedClass}`, { withCredentials: true })
        .subscribe({
          next: (specialites) => {
            this.specialites = specialites;
            // If the student's specialite is still in the list, keep it selected
            if (this.specialites.includes(this.selectedSpec)) {
              this.fetchGroupes();
            } else {
              this.selectedSpec = '';
              this.selectedGroup = '';
            }
          },
          error: (err) => console.error('Error fetching specialites', err)
        });
    }
  }

  fetchGroupes(): void {
    this.groupes = [];
    this.selectedGroup = '';
    if (this.selectedClass && this.selectedSpec) {
      this.http.get<string[]>(`${this.baseUrl}/emploi-temps/groupes?classe=${this.selectedClass}&specialite=${this.selectedSpec}`, { withCredentials: true })
        .subscribe({
          next: (groupes) => {
            this.groupes = groupes;
            // If the student's groupe is still in the list, keep it selected
            if (!this.groupes.includes(this.selectedGroup)) {
              this.selectedGroup = '';
            }
          },
          error: (err) => console.error('Error fetching groupes', err)
        });
    }
  }

  saveSettings(): void {
    if (!this.studentId) {
      this.updateMessage = 'Erreur: Utilisateur non authentifié.';
      return;
    }

    if (!this.selectedClass || !this.selectedSpec || !this.selectedGroup) {
      this.updateMessage = 'Erreur: Veuillez sélectionner une classe, une spécialité et un groupe.';
      return;
    }

    const updates = {
      classe: this.selectedClass,
      specialite: this.selectedSpec,
      groupe: this.selectedGroup
    };

    this.http.put(`${this.baseUrl}/etudiant/${this.studentId}/details`, updates, { withCredentials: true })
      .subscribe({
        next: () => {
          this.updateMessage = 'Paramètres enregistrés avec succès!';
        },
        error: (err) => {
          this.updateMessage = 'Erreur lors de la mise à jour: ' + (err.error?.error || err.message);
        }
      });
  }
}