import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class DialogService {

  private HTML_MODAL_ALERT = `
  <div id="modalAlert" class="fixed inset-0 flex items-center justify-center bg-black bg-opacity-50 z-50 opacity-0 pointer-events-none transition-opacity duration-300">
    <div class="bg-white rounded-lg shadow-lg w-96">
      <div class="px-4 py-2 border-b">
        <h5 id="modalAlertTitle" class="text-lg font-bold">Advertencia</h5>
      </div>
      <div id="modalAlertBody" class="px-4 py-4 text-gray-700">
        CUERPO
      </div>
      <div class="px-4 py-2 border-t flex justify-end">
        <button id="modalAlertClose" class="bg-gray-300 text-gray-700 px-4 py-2 rounded hover:bg-gray-400 transition">
          Aceptar
        </button>
      </div>
    </div>
  </div>
`;

  private HTML_MODAL_CONFIRMAR = `
  <div id="modalConfirmar" class="fixed inset-0 flex items-center justify-center bg-black bg-opacity-50 z-50 opacity-0 pointer-events-none transition-opacity duration-300">
  <div class="bg-white rounded-lg shadow-lg w-96">
    <div class="px-4 py-2 border-b">
      <h5 id="modalConfirmarTitle" class="text-lg font-bold">Advertencia</h5>
    </div>
    <div id="modalConfirmarBody" class="px-4 py-4 text-gray-700">
      CONTENIDO
    </div>
    <div class="px-4 py-2 border-t flex justify-end gap-2">
      <button id="modalConfirmarCancelar" class="bg-gray-300 text-gray-700 px-4 py-2 rounded hover:bg-gray-400 transition">
        Cancelar
      </button>
      <button id="modalConfirmarAceptar" class="bg-red-500 text-white px-4 py-2 rounded hover:bg-red-600 transition">
        Aceptar
      </button>
    </div>
  </div>
  </div>
`;

  private HTML_TOAST = `
  <div id="__Toast" class="fixed top-4 right-4 bg-gray-800 text-white px-4 py-2 rounded shadow-lg opacity-0 transition-opacity duration-300">
    <span id="__ToastMessage"></span>
  </div>
`;

  constructor() { }

  /**
   * Muestra un mensaje en un modal de alerta
   * 
   * @param mensaje 
   * @param titulo 
   */
  mostrarMensaje(mensaje: string, titulo: string = 'Advertencia'): void {
    if (!document.getElementById('modalAlert')) {
      document.body.insertAdjacentHTML('beforeend', this.HTML_MODAL_ALERT);
    }

    const modal = document.getElementById('modalAlert');
    const modalTitle = document.getElementById('modalAlertTitle');
    const modalBody = document.getElementById('modalAlertBody');
    const modalClose = document.getElementById('modalAlertClose');

    if (modal && modalTitle && modalBody && modalClose) {
      modalTitle.textContent = titulo;
      modalBody.textContent = mensaje;

      modal.classList.remove('hidden');

      modalClose.onclick = () => {
        modal.classList.add('hidden');
      };
    }
  }

  /**
   * Solicita confirmación al usuario
   * 
   * @param mensaje 
   * @param titulo 
   * @param accion 
   */
  solicitarConfirmacion(mensaje: string, titulo: string, accion: () => void): void {
    if (!document.getElementById('modalConfirmar')) {
      document.body.insertAdjacentHTML('beforeend', this.HTML_MODAL_CONFIRMAR);
    }

    const modal = document.getElementById('modalConfirmar');
    const modalTitle = document.getElementById('modalConfirmarTitle');
    const modalBody = document.getElementById('modalConfirmarBody');
    const botonAceptar = document.getElementById('modalConfirmarAceptar');
    const botonCancelar = document.getElementById('modalConfirmarCancelar');

    if (modal && modalTitle && modalBody && botonAceptar && botonCancelar) {
      modalTitle.textContent = titulo;
      modalBody.textContent = mensaje;

      // Muestra el modal (quita 'hidden' y añade 'flex')
      modal.classList.remove('hidden');
      modal.classList.add('flex');

      botonAceptar.onclick = () => {
        accion();
        // Oculta el modal (quita 'flex' y añade 'hidden')
        modal.classList.remove('flex');
        modal.classList.add('hidden');
      };

      botonCancelar.onclick = () => {
        // Oculta el modal (quita 'flex' y añade 'hidden')
        modal.classList.remove('flex');
        modal.classList.add('hidden');
      };
    }
  }

  /**
   * Muestra un mensaje en formato Toast
   * 
   * @param mensaje 
   */
  mostrarToast(mensaje: string): void {
    if (!document.getElementById('__Toast')) {
      document.body.insertAdjacentHTML('beforeend', this.HTML_TOAST);
    }

    const toastElement = document.getElementById('__Toast');
    const toastMessage = document.getElementById('__ToastMessage');

    if (toastElement && toastMessage) {
      toastMessage.textContent = mensaje;

      // Muestra el toast
      toastElement.classList.remove('opacity-0');
      toastElement.classList.add('opacity-100');

      // Oculta el toast después de 2 segundos
      setTimeout(() => {
        toastElement.classList.remove('opacity-100');
        toastElement.classList.add('opacity-0');
      }, 2000);
    }
  }
}