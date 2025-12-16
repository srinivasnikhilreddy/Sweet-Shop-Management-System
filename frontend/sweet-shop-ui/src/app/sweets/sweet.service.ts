import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Sweet } from '../shared/models/sweet.model';

@Injectable({
  providedIn: 'root'
})
export class SweetService
{
    private readonly apiUrl = 'http://localhost:8080/api/sweets';
    constructor(private http: HttpClient) {}

    getAll(): Observable<Sweet[]>
    {
        return this.http.get<Sweet[]>(`${this.apiUrl}/getAll`);
    }

    addSweet(payload: {
        name: string;
        category: string;
        price: number;
        quantity: number;
      }, image?: File): Observable<Sweet>
    {
        const formData = new FormData();
        formData.append('sweet', new Blob([JSON.stringify(payload)], { type: 'application/json' }));

        if(image){
          formData.append('image', image);
        }

        return this.http.post<Sweet>(`${this.apiUrl}/add`, formData);
    }

    updateSweet(id: number, payload: {
        name: string;
        category: string;
        price: number;
        quantity: number;
      }): Observable<Sweet>
    {
        return this.http.put<Sweet>(`${this.apiUrl}/update/${id}`, payload);
    }

    delete(id: number): Observable<void>
    {
        return this.http.delete<void>(`${this.apiUrl}/delete/${id}`);
    }

    search(filters: {
        name?: string;
        category?: string;
        minPrice?: number;
        maxPrice?: number;
      }): Observable<Sweet[]>
    {
        let params = new HttpParams();
        Object.entries(filters).forEach(([key, value]) => {
            if(value !== undefined && value !== null && value !== ''){
                params = params.set(key, value);
            }
        });
        return this.http.get<Sweet[]>(`${this.apiUrl}/search`, { params });
    }
}
