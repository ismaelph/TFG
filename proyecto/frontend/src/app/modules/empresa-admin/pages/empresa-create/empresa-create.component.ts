import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { EmpresaService } from '../../services/empresa.service';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-empresa-create',
  templateUrl: './empresa-create.component.html',
  styleUrls: ['./empresa-create.component.css']
})
export class EmpresaCreateComponent implements OnInit {
  empresaForm!: FormGroup;
  error: string | null = null;

  constructor(
    private fb: FormBuilder,
    private empresaService: EmpresaService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.empresaForm = this.fb.group({
      nombre: ['', Validators.required],
      claveAcceso: ['', Validators.required]
    });
  }

  onSubmit(): void {
    if (this.empresaForm.invalid) return;

    const nuevaEmpresa = this.empresaForm.value;

    this.empresaService.crearEmpresa(nuevaEmpresa).subscribe({
      next: () => {
        Swal.fire('¡Empresa creada!', 'Ya eres administrador de tu empresa.', 'success');
        this.router.navigate(['/empresa-admin/list']);
      },
      error: (err) => {
        console.error('Error al crear empresa:', err);
        this.error = 'No se pudo crear la empresa.';
      }
    });
  }

  cancelar(): void {
    this.router.navigate(['/empresa-admin/list']);
  }
}
