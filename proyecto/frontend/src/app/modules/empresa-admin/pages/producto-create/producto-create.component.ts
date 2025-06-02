import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ProductoService } from '../../services/producto.service';
import { CategoriaService } from '../../services/categoria.service';
import { ProveedorService } from '../../services/proveedor.service';
import { Categoria } from 'src/app/core/interfaces/categoria';
import { Proveedor } from 'src/app/core/interfaces/proveedor';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-producto-create',
  templateUrl: './producto-create.component.html',
  styleUrls: ['./producto-create.component.css']
})
export class ProductoCreateComponent implements OnInit {
  @Output() cerrar = new EventEmitter<void>();

  productoForm!: FormGroup;
  categorias: Categoria[] = [];
  proveedores: Proveedor[] = [];
  imagenSeleccionada: File | null = null;
  error: string | null = null;

  constructor(
    private fb: FormBuilder,
    private productoService: ProductoService,
    private categoriaService: CategoriaService,
    private proveedorService: ProveedorService
  ) { }

  ngOnInit(): void {
    this.productoForm = this.fb.group({
      nombre: ['', [Validators.required]],
      precio: [null, [Validators.required, Validators.min(0)]],
      cantidad: [0, [Validators.required, Validators.min(0)]],
      usoInterno: [true],
      stockMinimo: [0, [Validators.required, Validators.min(0)]],
      categoriaId: [null, Validators.required],
      proveedorId: [null],
      estanteriaId: [null, Validators.required]
    });

    this.categoriaService.getCategorias().subscribe({
      next: data => this.categorias = data,
      error: () => this.error = 'Error al cargar categorías'
    });

    this.proveedorService.getProveedores().subscribe({
      next: data => this.proveedores = data,
      error: () => this.error = 'Error al cargar proveedores'
    });
  }

  seleccionarImagen(event: any): void {
    const file = event.target.files?.[0];
    this.imagenSeleccionada = file ?? null;
  }

  onSubmit(): void {
    if (this.productoForm.invalid) return;

    const formData = new FormData();
    const producto = this.productoForm.value;
    formData.append('producto', JSON.stringify(producto));

    if (this.imagenSeleccionada) {
      formData.append('imagen', this.imagenSeleccionada);
    }

    this.productoService.crearProducto(producto, this.imagenSeleccionada ?? undefined).subscribe({
      next: () => {
        Swal.fire('¡Producto creado!', 'Se ha añadido correctamente al inventario.', 'success');
        this.cerrar.emit();
      },
      error: (err) => {
        console.error('❌ Error al crear producto:', err);
        this.error = 'No se pudo crear el producto.';
      }
    });
  }

  cancelar(): void {
    this.cerrar.emit();
  }
}
