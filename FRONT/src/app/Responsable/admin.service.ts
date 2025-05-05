import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { Absence, ProcessedAbsence } from './absence.model';
import { AccepterRattrapageRequest, RattrapageRequest, SeancesDispo, SeancesDispoMap, ProcessedRattrapage } from './rattrapage.model';
import { EnseignantDTO } from './enseignant.model';
import { EtudiantDTO } from './etudiant.model';

@Injectable({ providedIn: 'root' })
export class AdminService {
  private baseUrl = '/api/admin';

  constructor(private http: HttpClient) {}

  /** Fetch all pending absences */
  getPendingAbsences(): Observable<Absence[]> {
    return this.http.get<Absence[]>(`${this.baseUrl}/pending_absences`, {
      withCredentials: true
    });
  }

  /** Fetch all processed absences */
  getProcessedAbsences(): Observable<ProcessedAbsence[]> {
    return this.http.get<ProcessedAbsence[]>(`${this.baseUrl}/processed_absences`, {
      withCredentials: true
    });
  }

  /** Fetch all processed rattrapages */
  getProcessedRattrapages(): Observable<ProcessedRattrapage[]> {
    return this.http.get<ProcessedRattrapage[]>(`${this.baseUrl}/processed_rattrapages`, {
      withCredentials: true
    }).pipe(
      map(rattrapages =>
        rattrapages.map(ratt => ({
          ...ratt,
          dateAff: ratt.date_aff // Map date_aff to dateAff for UI
        }))
      )
    );
  }


  /** Fetch all enseignants */
  getAllEnseignants(): Observable<EnseignantDTO[]> {
    return this.http.get<EnseignantDTO[]>(`${this.baseUrl}/enseignants`, {
      withCredentials: true
    });
  }


  /** Fetch all étudiants */
  getAllEtudiants(): Observable<EtudiantDTO[]> {
    return this.http.get<EtudiantDTO[]>(`${this.baseUrl}/students`, {
      withCredentials: true
    });
  }

  /** Approve or reject a given absence request */
  respondToAbsence(id: number, accept: boolean): Observable<void> {
    const acceptee = accept ? 'oui' : 'non';
    const params = new HttpParams()
      .set('absenceId', id.toString())
      .set('acceptee', acceptee);
    return this.http.post<void>(
      `${this.baseUrl}/absences/respond`,
      null,
      { params, withCredentials: true }
    );
  }

  /** 1st dropdown: fetch available seances */
  getSeancesDispo(
    classe: string,
    specialite: string,
    groupe: string,
    start: string, // format "dd/MM/yyyy"
    end: string,
    idens: number
  ): Observable<SeancesDispoMap> {
    const params = new HttpParams()
      .set('classe', classe)
      .set('specialite', specialite)
      .set('groupe', groupe)
      .set('start', start)
      .set('end', end)
      .set('idens', idens.toString());

    return this.http.get<SeancesDispoMap>(
      '/api/admin/SeancesDispoST_ET',
      { params, withCredentials: true }
    );
  }

  /** 2nd dropdown: fetch available salles */
  getSallesDispo(
    dateS: string, // "dd/MM/yyyy"
    day: string, // e.g. "MONDAY"
    numS: string // e.g., "S1"
  ): Observable<string[]> {
    const params = new HttpParams()
      .set('dateS', dateS)
      .set('day', day)
      .set('numS', numS);
    return this.http.get<string[]>(
      '/api/admin/SallesDispo',
      { params, withCredentials: true }
    );
  }

  /** Final acceptance */
  accepterRattrapage(req: AccepterRattrapageRequest): Observable<void> {
    return this.http.post<void>(
      '/api/admin/accepter',
      req,
      { withCredentials: true }
    );
  }

  /** Reject a rattrapage */
  rejeterRattrapage(rattrapageId: number): Observable<void> {
    const params = new HttpParams().set('rattrapageId', rattrapageId.toString());
    return this.http.post<void>(
      '/api/admin/rejeter',
      null,
      { params, withCredentials: true }
    );
  }

  /** Fetch pending rattrapages */
  getPendingRattrapages(): Observable<RattrapageRequest[]> {
    return this.http.get<RattrapageRequest[]>(
      '/api/admin/pending_rattrapages',
      { withCredentials: true }
    );
  }
}