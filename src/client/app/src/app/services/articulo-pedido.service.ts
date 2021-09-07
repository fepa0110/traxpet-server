import { Injectable } from '@angular/core';

import { Observable, of } from 'rxjs';

import { HttpClient } from "@angular/common/http";
import { DataPackage } from "../data-package";

import { Articulo } from '../models/articulo';
import { Pedido } from '../models/pedido';
import { PedidoParaEnviar } from '../models/pedidoParaEnviar';

@Injectable({
  providedIn: 'root'
})
export class ArticuloPedidoService {

  private articulosPedidoUrl = 'rest/articulosPedido';

  constructor(private http: HttpClient) { }

  getByNumeroPedido(numeroPedido: string): Observable<DataPackage> {
    return this.http.get<DataPackage>(`${this.articulosPedidoUrl}/byPedido/${numeroPedido}`);
  }

  getByNumeroFactura(numeroFactura: string): Observable<DataPackage> {
    return this.http.get<DataPackage>(`${this.articulosPedidoUrl}/byFactura/${numeroFactura}`);
  }

  getByNumeroRemito(numeroRemito: string): Observable<DataPackage> {
    return this.http.get<DataPackage>(`${this.articulosPedidoUrl}/byRemito/${numeroRemito}`);
  }

  byNumeroClientePage(page: number, cant: number, numeroCliente: string): Observable<DataPackage> {
    return this.http.get<DataPackage>(`${this.articulosPedidoUrl}/byNumeroClientePage?page=${page}&cant=${cant}&numeroCliente=${numeroCliente}`);
  }
}
