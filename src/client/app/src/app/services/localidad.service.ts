import { Injectable } from '@angular/core';

import { Observable, of } from 'rxjs';

import { HttpClient } from "@angular/common/http";
import { DataPackage } from "../data-package";

@Injectable({
  providedIn: 'root'
})
export class LocalidadService {

  private localidadesUrl = 'rest/localidades';

  constructor(private http: HttpClient) { }

  search(text: string): Observable<DataPackage> {
    return this.http.get<DataPackage>(`${this.localidadesUrl}/search/${text}`);
  }
}
