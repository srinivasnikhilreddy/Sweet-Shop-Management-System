import { CanActivateFn, Router } from '@angular/router';
import { inject } from '@angular/core';
import { AuthService } from '../../auth/auth.service';

export const adminGuard: CanActivateFn = () => {
    const authService = inject(AuthService);
    const router = inject(Router);

    const roles = authService.getUserRoles();

    if(roles.includes("ROLE_ADMIN")){
        return true;
    }

    return router.parseUrl('/dashboard');
};
