import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
@Component({
  selector: 'app-options',
  imports: [FormsModule,CommonModule],
  templateUrl: './options.component.html',
  styleUrl: './options.component.css'
})
export class OptionsComponent {
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
  constructor() {}

}
