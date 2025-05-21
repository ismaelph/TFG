import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { AlmacenService } from '../../services/almacen.service';
import { PlantaService } from '../../services/planta.service';
import { EstanteriaService } from '../../services/estanteria.service';
import { EmpresaService } from '../../services/empresa.service';
import { Almacen } from 'src/app/core/interfaces/almacen';
import { Planta } from 'src/app/core/interfaces/planta';
import { Estanteria } from 'src/app/core/interfaces/estanteria';

@Component({
  selector: 'app-almacen-create',
  templateUrl: './almacen-create.component.html',
  styleUrls: ['./almacen-create.component.css']
})
export class AlmacenCreateComponent implements OnInit {
  almacenForm: FormGroup;
  estanteriaForm: FormGroup;
  modoEdicion: boolean = false;
  almacenId: number | null = null;
  empresaId: number = 0;
  plantas: Planta[] = [];

  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private almacenService: AlmacenService,
    private plantaService: PlantaService,
    private estanteriaService: EstanteriaService,
    private empresaService: EmpresaService
  ) {
    this.almacenForm = this.fb.group({
      nombre: ['', Validators.required],
      direccion: ['', Validators.required],
      numeroPlantas: [1, [Validators.required, Validators.min(1)]]
    });

    this.estanteriaForm = this.fb.group({
      codigo: ['', Validators.required],
      plantaId: [null, Validators.required]
    });
  }

  ngOnInit(): void {
    this.almacenId = this.route.snapshot.params['id'];
    this.modoEdicion = !!this.almacenId;

    if (this.modoEdicion) {
      this.almacenService.obtenerPorId(this.almacenId!).subscribe({
        next: (almacen) => {
          this.almacenForm.patchValue({
            nombre: almacen.nombre,
            direccion: almacen.direccion,
            numeroPlantas: 1
          });

          this.plantaService.obtenerPorAlmacen(almacen.id!).subscribe({
            next: (plantas) => {
              this.plantas = plantas;
              this.almacenForm.patchValue({ numeroPlantas: plantas.length });
            }
          });
        },
        error: () => alert('Error al cargar el almacén')
      });
    } else {
      this.empresaService.getMiEmpresa().subscribe({
        next: (empresa) => this.empresaId = empresa.id!,
        error: () => alert('Error al obtener la empresa')
      });
    }
  }

  onSubmit(): void {
    if (this.almacenForm.invalid) return;

    const { nombre, direccion, numeroPlantas } = this.almacenForm.value;

    if (this.modoEdicion && this.almacenId !== null) {
      const actualizado: Almacen = {
        id: this.almacenId,
        nombre,
        direccion,
        empresaId: 0
      };

      this.almacenService.actualizar(this.almacenId, actualizado).subscribe({
        next: () => {
          const plantasActuales = this.plantas.length;
          const diferencia = numeroPlantas - plantasActuales;

          if (diferencia > 0) {
            for (let i = plantasActuales; i < numeroPlantas; i++) {
              const nueva: Planta = {
                nombre: `Planta ${i === 0 ? 'Baja' : i}`,
                numero: i,
                almacenId: this.almacenId!
              };
              this.plantaService.crear(nueva).subscribe({
                next: (p) => this.plantas.push(p)
              });
            }
          }

          alert('Almacén actualizado correctamente');
        },
        error: () => alert('Error al actualizar')
      });
    } else {
      const nuevo: Almacen = {
        nombre,
        direccion,
        empresaId: this.empresaId
      };

      this.almacenService.crear(nuevo).subscribe({
        next: (almacenCreado) => {
          for (let i = 0; i < numeroPlantas; i++) {
            const planta: Planta = {
              nombre: `Planta ${i === 0 ? 'Baja' : i}`,
              numero: i,
              almacenId: almacenCreado.id!
            };
            this.plantaService.crear(planta).subscribe();
          }

          this.almacenForm.reset({ nombre: '', direccion: '', numeroPlantas: 1 });
          alert('Almacén creado');
        },
        error: () => alert('Error al crear')
      });
    }
  }

  anadirEstanteria(): void {
    if (this.estanteriaForm.invalid || !this.modoEdicion || !this.almacenId) return;

    const { codigo, plantaId } = this.estanteriaForm.value;
    const nuevaEstanteria: Estanteria = {
      codigo,
      plantaId
    };

    this.estanteriaService.crear(nuevaEstanteria).subscribe({
      next: () => {
        alert('Estantería añadida');
        this.estanteriaForm.reset();
      },
      error: () => {
        alert('Error al añadir estantería');
      }
    });
  }
}
