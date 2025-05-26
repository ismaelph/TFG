import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { EmpresaService } from '../../services/empresa.service';
import { Empresa } from '../../../../core/interfaces/empresa';
import { TokenService } from 'src/app/core/services/token.service';

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
  esAdminEmpresa = false;

  constructor(
    private fb: FormBuilder,
    private empresaService: EmpresaService,
    private route: ActivatedRoute,
    private router: Router,
    private tokenService: TokenService
  ) {}

  ngOnInit(): void {
    this.esAdminEmpresa = this.tokenService.hasRole('ROLE_ADMIN_EMPRESA');

    this.empresaForm = this.fb.group({
      nombre: ['', Validators.required]
    });

    this.route.paramMap.subscribe(params => {
      const id = params.get('id');
      if (id) {
        this.empresaId = +id;
        this.cargarEmpresa(this.empresaId);
      } else {
        this.cargando = false;
        this.error = 'ID inválido';
      }
    });
  }

  cargarEmpresa(id: number): void {
    this.empresaService.getEmpresaById(id).subscribe({
      next: (empresa) => {
        this.empresaForm.patchValue({ nombre: empresa.nombre });
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
      this.error = 'Formulario inválido.';
      return;
    }

    const empresa: Empresa = {
      id: this.empresaId,
      nombre: this.empresaForm.get('nombre')?.value,
      claveAcceso: '' // no se actualiza aquí
    };

    this.empresaService.actualizarEmpresa(this.empresaId, empresa).subscribe({
      next: () => {
        this.router.navigate(['/empresa/list']);
      },
      error: (err) => {
        console.error('[ERROR] No se pudo actualizar la empresa:', err);
        this.error = 'No se pudo actualizar la empresa.';
      }
    });
  }
}
