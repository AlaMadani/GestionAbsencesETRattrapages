export interface Absence {
  id: number;
  date_db: string;
  date_fin: string;
  seancedb: string;
  seancefin: string;
  acceptee: 'oui' | 'non' | null;
  idens: number;
  nom: string;
  prenom: string;
  grade: string;
  num_tel: string;
  nbAbsences: number;
}

export interface ProcessedAbsence extends Absence {
  acceptee: 'oui' | 'non';
}