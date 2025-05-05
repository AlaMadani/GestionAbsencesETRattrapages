import { Routes } from '@angular/router';
import { MainComponent } from './main/main.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { LoginComponent } from './login/login.component';
import { AccueilEtdComponent } from './accueil-etd/accueil-etd.component';
import { AbsRattEtdComponent } from './abs-ratt-etd/abs-ratt-etd.component';
import { EmploisTempsComponent } from './emplois-temps/emplois-temps.component';
import { HistoriqueComponent } from './historique/historique.component';
import { OptionsComponent } from './options/options.component';
import { SignupComponent } from './signup/signup.component';
import { DashboardTeacherComponent } from './dashboard-teacher/dashboard-teacher.component';
import { AccueilTeacherComponent } from './accueil-teacher/accueil-teacher.component';
import { AbsenceRequestFormComponent } from './absence-request-form/absence-request-form.component';
import { CatchUpRequestFormComponent } from './catch-up-request-form/catch-up-request-form.component';
import { RequestListComponent } from './request-list/request-list.component';
import { HistoriqueTeacherComponent } from './historique-teacher/historique-teacher.component';
import { EmploisTeacherComponent } from './emplois-teacher/emplois-teacher.component';
import { ResponsableDashbordComponent } from './Responsable/responsable-dashbord/responsable-dashbord.component';

import { RoleGuard } from './guards/role.guard';
import { PendingAbsencesComponent } from './Responsable/pending-absences/pending-absences.component';
import { PendingRattrapagesComponent } from './Responsable/pending-rattrapages/pending-rattrapages.component';
import { ProcessedAbsencesComponent } from './Responsable/processed-absences/processed-absences.component';
import { ProcessedRattrapagesComponent } from './Responsable/processed-rattrapages/processed-rattrapages.component';
import { EnseignantsListComponent } from './Responsable/enseignants-list/enseignants-list.component';
import { EtudiantsListComponent } from './Responsable/etudiants-list/etudiants-list.component';
import { AcceptedRattrapagesComponent } from './accepted-rattrapages/accepted-rattrapages.component';

export const routes: Routes = [
  { path: 'home', component: MainComponent },
  { path: '', redirectTo: 'student_login', pathMatch: 'full' },
  { path: 'student_login', component: LoginComponent },
  { path: 'student_signup', component: SignupComponent },

  {
    path: 'student_dashboard',
    component: DashboardComponent,
    children: [
      { path: 'Accueil', component: AccueilEtdComponent },
      { path: 'Rattrapages', component: AcceptedRattrapagesComponent },
      { path: 'abs&ratts', component: AbsRattEtdComponent },
      { path: 'Emplois', component: EmploisTempsComponent },
      { path: 'Historique', component: HistoriqueComponent },
      { path: 'Options', component: OptionsComponent },
      { path: '', component: AccueilEtdComponent }
    ],
    canActivate: [RoleGuard],
    data: { role: 'ETUDIANT' }
  },
  {
    path: 'teacher_dashboard',
    component: DashboardTeacherComponent,
    children: [
      { path: 'Accueil_teacher', component: AccueilTeacherComponent },
      { path: 'abs-demande', component: AbsenceRequestFormComponent },
      { path: 'ratt-demande', component: CatchUpRequestFormComponent },
      { path: 'teacher-demande', component: RequestListComponent },
      { path: 'teacher-historique', component: HistoriqueTeacherComponent },
      { path: 'teacher-emploi', component: EmploisTeacherComponent },
      { path: '', component: AccueilTeacherComponent }
    ],
    canActivate: [RoleGuard],
    data: { role: 'ENSEIGNANT' }
  },
  {
    path: 'res_dashbord',
    component: ResponsableDashbordComponent,
    canActivate: [RoleGuard],
    data: { role: 'ADMIN' },
    children: [
      { path: 'list-abs', component: PendingAbsencesComponent },
      { path: 'pending_rattrapages', component: PendingRattrapagesComponent },
      { path: 'processed-absences', component: ProcessedAbsencesComponent },
      { path: 'processed-rattrapages', component: ProcessedRattrapagesComponent },
      { path: 'enseignants', component: EnseignantsListComponent },
      { path: 'etudiants', component: EtudiantsListComponent },
      { path: '', redirectTo: 'list-abs', pathMatch: 'full' }
    ]
  },
  { path: '**', redirectTo: 'MainComponent' }
];