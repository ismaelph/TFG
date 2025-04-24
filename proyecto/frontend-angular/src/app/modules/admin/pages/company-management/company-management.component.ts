import { Component, OnInit } from '@angular/core';
import { AdminService } from '../../services/admin.service';
import { Company } from '../../../../interfaces/company';

@Component({
  selector: 'app-company-management',
  templateUrl: './company-management.component.html',
  styleUrls: ['./company-management.component.css']
})
export class CompanyManagementComponent implements OnInit {
  empresas: Company[] = [];
  modalAbierto: boolean = false;
  modalTitulo: string = '';
  empresaSeleccionada: Partial<Company> = {};
  notificacionVisible: boolean = false;
  mensajeNotificacion: string = '';

  constructor(private adminService: AdminService) {}

  ngOnInit(): void {
    this.cargarEmpresas();
  }

  cargarEmpresas(): void {
    this.adminService.obtenerEmpresas().subscribe(empresas => {
      console.log('Empresas cargadas:', empresas);
      this.empresas = empresas;
    }, error => {
      console.error('Error al cargar empresas:', error);
    });
  }

  abrirModal(accion: 'crear' | 'editar', empresa?: Company): void {
    this.modalAbierto = true;
    this.modalTitulo = accion === 'crear' ? 'Crear Empresa' : 'Editar Empresa';
    this.empresaSeleccionada = empresa ? { ...empresa } : { nombre: '', password: '' };
  }

  cerrarModal(): void {
    this.modalAbierto = false;
    this.empresaSeleccionada = {};
  }

  guardarEmpresa(): void {
    if (this.empresaSeleccionada.id) {
      // Editar empresa
      this.adminService.actualizarEmpresa(this.empresaSeleccionada.id, this.empresaSeleccionada).subscribe(
        () => {
          this.mostrarNotificacion('Empresa actualizada correctamente');
          this.cargarEmpresas();
          this.cerrarModal();
        },
        (error) => {
          console.error('Error al actualizar la empresa:', error);
          this.mostrarNotificacion('Error al actualizar la empresa');
        }
      );
    } else {
      // Crear empresa
      this.adminService.crearEmpresa(this.empresaSeleccionada).subscribe(
        () => {
          this.mostrarNotificacion('Empresa creada correctamente');
          this.cargarEmpresas();
          this.cerrarModal();
        },
        (error) => {
          console.error('Error al crear la empresa:', error);
          this.mostrarNotificacion('Error al crear la empresa');
        }
      );
    }
  }

  eliminarEmpresa(id: number): void {
    this.adminService.eliminarEmpresa(id).subscribe(() => {
      this.mostrarNotificacion('Empresa eliminada correctamente');
      this.cargarEmpresas();
    });
  }

  mostrarNotificacion(mensaje: string): void {
    this.mensajeNotificacion = mensaje;
    this.notificacionVisible = true;
    setTimeout(() => {
      this.notificacionVisible = false;
    }, 3000);
  }
}