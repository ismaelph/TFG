import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, catchError, map, Observable, of, throwError } from 'rxjs';
import { environment } from 'src/environments/environment';
import { LoginRequest, LoginResponse, RegisterRequest, RegisterResponse } from '../interfaces/autenticacion';

@Injectable({
  providedIn: 'root'
})
export class AutenticacionService {

  // URLs de los endpoints de autenticación
  private URL_LOGIN = `${environment.urlBackendSpring}/login`;
  private URL_REGISTER = `${environment.urlBackendSpring}/signup`;

  // Se inicializa el token JWT en null, ya que por defecto la sesión no está iniciada
  private jwtToken: string | null = null;

  // Inicializamos el id del usuario en null
  private userId: number | null = null;

  // Se declara un BehaviorSubject de tipo string[] para almacenar los roles del usuario
  // Esto permite que los componentes que se suscriban a los observables 
  // derivados de éste reciban los roles del usuario actualizados
  private rolesSubject = new BehaviorSubject<string[]>([]);

  // Roles se define como un observable de tipo rolesSubject 
  // al que se pueden suscribir los componentes que lo necesiten
  private roles = this.rolesSubject.asObservable();

  // Se declara un BehaviourSubject de tipo boolean para 
  // almacenar el estado de la sesión
  private sesionIniciadaSubject = new BehaviorSubject<boolean>(false);

  // sesionIniciada se define como un observable de tipo sesionIniciadaSubject 
  // al que se pueden suscribir los componentes que lo necesiten
  private sesionIniciada = this.sesionIniciadaSubject.asObservable();


  constructor(
    private httpClient: HttpClient
  ) {
    // Al inicializar el servicio, se comprueba si hay un token 
    // almacenado en el localStorage del navegador
    this.jwtToken = localStorage.getItem('token');

    // Si hay un token almacenado, se notifica a los componentes 
    // suscritos al observable sesionIniciadaSubject
    this.sesionIniciadaSubject.next(this.jwtToken !== null);

    // Se comprueba si hay roles almacenados en el localStorage del navegador
    const storedRoles = localStorage.getItem('roles');

    // Si hay roles almacenados, se notifica a los componentes 
    // suscritos al observable rolesSubject
    if (storedRoles) {
      this.rolesSubject.next(JSON.parse(storedRoles));
    }
  }

  /**
   * Permite iniciar sesión
   * 
   * @param login 
   * @param pass 
   * @returns Observable<boolean>
   *    
   */
  iniciarSesion(login: string, pass: string): Observable<boolean> {

    const credenciales: LoginRequest = { email: login, password: pass };

    // Se borran el token y los roles almacenados previamente en el localStorage
    localStorage.removeItem('token');
    localStorage.removeItem('roles');
    localStorage.removeItem('userId');

    return this.httpClient.post<LoginResponse>(this.URL_LOGIN, credenciales).pipe(
      map((response) => {
        this.jwtToken = response.token;

        // Se almacena el nuevo token en el localStorage
        localStorage.setItem('token', response.token);

        // Se almacenan los roles en el localStorage y se notifica 
        // a los componentes suscritos al observable rolesSubject
        const roles = response.roles ?? [];
        this.rolesSubject.next(roles);
        localStorage.setItem('roles', JSON.stringify(roles));

        // Se almacena el id del usuario en el localStorage
        this.userId = response.user.id;
        localStorage.setItem('userId', JSON.stringify(this.userId));

        // Se notifica a los componentes suscritos al observable 
        // sesionIniciadaSubject de que hay una sesión iniciada
        this.sesionIniciadaSubject.next(true);


        return true;
      })
    );
  }

  /**
   * 
   * @returns number | null
   * 
   */
  getUserId(): number | null {
    // Intentamos obtener el id desde el localStorage
    const storedUserId = localStorage.getItem('userId');
    if (storedUserId) {
      return JSON.parse(storedUserId);
    }
    // Si no está en el localStorage, devolvemos el valor que está en la propiedad
    return this.userId;
  }

  /**
   * Permite registrar un usuario
   * 
   * @param name 
   * @param username 
   * @param email 
   * @param password 
   * @returns Observable<boolean>
   */
  registrarUsuario(name: string, username: string, email: string, password: string): Observable<boolean> {
    const credenciales: RegisterRequest = { name, username, email, password };
    return this.httpClient.post<RegisterResponse>(this.URL_REGISTER, credenciales).pipe(
      map((response) => {
        this.jwtToken = response.accessToken;
        return true;
      })
    );
  }

  /**
   * Comprueba si la sesión está iniciada
   * 
   * @returns Observable<boolean>
   */
  isSesionIniciada(): Observable<boolean> {
    return this.sesionIniciada;
  }

  /**
   * Devuelve el token actual
   * 
   * @returns string | null
   */
  getJwtToken(): string | null {
    return this.jwtToken;
  }

  /**
   * Devuelve los roles actuales
   * 
   * @returns Observable<string[]>
   */
  getRoles(): Observable<string[]> {
    return this.roles;
  }

  /**
   * Cierra la sesión
   */
  cerrarSesion(): void {

    // Se borran el token, los roles almacenados y el id
    // en el localStorage y en las variables del servicio
    this.jwtToken = null;
    localStorage.removeItem('token');
    localStorage.removeItem('roles');
    this.userId = null;
    localStorage.removeItem('userId');

    // Se notifica a los componentes suscritos 
    // al observable rolesSubject de que no hay roles
    this.rolesSubject.next([]);

    // Se notifica a los componentes suscritos 
    // al observable sesionIniciadaSubject de que la sesión ha sido cerrada
    this.sesionIniciadaSubject.next(false);
  }
}

