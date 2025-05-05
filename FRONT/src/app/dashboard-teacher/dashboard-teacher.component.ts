import { Component } from '@angular/core';
import { SidebarTeacherComponent } from "../sidebar-teacher/sidebar-teacher.component";
import { RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-dashboard-teacher',
  imports: [SidebarTeacherComponent,RouterOutlet],
  templateUrl: './dashboard-teacher.component.html',
  styleUrl: './dashboard-teacher.component.css'
})
export class DashboardTeacherComponent {

}
