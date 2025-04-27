import { Component, ElementRef, HostListener, OnInit } from '@angular/core';
import { AutenticacionService } from 'src/app/modules/auth/services/autenticacion.service';
import { ClientService } from 'src/app/modules/client/services/client.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {
  sesionIniciada: boolean = false;
  dropdownVisible: boolean = false;
  fotoPerfilUrl: string | null = null; // URL de la foto de perfil

  constructor(
    private autenticacionService: AutenticacionService,
    private cliententService: ClientService,
    private elementRef: ElementRef
  ) {}

  ngOnInit(): void {
    // Suscribirse al estado de la sesión
    this.autenticacionService.isSesionIniciada().subscribe((sesion) => {
      this.sesionIniciada = sesion;

      // Si la sesión está iniciada, cargar la foto de perfil
      if (this.sesionIniciada) {
        this.cargarFotoPerfil();
      }
    });
  }

  cargarFotoPerfil(): void {
    this.cliententService.obtenerPerfil().subscribe((perfil) => {
      this.fotoPerfilUrl = perfil.fotoPerfil || 'assets/images/default-profile.png';
    }, (error) => {
      console.error('Error al cargar la foto de perfil:', error);
      this.fotoPerfilUrl = 'assets/images/default-profile.png'; // Imagen predeterminada en caso de error
    });
  }

  cerrarSesion(): void {
    this.autenticacionService.cerrarSesion();
    this.sesionIniciada = false;
    this.fotoPerfilUrl = null; // Limpiar la foto de perfil al cerrar sesión
  }

  toggleDropdown(): void {
    this.dropdownVisible = !this.dropdownVisible;
  }

  @HostListener('document:click', ['$event'])
  onDocumentClick(event: MouseEvent): void {
    const clickedInside = this.elementRef.nativeElement.contains(event.target);
    if (!clickedInside) {
      this.dropdownVisible = false; // Cierra el menú si se hace clic fuera
    }
  }
}