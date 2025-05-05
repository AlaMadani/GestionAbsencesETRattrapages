export interface RattrapageRequest {
  id: number;
  classe: string;
  specialite: string;
  groupe: string;
  date_db: string; // ISO format, e.g., "2025-05-06"
  date_fin: string; // ISO format, e.g., "2025-05-10"
  idens: number; // Enseignant ID
  prenom: string; // First name
  nom: string; // Last name
  grade: string; // Grade
  nbAbsences: number; // Number of absences
}

export interface AccepterRattrapageRequest {
  rattrapageId: number;
  dateAff: string; // e.g., "06/05/2025"
  seanceAff: string; // e.g., "S1"
  salleId: string;
  enseignantId: number;
}

export interface ProcessedRattrapage {
  id: number;
  classe: string;
  specialite: string;
  groupe: string;
  date_aff: string; // Assigned date, e.g., "2025-05-06"
  seanceAff: string; // Assigned seance, e.g., "S2"
  salleId: string;
  enseignantId: number;
  prenom: string; // First name
  nom: string; // Last name
  grade: string; // Grade
  date_db: string; // Original absence start date
  date_fin: string; // Original absence end date
  seancedb: string | null; // Original seance, nullable
  seancefin: string | null; // Final seance, nullable
  nbAbsences: number; // Number of absences
  acceptee: 'oui' | 'non'; // Status
  num_tel: string; // Phone number, e.g., "+21620000005"
}

export interface SeancesDispo {
  numSeance: string; // e.g., "S1"
  date: string; // e.g., "06/05/2025"
}

export interface SeancesDispoMap {
  [day: string]: {
    [seance: string]: string[]; // e.g., ["06/05/2025", "07/05/2025"]
  };
}