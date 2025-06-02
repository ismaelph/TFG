import { Planta } from "./planta";

export interface Estanteria {
  id?: number;
  codigo: string;
  plantaId: number;
    planta?: Planta;

}
