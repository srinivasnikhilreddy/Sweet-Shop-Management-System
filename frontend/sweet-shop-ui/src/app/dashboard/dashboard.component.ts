import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { SweetService } from '../sweets/sweet.service';
import { AuthService } from '../auth/auth.service';
import { Sweet } from '../shared/models/sweet.model';
import { InventoryService } from '../inventory/inventory.service';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit
{

    sweets: Sweet[] = [];
    filteredSweets: Sweet[] = [];
    searchText: string = '';
    isAdmin = false;

    private sweetService = inject(SweetService);
    private authService = inject(AuthService);
    private inventoryService = inject(InventoryService);
    private router = inject(Router);

    ngOnInit(): void
    {
        this.isAdmin = this.authService.getUserRoles()?.includes('ROLE_ADMIN') ?? false;
        this.loadSweets();
    }

    loadSweets(): void
    {
        this.sweetService.getAll().subscribe(res => {
            this.sweets = res;
            this.filteredSweets = res;
        });
    }

    filterSweets(): void
    {
        const text = this.searchText.toLowerCase().trim();
        this.filteredSweets = this.sweets.filter(s =>
            s.name.toLowerCase().includes(text)
        );
    }

    logout(): void
    {
        this.authService.logout();
        this.router.navigate(['/login']);
    }

    addSweet(): void
    {
        this.router.navigate(['/sweets/add']);
    }

    editSweet(id: number): void
    {
        this.router.navigate(['/sweets/edit', id]);
    }

    deleteSweet(id: number): void
    {
        if(confirm('Delete this sweet?')){
            this.sweetService.delete(id).subscribe(() => {
                this.sweets = this.sweets.filter(s => s.id !== id);
                this.filterSweets();
            });
        }
    }

    purchase(sweet: Sweet): void
    {
        this.inventoryService.purchaseSweet(sweet.id, 1).subscribe(() => {
            sweet.quantity--;
        });
    }

    restock(id: number): void
    {
        this.inventoryService.restockSweet(id, 10).subscribe(() => this.loadSweets());
    }
}
