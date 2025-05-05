import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { AbsenceService, NewAbsenceRequest } from '../dashboard-teacher/absence.service';
import { AuthService } from '../auth.service';
import { NumSeance, NumSeanceOptions } from '../dashboard-teacher/num-seance.model';
import { Router } from '@angular/router';

@Component({
  selector: 'app-absence-request-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './absence-request-form.component.html',
  styleUrls: ['./absence-request-form.component.css']
})
export class AbsenceRequestFormComponent implements OnInit {
  absenceForm: FormGroup;
  seanceOptions = NumSeanceOptions;
  error: string | null = null;
  success: string | null = null;
  enseignantId: number | null = null;

  constructor(
    private fb: FormBuilder,
    private absenceService: AbsenceService,
    private authService: AuthService,
    private router: Router
  ) {
    this.absenceForm = this.fb.group({
      dateDebut: ['', Validators.required],
      dateFin: ['', Validators.required],
      seanceDebut: ['', Validators.required],
      seanceFin: ['', Validators.required]
    });
  }

  ngOnInit(): void {
    // Fetch enseignantId from AuthService
    this.authService.userId$.subscribe(id => {
      this.enseignantId = id;
      if (!id) {
        this.error = 'Vous devez être connecté pour soumettre une demande.';
        this.absenceForm.disable();
      }
    });
  }

  onSubmit(): void {
    if (this.absenceForm.valid && this.enseignantId) {
      const formValue = this.absenceForm.value;
      const request: NewAbsenceRequest = {
        dateDebut: formValue.dateDebut, // Already in YYYY-MM-DD
        dateFin: formValue.dateFin,
        seanceDebut: formValue.seanceDebut,
        seanceFin: formValue.seanceFin,
        enseignantId: this.enseignantId
      };

      this.absenceService.createAbsence(request).subscribe({
        next: (id) => {
          this.success = `Demande d'absence soumise avec succès (ID: ${id})`;
          this.absenceForm.reset();
          setTimeout(() => this.router.navigate(['/teacher_dashboard/teacher-demande']), 2000); // Redirect to requests list
        },
        error: (err) => {
          this.error = 'Échec de la soumission de la demande. Veuillez réessayer.';
          console.error('Error submitting absence:', err);
        }
      });
    } else {
      this.error = 'Veuillez remplir tous les champs requis.';
    }
  }
}