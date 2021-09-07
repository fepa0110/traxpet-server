import { Injectable } from '@angular/core';

import { Pedido } from '../models/pedido';
import { PedidoParaEnviar } from '../models/pedidoParaEnviar';
import { Observable, of } from 'rxjs';

import { HttpClient } from "@angular/common/http";
import { DataPackage } from "../data-package";
import { Articulo } from '../models/articulo';

@Injectable({
  providedIn: 'root'
})
export class ArticuloService {

  private articulosUrl = 'rest/articulos';

  constructor(private http: HttpClient) { }

  getById(articuloId: string): Observable<DataPackage> {
    return this.http.get<DataPackage>(`${this.articulosUrl}/id/${articuloId}`);
  }

  byPage(page: number, cant: number): Observable<DataPackage> {
    return this.http.get<DataPackage>(`${this.articulosUrl}/byPage?page=${page}&cant=${cant}`);
  }

  search(text: string): Observable<DataPackage> {
    return this.http.get<DataPackage>(`${this.articulosUrl}/search/${text}`);
  }

  save(articulo: Articulo): Observable<DataPackage> {
    return this.http['post']<DataPackage>(this.articulosUrl, articulo);
  }
}
