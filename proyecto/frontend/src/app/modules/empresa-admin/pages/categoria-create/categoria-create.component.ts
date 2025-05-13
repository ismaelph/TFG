import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CategoriaService } from '../../services/categoria.service';
import { ActivatedRoute, Router } from '@angular/router';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-categoria-create',
  templateUrl: './categoria-create.component.html',
  styleUrls: ['./categoria-create.component.css']
})
export class CategoriaCreateComponent implements OnInit {
  categoriaForm!: FormGroup;
  error: string | null = null;
  editando = false;
  categoriaId!: number;

  constructor(
    private fb: FormBuilder,
    private categoriaService: CategoriaService,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.categoriaForm = this.fb.group({
      nombre: ['', [Validators.required, Validators.minLength(3)]]
    });

    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.editando = true;
      this.categoriaId = +id;
      this.categoriaService.getCategoriaById(this.categoriaId).subscribe({
        next: (data) => {
          this.categoriaForm.patchValue({ nombre: data.nombre });
        },
        error: () => {
          this.error = 'No se pudo cargar la categoría.';
        }
      });
    }
  }

  onSubmit(): void {
    if (this.categoriaForm.invalid) return;

    if (this.editando) {
      this.categoriaService.actualizarCategoria(this.categoriaId, this.categoriaForm.value).subscribe({
        next: () => {
          Swal.fire('¡Categoría actualizada!', '', 'success');
          this.router.navigate(['/empresa/categoria-list']);
        },
        error: () => this.error = 'No se pudo actualizar la categoría.'
      });
    } else {
      this.categoriaService.crearCategoria(this.categoriaForm.value).subscribe({
        next: () => {
          Swal.fire('¡Categoría creada!', '', 'success');
          this.router.navigate(['/empresa/categoria-list']);
        },
        error: () => this.error = 'No se pudo crear la categoría.'
      });
    }
  }

  cancelar(): void {
    this.router.navigate(['/empresa/categoria-list']);
  }
}
