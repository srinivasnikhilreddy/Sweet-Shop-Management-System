import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { LoginRequest, RegisterRequest, AuthResponse } from '../shared/models/auth.model';

@Injectable({
  providedIn: 'root'
})
export class AuthService
{
    private readonly apiUrl = 'http://localhost:9070/api/auth';

    private readonly ACCESS_TOKEN = 'accessToken';
    private readonly REFRESH_TOKEN = 'refreshToken';
    private readonly ROLE_KEY = 'userRole';

    constructor(private http: HttpClient) {}

    login(request: LoginRequest): Observable<AuthResponse>
    {
        return this.http.post<AuthResponse>(`${this.apiUrl}/login`, request).pipe(
            tap(res => {
                localStorage.setItem(this.ACCESS_TOKEN, res.accessToken);
                if(res.refreshToken){
                    localStorage.setItem(this.REFRESH_TOKEN, res.refreshToken);
                }
                if(res.roles){
                    localStorage.setItem('roles', JSON.stringify(res.roles));
                }
            })
        );
    }

    register(request: RegisterRequest, file?: File)
    {
        const formData = new FormData();
        formData.append(
          'user',
          new Blob([JSON.stringify(request)], { type: 'application/json' })
        );
        if(file){
          formData.append('file', file);
        }
        return this.http.post(`${this.apiUrl}/register`, formData);
    }

    logout(): void
    {
        localStorage.clear();
    }

    getAccessToken(): string | null
    {
        return localStorage.getItem(this.ACCESS_TOKEN);
    }

    hasValidAccessToken(): boolean
    {
        return !!this.getAccessToken();
    }

    getUserRoles(): string[]
    {
        const roles = localStorage.getItem('roles');
        return roles ? JSON.parse(roles) : [];
    }
}
