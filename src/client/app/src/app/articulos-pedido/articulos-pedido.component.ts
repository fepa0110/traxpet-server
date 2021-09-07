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


import { Pedido } from "../models/pedido";
import { Cliente } from "../models/cliente";
import { Remito } from "../models/remito";
import { ArticuloPedido } from "../models/articuloPedido";
import { DomicilioEntrega } from "../models/domicilioEntrega";
import { DataPackage } from "../data-package";
import { ResultsPage } from "../results-page";

import { PedidoService } from "../services/pedido.service";
import { ClienteService } from "../services/cliente.service";
import { ArticuloService } from "../services/articulo.service";
import { ArticuloPedidoService } from "../services/articulo-pedido.service";
import { RemitoService } from "../services/remito.service";

@Component({
  selector: 'app-articulos-pedido',
  templateUrl: './articulos-pedido.component.html',
  styleUrls: ['./articulos-pedido.component.css']
})
export class ArticulosPedidoComponent implements OnInit {
  alertMessage = "";
  showMessage: boolean = false;
  numeroCliente: string;

  articulosPedido: ArticuloPedido[] = []

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
    private modalService: ModalService
  ) {}

  ngOnInit() {
    this.getArticulosPedido();
  }

  goBack(): void {
    this.location.back();
  }
  
  getArticulosPedido(): void {
    const numeroCliente = this.route.snapshot.paramMap.get('id');
    this.articuloPedidoService.byNumeroClientePage(this.currentPage, 10, numeroCliente).subscribe((dataPackage) => {
      this.numeroCliente = numeroCliente;
      this.resultsPage = <ResultsPage>dataPackage.data;
      this.pages = Array.from(Array(this.resultsPage.last).keys());
    });
  }

  closeAlert(): void{
    this.showMessage = false;
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
    this.getArticulosPedido();
  };

}
