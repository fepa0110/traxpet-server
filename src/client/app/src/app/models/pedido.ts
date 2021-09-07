import { Cliente } from './cliente'
import { DomicilioEntrega } from './domicilioEntrega'

export interface Pedido {
    id: number;
    pedido: number;
    fecha: Date;
    observaciones: string;
    cliente: Cliente;
    domicilioEntrega: DomicilioEntrega;
}