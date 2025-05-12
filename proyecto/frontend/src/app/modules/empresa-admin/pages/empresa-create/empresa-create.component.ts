import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { EmpresaService } from '../../services/empresa.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-empresa-create',
  templateUrl: './empresa-create.component.html',
  styleUrls: ['./empresa-create.component.css']
})
export class EmpresaCreateComponent implements OnInit {
  empresaForm!: FormGroup;
  error: string | null = null;

  @Output() cerrar = new EventEmitter<void>(); // nuevo output para cerrar modal

  constructor(
    private fb: FormBuilder,
    private empresaService: EmpresaService
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
        this.cerrar.emit(); // cierra el modal
      },
      error: (err) => {
        console.error('Error al crear empresa:', err);
        this.error = 'No se pudo crear la empresa.';
      }
    });
  }

  cancelar(): void {
    this.cerrar.emit(); // antes hacía router.navigate, ahora solo cierra
  }
}
