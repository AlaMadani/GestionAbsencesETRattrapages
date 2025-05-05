import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../auth.service';
import { Router } from '@angular/router';
import { ReactiveFormsModule } from '@angular/forms';  
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-login',
  standalone: true,    
  imports: [                         // <–– bring modules in
    CommonModule,
    ReactiveFormsModule,
    RouterModule
  ],
  templateUrl: 'login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  form!: FormGroup;       // declare without initializing
  error: string|null = null;

  constructor(
    private fb: FormBuilder,
    private auth: AuthService,
    private router: Router
  ) {}

  ngOnInit() {
    // now fb is initialized by Angular, safe to use
    this.form = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required]
    });
  }
  
  submit() {
    if (this.form.invalid) { return; }

    const { email, password } = this.form.value;
    this.auth.login(email, password).subscribe({
      next: () => {
        const role = this.auth.role;
        if (role === 'ETUDIANT') {
          this.router.navigate(['/student_dashboard']);
        } else if (role === 'ENSEIGNANT') {
          this.router.navigate(['/teacher_dashboard']);
        } else if (role === 'ADMIN') {
          this.router.navigate(['/res_dashbord']);
        } else {
          this.router.navigate(['/']);
        }
      },
      error: () => this.error = 'Login failed'
    });
  }
}
