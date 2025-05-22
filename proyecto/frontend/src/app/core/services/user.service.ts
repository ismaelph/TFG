import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private apiUrl = `${environment.apiUrl}/usuarios`;

  constructor(private http: HttpClient) {}

  abandonarEmpresa(userId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${userId}/empresa`);
  }
}
