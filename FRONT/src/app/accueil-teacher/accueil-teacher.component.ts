import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../auth.service'; // Adjust path as needed

interface RattrapageDTOENS {
  id: number;
  dateDebut: string;
  dateFin: string;
  seanceDebut: string;
  seanceFin: string;
  acceptee: string | null;
  dateAffectee: string | null;
  seanceAffectee: string | null;
  pinned: string | null;
  classe: string;
  specialite: string;
  groupe: string;
  salleaff: string | null;
}

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
  selector: 'app-accueil-teacher',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './accueil-teacher.component.html',
  styleUrls: ['./accueil-teacher.component.css']
})
export class AccueilTeacherComponent implements OnInit {
  private baseUrl = '/api';
  imminentRattrapages: RattrapageDTOENS[] = [];
  imminentAbsences: AbsenceDTOENS[] = [];

  constructor(
    private http: HttpClient,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    // Fetch enseignant ID and load data
    this.authService.userId$.subscribe(id => {
      if (id) {
        this.loadImminentRattrapages(id);
        this.loadImminentAbsences(id);
      } else {
        console.error('No enseignantId found. User not authenticated.');
      }
    });
  }

  loadImminentRattrapages(enseignantId: number): void {
    this.http.get<RattrapageDTOENS[]>(`${this.baseUrl}/enseignant/rattrapages?enseignantId=${enseignantId}`, { withCredentials: true })
      .subscribe({
        next: (rattrapages) => {
          this.imminentRattrapages = rattrapages.filter(rattrapage => {
            // Filter for accepted rattrapages
            const isAccepted = rattrapage.acceptee === 'oui' && rattrapage.dateAffectee;

            // Check if dateAffectee is within 3 days from the current date
            const today = new Date();
            const dateAff = rattrapage.dateAffectee ? new Date(rattrapage.dateAffectee) : null;
            const diffTime = dateAff ? dateAff.getTime() - today.getTime() : -1;
            const diffDays = diffTime / (1000 * 3600 * 24);
            const isImminent = diffDays >= 0 && diffDays <= 3;

            return isAccepted && isImminent;
          });
        },
        error: (err) => console.error('Error fetching rattrapages', err)
      });
  }

  loadImminentAbsences(enseignantId: number): void {
    this.http.get<AbsenceDTOENS[]>(`${this.baseUrl}/absences?enseignantId=${enseignantId}`, { withCredentials: true })
      .subscribe({
        next: (absences) => {
          this.imminentAbsences = absences.filter(absence => {
            // Filter for accepted absences
            const isAccepted = absence.acceptee === 'oui';

            // Check if dateDebut is within 3 days from the current date
            const today = new Date();
            const dateDb = new Date(absence.dateDebut);
            const diffTime = dateDb.getTime() - today.getTime();
            const diffDays = diffTime / (1000 * 3600 * 24);
            const isImminent = diffDays >= 0 && diffDays <= 3;

            return isAccepted && isImminent;
          });
        },
        error: (err) => console.error('Error fetching absences', err)
      });
  }

  getDateRange(rattrapage: RattrapageDTOENS): string {
    const debut = new Date(rattrapage.dateDebut).toLocaleDateString('fr-FR');
    const fin = new Date(rattrapage.dateFin).toLocaleDateString('fr-FR');
    return `${debut} - ${fin}`;
  }

  getDateAffectee(rattrapage: RattrapageDTOENS): string {
    return rattrapage.dateAffectee ? new Date(rattrapage.dateAffectee).toLocaleDateString('fr-FR') : 'N/A';
  }

  getClassGroup(rattrapage: RattrapageDTOENS): string {
    return `${rattrapage.classe} : ${rattrapage.specialite} : ${rattrapage.groupe}`;
  }

  getAbsenceDateRange(absence: AbsenceDTOENS): string {
    const debut = new Date(absence.dateDebut).toLocaleDateString('fr-FR');
    const fin = new Date(absence.dateFin).toLocaleDateString('fr-FR');
    return `${debut} - ${fin}`;
  }

  getSeanceRange(absence: AbsenceDTOENS): string {
    return `${absence.seanceDebut} - ${absence.seanceFin}`;
  }
}