import { Cliente } from './cliente'
import { Remito } from './remito'

export interface FacturaGenerada {
    factura: number;
    cliente: Cliente;
    remitos: Remito[];
    totalFacturado: number;
}