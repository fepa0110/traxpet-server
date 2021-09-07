import { Injectable } from '@angular/core';

import { Factura } from '../models/factura';
import { PedidoParaEnviar } from '../models/pedidoParaEnviar';
import { Observable, of } from 'rxjs';

import { HttpClient, HttpParams } from "@angular/common/http";
import { DataPackage } from "../data-package";

@Injectable({
  providedIn: 'root'
})
export class FacturaService {
  private facturasUrl = 'rest/facturas';

  constructor(private http: HttpClient) { }
  
  generarFacturas(): Observable<DataPackage> {
    return this.http['post']<DataPackage>(this.facturasUrl+"/facturar",{});
  }

  byPage(page: number, cant: number): Observable<DataPackage> {
    return this.http.get<DataPackage>(`${this.facturasUrl}/byPage?page=${page}&cant=${cant}`);
  }

  byNumeroClientePage(page: number, cant: number, numeroCliente: string): Observable<DataPackage> {
    return this.http.get<DataPackage>(`${this.facturasUrl}/byNumeroClientePage?page=${page}&cant=${cant}&numeroCliente=${numeroCliente}`);
  }

  abonar(factura: Factura): Observable<DataPackage> {
    return this.http['put']<DataPackage>(this.facturasUrl+"/abonar", factura);
  }

  getTotalFacturado(numeroFactura: string): Observable<DataPackage> {
    return this.http.get<DataPackage>(`${this.facturasUrl}/totalFacturado/${numeroFactura}`);
  }

  findByNumeroFactura(numeroFactura: string): Observable<DataPackage> {
    return this.http.get<DataPackage>(`${this.facturasUrl}/${numeroFactura}`);
  }
}