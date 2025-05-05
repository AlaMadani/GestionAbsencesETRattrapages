import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { BehaviorSubject } from 'rxjs';
import { AuthService } from '../auth.service'; // Adjust path based on your structure

interface EmploiTempsRowsDTO {
  jourr: string;
  classe: string;
  specialite: string;
  groupe: string;
  seance1: number | null;
  seance2: number | null;
  seance3: number | null;
  seance4: number | null;
  seance5: number | null;
}

interface SeanceDTO {
  id: number;
  matiere: string;
  isRattrapage: string;
  enseignantNom: string;
  enseignantPrenom: string;
  salleNumero: string;
  rattrapageId: number | null;
}

interface Timetable {
  classe: string;
  specialite: string;
  groupe: string;
  days: { jour: string; seances: (number | null)[] }[];
}

interface ProcessedTimetable {
  classe: string;
  specialite: string;
  groupe: string;
  days: { jour: string; seances: (SeanceDTO | null)[] }[];
}

@Component({
  selector: 'app-emplois-temps',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './emplois-temps.component.html',
  styleUrl: './emplois-temps.component.css'
})
export class EmploisTempsComponent implements OnInit {
  private baseUrl = '/api';
  title: string = 'Emplois du Temps';
  timetables: Timetable[] = [];
  filteredTimetables: ProcessedTimetable[] = [];
  classes: string[] = [];
  specialites: string[] = [];
  groupes: string[] = [];
  selectedClass: string = '';
  selectedSpec: string = '';
  selectedGroup: string = '';
  days: string[] = ['LUNDI', 'MARDI', 'MERCREDI', 'JEUDI', 'VENDREDI', 'SAMEDI'];
  seances: string[] = ['SEANCE_1', 'SEANCE_2', 'SEANCE_3', 'SEANCE_4', 'SEANCE_5'];
  private seanceCache: Map<number, SeanceDTO> = new Map();
  private dateAffCache: Map<number, string> = new Map(); // Keyed by seanceId
  private studentIdSubject: BehaviorSubject<number | null> = new BehaviorSubject<number | null>(null);

  constructor(
    private http: HttpClient,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    // Fetch classes and timetables
    this.http.get<string[]>(`${this.baseUrl}/emploi-temps/classes`, { withCredentials: true })
      .subscribe({
        next: (classes) => this.classes = classes,
        error: (err) => console.error('Error fetching classes', err)
      });

    this.loadTimetables();

    // Subscribe to userId for authentication check
    this.authService.userId$.subscribe(id => {
      this.studentIdSubject.next(id);
      if (!id) {
        console.error('No studentId found. User not authenticated.');
      }
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

  loadTimetables(): void {
    this.http.get<EmploiTempsRowsDTO[]>(`${this.baseUrl}/emploi-temps/All`, { withCredentials: true })
      .subscribe({
        next: (rows) => this.processTimetables(rows),
        error: (err) => console.error('Error fetching timetables', err)
      });
  }

  processTimetables(rows: EmploiTempsRowsDTO[]): void {
    const grouped = new Map<string, EmploiTempsRowsDTO[]>();
    rows.forEach(row => {
      const key = `${row.classe}_${row.specialite}_${row.groupe}`;
      if (!grouped.has(key)) {
        grouped.set(key, []);
      }
      grouped.get(key)!.push(row);
    });

    this.timetables = Array.from(grouped.entries()).map(([key, rows]) => {
      const [classe, specialite, groupe] = key.split('_');
      const days = this.days.map(jour => {
        const row = rows.find(r => r.jourr === jour) || {
          jourr: jour,
          classe,
          specialite,
          groupe,
          seance1: null,
          seance2: null,
          seance3: null,
          seance4: null,
          seance5: null
        };
        return {
          jour,
          seances: [row.seance1, row.seance2, row.seance3, row.seance4, row.seance5]
        };
      });
      return { classe, specialite, groupe, days };
    });

    // Fetch seance details
    this.fetchSeanceDetails();
  }

  fetchSeanceDetails(): void {
    const seanceIds = new Set<number>();
    this.timetables.forEach(timetable => {
      timetable.days.forEach(day => {
        day.seances.forEach(seance => {
          if (seance !== null) {
            seanceIds.add(seance);
          }
        });
      });
    });

    seanceIds.forEach(id => {
      if (!this.seanceCache.has(id)) {
        this.http.get<SeanceDTO>(`${this.baseUrl}/seance/${id}`, { withCredentials: true })
          .subscribe({
            next: (seance) => {
              this.seanceCache.set(id, seance);
              if (seance.isRattrapage.toUpperCase() === 'OUI') {
                this.fetchDateAffectee(seance.id);
              }
              this.updateTimetables();
            },
            error: (err) => console.error(`Error fetching seance ${id}`, err)
          });
      }
    });
  }

  fetchDateAffectee(seanceId: number): void {
    if (!this.dateAffCache.has(seanceId)) {
      this.http.get<string>(`${this.baseUrl}/etudiant/DateAffRatt?id=${seanceId}`, { withCredentials: true })
        .subscribe({
          next: (date) => {
            this.dateAffCache.set(seanceId, date);
            console.log(`Fetched dateAff for seanceId ${seanceId}: ${date}`);
            this.updateTimetables();
          },
          error: (err) => console.error(`Error fetching date_affectee for seanceId ${seanceId}`, err)
        });
    }
  }

  updateTimetables(): void {
    const currentDate = new Date();
    currentDate.setHours(0, 0, 0, 0); // Normalize to start of day
    this.filteredTimetables = this.timetables.map(timetable => ({
      classe: timetable.classe,
      specialite: timetable.specialite,
      groupe: timetable.groupe,
      days: timetable.days.map(day => ({
        jour: day.jour,
        seances: day.seances.map(seanceId => {
          if (seanceId === null) return null;
          const seance = this.seanceCache.get(seanceId);
          if (!seance) {
            console.log(`No seance found for seanceId ${seanceId}`);
            return null;
          }
          if (seance.isRattrapage.toUpperCase() === 'OUI') {
            const dateAff = this.dateAffCache.get(seance.id);
            if (!dateAff) {
              console.log(`No dateAff for seanceId ${seance.id}`);
              return null; // Treat missing dateAff as past
            }
            const dateAffDate = new Date(dateAff);
            if (isNaN(dateAffDate.getTime())) {
              console.error(`Invalid dateAff format for seanceId ${seance.id}: ${dateAff}`);
              return null; // Treat invalid dateAff as past
            }
            dateAffDate.setHours(0, 0, 0, 0);
            if (dateAffDate < currentDate) {
              console.log(`Past rattrapage: seanceId=${seance.id}, dateAff=${dateAff}, currentDate=${currentDate}`);
              return null; // Past rattrapage
            }
          }
          return seance;
        })
      }))
    }));
    this.applyFilters();
  }

  applyFilters(): void {
    this.filteredTimetables = this.filteredTimetables.filter(timetable => {
      const matchesClass = !this.selectedClass || timetable.classe === this.selectedClass;
      const matchesSpec = !this.selectedSpec || timetable.specialite === this.selectedSpec;
      const matchesGroup = !this.selectedGroup || timetable.groupe === this.selectedGroup;
      return matchesClass && matchesSpec && matchesGroup;
    });
  }

  getSeanceContent(seance: SeanceDTO | null): string {
    if (!seance) return '';
    return `${seance.matiere}\n${seance.enseignantNom} ${seance.enseignantPrenom}\n${seance.salleNumero}`;
  }

  isRattrapageTodayOrFuture(seance: SeanceDTO | null): boolean {
    if (!seance || seance.isRattrapage.toUpperCase() !== 'OUI') {
      console.log(`Not a rattrapage: id=${seance?.id}, isRattrapage=${seance?.isRattrapage}`);
      return false;
    }
    const dateAff = this.dateAffCache.get(seance.id);
    if (!dateAff) {
      console.log(`No dateAff for seanceId ${seance.id}`);
      return false;
    }
    const currentDate = new Date();
    currentDate.setHours(0, 0, 0, 0); // Normalize to start of day
    const dateAffDate = new Date(dateAff);
    if (isNaN(dateAffDate.getTime())) {
      console.error(`Invalid dateAff format for seanceId ${seance.id}: ${dateAff}`);
      return false;
    }
    dateAffDate.setHours(0, 0, 0, 0);
    const isFutureOrToday = dateAffDate >= currentDate;
    console.log(`Rattrapage check: seanceId=${seance.id}, isRattrapage=${seance.isRattrapage}, dateAff=${dateAff}, currentDate=${currentDate}, isFutureOrToday=${isFutureOrToday}`);
    return isFutureOrToday;
  }
}