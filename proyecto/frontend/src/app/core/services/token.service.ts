import { Injectable } from '@angular/core';
import { TOKEN_KEY } from 'src/app/core/constants/constants';

@Injectable({
  providedIn: 'root'
})
export class TokenService {
  saveToken(token: string): void {
    localStorage.setItem(TOKEN_KEY, token);
  }

  getToken(): string | null {
    return localStorage.getItem(TOKEN_KEY);
  }

  clearToken(): void {
    localStorage.removeItem(TOKEN_KEY);
  }

  isLogged(): boolean {
    return !!this.getToken();
  }
}
