import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { AuthService } from '../auth.service'; // Adjust path as needed

interface AbsenceDTOENS {
  id: number;
  dateDebut: string; // ISO date string
  dateFin: string;
  seanceDebut: string;
  seanceFin: string;
  acceptee: string; // e.g., "oui", "non", "en attente"
  pinned: string;
}

@Component({
  selector: 'app-historique-teacher',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './historique-teacher.component.html',
  styleUrls: ['./historique-teacher.component.css']
})
export class HistoriqueTeacherComponent implements OnInit {
  private baseUrl = '/api';
  absences: AbsenceDTOENS[] = [];
  expandedRows: Set<number> = new Set();

  constructor(
    private http: HttpClient,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    // Fetch enseignant ID and load absences
    this.authService.userId$.subscribe(id => {
      if (id) {
        this.loadAbsences(id);
      } else {
        console.error('No enseignantId found. User not authenticated.');
      }
    });
  }

  loadAbsences(enseignantId: number): void {
    this.http.get<AbsenceDTOENS[]>(`${this.baseUrl}/absences?enseignantId=${enseignantId}`, { withCredentials: true })
      .subscribe({
        next: (absences) => {
          this.absences = absences;
        },
        error: (err) => console.error('Error fetching absences', err)
      });
  }

  getDateRange(absence: AbsenceDTOENS): string {
    const debut = new Date(absence.dateDebut).toLocaleDateString('fr-FR');
    const fin = new Date(absence.dateFin).toLocaleDateString('fr-FR');
    return `${debut} - ${fin}`;
  }

  getSeanceRange(absence: AbsenceDTOENS): string {
    return `${absence.seanceDebut} - ${absence.seanceFin}`;
  }

  getStatus(acceptee: string): string {
    switch (acceptee.toLowerCase()) {
      case 'oui':
        return 'Acceptée';
      case 'non':
        return 'Refusée';
      default:
        return 'En Attente';
    }
  }

  toggleRow(absenceId: number): void {
    if (this.expandedRows.has(absenceId)) {
      this.expandedRows.delete(absenceId);
    } else {
      this.expandedRows.add(absenceId);
    }
  }

  isRowExpanded(absenceId: number): boolean {
    return this.expandedRows.has(absenceId);
  }
}