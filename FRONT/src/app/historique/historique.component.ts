import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-historique',
  imports: [FormsModule,CommonModule],
  templateUrl: './historique.component.html',
  styleUrl: './historique.component.css'
})
export class HistoriqueComponent {
  classes = ['Classe A', 'Classe B', 'Classe C'];
  specs = ['Spec 1', 'Spec 2'];
  groups = ['Groupe 1', 'Groupe 2'];
  teachers = ['Prof X', 'Prof Y'];
  labels = ['Label 1', 'Label 2'];

  // Variables liées aux <select> via [(ngModel)]
  selectedClass = '';
  selectedSpec = '';
  selectedGroup = '';
  selectedTeacher = '';
  selectedLabel = '';

  // Liste des éléments épinglés
  pinnedItems = [
    { content: 'Absence de M. Dupont'},
    { content: 'Rattrapage - Salle 101'},
    { content: 'Absence de Mme Martin' },
    { content: 'Absence de M. Dupont' },
    { content: 'Rattrapage - Salle 101' },
    { content: 'Absence de Mme Martin'},
    { content: 'Absence de M. Dupont' },
    { content: 'Rattrapage - Salle 101'},
    { content: 'Absence de Mme Martin' },
  ];

  constructor() {}

}
