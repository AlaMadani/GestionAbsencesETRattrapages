export interface EnseignantDTO {
    id: number;
    nom: string;
    prenom: string;
    email: string;
    motdepass: string; // Hashed password
    grade: string;
    num_tel: string;
    nbAbsences: number;
  }