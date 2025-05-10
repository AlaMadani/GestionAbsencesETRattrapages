import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-signup',
  standalone: true,
  imports: [ReactiveFormsModule, HttpClientModule, CommonModule],
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {
  signupForm!: FormGroup;
  successMessage: string | null = null;
  errorMessage: string | null = null;

  constructor(
    private fb: FormBuilder,
    private http: HttpClient,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.signupForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required],
      confirmPassword: ['', Validators.required],
      nom: ['', Validators.required],
      prenom: ['', Validators.required],
      userType: ['', Validators.required],
      grade: [''],
      numTel: ['']
    }, { validators: this.passwordMatchValidator });
  }

  passwordMatchValidator(form: FormGroup) {
    const password = form.get('password')?.value;
    const confirmPassword = form.get('confirmPassword')?.value;
    return password === confirmPassword ? null : { mismatch: true };
  }

  onSubmit(): void {
    if (this.signupForm.invalid) {
      this.signupForm.markAllAsTouched();
      return;
    }

    const formValue = this.signupForm.value;
    const signupRequest = {
      email: formValue.email,
      password: formValue.password,
      nom: formValue.nom,
      prenom: formValue.prenom,
      userType: formValue.userType,
      grade: formValue.userType === 'ENSEIGNANT' ? formValue.grade : null,
      numTel: formValue.userType === 'ENSEIGNANT' ? formValue.numTel : null
    };

    this.http.post('/api/auth/signup', signupRequest, { responseType: 'text' })
      .subscribe({
        next: (response) => {
          this.successMessage = response;
          this.errorMessage = null;
          setTimeout(() => {
            this.router.navigate(['/']);
          }, 2000);
        },
        error: (error) => {
          this.errorMessage = error.error || 'Error registering user';
          this.successMessage = null;
        }
      });
  }
}