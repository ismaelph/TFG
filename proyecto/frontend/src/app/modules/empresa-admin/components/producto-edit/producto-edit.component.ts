import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ProductoService } from '../../services/producto.service';
import { CategoriaService } from '../../services/categoria.service';
import { ProveedorService } from '../../services/proveedor.service';
import { EstanteriaService } from '../../services/estanteria.service';
import { Categoria } from 'src/app/core/interfaces/categoria';
import { Proveedor } from 'src/app/core/interfaces/proveedor';
import { Estanteria } from 'src/app/core/interfaces/estanteria';
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
  estanterias: Estanteria[] = [];

  error: string | null = null;
  productoId!: number;
  cargando: boolean = true;

  constructor(
    private fb: FormBuilder,
    private productoService: ProductoService,
    private categoriaService: CategoriaService,
    private proveedorService: ProveedorService,
    private estanteriaService: EstanteriaService
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
      estanteriaId: [null, Validators.required],
      stockMinimo: [0, [Validators.required, Validators.min(0)]]
    });

    Promise.all([
      this.categoriaService.getCategorias().toPromise(),
      this.proveedorService.getProveedores().toPromise(),
      this.estanteriaService.getEstanterias().toPromise()
    ])
    .then(([cats, provs, ests]) => {
      this.categorias = cats ?? [];
      this.proveedores = provs ?? [];
      this.estanterias = ests ?? [];

      console.log('✅ Categorías:', this.categorias);
      console.log('✅ Proveedores:', this.proveedores);
      console.log('✅ Estanterías:', this.estanterias);

      this.productoService.getProductoById(this.productoId).subscribe({
        next: (producto) => {
          console.log('✅ Producto:', producto);

          if (producto.categoriaId != null) producto.categoriaId = +producto.categoriaId;
          if (producto.proveedorId != null) producto.proveedorId = +producto.proveedorId;
          if (producto.estanteriaId != null) producto.estanteriaId = +producto.estanteriaId;
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
    })
    .catch((err) => {
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
    return a == b;
  }
}
