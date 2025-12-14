import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormGroup, FormControl, Validators } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { AuthService } from '../auth.service';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, RouterModule, ReactiveFormsModule],
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent
{
    reactiveForm = new FormGroup({
        username: new FormControl('', [Validators.required, Validators.email]),
        password: new FormControl('', [Validators.required, Validators.minLength(5)]),
        name: new FormControl('', [Validators.required, Validators.minLength(2)]),
        phone: new FormControl('', [
            Validators.required,
            Validators.pattern('^[0-9]{10}$')
        ]),
        role: new FormControl<'ROLE_USER' | 'ROLE_ADMIN'>('ROLE_USER', Validators.required),

        //Admin-only
        department: new FormControl(''),
        designation: new FormControl('')
    });

    submitting = false;
    errorMessage: string | null = null;

    selectedFile?: File;

    private authService = inject(AuthService);
    private router = inject(Router);

    register(): void
    {
        this.errorMessage = null;
        if(this.reactiveForm.invalid){
            this.reactiveForm.markAllAsTouched();
            return;
        }

        const formValue = this.reactiveForm.value;

        const payload = {
            username: formValue.username!,
            password: formValue.password!,
            name: formValue.name!,
            phone: formValue.phone!,
            role: formValue.role!,
            department: formValue.role === 'ROLE_ADMIN' ? formValue.department ?? undefined : undefined,
            designation: formValue.role === 'ROLE_ADMIN' ? formValue.designation ?? undefined : undefined
        };

        this.submitting = true;

        this.authService.register(payload, this.selectedFile).subscribe({
            next: () => {
                this.submitting = false;
                this.router.navigate(['/login']);
            },
            error: () => {
                this.submitting = false;
                this.errorMessage = 'Registration failed';
            }
        });
    }

    onFileSelected(event: Event): void
    {
        const input = event.target as HTMLInputElement;
        if(input.files && input.files.length > 0){
          this.selectedFile = input.files[0];
        }
    }

    isAdminSelected(): boolean
    {
        return this.reactiveForm.value.role === 'ROLE_ADMIN';
    }
}
