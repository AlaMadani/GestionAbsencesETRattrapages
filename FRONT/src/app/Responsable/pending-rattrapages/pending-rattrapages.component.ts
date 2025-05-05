import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { AdminService } from '../admin.service';
import {
  RattrapageRequest,
  SeancesDispoMap,
  AccepterRattrapageRequest
} from '../rattrapage.model';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-pending-rattrapages',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './pending-rattrapages.component.html',
  styleUrls: ['./pending-rattrapages.component.css']
})
export class PendingRattrapagesComponent implements OnInit {
  private baseUrl = '/api';
  requests: RattrapageRequest[] = [];
  filteredRequests: RattrapageRequest[] = [];
  loading = true;
  error: string | null = null;

  // Filter properties
  classes: string[] = [];
  specialites: string[] = [];
  groupes: string[] = [];
  filterClasse: string = '';
  filterSpecialite: string = '';
  filterGroupe: string = '';

  seancesMap = new Map<
    number,
    {
      options?: { date: string; day: string; seance: number }[];
      selectedOption?: { date: string; day: string; seance: number };
      salles?: string[];
      selectedSalle?: string;
    }
  >();

  // Mapping of English day names to French
  private dayTranslations: { [key: string]: string } = {
    MONDAY: 'Lundi',
    TUESDAY: 'Mardi',
    WEDNESDAY: 'Mercredi',
    THURSDAY: 'Jeudi',
    FRIDAY: 'Vendredi',
    SATURDAY: 'Samedi',
    SUNDAY: 'Dimanche'
  };

  constructor(
    private http: HttpClient,
    private admin: AdminService
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

    // Fetch pending rattrapages
    this.admin.getPendingRattrapages().subscribe({
      next: (list) => {
        this.requests = list;
        this.filteredRequests = list; // Initialize filteredRequests
        // Initialize seancesMap for each request
        list.forEach((req) => {
          if (!this.seancesMap.has(req.id)) {
            this.seancesMap.set(req.id, {});
          }
        });
        this.loading = false;
      },
      error: (err) => {
        this.error = 'Impossible de charger les rattrapages.';
        console.error('Error loading rattrapages:', err);
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
    this.filteredRequests = this.requests.filter((req) => {
      const matchesClasse = !this.filterClasse || req.classe === this.filterClasse;
      const matchesSpecialite = !this.filterSpecialite || req.specialite === this.filterSpecialite;
      const matchesGroupe = !this.filterGroupe || req.groupe === this.filterGroupe;
      return matchesClasse && matchesSpecialite && matchesGroupe;
    });
  }

  onLoadSeances(req: RattrapageRequest) {
    const key = req.id;
    const entry = this.seancesMap.get(key) || {};

    // Avoid reloading if options are already loaded
    if (entry.options) {
      return;
    }

    this.admin
      .getSeancesDispo(
        req.classe,
        req.specialite,
        req.groupe,
        this.format(req.date_db),
        this.format(req.date_fin),
        req.idens
      )
      .subscribe({
        next: (dispo: SeancesDispoMap) => {
          const opts: { date: string; day: string; seance: number }[] = [];
          Object.entries(dispo).forEach(([day, map]) => {
            Object.entries(map).forEach(([sn, dates]: [string, string[]]) => {
              const seanceNum = Number(sn);
              dates.forEach((dateStr: string) => {
                opts.push({ date: dateStr, day, seance: seanceNum });
              });
            });
          });
          entry.options = opts;
          this.seancesMap.set(key, entry);
        },
        error: (err) => {
          this.error = 'Erreur lors du chargement des séances.';
          console.error('Error loading seances:', err);
        }
      });
  }

  onOptionSelect(
    req: RattrapageRequest,
    opt: { date: string; day: string; seance: number } | undefined
  ) {
    const entry = this.seancesMap.get(req.id)!;
    entry.selectedOption = opt;
    entry.salles = undefined; // Reset salles
    entry.selectedSalle = undefined; // Reset selected salle

    if (opt) {
      // Format seance as "S1", "S2", etc.
      const seanceStr = `S${opt.seance}`;
      console.log('Calling getSallesDispo with:', {
        dateS: opt.date,
        day: opt.day,
        numS: seanceStr
      });

      // Load salles for the selected option
      this.admin
        .getSallesDispo(opt.date, opt.day, seanceStr)
        .subscribe({
          next: (salles) => {
            console.log('Salles received:', salles);
            entry.salles = salles;
            this.seancesMap.set(req.id, entry);
          },
          error: (err) => {
            this.error = 'Erreur lors du chargement des salles.';
            console.error('Error loading salles:', err);
          }
        });
    } else {
      this.seancesMap.set(req.id, entry);
    }
  }

  onSalleSelect(req: RattrapageRequest, salle: string | undefined) {
    const entry = this.seancesMap.get(req.id)!;
    entry.selectedSalle = salle;
    this.seancesMap.set(req.id, entry);
  }

  onAccept(req: RattrapageRequest) {
    const entry = this.seancesMap.get(req.id)!;
    const opt = entry.selectedOption!;
    const payload: AccepterRattrapageRequest = {
      rattrapageId: req.id,
      dateAff: opt.date,
      seanceAff: `S${opt.seance}`,
      salleId: entry.selectedSalle!,
      enseignantId: req.idens
    };

    console.log('Sending accepterRattrapage payload:', payload);

    this.admin.accepterRattrapage(payload).subscribe({
      next: () => {
        console.log('Rattrapage accepted successfully');
        this.requests = this.requests.filter((r) => r.id !== req.id);
        this.applyFilters(); // Re-apply filters after removing request
        this.seancesMap.delete(req.id);
      },
      error: (err) => {
        const errorMessage = err.error?.message || err.statusText || 'Unknown error';
        alert(`Erreur lors de l'acceptation: ${errorMessage}`);
        console.error('Error accepting rattrapage:', {
          status: err.status,
          statusText: err.statusText,
          message: err.error?.message,
          error: err
        });
      }
    });
  }

  onReject(req: RattrapageRequest) {
    console.log('Rejecting rattrapage with ID:', req.id);

    this.admin.rejeterRattrapage(req.id).subscribe({
      next: () => {
        console.log('Rattrapage rejected successfully');
        this.requests = this.requests.filter((r) => r.id !== req.id);
        this.applyFilters(); // Re-apply filters after removing request
        this.seancesMap.delete(req.id);
      },
      error: (err) => {
        const errorMessage = err.error?.message || err.statusText || 'Unknown error';
        alert(`Erreur lors du refus: ${errorMessage}`);
        console.error('Error rejecting rattrapage:', {
          status: err.status,
          statusText: err.statusText,
          message: err.error?.message,
          error: err
        });
      }
    });
  }

  getFrenchDay(englishDay: string): string {
    return this.dayTranslations[englishDay.toUpperCase()] || englishDay;
  }

  private format(iso: string) {
    const d = new Date(iso);
    const dd = String(d.getDate()).padStart(2, '0');
    const mm = String(d.getMonth() + 1).padStart(2, '0');
    const yy = d.getFullYear();
    return `${dd}/${mm}/${yy}`;
  }
}