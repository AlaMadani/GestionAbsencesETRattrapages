import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { catchError, forkJoin, map, Observable, of } from 'rxjs';
import { AuthService } from '../auth.service'; // Adjust path to your AuthService

interface SeanceInfo {
  numseance: number;
  classe: string;
  specialite: string;
  groupe: string;
  seanceId: number;
  isRattrapage?: boolean;
  salle?: string;
}

interface Timetable {
  days: { jour: string; seances: (SeanceInfo | null)[] }[];
}

@Component({
  selector: 'app-emplois-teacher',
  standalone: true,
  imports: [CommonModule, HttpClientModule],
  templateUrl: './emplois-teacher.component.html',
  styleUrls: ['./emplois-teacher.component.css']
})
export class EmploisTeacherComponent implements OnInit {
  seances: string[] = ['Séance 1', 'Séance 2', 'Séance 3', 'Séance 4', 'Séance 5'];
  timetable: Timetable = { days: [] };
  loading: boolean = true;
  error: string | null = null;
  private idens: number | null = null;
  private apiBaseUrl: string = '/api'; // Adjust to your backend URL

  constructor(
    private http: HttpClient,
    private authService: AuthService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    console.log('EmploisTeacherComponent initialized');
    this.authService.userId$.subscribe({
      next: (id) => {
        console.log('Received idens from AuthService:', id);
        this.idens = id;
        this.updateState();
      },
      error: (err) => {
        console.error('Error in userId$ subscription:', err);
        this.error = 'Erreur d’authentification. Veuillez réessayer.';
        this.loading = false;
        this.cdr.detectChanges();
      }
    });
  }

  private updateState(): void {
    console.log('Updating state: idens=', this.idens, 'loading=', this.loading, 'error=', this.error, 'days=', this.timetable.days.length);
    if (!this.idens) {
      this.error = 'Utilisateur non authentifié. Veuillez vous connecter.';
      this.loading = false;
    } else {
      this.error = null;
      this.fetchTimetable();
    }
    this.cdr.detectChanges();
  }

  fetchTimetable(): void {
    if (!this.idens) {
      this.error = 'ID enseignant non disponible.';
      this.loading = false;
      this.cdr.detectChanges();
      return;
    }

    this.loading = true;
    this.error = null;
    console.log('State before fetch: loading=', this.loading, 'error=', this.error);

    console.log('Fetching timetable for idens:', this.idens);
    this.http.get<{ [key: string]: { [key: string]: SeanceInfo } }>(`${this.apiBaseUrl}/timetable/ens?idens=${this.idens}`)
      .pipe(
        catchError((err) => {
          this.error = 'Impossible de charger l’emploi du temps. Vérifiez la connexion au serveur.';
          console.error('Error fetching timetable:', err);
          this.loading = false;
          this.cdr.detectChanges();
          return of({});
        })
      )
      .subscribe((data) => {
        console.log('Timetable data received:', JSON.stringify(data, null, 2));
        if (Object.keys(data).length === 0) {
          this.error = 'Aucun emploi du temps trouvé pour cet enseignant.';
          this.loading = false;
          this.cdr.detectChanges();
          return;
        }
        this.processTimetableData(data);
      });
  }

  processTimetableData(data: { [key: string]: { [key: string]: SeanceInfo } }): void {
    const days: { jour: string; seances: (SeanceInfo | null)[] }[] = [];
    const seanceChecks: Observable<void>[] = [];

    // Map English day names to French
    const dayMap: { [key: string]: string } = {
      'MONDAY': 'Lundi',
      'TUESDAY': 'Mardi',
      'WEDNESDAY': 'Mercredi',
      'THURSDAY': 'Jeudi',
      'FRIDAY': 'Vendredi',
      'SATURDAY': 'Samedi',
      'SUNDAY': 'Dimanche'
    };

    // Process each day
    for (const dayKey of Object.keys(data)) {
      const frenchDay = dayMap[dayKey.toUpperCase()];
      if (!frenchDay) {
        console.warn(`Unknown day key: ${dayKey}`);
        continue;
      }

      const seances: (SeanceInfo | null)[] = [null, null, null, null, null];
      const dayData = data[dayKey];

      if (!dayData || typeof dayData !== 'object') {
        console.warn(`Invalid or empty day data for ${dayKey}:`, dayData);
        days.push({ jour: frenchDay, seances });
        continue;
      }

      console.log(`Processing day ${frenchDay} with data:`, JSON.stringify(dayData, null, 2));

      // Fill seances array
      for (const seanceKey of Object.keys(dayData)) {
        const numSeance = parseInt(seanceKey, 10);
        if (isNaN(numSeance) || numSeance < 1 || numSeance > 5) {
          console.warn(`Invalid seance key: ${seanceKey} for day ${frenchDay}`);
          continue;
        }

        const seance = data[dayKey][seanceKey];
        if (!seance || !seance.seanceId) {
          console.warn(`Invalid seance data for ${frenchDay}, seance ${seanceKey}:`, seance);
          continue;
        }

        // Create a new object to ensure updates persist
        seances[numSeance - 1] = { ...seance, isRattrapage: false, salle: 'Unknown' };
        console.log(`Added seance for ${frenchDay}, seance ${numSeance}, seanceId: ${seance.seanceId}`);

        // Queue rattrapage check
        seanceChecks.push(
          this.checkRattrapage(seance.seanceId).pipe(
            map((response) => {
              console.log(`Raw rattrapage response for seanceId ${seance.seanceId}:`, response);
              const isRattrapage = String(response.isRattrapage).toLowerCase() === 'oui';
              seances[numSeance - 1] = {
                ...seances[numSeance - 1]!,
                isRattrapage,
                salle: response.salle || 'Unknown'
              };
              console.log(`Updated seance for seanceId ${seance.seanceId}: isRattrapage=${isRattrapage}, salle=${response.salle || 'Unknown'}`);
            }),
            catchError((err) => {
              console.error(`Error checking rattrapage for seanceId ${seance.seanceId}:`, err);
              seances[numSeance - 1] = {
                ...seances[numSeance - 1]!,
                isRattrapage: false,
                salle: 'Unknown'
              };
              return of(void 0);
            })
          )
        );
      }

      days.push({ jour: frenchDay, seances });
    }

    // Sort days by order
    const dayOrder = ['Lundi', 'Mardi', 'Mercredi', 'Jeudi', 'Vendredi', 'Samedi', 'Dimanche'];
    days.sort((a, b) => dayOrder.indexOf(a.jour) - dayOrder.indexOf(b.jour));

    // Log days
    console.log('Days processed:', JSON.stringify(days, null, 2));

    // Handle case with no rattrapage checks
    if (seanceChecks.length === 0) {
      console.warn('No séances found to check for rattrapage. Displaying timetable without rattrapage checks.');
      this.timetable = { days };
      this.loading = false;
      this.cdr.detectChanges();
      return;
    }

    // Wait for all rattrapage checks
    forkJoin(seanceChecks).subscribe({
      next: () => {
        this.timetable = { days };
        console.log('Processed timetable:', JSON.stringify(this.timetable, null, 2));
        this.loading = false;
        this.cdr.detectChanges();
      },
      error: (err) => {
        this.error = 'Erreur lors de la vérification des rattrapages.';
        console.error('Error checking rattrapages:', err);
        this.loading = false;
        this.cdr.detectChanges();
      }
    });
  }

  checkRattrapage(seanceId: number): Observable<{ isRattrapage: string; salle: string; idens?: number }> {
    console.log(`Checking rattrapage for seanceId: ${seanceId}`);
    return this.http.get<{ isRattrapage: string; salle: string; idens?: number }>(`${this.apiBaseUrl}/IsRattrapage?id=${seanceId}`)
      .pipe(
        catchError((err) => {
          console.error(`Failed to fetch rattrapage for seanceId ${seanceId}:`, err);
          return of({ isRattrapage: 'non', salle: 'Unknown' });
        })
      );
  }

  getSeanceContent(seance: SeanceInfo | null): string {
    return seance ? `${seance.classe} ${seance.specialite} ${seance.groupe} \n ${seance.salle || 'Unknown'}` : '-';
  }
}