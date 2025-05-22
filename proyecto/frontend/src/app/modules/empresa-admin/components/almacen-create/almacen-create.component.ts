import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AlmacenService } from '../../services/almacen.service';
import { EmpresaService } from '../../services/empresa.service';
import { PlantaService } from '../../services/planta.service';
import { Almacen } from 'src/app/core/interfaces/almacen';
import { Planta } from 'src/app/core/interfaces/planta';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-almacen-create',
  templateUrl: './almacen-create.component.html',
  styleUrls: ['./almacen-create.component.css']
})
export class AlmacenCreateComponent implements OnInit {
  @Output() cerrar = new EventEmitter<void>();
  @Output() guardado = new EventEmitter<void>();

  almacenForm: FormGroup;
  empresaId: number = 0;

  constructor(
    private fb: FormBuilder,
    private almacenService: AlmacenService,
    private empresaService: EmpresaService,
    private plantaService: PlantaService
  ) {
    this.almacenForm = this.fb.group({
      nombre: ['', Validators.required],
      direccion: ['', Validators.required],
      numeroPlantas: [1, [Validators.required, Validators.min(1)]]
    });
  }

  ngOnInit(): void {
    this.empresaService.getMiEmpresa().subscribe({
      next: (empresa) => this.empresaId = empresa.id!,
      error: () => alert('No se pudo obtener la empresa')
    });
  }

  crear(): void {
  if (this.almacenForm.invalid || this.empresaId === 0) return;

  const { nombre, direccion, numeroPlantas } = this.almacenForm.value;

  const nuevo: Almacen = {
    nombre,
    direccion,
    empresaId: this.empresaId
  };

  this.almacenService.crear(nuevo).subscribe({
    next: (almacenCreado) => {
      for (let i = 0; i < numeroPlantas; i++) {
        const planta: Planta = {
          nombre: i === 0 ? 'Planta Baja' : `Planta ${i}`,
          numero: i,
          almacenId: almacenCreado.id!
        };
        this.plantaService.crear(planta).subscribe();
      }

      Swal.fire({
        icon: 'success',
        title: 'Almacén creado',
        text: 'Se han generado correctamente el almacén y sus plantas.',
        timer: 2000,
        showConfirmButton: false
      });

      this.guardado.emit();
      this.cerrar.emit();
      this.almacenForm.reset({ nombre: '', direccion: '', numeroPlantas: 1 });
    },
    error: () => {
      Swal.fire({
        icon: 'error',
        title: 'Error',
        text: 'No se pudo crear el almacén'
      });
    }
  });
}

}
