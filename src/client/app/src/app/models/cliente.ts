import { DomicilioEntrega } from './domicilioEntrega'

export interface Cliente {
    id: number;
    cliente: number;
    cuit: number;
    tipoDoc: string;
    nroDoc: string;
    razonSocial: string;
    domiciliosEntrega: DomicilioEntrega[];
}