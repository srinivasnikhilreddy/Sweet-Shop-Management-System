import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { SweetService } from '../sweet.service';

@Component({
  selector: 'app-add-sweet',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './add-sweet.component.html',
  styleUrls: ['./add-sweet.component.css']
})
export class AddSweetComponent
{
    name: string = '';
    price: number = 0;
    quantity: number = 0;
    category: string = 'DEFAULT';

    selectedImage?: File;

    private sweetService = inject(SweetService);
    private router = inject(Router);

    onImageSelected(event: Event): void
    {
        const input = event.target as HTMLInputElement;
        if(input.files && input.files.length > 0){
            this.selectedImage = input.files[0];
        }
    }

    save(): void
    {
        this.sweetService.addSweet(
            {
                name: this.name,
                category: this.category,
                price: this.price,
                quantity: this.quantity
            },
            this.selectedImage
        ).subscribe(() => {
            this.router.navigate(['/dashboard']);
        });
    }

    cancel(): void
    {
        this.router.navigate(['/dashboard']);
    }
}
