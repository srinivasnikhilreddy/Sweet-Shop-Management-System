export interface LoginRequest
{
    username: string;
    password: string;
}

export interface RegisterRequest
{
    username: string;
    password: string;
    name: string;
    phone: string;
    role: 'ROLE_USER' | 'ROLE_ADMIN';

    //Admin-only fields
    department?: string;
    designation?: string;
}

export interface AuthResponse
{
    accessToken: string;
    refreshToken: string;
    expiresIn: number;
    roles: string[];
}
