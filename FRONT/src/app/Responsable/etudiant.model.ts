export interface EtudiantDTO {
    id: number;
    nom: string;
    prenom: string;
    email: string;
    motdepass: string; // Hashed password
    classe: string;
    specialite: string;
    groupe: string;
  }