import { Cliente } from './cliente'

export interface Factura {
    id: number;
    factura: number;
    fechaEmision: Date;
    fechaPago: Date;
    cliente: Cliente;
}