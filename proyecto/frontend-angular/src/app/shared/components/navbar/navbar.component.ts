import { Component, ElementRef, HostListener, OnInit } from '@angular/core';
import { AutenticacionService } from 'src/app/modules/auth/services/autenticacion.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {
  sesionIniciada: boolean = false;
  dropdownVisible: boolean = false;

  constructor(
    private autenticacionService: AutenticacionService,
    private elementRef: ElementRef
  ) {}

  ngOnInit(): void {
    // Suscribirse al estado de la sesión
    this.autenticacionService.isSesionIniciada().subscribe((sesion) => {
      this.sesionIniciada = sesion;
    });
  }

  cerrarSesion(): void {
    this.autenticacionService.cerrarSesion();
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