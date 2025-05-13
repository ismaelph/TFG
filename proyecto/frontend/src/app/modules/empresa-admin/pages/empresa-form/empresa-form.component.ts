import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { EmpresaService } from '../../services/empresa.service';
import { Empresa } from '../../../../core/interfaces/empresa';

@Component({
  selector: 'app-empresa-form',
  templateUrl: './empresa-form.component.html',
  styleUrls: ['./empresa-form.component.css']
})
export class EmpresaFormComponent implements OnInit {
  empresaForm!: FormGroup;
  empresaId!: number;
  cargando = true;
  error: string | null = null;

  constructor(
    private fb: FormBuilder,
    private empresaService: EmpresaService,
    private route: ActivatedRoute,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.empresaForm = this.fb.group({
      nombre: ['', Validators.required],
      claveAcceso: ['', Validators.required]
    });

    this.route.paramMap.subscribe(params => {
      const id = params.get('id');
      if (id) {
        this.empresaId = +id;
        console.log('[INIT] ID obtenido de ruta:', this.empresaId);
        this.cargarEmpresa(this.empresaId);
      } else {
        console.warn('[INIT] No se encontró parámetro ID');
        this.cargando = false;
      }
    });
  }

  cargarEmpresa(id: number): void {
    console.log('[CARGA] Cargando empresa con ID:', id);
    this.empresaService.getEmpresaById(id).subscribe({
      next: (empresa) => {
        console.log('[CARGA] Empresa recibida:', empresa);
        this.empresaForm.patchValue(empresa);
        this.cargando = false;
      },
      error: (err) => {
        console.error('[ERROR] No se pudo cargar la empresa:', err);
        this.error = 'No se pudo cargar la empresa';
        this.cargando = false;
      }
    });
  }

  onSubmit(): void {
    if (this.empresaForm.invalid) {
      console.warn('[SUBMIT] Formulario inválido');
      return;
    }

    if (!this.empresaId || isNaN(this.empresaId)) {
      console.error('[SUBMIT] ID inválido antes de actualizar:', this.empresaId);
      this.error = 'Error interno: ID de empresa no válido';
      return;
    }

    const empresa: Empresa = this.empresaForm.value;
    console.log('[SUBMIT] Actualizando empresa con ID:', this.empresaId);
    console.log('[SUBMIT] Datos enviados:', empresa);

    this.empresaService.actualizarEmpresa(this.empresaId, empresa).subscribe({
      next: () => {
        console.log('[SUBMIT] Actualización exitosa');
        this.router.navigate(['/empresa/list']);
      },
      error: (err) => {
        console.error('[ERROR] No se pudo actualizar la empresa:', err);
        this.error = 'No se pudo actualizar la empresa';
      }
    });
  }

}
