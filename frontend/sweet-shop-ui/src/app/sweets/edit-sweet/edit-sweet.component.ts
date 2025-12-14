import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { SweetService } from '../sweet.service';
import { Sweet } from '../../shared/models/sweet.model';

@Component({
  selector: 'app-edit-sweet',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './edit-sweet.component.html',
  styleUrls: ['./edit-sweet.component.css']
})
export class EditSweetComponent implements OnInit
{
    sweet!: Sweet;

    private route = inject(ActivatedRoute);
    private sweetService = inject(SweetService);
    private router = inject(Router);

    ngOnInit(): void
    {
        const id = Number(this.route.snapshot.paramMap.get('id'));
        this.sweetService.getAll().subscribe((list: Sweet[]) => {
            const found = list.find(s => s.id === id);
            if(found){
              this.sweet = { ...found };
            }
        });
    }

    update(): void
    {
        this.sweetService.updateSweet(this.sweet.id, {
            name: this.sweet.name,
            category: this.sweet.category,
            price: this.sweet.price,
            quantity: this.sweet.quantity
        }).subscribe(() => {
            this.router.navigate(['/dashboard']);
        });
    }

    cancel(): void
    {
        this.router.navigate(['/dashboard']);
    }
}
