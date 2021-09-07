import { DomicilioEntrega } from './domicilioEntrega'

export interface Remito {
    id: number;
    remito: number;
    fechaArmado: Date;
    entregado: boolean;
    domicilioEntrega: DomicilioEntrega;
}