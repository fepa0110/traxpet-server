import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from "@angular/router";
import { Location } from "@angular/common";
import { ModalService } from "../modal.service";

import { NgbModal } from "@ng-bootstrap/ng-bootstrap";
import { NgbdModalConfirm } from "../modal-component";

import { Pedido } from '../models/pedido';
import { PedidoService } from '../services/pedido.service';
import { ResultsPage } from "../results-page";

@Component({
  selector: 'app-pedidos',
  templateUrl: './pedidos.component.html',
  styleUrls: ['./pedidos.component.css']
})
export class PedidosComponent implements OnInit {
  pedidos: Pedido[];
  byCliente: boolean = false;
  numeroCliente: string = "";
  
  resultsPage: ResultsPage = <ResultsPage>{};
  pages: number[];
  currentPage: number = 1;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private pedidoService: PedidoService,
    private _modalService: NgbModal,
    private location: Location
  ) { }

  ngOnInit() {
    this.getPedidos();
  }
  
  goBack(): void {
    this.location.back();
  }
  
  getPedidos(): void {
    const numeroCliente = this.route.snapshot.paramMap.get('id');
    
    if(numeroCliente == null){
      this.pedidoService.byPage(this.currentPage, 10).subscribe((dataPackage) => {
        this.resultsPage = <ResultsPage>dataPackage.data;
        this.pages = Array.from(Array(this.resultsPage.last).keys());
      });
    }
    else{
      this.byCliente = true;
      this.numeroCliente = numeroCliente;
      this.pedidoService.byNumeroClientePage(this.currentPage, 10, numeroCliente).subscribe((dataPackage) => {
        this.resultsPage = <ResultsPage>dataPackage.data;
        this.pages = Array.from(Array(this.resultsPage.last).keys());
      });
    }
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
    this.getPedidos();
  };
}