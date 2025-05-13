import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ProductoService } from '../../services/producto.service';
import { CategoriaService } from '../../services/categoria.service';
import { ProveedorService } from '../../services/proveedor.service';
import { Categoria } from 'src/app/core/interfaces/categoria';
import { Proveedor } from 'src/app/core/interfaces/proveedor';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-producto-create',
  templateUrl: './producto-create.component.html',
  styleUrls: ['./producto-create.component.css']
})
export class ProductoCreateComponent implements OnInit {
  productoForm!: FormGroup;
  categorias: Categoria[] = [];
  proveedores: Proveedor[] = [];
  error: string | null = null;

  constructor(
    private fb: FormBuilder,
    private productoService: ProductoService,
    private categoriaService: CategoriaService,
    private proveedorService: ProveedorService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.productoForm = this.fb.group({
      nombre: ['', [Validators.required]],
      precio: [null, [Validators.min(0)]],
      cantidad: [0, [Validators.required, Validators.min(0)]],
      usoInterno: [true],
      categoriaId: [null, Validators.required],
      proveedorId: [null]
    });

    this.categoriaService.getCategorias().subscribe({
      next: (data) => this.categorias = data
    });

    this.proveedorService.getProveedores().subscribe({
      next: (data) => this.proveedores = data
    });
  }

  onSubmit(): void {
    if (this.productoForm.invalid) return;

    const producto = this.productoForm.value;

    this.productoService.crearProducto(producto).subscribe({
      next: () => {
        Swal.fire('¡Producto creado!', 'Se ha añadido correctamente al inventario.', 'success');
        this.router.navigate(['/empresa/producto-list']);
      },
      error: () => {
        this.error = 'No se pudo crear el producto.';
      }
    });
  }

  cancelar(): void {
    this.router.navigate(['/empresa/producto-list']);
  }
}
