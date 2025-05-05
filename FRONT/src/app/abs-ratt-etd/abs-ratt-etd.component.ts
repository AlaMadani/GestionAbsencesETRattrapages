import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { BehaviorSubject } from 'rxjs';
import { AuthService } from '../auth.service'; // Adjust path as needed

interface AbsenceDTO {
  id: number;
  date_db: string; // ISO date string (e.g., "2025-05-01")
  date_fin: string;
  seancedb: string;
  seancefin: string;
  nom: string;
  prenom: string;
}

interface StudentDetails {
  classe: string;
  specialite: string;
  groupe: string;
}

@Component({
  selector: 'app-abs-ratt-etd',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './abs-ratt-etd.component.html',
  styleUrls: ['./abs-ratt-etd.component.css']
})
export class AbsRattEtdComponent implements OnInit {
  private baseUrl = '/api';
  title: string = 'Absences Confirmées';
  absences: AbsenceDTO[] = [];
  filteredAbsences: AbsenceDTO[] = [];
  classes: string[] = [];
  specialites: string[] = [];
  groupes: string[] = [];
  selectedClass: string = '';
  selectedSpec: string = '';
  selectedGroup: string = '';
  private studentIdSubject: BehaviorSubject<number | null> = new BehaviorSubject<number | null>(null);

  constructor(
    private http: HttpClient,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    // Fetch student ID and details
    this.authService.userId$.subscribe(id => {
      this.studentIdSubject.next(id);
      if (id) {
       
        this.loadAbsences();
      } else {
        console.error('No studentId found. User not authenticated.');
      }
    });

    // Fetch classes
    this.http.get<string[]>(`${this.baseUrl}/emploi-temps/classes`, { withCredentials: true })
      .subscribe({
        next: (classes) => this.classes = classes,
        error: (err) => console.error('Error fetching classes', err)
      });
  }

  

  loadSpecialites(): void {
    this.specialites = [];
    this.groupes = [];
    this.selectedSpec = '';
    this.selectedGroup = '';
    if (this.selectedClass) {
      this.http.get<string[]>(`${this.baseUrl}/emploi-temps/specialites?classe=${this.selectedClass}`, { withCredentials: true })
        .subscribe({
          next: (specialites) => this.specialites = specialites,
          error: (err) => console.error('Error fetching specialites', err)
        });
    }
    this.applyFilters();
  }

  loadGroupes(): void {
    this.groupes = [];
    this.selectedGroup = '';
    if (this.selectedClass && this.selectedSpec) {
      this.http.get<string[]>(`${this.baseUrl}/emploi-temps/groupes?classe=${this.selectedClass}&specialite=${this.selectedSpec}`, { withCredentials: true })
        .subscribe({
          next: (groupes) => this.groupes = groupes,
          error: (err) => console.error('Error fetching groupes', err)
        });
    }
    this.applyFilters();
  }

  loadAbsences(): void {
    this.http.get<AbsenceDTO[]>(`${this.baseUrl}/etudiant/accepted_absences`, { withCredentials: true })
      .subscribe({
        next: (absences) => {
          this.absences = absences;
          this.applyFilters();
        },
        error: (err) => console.error('Error fetching absences', err)
      });
  }

  applyFilters(): void {
    this.filteredAbsences = this.absences.filter(absence => {
      // Filter by student's classe, specialite, and groupe
      // Assuming AbsenceDTO includes these fields; if not, backend should filter
      return true; // Modify if backend includes classe/specialite/groupe
    });
  }

  getDateRange(absence: AbsenceDTO): string {
    const debut = new Date(absence.date_db).toLocaleDateString('fr-FR');
    const fin = new Date(absence.date_fin).toLocaleDateString('fr-FR');
    return `${debut} - ${fin}`;
  }

  getSeanceRange(absence: AbsenceDTO): string {
    return `${absence.seancedb} - ${absence.seancefin}`;
  }

  getTeacherName(absence: AbsenceDTO): string {
    return `${absence.nom} ${absence.prenom}`;
  }
}