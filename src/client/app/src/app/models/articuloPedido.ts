import { Articulo } from './articulo'

export interface ArticuloPedido {
    id: number;
    cantidad: number;
    precio: number;
    articulo: Articulo
}