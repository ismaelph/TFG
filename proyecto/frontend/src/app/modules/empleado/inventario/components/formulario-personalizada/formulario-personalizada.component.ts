import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { SolicitudPersonalizadaEmpleadoService } from '../../../services/solicitud-personalizada-Empleado.service';
import { NuevaSolicitudPersonalizada } from 'src/app/core/interfaces/NuevaSolicitudPersonalizada';

@Component({
  selector: 'app-formulario-personalizada',
  templateUrl: './formulario-personalizada.component.html',
  styleUrls: ['./formulario-personalizada.component.css']
})
export class FormularioPersonalizadaComponent implements OnInit {
  @Output() cerrarse = new EventEmitter<void>();

  form!: FormGroup;
  enviado = false;
  error = '';

  constructor(
    private fb: FormBuilder,
    private solicitudService: SolicitudPersonalizadaEmpleadoService
  ) {}

  ngOnInit(): void {
    this.form = this.fb.group({
      nombreProductoSugerido: ['', Validators.required],
      cantidadDeseada: [1, [Validators.required, Validators.min(1)]],
      descripcion: ['', Validators.required]
    });
  }

  enviarSolicitud(): void {
    if (this.form.invalid) {
      this.error = 'Por favor, completa todos los campos correctamente.';
      return;
    }

    const dto: NuevaSolicitudPersonalizada = this.form.value;
    this.enviado = true;
    this.error = '';

    this.solicitudService.crearSolicitud(dto).subscribe({
      next: () => {
        console.log('✅ Solicitud personalizada enviada:', dto);
        this.cerrarse.emit(); // Cierra el modal
      },
      error: (err) => {
        console.error('❌ Error al enviar solicitud personalizada:', err);
        this.error = 'Error al enviar la solicitud. Inténtalo de nuevo.';
        this.enviado = false;
      }
    });
  }

  cancelar(): void {
    this.cerrarse.emit(); // También se llama al hacer clic fuera del modal
  }
}
