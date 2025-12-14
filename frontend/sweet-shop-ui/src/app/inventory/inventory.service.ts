import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Sweet } from '../shared/models/sweet.model';

@Injectable({
  providedIn: 'root'
})
export class InventoryService
{
    private readonly apiUrl = 'http://localhost:9070/api/sweets';
    constructor(private http: HttpClient) {}

    // Purchase sweet (USER)
    purchaseSweet(id: number, quantity: number): Observable<Sweet>
    {
        return this.http.post<Sweet>(`${this.apiUrl}/${id}/purchase`, { quantity });
    }

    // Restock sweet (ADMIN)
    restockSweet(id: number, quantity: number): Observable<Sweet>
    {
        return this.http.post<Sweet>(`${this.apiUrl}/${id}/restock`, { quantity });
    }
}
