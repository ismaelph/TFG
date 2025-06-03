import { Component, Input } from '@angular/core';
import { Producto } from 'src/app/core/interfaces/producto';

@Component({
  selector: 'app-banner-estado',
  templateUrl: './banner-estado.component.html',
  styleUrls: ['./banner-estado.component.css']
})
export class BannerEstadoComponent {
  @Input() producto!: Producto;

  get esUsoInterno(): boolean {
    return this.producto.usoInterno;
  }

  get esStockBajo(): boolean {
    return this.producto.stockMinimo !== undefined &&
      this.producto.cantidad <= this.producto.stockMinimo;
  }
}
