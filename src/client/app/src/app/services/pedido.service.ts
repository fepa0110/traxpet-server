import { Injectable } from '@angular/core';

import { Pedido } from '../models/pedido';
import { PedidoParaEnviar } from '../models/pedidoParaEnviar';
import { Observable, of } from 'rxjs';

import { HttpClient } from "@angular/common/http";
import { DataPackage } from "../data-package";

@Injectable({
  providedIn: 'root'
})
export class PedidoService {
  private pedidosUrl = 'rest/pedidos';

  constructor(private http: HttpClient) { }
  
  byPage(page: number, cant: number): Observable<DataPackage> {
    return this.http.get<DataPackage>(`${this.pedidosUrl}/byPage?page=${page}&cant=${cant}`);
  }

  byNumeroClientePage(page: number, cant: number, numeroCliente: string): Observable<DataPackage> {
    return this.http.get<DataPackage>(`${this.pedidosUrl}/byNumeroClientePage?page=${page}&cant=${cant}&numeroCliente=${numeroCliente}`);
  }

  save(pedidoParaEnviar: PedidoParaEnviar): Observable<DataPackage> {
    return this.http['post']<DataPackage>(this.pedidosUrl, pedidoParaEnviar);
  }

  get(numeroPedido: string): Observable<DataPackage> {
    return this.http.get<DataPackage>(`${this.pedidosUrl}/${numeroPedido}`);
  }

  getPrecioTotal(numeroPedido: string): Observable<DataPackage> {
    return this.http.get<DataPackage>(`${this.pedidosUrl}/precioTotal/${numeroPedido}`);
  }
  /*

  search(text: string): Observable<DataPackage> {
    return this.http.get<DataPackage>(`${this.pedidosUrl}/search/${text}`);
  }
  
  searchTypes(text: string): Observable<DataPackage> {
    return this.http.get<DataPackage>(`${this.pedidosUrl}/types/${text}`);
  }
  
  remove(code: string): Observable<DataPackage> {
    return this.http['delete']<DataPackage>(`${this.pedidosUrl}/${code}`);
  } */
}