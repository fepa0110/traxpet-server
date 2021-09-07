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

import { Pedido } from "../models/pedido";
import { Cliente } from "../models/cliente";
import { Remito } from "../models/remito";
import { DomicilioEntrega } from "../models/domicilioEntrega";
import { DataPackage } from "../data-package";
import { ResultsPage } from "../results-page";

import { PedidoService } from "../services/pedido.service";
import { ClienteService } from "../services/cliente.service";
import { ArticuloService } from "../services/articulo.service";
import { ArticuloPedidoService } from "../services/articulo-pedido.service";
import { RemitoService } from "../services/remito.service";

@Component({
  selector: 'app-remitos',
  templateUrl: './remitos.component.html',
  styleUrls: ['./remitos.component.css']
})
export class RemitosComponent implements OnInit {
  alertMessage = "";
  showMessage: boolean = false;
  byCliente: boolean = false;
  numeroCliente: string;

  fechaProcesoDate: NgbDateStruct;
  fechaProceso: Date;
  remitosGenerados: Remito[] = []
  remitosById: Remito[] = []
  remitoEntregado: Remito = null;

  resultsPage: ResultsPage = <ResultsPage>{};
  pages: number[];
  currentPage: number = 1;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private pedidoService: PedidoService,
    private clienteService: ClienteService,
    private articuloService: ArticuloService,
    private remitoService: RemitoService,
    private articuloPedidoService: ArticuloPedidoService,
    private location: Location,
    private calendar: NgbCalendar,
    private modalService: ModalService
  ) {}

  ngOnInit() {
    this.fechaProceso = new Date();
    this.fechaProcesoDate = this.calendar.getToday();
    this.getRemitos();
  }

  goBack(): void {
    this.location.back();
  }
  
  getRemitos(): void {
    const action = this.route.snapshot.paramMap.get('action');
    if(!action){
      this.remitoService.byPage(this.currentPage, 10).subscribe((dataPackage) => {
        this.resultsPage = <ResultsPage>dataPackage.data;
        this.pages = Array.from(Array(this.resultsPage.last).keys());
      });
    }
    else{
      const id = this.route.snapshot.paramMap.get('id');
      if(action === "byCliente"){
        this.byCliente = true;
        this.remitoService.getByNumeroCliente(id).subscribe((dataPackage) => {
          this.remitosById = <Remito[]>dataPackage.data;
          this.numeroCliente = id;
        });
      }
    }
  }

  closeAlert(): void{
    this.showMessage = false;
  }

  generarRemitos(): void {
    this.fechaProceso = new Date(
        this.fechaProcesoDate.year,
        this.fechaProcesoDate.month,
        this.fechaProcesoDate.day
      );

    let fechaProcesoRequest = this.fechaProcesoDate.year+"-"+
        this.fechaProcesoDate.month+"-"+
        this.fechaProcesoDate.day;

    this.remitoService.generarRemitos(fechaProcesoRequest).subscribe((dataPackage) => {
      this.remitosGenerados = <Remito[]>dataPackage.data;
      this.alertMessage = this.remitosGenerados.length > 0 ? "Remitos generados correctamente" :
        "No se generaron remitos";
      this.showMessage = true;
    });
  }

  entregar(remito: Remito): void{
    this.remitoService.entregar(remito).subscribe((dataPackage) => {
      this.remitoEntregado = <Remito>dataPackage.data;
      this.alertMessage = dataPackage.StatusCode == 200 ? "Remito "+this.remitoEntregado.remito+" entregado correctamente" :
        "No se pudo entregar el remito";
      this.showMessage = true;
      this.getRemitos();
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
    this.getRemitos();
  };

}
