import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormGroup, FormControl, Validators } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { AuthService } from '../auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, RouterModule, ReactiveFormsModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent
{
    reactiveForm = new FormGroup({
        username: new FormControl('', [Validators.required, Validators.email]),
        password: new FormControl('', [Validators.required, Validators.minLength(5)])
    });

    submitting = false;
    errorMessage: string | null = null;

    private authService = inject(AuthService);
    private router = inject(Router);

    login(): void
    {
        this.errorMessage = null;

        if(this.reactiveForm.invalid){
            this.reactiveForm.markAllAsTouched();
            return;
        }

        const username = this.reactiveForm.value.username!;
        const password = this.reactiveForm.value.password!;

        this.submitting = true;

        this.authService.login({ username, password }).subscribe({
            next: () => {
                this.submitting = false;
                this.router.navigate(['/dashboard']);
            },
            error: () => {
                this.submitting = false;
                this.errorMessage = 'Invalid username or password';
            }
        });
    }
}
