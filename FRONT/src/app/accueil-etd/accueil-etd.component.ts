import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../auth.service'; // Adjust path as needed

interface StudentDetails {
  classe: string;
  specialite: string;
  groupe: string;
}

interface AbsenceDTO {
  id: number;
  date_db: string; // ISO date string (e.g., "2025-05-01")
  date_fin: string;
  seancedb: string;
  seancefin: string;
  nom: string;
  prenom: string;
}

interface ProcessedRattrapageDTO {
  id: number;
  classe: string;
  specialite: string;
  groupe: string;
  nom: string;
  prenom: string;
  date_aff: string; // ISO date string (e.g., "2025-05-03")
  seanceAff: string;
  salleId: string;
}

@Component({
  selector: 'app-accueil-etd',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './accueil-etd.component.html',
  styleUrls: ['./accueil-etd.component.css']
})
export class AccueilEtdComponent implements OnInit {
  private baseUrl = '/api';
  studentDetails: StudentDetails | null = null;
  imminentRattrapages: ProcessedRattrapageDTO[] = [];
  imminentAbsences: AbsenceDTO[] = [];

  constructor(
    private http: HttpClient,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    // Fetch student ID and details
    this.authService.userId$.subscribe(id => {
      if (id) {
        this.fetchStudentDetails(id);
      } else {
        console.error('No studentId found. User not authenticated.');
      }
    });
  }

  fetchStudentDetails(studentId: number): void {
    this.http.get<StudentDetails>(`${this.baseUrl}/etudiantt/${studentId}/details`, { withCredentials: true })
      .subscribe({
        next: (details) => {
          this.studentDetails = details;
          if (details.classe && details.specialite && details.groupe) {
            this.loadImminentRattrapages();
            this.loadImminentAbsences();
          } else {
            console.error('Student details incomplete:', details);
          }
        },
        error: (err) => console.error('Error fetching student details', err)
      });
  }

  loadImminentRattrapages(): void {
    this.http.get<ProcessedRattrapageDTO[]>(`${this.baseUrl}/etudiant/accepted_rattrapages`, { withCredentials: true })
      .subscribe({
        next: (rattrapages) => {
          this.imminentRattrapages = rattrapages.filter(rattrapage => {
            // Match student's classe, specialite, groupe
            const matchesDetails = this.studentDetails &&
              rattrapage.classe === this.studentDetails.classe &&
              rattrapage.specialite === this.studentDetails.specialite &&
              rattrapage.groupe === this.studentDetails.groupe;

            // Check if date_aff is within 3 days from the current date
            const today = new Date();
            const dateAff = new Date(rattrapage.date_aff);
            const diffTime = dateAff.getTime() - today.getTime();
            const diffDays = diffTime / (1000 * 3600 * 24);
            const isImminent = diffDays >= 0 && diffDays <= 3;

            return matchesDetails && isImminent;
          });
        },
        error: (err) => console.error('Error fetching rattrapages', err)
      });
  }

  loadImminentAbsences(): void {
    this.http.get<AbsenceDTO[]>(`${this.baseUrl}/etudiant/accepted_absences`, { withCredentials: true })
      .subscribe({
        next: (absences) => {
          this.imminentAbsences = absences.filter(absence => {
            // Check if date_db is within 3 days from the current date
            const today = new Date();
            const dateDb = new Date(absence.date_db);
            const diffTime = dateDb.getTime() - today.getTime();
            const diffDays = diffTime / (1000 * 3600 * 24);
            const isImminent = diffDays >= 0 && diffDays <= 3;

            return isImminent;
          });
        },
        error: (err) => console.error('Error fetching absences', err)
      });
  }

  getDateAff(rattrapage: ProcessedRattrapageDTO): string {
    return new Date(rattrapage.date_aff).toLocaleDateString('fr-FR');
  }

  getClassGroup(rattrapage: ProcessedRattrapageDTO): string {
    return `${rattrapage.classe} : ${rattrapage.specialite} : ${rattrapage.groupe}`;
  }

  getTeacherName(rattrapage: ProcessedRattrapageDTO): string {
    return `${rattrapage.prenom} ${rattrapage.nom} `;
  }

  getDateRange(absence: AbsenceDTO): string {
    const debut = new Date(absence.date_db).toLocaleDateString('fr-FR');
    const fin = new Date(absence.date_fin).toLocaleDateString('fr-FR');
    return `${debut} - ${fin}`;
  }

  getSeanceRange(absence: AbsenceDTO): string {
    return `${absence.seancedb} - ${absence.seancefin}`;
  }

  getTeacherNameAbsence(absence: AbsenceDTO): string {
    return `${absence.prenom} ${absence.nom} `;
  }
}