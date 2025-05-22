import { Component, OnInit } from '@angular/core';
import { Empresa } from 'src/app/core/interfaces/empresa';
import { EmpresaService } from '../services/empresa.service';
import { TokenService } from '../../../core/services/token.service';
import Swal from 'sweetalert2';
import { Router } from '@angular/router';

@Component({
  selector: 'app-empresa-join',
  templateUrl: './empresa-join.component.html',
  styleUrls: ['./empresa-join.component.css']
})
export class EmpresaJoinComponent implements OnInit {
  empresas: Empresa[] = [];
  claves: { [empresaId: number]: string } = {};
  mostrandoClave: number | null = null;

  constructor(
    private empresaService: EmpresaService,
    private tokenService: TokenService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.empresaService.obtenerTodas().subscribe({
      next: (res) => this.empresas = res,
      error: () => Swal.fire('Error', 'No se pudieron cargar las empresas.', 'error')
    });
  }

  mostrarInput(empresaId: number): void {
    this.mostrandoClave = empresaId;
  }

  unirse(empresaId: number): void {
  const clave = this.claves[empresaId]?.trim();
  if (!clave) {
    console.error('Campo vacío:', clave);
    Swal.fire('Campo vacío', 'Debes ingresar la clave de acceso.', 'warning');
    return;
  }

  const empresa = this.empresas.find(e => e.id === empresaId);
  if (!empresa) return;

  if (empresa.claveAcceso !== clave) {
    console.error('Clave incorrecta:', clave);
    Swal.fire('Clave incorrecta', 'La clave no coincide con la empresa seleccionada.', 'error');
    return;
  }

  const userId = this.tokenService.getUserId();
  if (userId == null) {
    console.error('No se pudo obtener el ID del usuario.');
    Swal.fire('Error', 'No se pudo obtener el ID del usuario.', 'error');
    return;
  }

  this.empresaService.unirseAEmpresa(userId, empresaId).subscribe({
    next: () => {
      Swal.fire({
        icon: 'success',
        title: '¡Te has unido a la empresa!',
        text: `Ahora perteneces a ${empresa.nombre}. Tu sesión se cerrará para aplicar los cambios.`,
        confirmButtonText: 'Aceptar',
        allowOutsideClick: false
      }).then(() => {
        console.log('Unido a la empresa:', empresaId);
        this.tokenService.clearAll(); // cerrar sesión y token
        window.location.href = '/login'; // redirigir a login
      });

      this.mostrandoClave = null;
      this.claves[empresaId] = '';
    },
    error: (err) => {
      console.error('ERROR AL UNIRSE:', err);
      Swal.fire('Error', 'No se pudo completar la solicitud.', 'error');
    }
  });
}

}
