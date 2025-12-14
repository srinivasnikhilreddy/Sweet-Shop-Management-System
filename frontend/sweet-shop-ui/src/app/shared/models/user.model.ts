export interface User
{
    id?: number;
    username: string;
    name: string;
    phone?: string;
    role: 'ROLE_USER' | 'ROLE_ADMIN';
}
