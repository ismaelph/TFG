import { Component, OnInit, OnDestroy } from '@angular/core';
import { Router } from '@angular/router';
import { TokenService } from 'src/app/core/services/token.service';
import { SessionService } from 'src/app/core/services/session.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit, OnDestroy {
  dropdownVisible = false;
  timeoutRef: any;
  sesionIniciada = false;

  private sessionSub!: Subscription;

  constructor(
    private tokenService: TokenService,
    private sessionService: SessionService,
    private router: Router
  ) {}

  ngOnInit() {
    this.sesionIniciada = this.tokenService.isLogged();

    this.sessionSub = this.sessionService.isSesionIniciada().subscribe((activa) => {
      this.sesionIniciada = activa;
    });
  }

  ngOnDestroy() {
    if (this.sessionSub) {
      this.sessionSub.unsubscribe();
    }
  }

  mostrarMenu() {
    clearTimeout(this.timeoutRef);
    this.dropdownVisible = true;
  }

  ocultarMenu() {
    this.timeoutRef = setTimeout(() => {
      this.dropdownVisible = false;
    }, 150); 
  }

  cerrarSesion() {
    this.sessionService.cerrarSesion();
    this.router.navigate(['/auth/login']);
  }
}
