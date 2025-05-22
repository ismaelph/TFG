import { Component, Input, Output, EventEmitter, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Almacen } from 'src/app/core/interfaces/almacen';
import { AlmacenService } from '../../services/almacen.service';
import { PlantaService } from '../../services/planta.service';
import { Planta } from 'src/app/core/interfaces/planta';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-almacen-edit',
  templateUrl: './almacen-edit.component.html',
  styleUrls: ['./almacen-edit.component.css']
})
export class AlmacenEditComponent implements OnInit {
  @Input() almacen: Almacen | null = null;
  @Output() cerrar = new EventEmitter<void>();
  @Output() guardado = new EventEmitter<void>();

  almacenForm: FormGroup;
  plantasActuales: number = 0;

  constructor(
    private fb: FormBuilder,
    private almacenService: AlmacenService,
    private plantaService: PlantaService
  ) {
    this.almacenForm = this.fb.group({
      nombre: ['', Validators.required],
      direccion: ['', Validators.required],
      numeroPlantas: [1, [Validators.required, Validators.min(1)]]
    });
  }

  ngOnInit(): void {
    if (this.almacen) {
      this.almacenForm.patchValue({
        nombre: this.almacen.nombre,
        direccion: this.almacen.direccion,
        numeroPlantas: 1
      });

      this.plantaService.obtenerPorAlmacen(this.almacen.id!).subscribe({
        next: (plantas) => {
          this.plantasActuales = plantas.length;
          this.almacenForm.patchValue({ numeroPlantas: this.plantasActuales });
        }
      });
    }
  }

  guardar(): void {
  if (this.almacenForm.invalid || !this.almacen || !this.almacen.id) return;

  const { nombre, direccion, numeroPlantas } = this.almacenForm.value;

  const actualizado: Almacen = {
    ...this.almacen,
    nombre,
    direccion
  };

  this.almacenService.actualizar(this.almacen.id, actualizado).subscribe({
    next: () => {
      const nuevas = numeroPlantas - this.plantasActuales;

      if (nuevas > 0) {
        for (let i = this.plantasActuales; i < numeroPlantas; i++) {
          const nueva: Planta = {
            nombre: i === 0 ? 'Planta Baja' : `Planta ${i}`,
            numero: i,
            almacenId: this.almacen!.id!
          };
          this.plantaService.crear(nueva).subscribe();
        }
      }

      Swal.fire({
        icon: 'success',
        title: 'Almacén actualizado',
        text: 'Los datos y las plantas fueron guardados correctamente',
        timer: 2000,
        showConfirmButton: false
      });

      this.guardado.emit();
      this.cerrar.emit();
    },
    error: () => {
      Swal.fire({
        icon: 'error',
        title: 'Error',
        text: 'No se pudo actualizar el almacén'
      });
    }
  });
}

}
