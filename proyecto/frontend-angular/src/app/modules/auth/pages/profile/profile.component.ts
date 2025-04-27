import { Component, OnInit } from '@angular/core';
import { AutenticacionService } from '../../services/autenticacion.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  usuario = {
    username: '',
    email: '',
    empresa: '',
    rol: '',
    fotoPerfil: '' // URL de la foto de perfil
  };
  fotoPerfilUrl: string | null = null; // URL para mostrar la foto de perfil

  constructor(private autenticacionService: AutenticacionService) {}

  ngOnInit(): void {
    this.cargarPerfil();
  }

  cargarPerfil(): void {
    this.autenticacionService.obtenerPerfil().subscribe((datos) => {
      // Asignar los datos recibidos al objeto usuario
      this.usuario = {
        username: datos.username || 'Sin usuario',
        email: datos.email || 'Sin correo',
        empresa: datos.empresa?.nombre || 'Sin empresa',
        rol: datos.rol || 'Sin rol',
        fotoPerfil: datos.fotoPerfil || 'assets/images/default-profile.png'
      };
      this.fotoPerfilUrl = this.usuario.fotoPerfil; // Actualizar la URL de la foto
    }, (error) => {
      console.error('Error al cargar el perfil:', error);
    });
  }

  onFileSelected(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files[0]) {
      const reader = new FileReader();
      reader.onload = () => {
        this.usuario.fotoPerfil = reader.result as string; // Convertir a Base64
        this.fotoPerfilUrl = this.usuario.fotoPerfil;
      };
      reader.readAsDataURL(input.files[0]);
    }
  }

  actualizarPerfil(): void {
    this.autenticacionService.actualizarPerfil(this.usuario).subscribe(() => {
      alert('Perfil actualizado correctamente');
    }, (error) => {
      console.error('Error al actualizar el perfil:', error);
    });
  }
}