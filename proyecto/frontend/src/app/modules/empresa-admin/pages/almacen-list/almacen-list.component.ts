import { Component, OnInit } from '@angular/core';
import { AlmacenService } from '../../services/almacen.service';
import { Almacen } from 'src/app/core/interfaces/almacen';
import { PlantaService } from '../../services/planta.service';
import { EstanteriaService } from '../../services/estanteria.service';
import Swal from 'sweetalert2';
import { forkJoin, map } from 'rxjs';

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

  cargando = false;
  error: string = '';

  modalAbierto = false;
  editando = false;
  almacenEditando: Almacen | null = null;

  infoAbierta = false;
  almacenInfo: any = null;

  estructuraAbierta = false;
  almacenEstructuraId: number | null = null;

  constructor(
    private almacenService: AlmacenService,
    private plantaService: PlantaService,
    private estanteriaService: EstanteriaService
  ) { }

  ngOnInit(): void {
    this.cargarAlmacenes();
  }

  cargarAlmacenes(): void {
    this.cargando = true;
    this.almacenService.obtenerTodos().subscribe({
      next: (res) => {
        this.almacenes = res;
        this.actualizarFiltro();
        this.cargando = false;
      },
      error: () => {
        this.error = 'Error al cargar los almacenes.';
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
    this.editando = false;
    this.almacenEditando = null;
    this.modalAbierto = true;
  }

  abrirModalEditar(almacen: Almacen): void {
    this.editando = true;
    this.almacenEditando = { ...almacen };
    this.modalAbierto = true;
  }

  cerrarModal(): void {
    this.modalAbierto = false;
  }

  confirmarEliminar(id: number): void {
    Swal.fire({
      title: '¿Eliminar almacén?',
      text: 'Esta acción no se puede deshacer.',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Sí, eliminar',
      cancelButtonText: 'Cancelar',
      confirmButtonColor: '#e53e3e',
      cancelButtonColor: '#6b7280'
    }).then((result) => {
      if (result.isConfirmed) {
        console.log('Intentando eliminar almacén ID:', id);

        this.almacenService.eliminar(id).subscribe({
          next: (res) => {
            console.log('Respuesta de eliminación:', res);
            this.almacenes = this.almacenes.filter(a => a.id !== id);
            this.actualizarFiltro();

            Swal.fire({
              icon: 'success',
              title: 'Eliminado',
              text: 'El almacén fue eliminado correctamente.',
              timer: 2000,
              showConfirmButton: false
            });
          },
          error: (err) => {
            console.error('Error al eliminar el almacén:', err);
            Swal.fire('Error', 'No se pudo eliminar el almacén.', 'error');
          }
        });
      }
    });
  }


  abrirInfo(almacen: Almacen): void {
    this.plantaService.obtenerPorAlmacen(almacen.id!).subscribe({
      next: (plantas) => {
        const peticiones = plantas.map(p =>
          this.estanteriaService.obtenerPorPlanta(p.id!).pipe(
            map(estanterias => ({ ...p, estanterias }))
          )
        );

        forkJoin(peticiones).subscribe({
          next: (plantasConEstanterias) => {
            this.almacenInfo = { ...almacen, plantas: plantasConEstanterias };
            this.infoAbierta = true;
          },
          error: () => {
            Swal.fire('Error', 'No se pudieron cargar las plantas/estanterías.', 'error');
          }
        });
      },
      error: () => {
        Swal.fire('Error', 'No se pudieron cargar las plantas.', 'error');
      }
    });
  }

  cerrarInfo(): void {
    this.almacenInfo = null;
    this.infoAbierta = false;
  }

  abrirEstructura(id: number): void {
    this.almacenEstructuraId = id;
    this.estructuraAbierta = true;
  }

  cerrarEstructura(): void {
    this.almacenEstructuraId = null;
    this.estructuraAbierta = false;
  }
}