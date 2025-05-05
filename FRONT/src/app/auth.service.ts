import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { tap, switchMap } from 'rxjs/operators';
import { BehaviorSubject, of } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private _role$ = new BehaviorSubject<string | null>(null);
  private _userId$ = new BehaviorSubject<number | null>(null); // Store user ID
  readonly role$ = this._role$.asObservable();
  readonly userId$ = this._userId$.asObservable(); // Expose user ID

  constructor(private http: HttpClient) {}

  login(email: string, password: string) {
    const body = new HttpParams()
      .set('username', email)
      .set('password', password);
  
    return this.http
      .post(
        '/api/auth/login',
        body.toString(),
        {
          headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
          },
          withCredentials: true,
          observe: 'response'
        }
      )
      .pipe(
        switchMap(() => this.fetchProfile())
      );
  }
  
  fetchProfile() {
    return this.http.get<{ roles: string[], id: number }>('/api/auth/me', {
      withCredentials: true
    }).pipe(
      tap(profile => {
        const role = profile.roles[0]?.replace('ROLE_', '') || null;
        this._role$.next(role);
        this._userId$.next(profile.id); // Store enseignantId
      })
    );
  }
  
  logout() {
    return this.http.post('/api/auth/logout', {}, {
      withCredentials: true
    }).pipe(
      tap(() => {
        this._role$.next(null);
        this._userId$.next(null); // Clear user ID
      })
    );
  }

  get role(): string | null {
    return this._role$.value;
  }

  get userId(): number | null {
    return this._userId$.value;
  }
}