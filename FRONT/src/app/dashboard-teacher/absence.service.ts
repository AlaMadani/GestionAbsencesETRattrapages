import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface NewAbsenceRequest {
  dateDebut: string; // ISO date, e.g., "2025-05-03"
  dateFin: string;
  seanceDebut: string; // e.g., "S1"
  seanceFin: string;
  enseignantId: number;
}

@Injectable({ providedIn: 'root' })
export class AbsenceService {
  private baseUrl = '/api/enseignant';

  constructor(private http: HttpClient) {}

  createAbsence(request: NewAbsenceRequest): Observable<number> {
    return this.http.post<number>(`${this.baseUrl}/creerAbs`, request, {
      withCredentials: true
    });
  }
}