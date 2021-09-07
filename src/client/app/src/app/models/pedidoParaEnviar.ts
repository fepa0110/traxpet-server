import { ArticuloPedido } from './articuloPedido'
import { Cliente } from './cliente'
import { DomicilioEntrega } from './domicilioEntrega'

export interface PedidoParaEnviar {
    cliente: Cliente;
    domicilioEntrega: DomicilioEntrega;
    observaciones: string;
    fecha: Date;
    articulosPedido: ArticuloPedido[];
}