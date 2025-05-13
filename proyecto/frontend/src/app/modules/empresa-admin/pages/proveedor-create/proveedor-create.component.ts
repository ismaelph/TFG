import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import Swal from 'sweetalert2';
import { ProveedorService } from '../../services/proveedor.service';
import { Proveedor } from 'src/app/core/interfaces/proveedor';

@Component({
  selector: 'app-proveedor-create',
  templateUrl: './proveedor-create.component.html',
  styleUrls: ['./proveedor-create.component.css']
})
export class ProveedorCreateComponent implements OnInit {
  proveedorForm!: FormGroup;
  error: string | null = null;
  editando = false;
  proveedorId!: number;

  constructor(
    private fb: FormBuilder,
    private proveedorService: ProveedorService,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.proveedorForm = this.fb.group({
      nombre: ['', [Validators.required, Validators.minLength(3)]],
      email: ['', [Validators.required, Validators.email]],
      telefono: ['', [Validators.required, Validators.minLength(6)]]
    });

    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.editando = true;
      this.proveedorId = +id;
      this.proveedorService.getProveedorPorId(this.proveedorId).subscribe({
        next: (data) => this.proveedorForm.patchValue(data),
        error: () => this.error = 'No se pudo cargar el proveedor.'
      });
    }
  }

  onSubmit(): void {
    if (this.proveedorForm.invalid) return;

    const proveedor: Proveedor = this.proveedorForm.value;

    if (this.editando) {
      this.proveedorService.actualizarProveedor(this.proveedorId, proveedor).subscribe({
        next: () => {
          Swal.fire('¡Proveedor actualizado!', '', 'success');
          this.router.navigate(['/empresa/proveedor-list']);
        },
        error: () => this.error = 'No se pudo actualizar el proveedor.'
      });
    } else {
      this.proveedorService.crearProveedor(proveedor).subscribe({
        next: () => {
          Swal.fire('¡Proveedor creado!', '', 'success');
          this.router.navigate(['/empresa/proveedor-list']);
        },
        error: () => this.error = 'No se pudo crear el proveedor.'
      });
    }
  }

  cancelar(): void {
    this.router.navigate(['/empresa/proveedor-list']);
  }
}
