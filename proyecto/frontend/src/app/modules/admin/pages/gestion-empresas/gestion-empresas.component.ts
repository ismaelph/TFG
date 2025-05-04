import { Component, OnInit } from '@angular/core';
import { EmpresasService } from '../../services/empresas.service';
import { Empresa } from 'src/app/interfaces/empresa';

@Component({
  selector: 'app-gestion-empresas',
  templateUrl: './gestion-empresas.component.html',
  styleUrls: ['./gestion-empresas.component.css'],
})
export class GestionEmpresasComponent implements OnInit {
  empresas: Empresa[] = [];
  empresaEditada: Partial<Empresa> = {};
  mostrarModal: boolean = false;

  constructor(private empresaService: EmpresasService) {}

  ngOnInit(): void {
    this.cargarEmpresas();
  }

  cargarEmpresas(): void {
    this.empresaService.get().subscribe((data) => {
      this.empresas = data;
    });
  }

  abrirModal(empresa?: Empresa): void {
    this.empresaEditada = empresa ? { ...empresa } : {};
    this.mostrarModal = true;
  }

  cerrarModal(): void {
    this.mostrarModal = false;
    this.empresaEditada = {};
  }

  save(): void {
    if (this.empresaEditada.id) {
      this.empresaService.put(this.empresaEditada.id, this.empresaEditada).subscribe(() => {
        this.cargarEmpresas();
        this.cerrarModal();
      });
    } else {
      this.empresaService.post(this.empresaEditada).subscribe(() => {
        this.cargarEmpresas();
        this.cerrarModal();
      });
    }
  }

  eliminarEmpresa(id: number): void {
    if (confirm('¿Estás seguro de eliminar esta empresa?')) {
      this.empresaService.delete(id).subscribe(() => {
        this.cargarEmpresas();
      });
    }
  }
}