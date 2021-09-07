import { Component, OnInit } from '@angular/core';

import { ActivatedRoute, Router } from "@angular/router";
import { Location } from "@angular/common";
import { ModalService } from "../modal.service";

import {
  catchError,
  debounceTime,
  distinctUntilChanged,
  tap,
  switchMap,
  map,
} from "rxjs/operators";

import { Observable, of } from "rxjs";

import {
  NgbDateStruct,
  NgbCalendar,
  NgbDate,
} from "@ng-bootstrap/ng-bootstrap";

import { DataPackage } from "../data-package";
import { ResultsPage } from "../results-page";
import { Factura } from "../models/factura";
import { FacturaGenerada } from "../models/facturaGenerada";

import { PedidoService } from "../services/pedido.service";
import { ClienteService } from "../services/cliente.service";
import { ArticuloService } from "../services/articulo.service";
import { ArticuloPedidoService } from "../services/articulo-pedido.service";
import { FacturaService } from "../services/factura.service";

export interface FacturasGeneradas {
  facturas: FacturaGenerada[];
}

@Component({
  selector: 'app-facturas',
  templateUrl: './facturas.component.html',
  styleUrls: ['./facturas.component.css']
})
export class FacturasComponent implements OnInit {

  facturasGeneradas: FacturaGenerada[] = [];
  facturaAbonada: Factura;
  
  alertMessage = "";
  showMessage: boolean = false;
  fechaEmisionDate: NgbDateStruct;
  fechaPagoDate: NgbDateStruct;
  fechaEmision: Date;
  fechaPago: Date;

  resultsPage: ResultsPage = <ResultsPage>{};
  pages: number[];
  currentPage: number = 1;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private pedidoService: PedidoService,
    private clienteService: ClienteService,
    private articuloService: ArticuloService,
    private facturaService: FacturaService,
    private articuloPedidoService: ArticuloPedidoService,
    private location: Location,
    private calendar: NgbCalendar,
    private modalService: ModalService
  ) { }

  ngOnInit() {
    this.getFacturas();
  }

  closeAlert(): void{
    this.showMessage = false;
  }

  facturarTodo(): void{
    this.facturaService.generarFacturas().subscribe((dataPackage) => {
      this.facturasGeneradas = <FacturaGenerada[]>(<FacturasGeneradas>dataPackage.data).facturas;
      this.alertMessage = this.facturasGeneradas.length > 0 ? this.facturasGeneradas.length+" facturas generadas correctamente" :
        "No hay nada para facturar";
      this.showMessage = true;
    });
  }

  getFacturas(): void {
    const numeroCliente = this.route.snapshot.paramMap.get('id');

    if(numeroCliente == null){
      this.facturaService.byPage(this.currentPage, 10).subscribe((dataPackage) => {
        this.resultsPage = <ResultsPage>dataPackage.data;
        this.pages = Array.from(Array(this.resultsPage.last).keys());
      });
    }
    else{
      this.facturaService.byNumeroClientePage(this.currentPage, 10, numeroCliente).subscribe((dataPackage) => {
        this.resultsPage = <ResultsPage>dataPackage.data;
        this.pages = Array.from(Array(this.resultsPage.last).keys());
      });
    }
  }

  abonar(factura: Factura): void{
    this.facturaService.abonar(factura).subscribe((dataPackage) => {
      this.facturaAbonada = <Factura>dataPackage.data;
      this.alertMessage = dataPackage.StatusCode == 200 ? "Factura "+this.facturaAbonada.factura+" abonada correctamente" :
        "No se pudo entregar la factura";
      this.showMessage = true;
      this.getFacturas();
    });
  }

  showPage(pageId: number): void {
    if (!this.currentPage) {
      this.currentPage = 1;
    }
    let page = pageId;
    if (pageId == -2) { // First
      page = 1;
    }
    if (pageId == -1) { // Previous
      page = this.currentPage > 1 ? this.currentPage - 1 : this.currentPage;
    }
    if (pageId == -3) { // Next
      page = this.currentPage < this.resultsPage.last ? this.currentPage + 1 : this.currentPage;
    }
    if (pageId == -4) { // Last
      page = this.resultsPage.last;
    }
    if (pageId > 1 && this.pages.length >= pageId) { // Number
      page = this.pages[pageId - 1] + 1;
    }
    this.currentPage = page;
    this.getFacturas();
  };
}
