import { Component, Input } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import Swal from 'sweetalert2';
import { EmpresaService } from '../../services/empresa.service';

@Component({
  selector: 'app-empresa-cambiar-clave',
  templateUrl: './empresa-cambiar-clave.component.html'
})
export class EmpresaCambiarClaveComponent {
  @Input() empresaId!: number;
  form: FormGroup;
  cargando = false;

  constructor(private fb: FormBuilder, private empresaService: EmpresaService) {
    this.form = this.fb.group({
      claveNueva: ['', [Validators.required, Validators.minLength(6)]],
      confirmarClave: ['', Validators.required]
    });
  }

  cambiarClave(): void {
    const { claveNueva, confirmarClave } = this.form.value;

    if (claveNueva !== confirmarClave) {
      Swal.fire('Error', 'Las claves no coinciden', 'error');
      return;
    }

    this.cargando = true;
    this.empresaService.cambiarClave(this.empresaId, claveNueva).subscribe({
      next: () => {
        this.cargando = false;
        Swal.fire('Listo', 'La clave ha sido actualizada correctamente', 'success');
        this.form.reset();
      },
      error: () => {
        this.cargando = false;
        Swal.fire('Error', 'No se pudo actualizar la clave', 'error');
      }
    });
  }
}
