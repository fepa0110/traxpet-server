import { Injectable } from '@angular/core';

import { Remito } from '../models/remito';
import { PedidoParaEnviar } from '../models/pedidoParaEnviar';
import { Observable, of } from 'rxjs';

import { HttpClient, HttpParams } from "@angular/common/http";
import { DataPackage } from "../data-package";

@Injectable({
  providedIn: 'root'
})
export class RemitoService {
  private remitosUrl = 'rest/remitos';

  constructor(private http: HttpClient) { }
  
  generarRemitos(fechaProceso: string): Observable<DataPackage> {
    return this.http['post']<DataPackage>(this.remitosUrl+"/generarRemitos?fechaProceso="+fechaProceso,{});
  }

  byPage(page: number, cant: number): Observable<DataPackage> {
    return this.http.get<DataPackage>(`${this.remitosUrl}/byPage?page=${page}&cant=${cant}`);
  }

  entregar(remito: Remito): Observable<DataPackage> {
    return this.http['put']<DataPackage>(this.remitosUrl+"/entregar", remito);
  }

  getByNumeroRemito(remito: string): Observable<DataPackage> {
    return this.http.get<DataPackage>(`${this.remitosUrl}/numero/${remito}`);
  }

  getByNumeroCliente(numeroCliente: string): Observable<DataPackage> {
    return this.http.get<DataPackage>(`${this.remitosUrl}/cliente/${numeroCliente}`);
  }
}