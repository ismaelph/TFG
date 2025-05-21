import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AlmacenService } from '../../services/almacen.service';
import { PlantaService } from '../../services/planta.service';
import { EstanteriaService } from '../../services/estanteria.service';
import { Almacen } from 'src/app/core/interfaces/almacen';
import { Planta } from 'src/app/core/interfaces/planta';
import { Estanteria } from 'src/app/core/interfaces/estanteria';
import { lastValueFrom } from 'rxjs';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-almacen-list',
  templateUrl: './almacen-list.component.html',
  styleUrls: ['./almacen-list.component.css']
})
export class AlmacenListComponent implements OnInit {
  almacenes: Almacen[] = [];
  filtrados: Almacen[] = [];
  searchTerm: string = '';
  ordenAscendente: boolean = true;
  cargando = true;
  error = '';

  almacenSeleccionado: Almacen | null = null;
  plantas: Planta[] = [];
  estanterias: Estanteria[] = [];

  modalAbierto = false;
  editando: boolean = false;
  almacenForm!: FormGroup;
  almacenEditadoId: number | null = null;
  empresaId: number = 0;

  constructor(
    private almacenService: AlmacenService,
    private plantaService: PlantaService,
    private estanteriaService: EstanteriaService,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.initForm();
    this.cargarAlmacenes();
  }

  initForm(): void {
    this.almacenForm = this.fb.group({
      nombre: ['', Validators.required],
      direccion: ['', Validators.required]
    });
  }

  cargarAlmacenes(): void {
    this.almacenService.obtenerTodos().subscribe({
      next: (data) => {
        this.almacenes = data;
        this.actualizarFiltro();
        this.cargando = false;
      },
      error: () => {
        this.error = 'Error al cargar almacenes';
        this.cargando = false;
      }
    });
  }

  actualizarFiltro(): void {
    const term = this.searchTerm.toLowerCase();
    this.filtrados = this.almacenes
      .filter(a => a.nombre.toLowerCase().includes(term))
      .sort((a, b) =>
        this.ordenAscendente
          ? a.nombre.localeCompare(b.nombre)
          : b.nombre.localeCompare(a.nombre)
      );
  }

  cambiarOrden(): void {
    this.ordenAscendente = !this.ordenAscendente;
    this.actualizarFiltro();
  }

  abrirModalCrear(): void {
    this.modalAbierto = true;
    this.editando = false;
    this.almacenEditadoId = null;
    this.almacenForm.reset();
  }

  abrirModalEditar(almacen: Almacen): void {
    this.modalAbierto = true;
    this.editando = true;
    this.almacenEditadoId = almacen.id!;
    this.almacenForm.setValue({
      nombre: almacen.nombre,
      direccion: almacen.direccion
    });
  }

  cerrarModal(): void {
    this.modalAbierto = false;
    this.almacenForm.reset();
  }

  guardar(): void {
    if (this.almacenForm.invalid) return;

    const formValue = this.almacenForm.value;

    if (this.editando && this.almacenEditadoId !== null) {
      const actualizado: Almacen = {
        id: this.almacenEditadoId,
        nombre: formValue.nombre,
        direccion: formValue.direccion,
        empresaId: 0
      };

      this.almacenService.actualizar(this.almacenEditadoId, actualizado).subscribe({
        next: (res) => {
          const index = this.almacenes.findIndex(a => a.id === res.id);
          if (index !== -1) this.almacenes[index] = res;
          this.actualizarFiltro();
          this.cerrarModal();
          Swal.fire('Actualizado', 'Almacén actualizado correctamente', 'success');
        },
        error: () => Swal.fire('Error', 'No se pudo actualizar el almacén', 'error')
      });

    } else {
      const nuevo: Almacen = {
        nombre: formValue.nombre,
        direccion: formValue.direccion,
        empresaId: this.empresaId // si lo necesitas, deberías obtenerlo al iniciar
      };

      this.almacenService.crear(nuevo).subscribe({
        next: (res) => {
          this.almacenes.push(res);
          this.actualizarFiltro();
          this.cerrarModal();
          Swal.fire('Creado', 'Almacén creado correctamente', 'success');
        },
        error: () => Swal.fire('Error', 'No se pudo crear el almacén', 'error')
      });
    }
  }

  confirmarEliminar(id: number): void {
    Swal.fire({
      title: '¿Eliminar almacén?',
      text: 'Esta acción no se puede deshacer',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Sí, eliminar',
      cancelButtonText: 'Cancelar'
    }).then(result => {
      if (result.isConfirmed) {
        this.almacenService.eliminar(id).subscribe({
          next: () => {
            this.almacenes = this.almacenes.filter(a => a.id !== id);
            this.actualizarFiltro();
            Swal.fire('Eliminado', 'Almacén eliminado correctamente', 'success');
          },
          error: () => Swal.fire('Error', 'No se pudo eliminar el almacén', 'error')
        });
      }
    });
  }

  abrirDetalles(almacen: Almacen): void {
    this.almacenSeleccionado = almacen;
    this.plantas = [];
    this.estanterias = [];

    this.plantaService.obtenerPorAlmacen(almacen.id!).subscribe({
      next: async (plantas) => {
        this.plantas = plantas;

        const allEstanterias = await Promise.all(
          plantas.map(p => lastValueFrom(this.estanteriaService.obtenerPorPlanta(p.id!)))
        );
        this.estanterias = allEstanterias.flat();
      }
    });
  }

  cerrarDetalles(): void {
    this.almacenSeleccionado = null;
    this.plantas = [];
    this.estanterias = [];
  }

  getEstanteriasPorPlanta(plantaId: number): Estanteria[] {
    return this.estanterias.filter(e => e.plantaId === plantaId);
  }
}
