import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { BehaviorSubject } from 'rxjs';
import { AuthService } from '../auth.service'; // Adjust path as needed

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

interface StudentDetails {
  classe: string;
  specialite: string;
  groupe: string;
}

@Component({
  selector: 'app-accepted-rattrapages',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './accepted-rattrapages.component.html',
  styleUrls: ['./accepted-rattrapages.component.css']
})
export class AcceptedRattrapagesComponent implements OnInit {
  private baseUrl = '/api';
  title: string = 'Rattrapages Acceptés';
  rattrapages: ProcessedRattrapageDTO[] = [];
  filteredRattrapages: ProcessedRattrapageDTO[] = [];
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
       
        this.loadRattrapages();
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

  loadRattrapages(): void {
    this.http.get<ProcessedRattrapageDTO[]>(`${this.baseUrl}/etudiant/accepted_rattrapages`, { withCredentials: true })
      .subscribe({
        next: (rattrapages) => {
          this.rattrapages = rattrapages;
          this.applyFilters();
        },
        error: (err) => console.error('Error fetching rattrapages', err)
      });
  }

  applyFilters(): void {
    this.filteredRattrapages = this.rattrapages.filter(rattrapage => {
      const matchesClass = !this.selectedClass || rattrapage.classe === this.selectedClass;
      const matchesSpec = !this.selectedSpec || rattrapage.specialite === this.selectedSpec;
      const matchesGroup = !this.selectedGroup || rattrapage.groupe === this.selectedGroup;
      return matchesClass && matchesSpec && matchesGroup;
    });
  }

  getDateAff(rattrapage: ProcessedRattrapageDTO): string {
    return new Date(rattrapage.date_aff).toLocaleDateString('fr-FR');
  }

  getClassGroup(rattrapage: ProcessedRattrapageDTO): string {
    return `${rattrapage.classe} : ${rattrapage.specialite} : ${rattrapage.groupe}`;
  }

  getTeacherName(rattrapage: ProcessedRattrapageDTO): string {
    return `${rattrapage.nom} ${rattrapage.prenom}`;
  }
}