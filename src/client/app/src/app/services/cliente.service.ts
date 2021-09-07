import { Injectable } from '@angular/core';

import { Pedido } from '../models/pedido';
import { PedidoParaEnviar } from '../models/pedidoParaEnviar';
import { Observable, of } from 'rxjs';

import { HttpClient } from "@angular/common/http";
import { DataPackage } from "../data-package";
import { Cliente } from '../models/cliente';

@Injectable({
  providedIn: 'root'
})
export class ClienteService {

  private clientesUrl = 'rest/clientes';

  constructor(private http: HttpClient) { }
  
  byPage(page: number, cant: number): Observable<DataPackage> {
    return this.http.get<DataPackage>(`${this.clientesUrl}/byPage?page=${page}&cant=${cant}`);
  }

  save(cliente: Cliente): Observable<DataPackage> {
    return this.http[cliente.id ? 'put' : 'post']<DataPackage>(this.clientesUrl, cliente);
  }

  remove(numeroCliente: string): Observable<DataPackage> {
    return this.http['delete']<DataPackage>(`${this.clientesUrl}/cliente/${numeroCliente}`);
  }

  search(text: string): Observable<DataPackage> {
    return this.http.get<DataPackage>(`${this.clientesUrl}/search/${text}`);
  }

  getDomiciliosEntregaByCuit(cuit: number): Observable<DataPackage> {
    return this.http.get<DataPackage>(`${this.clientesUrl}/cuit/${cuit}`);
  }

  getByNumeroCliente(cliente: string): Observable<DataPackage> {
    return this.http.get<DataPackage>(`${this.clientesUrl}/cliente/${cliente}`);
  }
}
