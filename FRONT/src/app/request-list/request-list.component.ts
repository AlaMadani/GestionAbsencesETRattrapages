import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { BehaviorSubject } from 'rxjs';
import { AuthService } from '../auth.service';

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

@Component({
  selector: 'app-request-list',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './request-list.component.html',
  styleUrls: ['./request-list.component.css']
})
export class RequestListComponent implements OnInit {
  private baseUrl = '/api';
  title: string = 'Mes Demandes de Rattrapage';
  requests: RattrapageDTOENS[] = [];
  filteredRequests: RattrapageDTOENS[] = [];
  classes: string[] = [];
  specialites: string[] = [];
  groupes: string[] = [];
  filterClasse: string = '';
  filterSpecialite: string = '';
  filterGroupe: string = '';
  filterStatus: string = '';
  expandedRows: Set<number> = new Set();
  private enseignantIdSubject: BehaviorSubject<number | null> = new BehaviorSubject<number | null>(null);

  constructor(
    private http: HttpClient,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    // Fetch enseignantId
    this.authService.userId$.subscribe(id => {
      this.enseignantIdSubject.next(id);
      if (id) {
        this.loadRequests(id);
      } else {
        console.error('No enseignantId found. User not authenticated.');
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
    this.filterSpecialite = '';
    this.filterGroupe = '';
    if (this.filterClasse) {
      this.http.get<string[]>(`${this.baseUrl}/emploi-temps/specialites?classe=${this.filterClasse}`, { withCredentials: true })
        .subscribe({
          next: (specialites) => this.specialites = specialites,
          error: (err) => console.error('Error fetching specialites', err)
        });
    }
    this.applyFilters();
  }

  loadGroupes(): void {
    this.groupes = [];
    this.filterGroupe = '';
    if (this.filterClasse && this.filterSpecialite) {
      this.http.get<string[]>(`${this.baseUrl}/emploi-temps/groupes?classe=${this.filterClasse}&specialite=${this.filterSpecialite}`, { withCredentials: true })
        .subscribe({
          next: (groupes) => this.groupes = groupes,
          error: (err) => console.error('Error fetching groupes', err)
        });
    }
    this.applyFilters();
  }

  loadRequests(enseignantId: number): void {
    this.http.get<RattrapageDTOENS[]>(`${this.baseUrl}/enseignant/rattrapages?enseignantId=${enseignantId}`, { withCredentials: true })
      .subscribe({
        next: (requests) => {
          this.requests = requests;
          this.applyFilters();
        },
        error: (err) => console.error('Error fetching rattrapages', err)
      });
  }

  applyFilters(): void {
    this.filteredRequests = this.requests.filter(request => {
      const matchesClasse = !this.filterClasse || request.classe === this.filterClasse;
      const matchesSpecialite = !this.filterSpecialite || request.specialite === this.filterSpecialite;
      const matchesGroupe = !this.filterGroupe || request.groupe === this.filterGroupe;
      const matchesStatus = !this.filterStatus || this.getStatus(request.acceptee) === this.filterStatus;
      return matchesClasse && matchesSpecialite && matchesGroupe && matchesStatus;
    });
  }

  toggleRow(requestId: number): void {
    if (this.expandedRows.has(requestId)) {
      this.expandedRows.delete(requestId);
    } else {
      this.expandedRows.add(requestId);
    }
  }

  isRowExpanded(requestId: number): boolean {
    return this.expandedRows.has(requestId);
  }

  getStatus(acceptee: string | null): string {
    switch (acceptee) {
      case 'oui':
        return 'Acceptée';
      case 'non':
        return 'Refusée';
      default:
        return 'En Attente';
    }
  }

  getDateRange(request: RattrapageDTOENS): string {
    const debut = new Date(request.dateDebut).toLocaleDateString('fr-FR');
    const fin = new Date(request.dateFin).toLocaleDateString('fr-FR');
    return `${debut} - ${fin}`;
  }

  getClassGroup(request: RattrapageDTOENS): string {
    return `${request.classe} : ${request.specialite} : ${request.groupe}`;
  }

  getDateAffectee(request: RattrapageDTOENS): string {
    return request.dateAffectee ? new Date(request.dateAffectee).toLocaleDateString('fr-FR') : 'N/A';
  }
}