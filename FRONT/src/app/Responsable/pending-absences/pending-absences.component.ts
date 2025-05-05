import { Component, OnInit } from '@angular/core';
import { AdminService } from '../admin.service';
import { Absence } from '../absence.model';
import { CommonModule } from '@angular/common';
import { NgIf, NgFor } from '@angular/common';

@Component({
  selector: 'app-pending-absences',
  standalone: true,
  imports: [CommonModule, NgIf, NgFor],
  templateUrl: './pending-absences.component.html',
  styleUrls: ['./pending-absences.component.css']
})
export class PendingAbsencesComponent implements OnInit {
  absences: Absence[] = [];
  loading = true;
  error: string | null = null;

  constructor(private adminService: AdminService) {}

  ngOnInit() {
    this.adminService.getPendingAbsences().subscribe({
      next: list => {
        this.absences = list;
        this.loading = false;
      },
      error: err => {
        this.error = 'Impossible de charger les demandes d’absences.';
        console.error('Error loading absences:', err);
        this.loading = false;
      }
    });
  }

  onRespond(abs: Absence, accept: boolean) {
    const action = accept ? 'Accepter' : 'Refuser';
    console.log(`${action} absence with ID:`, abs.id);

    this.adminService.respondToAbsence(abs.id, accept).subscribe({
      next: () => {
        console.log(`Absence ${action.toLowerCase()} successfully`);
        this.absences = this.absences.filter(a => a.id !== abs.id);
      },
      error: err => {
        const errorMessage = err.error?.message || err.statusText || 'Erreur inconnue';
        alert(`Erreur lors de ${action.toLowerCase()} la demande : ${errorMessage}`);
        console.error(`Error ${action.toLowerCase()} absence:`, {
          status: err.status,
          statusText: err.statusText,
          message: err.error?.message,
          error: err
        });
      }
    });
  }
}