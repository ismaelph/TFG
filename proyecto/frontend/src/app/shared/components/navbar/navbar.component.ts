import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { SessionService } from 'src/app/core/services/session.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent {
  dropdownVisible = false;
  sesionIniciada = false;

  constructor(private sessionService: SessionService, private router: Router) {
    this.sessionService.isSesionIniciada().subscribe(state => {
      this.sesionIniciada = state;
    });
  }

  toggleDropdown(): void {
    this.dropdownVisible = !this.dropdownVisible;
  }

  cerrarSesion(): void {
    this.sessionService.cerrarSesion();
    this.router.navigate(['/auth/login']);
  }
}
