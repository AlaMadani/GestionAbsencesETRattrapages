import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface NewRattrapageRequest {
  classe: string;
  specialite: string;
  groupe: string;
  dateDebut: string;
  dateFin: string;
  enseignantId: number;
  matiere: string; // Added matiere
}

@Injectable({
  providedIn: 'root'
})
export class RattrapageService {
  private baseUrl = '/api/enseignant';

  constructor(private http: HttpClient) {}

  submitRattrapageRequest(request: NewRattrapageRequest): Observable<number> {
    return this.http.post<number>(`${this.baseUrl}/creer`, request, { withCredentials: true });
  }
}