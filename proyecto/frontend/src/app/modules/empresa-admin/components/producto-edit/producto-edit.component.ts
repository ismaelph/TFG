import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ProductoService } from '../../services/producto.service';
import { CategoriaService } from '../../services/categoria.service';
import { ProveedorService } from '../../services/proveedor.service';
import { Categoria } from 'src/app/core/interfaces/categoria';
import { Proveedor } from 'src/app/core/interfaces/proveedor';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-producto-edit',
  templateUrl: './producto-edit.component.html',
  styleUrls: ['./producto-edit.component.css']
})
export class ProductoEditComponent implements OnInit {
  @Input() id!: number;
  @Output() cerrar = new EventEmitter<void>();

  productoForm!: FormGroup;
  categorias: Categoria[] = [];
  proveedores: Proveedor[] = [];
  error: string | null = null;
  productoId!: number;
  cargando: boolean = true;

  constructor(
    private fb: FormBuilder,
    private productoService: ProductoService,
    private categoriaService: CategoriaService,
    private proveedorService: ProveedorService
  ) {}

  ngOnInit(): void {
    this.productoId = this.id;
    console.log('📦 ID recibido para editar:', this.productoId);

    this.productoForm = this.fb.group({
      nombre: ['', Validators.required],
      precio: [null, Validators.min(0)],
      cantidad: [0, [Validators.required, Validators.min(0)]],
      usoInterno: [true],
      categoriaId: [null, Validators.required],
      proveedorId: [null],
      stockMinimo: [0, [Validators.required, Validators.min(0)]]
    });

    Promise.all([
      this.categoriaService.getCategorias().toPromise(),
      this.proveedorService.getProveedores().toPromise()
    ]).then(([cats, provs]) => {
      this.categorias = cats ?? [];
      this.proveedores = provs ?? [];
      console.log('✅ Categorías:', this.categorias);
      console.log('✅ Proveedores:', this.proveedores);

      this.productoService.getProductoById(this.productoId).subscribe({
        next: (producto) => {
          console.log('✅ Producto:', producto);

          producto.categoriaId = producto.categoriaId !== null ? +producto.categoriaId : null;
          producto.proveedorId = producto.proveedorId !== null ? +producto.proveedorId : null;
          producto.stockMinimo = producto.stockMinimo ?? 0;

          setTimeout(() => {
            this.productoForm.patchValue(producto);
            console.log('✅ patchValue aplicado:', this.productoForm.value);
            this.cargando = false;
          }, 0);
        },
        error: (err) => {
          console.error('❌ Error al cargar producto:', err);
          this.error = 'No se pudo cargar el producto.';
        }
      });
    }).catch((err) => {
      console.error('❌ Error inicial:', err);
      this.error = 'Error al cargar datos iniciales.';
    });
  }

  onSubmit(): void {
    if (this.productoForm.invalid) {
      console.warn('⚠️ Formulario inválido');
      return;
    }

    const producto = this.productoForm.value;
    console.log('📤 Guardando producto:', producto);

    this.productoService.actualizarProducto(this.productoId, producto).subscribe({
      next: () => {
        Swal.fire('¡Producto actualizado!', 'Cambios guardados correctamente.', 'success');
        this.cerrar.emit();
      },
      error: (err) => {
        console.error('❌ Error al guardar:', err);
        this.error = 'Error al guardar los cambios.';
      }
    });
  }

  cancelar(): void {
    this.cerrar.emit();
  }

  compararIds(a: any, b: any): boolean {
    return a == b; // importante: usar == y no ===
  }
}
