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
  ) {}

  ngOnInit(): void {
    console.log('🔄 Cargando empresas...');
    this.empresaService.obtenerTodas().subscribe({
      next: (res) => {
        this.empresas = res;
        console.log('✅ Empresas cargadas:', this.empresas);
      },
      error: (err) => {
        console.error('❌ Error al cargar empresas:', err);
        Swal.fire('Error', 'No se pudieron cargar las empresas.', 'error');
      }
    });
  }

  mostrarInput(empresaId: number): void {
    console.log('📝 Mostrando input para empresa ID:', empresaId);
    this.mostrandoClave = empresaId;
  }

  unirse(empresaId: number): void {
    console.log('🚀 Iniciando proceso para unirse a empresa ID:', empresaId);
    const clave = this.claves[empresaId]?.trim();
    console.log('🔑 Clave introducida:', clave);

    if (!clave) {
      console.warn('⚠️ Clave vacía');
      Swal.fire('Campo vacío', 'Debes ingresar la clave de acceso.', 'warning');
      return;
    }

    const empresa = this.empresas.find(e => e.id === empresaId);
    if (!empresa) {
      console.error('❌ Empresa no encontrada en la lista local');
      return;
    }

    const userId = this.tokenService.getUserId();
    console.log('👤 ID del usuario actual:', userId);

    if (userId == null) {
      console.error('❌ No se pudo obtener el ID del usuario desde el token');
      Swal.fire('Error', 'No se pudo obtener el ID del usuario.', 'error');
      return;
    }

    console.log('📡 Enviando solicitud al backend...');
    this.empresaService.unirseAEmpresa(userId, empresaId, clave).subscribe({
      next: () => {
        console.log('✅ Unión a empresa completada. Cerrando sesión y redirigiendo.');
        this.tokenService.clearAll();
        window.location.href = '/login';
      },
      error: (err) => {
        console.error('❌ ERROR AL UNIRSE:', err);
        const mensaje = err?.error?.error || 'No se pudo completar la solicitud.';
        Swal.fire('Error', mensaje, 'error');
      }
    });

    this.mostrandoClave = null;
    this.claves[empresaId] = '';
  }
}
