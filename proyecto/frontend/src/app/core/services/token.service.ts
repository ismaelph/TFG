import { Injectable } from '@angular/core';
import { TOKEN_KEY } from 'src/app/core/constants/constants';

const USER_KEY = 'invecta-user';

@Injectable({
  providedIn: 'root'
})
export class TokenService {

  saveToken(token: string): void {
    localStorage.setItem(TOKEN_KEY, token);
  }

  getToken(): string | null {
    const rawUser = localStorage.getItem(USER_KEY);

    if (!rawUser) {
      console.warn('[TokenService] No se encontró ningún usuario en localStorage');
      return null;
    }

    try {
      const parsedUser = JSON.parse(rawUser);

      const token = parsedUser.token || parsedUser.accessToken || null;

      if (!token) {
        console.warn('[TokenService] No se encontró token en el usuario guardado');
      } else {
        console.log('[TokenService] Token encontrado:', token);
      }

      return token;
    } catch (e) {
      console.error('[TokenService] Error al parsear el usuario desde localStorage', e);
      return null;
    }
  }



  clearToken(): void {
    localStorage.removeItem(TOKEN_KEY);
  }

  isLogged(): boolean {
    return !!this.getToken();
  }

  saveUser(user: any): void {
    localStorage.setItem(USER_KEY, JSON.stringify(user));
  }

  getUser(): any {
    const user = localStorage.getItem(USER_KEY);
    return user ? JSON.parse(user) : null;
  }

  hasRole(rol: string): boolean {
    const user = this.getUser();
    return user?.roles?.includes(rol);
  }

  clearUser(): void {
    localStorage.removeItem(USER_KEY);
  }

  clearAll(): void {
    this.clearToken();
    this.clearUser();
  }

  getUserId(): number | null {
    const user = this.getUser();
    return user && user.id ? user.id : null;
  }

}
