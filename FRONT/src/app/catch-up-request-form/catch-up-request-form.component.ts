import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { RattrapageService, NewRattrapageRequest } from './rattrapage.service';
import { AuthService } from '../auth.service';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { BehaviorSubject } from 'rxjs';

@Component({
  selector: 'app-catch-up-request-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './catch-up-request-form.component.html',
  styleUrls: ['./catch-up-request-form.component.css']
})
export class CatchUpRequestFormComponent implements OnInit {
  private baseUrl = '/api';
  rattrapageForm: FormGroup;
  private enseignantIdSubject: BehaviorSubject<number | null> = new BehaviorSubject<number | null>(null);
  errorMessage: string | null = null;
  successMessage: string | null = null;
  classes: string[] = [];
  specialites: string[] = [];
  groupes: string[] = [];

  constructor(
    private fb: FormBuilder,
    private rattrapageService: RattrapageService,
    private authService: AuthService,
    private http: HttpClient
  ) {
    this.rattrapageForm = this.fb.group({
      dateDebut: ['', Validators.required],
      dateFin: ['', Validators.required],
      classe: ['', Validators.required],
      specialite: ['', Validators.required],
      groupe: ['', Validators.required],
      matiere: ['', Validators.required] // Added matiere with required validator
    });
  }

  ngOnInit(): void {
    // Fetch enseignantId
    this.authService.userId$.subscribe(id => {
      this.enseignantIdSubject.next(id);
      if (!id) {
        this.errorMessage = 'Utilisateur non authentifié. Veuillez vous connecter.';
        this.rattrapageForm.disable();
      } else {
        this.rattrapageForm.enable();
      }
    });

    // Fetch classes
    this.http.get<string[]>(`${this.baseUrl}/emploi-temps/classes`, { withCredentials: true })
      .subscribe({
        next: (classes) => this.classes = classes,
        error: (error) => console.error('Error fetching classes', error)
      });

    // Update specialites when classe changes
    this.rattrapageForm.get('classe')?.valueChanges.subscribe(classe => {
      this.rattrapageForm.get('specialite')?.setValue('');
      this.rattrapageForm.get('groupe')?.setValue('');
      this.specialites = [];
      this.groupes = [];
      if (classe) {
        this.http.get<string[]>(`${this.baseUrl}/emploi-temps/specialites?classe=${classe}`, { withCredentials: true })
          .subscribe({
            next: (specialites) => this.specialites = specialites,
            error: (error) => console.error('Error fetching specialites', error)
          });
      }
    });

    // Update groupes when specialite changes
    this.rattrapageForm.get('specialite')?.valueChanges.subscribe(specialite => {
      this.rattrapageForm.get('groupe')?.setValue('');
      this.groupes = [];
      const classe = this.rattrapageForm.get('classe')?.value;
      if (classe && specialite) {
        this.http.get<string[]>(`${this.baseUrl}/emploi-temps/groupes?classe=${classe}&specialite=${specialite}`, { withCredentials: true })
          .subscribe({
            next: (groupes) => this.groupes = groupes,
            error: (error) => console.error('Error fetching groupes', error)
          });
      }
    });
  }

  onSubmit(): void {
    if (this.rattrapageForm.valid && this.enseignantIdSubject.value) {
      const request: NewRattrapageRequest = {
        ...this.rattrapageForm.value,
        dateDebut: new Date(this.rattrapageForm.value.dateDebut).toISOString().split('T')[0],
        dateFin: new Date(this.rattrapageForm.value.dateFin).toISOString().split('T')[0],
        enseignantId: this.enseignantIdSubject.value,
        matiere: this.rattrapageForm.value.matiere // Include matiere
      };

      this.rattrapageService.submitRattrapageRequest(request).subscribe({
        next: (rattrapageId) => {
          this.successMessage = `Demande de rattrapage soumise avec succès (ID: ${rattrapageId})`;
          this.errorMessage = null;
          this.rattrapageForm.reset();
        },
        error: (error) => {
          this.errorMessage = 'Erreur lors de la soumission de la demande : ' + (error.error?.message || error.message);
          this.successMessage = null;
          console.error('Error submitting rattrapage request', error);
        }
      });
    } else {
      this.errorMessage = 'Veuillez remplir tous les champs obligatoires.';
      this.successMessage = null;
    }
  }
}