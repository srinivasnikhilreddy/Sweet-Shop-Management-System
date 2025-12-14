import { Routes } from '@angular/router';

import { authGuard } from './core/guards/auth.guard';
import { adminGuard } from './core/guards/admin.guard';

import { LoginComponent } from './auth/login/login.component';
import { RegisterComponent } from './auth/register/register.component';
import { DashboardComponent } from './dashboard/dashboard.component';

import { AddSweetComponent } from './sweets/add-sweet/add-sweet.component';
import { EditSweetComponent } from './sweets/edit-sweet/edit-sweet.component';

export const routes: Routes = [
      /*path: '' -> This is the default empty path, i.e., when the app first loads at the root URL (http://localhost:4200/).
        redirectTo: 'login' -> Automatically redirects the user to the /login page.
        pathMatch: 'full' -> This tells Angular to match the entire URL ('') for this route. */
      { path: '', redirectTo: 'login', pathMatch: 'full' },

      /*path: 'login' -> This is the URL path: /login.
        component: LoginComponent -> Angular will render the LoginComponent in the <router-outlet> when the user navigates to /login.*/
      { path: 'login', component: LoginComponent },
      { path: 'register', component: RegisterComponent },

      { path: 'dashboard', component: DashboardComponent, canActivate: [authGuard] },

      { path: 'sweets/add', component: AddSweetComponent, canActivate: [authGuard, adminGuard] },

      { path: 'sweets/edit/:id', component: EditSweetComponent, canActivate: [authGuard, adminGuard] }
];
