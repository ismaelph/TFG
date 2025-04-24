import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AutenticacionService } from '../../services/autenticacion.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ValidacionService } from '../../services/validacion.service';
import {DialogService} from "../../../main/services/dialog.service";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html'
})
export class RegisterComponent implements OnInit {

  formulario: FormGroup = this.fb.group({
    name              : ['',
      [Validators.required]],

    username          : ['',[Validators.required]],

    email             : [
                          // Valor por defecto
                          '',
                          // Validaciones síncronas
                          [
                            Validators.required,
                            this.validacionService.validarDominioCorreo,
                          ],
                          // Validaciones asíncronas
                          [],
                          { updateOn: 'blur' }  // Esto hará que la validación se ejecute solo cuando el campo pierda el foco

    ],

    password          : ['',[Validators.required]],

}, {
  // 008 Este segundo argumento que puedo enviar al formgroup permite por ejemplo ejecutar
  // validadores sincronos y asíncronos. Son validaciones al formgroup
  validators: [

   ]
});

  credenciales = {
    name: '',
    username: '',
    email: '',
    password: ''
  }

  errorRegistroUsuario: boolean = false;

  constructor(
    private router: Router,
    private autenticacionService: AutenticacionService,
    private validacionService: ValidacionService,
    private fb: FormBuilder,
    private dialogService: DialogService,
  ) { }

  ngOnInit(): void {
  }

  register() {
    if(this.formulario.invalid){
      this.formulario.markAllAsTouched();
      this.dialogService.mostrarMensaje('Revisar los campos');
      return;
    }
    const { name, username, email, password} = this.formulario.value;

    this.autenticacionService.registrarUsuario
      (name, username, email, password)
      .subscribe({
        next: (autentificado: boolean) => {
          this.router.navigate(['/auth/login']);
        },

        complete: () => { },

        error: (error: any) => {
          this.errorRegistroUsuario = true;
        }
      });
  }

  esDominioNoValido(campo: string) : boolean | undefined {
    // Se devuelve true cuando el campo es inválido y ha sido tocado por el usuario
    return this.formulario.get(campo)?.invalid && this.formulario.get(campo)?.touched;
  }

  mensajeErrorCampo(campo : string) : string {
    const errors = this.formulario.get(campo)?.errors;
    let mensajeError = '';

    if(errors) {
      for(let e in errors) {

        // Obtiene el mensaje
        const mensaje = this.validacionService.getMensajeError(e);
        mensajeError = mensajeError + mensaje;

        // Solo quiero el primero en estos momentos. Si hubiera más podría tenerlos en un atributo
        // y mostrarlos con un ngFor
        break;
      }
    }
    return mensajeError;
  }
}
