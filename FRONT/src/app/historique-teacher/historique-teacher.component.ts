import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-historique-teacher',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './historique-teacher.component.html',
  styleUrls: ['./historique-teacher.component.css']
})
export class HistoriqueTeacherComponent {
  rattrapages: any[] = []; // Replace with actual rattrapage type
}