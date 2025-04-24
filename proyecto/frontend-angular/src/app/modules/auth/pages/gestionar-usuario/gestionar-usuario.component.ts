import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { tap } from 'rxjs';
import { User } from 'src/app/interfaces/user';
import { AdminService } from 'src/app/modules/admin/services/admin.service';
import { AutenticacionService } from 'src/app/modules/auth/services/autenticacion.service';

@Component({
  selector: 'app-gestionar-usuario',
  templateUrl: './gestionar-usuario.component.html'
})
export class GestionarUsuarioComponent implements OnInit {

  formulario: FormGroup = this.fb.group({
    id: [-1],
    name: [

    ],
    username: [

    ],
    email: [

    ],
    password: [

    ],
  })

  editar: boolean = false

  userId: number | null = null;

  constructor(
    private activatedRoute: ActivatedRoute,
    private fb: FormBuilder,
    private router: Router,
    private usuariosService: AdminService,
    private autenticacionService: AutenticacionService,
  ) {

  }

  ngOnInit(): void {
    this.userId = this.autenticacionService.getUserId();  
    if (this.userId) {
      this.cargarUsuarios();
      this.editar = true;
    }
  }

  guardar() {
    if (this.formulario.get('id')?.value > 0) {
      this.editarUsuario();
    }
  }

  cargarUsuarios(): void {
    if (this.userId) {
      this.usuariosService.obtenerUsuarioPorId(this.userId).pipe(
        tap(console.log)
      ).subscribe({
        next: (usuario: User) => {
          this.formulario.reset(usuario);
        },
        error: (error: any) => {
          console.error('Error al cargar los datos del usuario', error);
          this.router.navigate(['/home']);
        }
      });
    }
  }

  editarUsuario() {
    const idUsuario = this.formulario.get('id')?.value; // Obtén el ID del usuario del formulario
    const datosUsuario = this.formulario.getRawValue(); // Obtén los datos del formulario
  
    if (idUsuario) {
      this.usuariosService.actualizarUsuario(idUsuario, datosUsuario).subscribe({
        next: (usuario: User) => {
          console.log('Usuario actualizado con éxito:', usuario);
          this.router.navigate(['bienvenida']);
        },
        error: (error: any) => {
          console.error('Error al actualizar el usuario:', error);
        }
      });
    } else {
      console.error('El ID del usuario no está definido.');
    }
  }

}