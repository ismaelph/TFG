import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { PlantaService } from '../../services/planta.service';
import { EstanteriaService } from '../../services/estanteria.service';
import { Planta } from 'src/app/core/interfaces/planta';
import { Estanteria } from 'src/app/core/interfaces/estanteria';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-estructura-edit',
  templateUrl: './estructura-edit.component.html',
  styleUrls: ['./estructura-edit.component.css']
})
export class EstructuraEditComponent implements OnInit {
  @Input() almacenId!: number;

  plantas: Planta[] = [];
  estanteriaForm: FormGroup;

  constructor(
    private fb: FormBuilder,
    private plantaService: PlantaService,
    private estanteriaService: EstanteriaService
  ) {
    this.estanteriaForm = this.fb.group({
      codigo: ['', Validators.required],
      plantaId: [null, Validators.required]
    });
  }

  ngOnInit(): void {
    if (this.almacenId) {
      this.plantaService.obtenerPorAlmacen(this.almacenId).subscribe({
        next: (plantas) => this.plantas = plantas,
        error: () => alert('Error al cargar las plantas')
      });
    }
  }

  crear(): void {
  if (this.estanteriaForm.invalid) return;

  const nueva: Estanteria = this.estanteriaForm.value;

  this.estanteriaService.crear(nueva).subscribe({
    next: () => {
      Swal.fire({
        icon: 'success',
        title: 'Estantería añadida',
        text: 'La estantería se ha creado correctamente.',
        timer: 2000,
        showConfirmButton: false
      });

      this.estanteriaForm.reset();
    },
    error: () => {
      Swal.fire({
        icon: 'error',
        title: 'Error',
        text: 'No se pudo crear la estantería'
      });
    }
  });
}

}
