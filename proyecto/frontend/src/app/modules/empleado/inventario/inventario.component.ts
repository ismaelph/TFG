import { Component, OnInit } from '@angular/core';
import { Producto } from 'src/app/core/interfaces/producto';
import Swal from 'sweetalert2';
import { ProductoService } from '../services/producto.service';
import { MovimientoProducto } from 'src/app/core/interfaces/movimiento-producto';
import { MovimientoProductoService } from '../services/movimiento-producto.service';

@Component({
  selector: 'app-inventario',
  templateUrl: './inventario.component.html',
  styleUrls: ['./inventario.component.css']
})
export class InventarioComponent implements OnInit {
  productos: Producto[] = [];

  sliderActivo: { [id: number]: boolean } = {};
  cantidadSeleccionada: { [id: number]: number } = {};

  constructor(private productoService: ProductoService, private movimientoService: MovimientoProductoService) { }

  ngOnInit(): void {
    this.cargarProductos();
  }

  cargarProductos(): void {
    this.productoService.getProductos().subscribe({
      next: data => {
        this.productos = data;
        this.productos.forEach(p => {
          this.sliderActivo[p.id!] = false;
          this.cantidadSeleccionada[p.id!] = 1;
        });
      },
      error: err => {
        console.error('Error al cargar productos:', err);
      }
    });
  }

  toggleSlider(producto: Producto): void {
    this.sliderActivo[producto.id!] = !this.sliderActivo[producto.id!];
  }

  sumarCantidad(id: number): void {
    this.cantidadSeleccionada[id]++;
  }

  restarCantidad(id: number): void {
    if (this.cantidadSeleccionada[id] > 1) {
      this.cantidadSeleccionada[id]--;
    }
  }

  confirmarCoger(producto: Producto): void {
    const cantidad = this.cantidadSeleccionada[producto.id!];

    if (cantidad < 1 || cantidad > producto.cantidad) {
      Swal.fire('Error', 'Cantidad inválida', 'error');
      return;
    }

    this.movimientoService.transferirProducto({
      productoId: producto.id!,
      cantidad: cantidad
    }).subscribe({
      next: () => {
        Swal.fire('Hecho', `Has cogido ${cantidad} unidad(es) de "${producto.nombre}"`, 'success');
        this.sliderActivo[producto.id!] = false;
        this.cantidadSeleccionada[producto.id!] = 1;
        this.cargarProductos(); // recarga inventario de empresa
      },
      error: () => {
        Swal.fire('Error', 'No se pudo realizar la transferencia', 'error');
      }
    });
  }



}
