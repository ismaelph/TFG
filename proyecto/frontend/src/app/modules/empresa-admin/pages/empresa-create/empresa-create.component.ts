import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { EmpresaService } from '../../services/empresa.service';
import { Empresa } from '../../../../core/interfaces/empresa';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-empresa-create',
  templateUrl: './empresa-create.component.html',
  styleUrls: ['./empresa-create.component.css']
})
export class EmpresaCreateComponent implements OnInit {
  empresaForm!: FormGroup;
  error: string | null = null;

  @Output() cerrar = new EventEmitter<void>();

  constructor(
    private fb: FormBuilder,
    private empresaService: EmpresaService
  ) {}

  ngOnInit(): void {
    this.empresaForm = this.fb.group({
      nombre: ['', Validators.required],
      claveAcceso: ['', [Validators.required, Validators.minLength(4)]],
      repetirClave: ['', Validators.required]
    });
  }

  onSubmit(): void {
    this.error = null;

    if (this.empresaForm.invalid) {
      this.error = 'Completa todos los campos correctamente.';
      return;
    }

    const clave = this.empresaForm.get('claveAcceso')?.value;
    const repetir = this.empresaForm.get('repetirClave')?.value;

    if (clave !== repetir) {
      this.error = 'Las claves no coinciden.';
      return;
    }

    const nuevaEmpresa: Empresa = {
      nombre: this.empresaForm.get('nombre')?.value,
      claveAcceso: clave
    };

    console.log('[CREATE] Enviando empresa:', nuevaEmpresa);

    this.empresaService.crearEmpresa(nuevaEmpresa).subscribe({
      next: () => {
        Swal.fire('✅ Empresa creada', 'Ya eres administrador de tu empresa.', 'success');
        this.cerrar.emit();
      },
      error: (err) => {
        console.error('❌ Error al crear empresa:', err);
        this.error = 'No se pudo crear la empresa.';
      }
    });
  }

  cancelar(): void {
    this.cerrar.emit();
  }
}
