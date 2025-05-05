import { CanActivateFn, Router } from '@angular/router';
import { inject } from '@angular/core';
import { AuthService } from '../auth.service';

export const RoleGuard: CanActivateFn = (route, state) => {
  const auth = inject(AuthService);
  const router = inject(Router);
  const expected = route.data['role'];
  if (auth.role === expected) {
    return true;
  }
  router.navigate(['/student_login']);
  return false;
};
