import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { TokenService } from 'src/app/core/services/token.service';
import { SessionService } from 'src/app/core/services/session.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {
  dropdownVisible = false;
  timeoutRef: any;
  sesionIniciada = false;

  constructor(
    private tokenService: TokenService,
    private sessionService: SessionService,
    private router: Router
  ) {}

  ngOnInit() {
    this.sesionIniciada = this.tokenService.isLogged();
  }

  mostrarMenu() {
    clearTimeout(this.timeoutRef);
    this.dropdownVisible = true;
  }

  ocultarMenu() {
    this.timeoutRef = setTimeout(() => {
      this.dropdownVisible = false;
    }, 150); // retardo para evitar cierre brusco
  }

  cerrarSesion() {
    this.sessionService.cerrarSesion();
    this.router.navigate(['/auth/login']);
  }
}
