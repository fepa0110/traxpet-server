import { Component, OnInit } from '@angular/core';

import { NgbModal } from "@ng-bootstrap/ng-bootstrap";
import { NgbdModalConfirm } from "../modal-component";

import { Cliente } from '../models/cliente';
import { ClienteService } from '../services/cliente.service';
import { ResultsPage } from "../results-page";

@Component({
  selector: 'app-clientes',
  templateUrl: './clientes.component.html',
  styleUrls: ['./clientes.component.css']
})
export class ClientesComponent implements OnInit {
  clientes: Cliente[];
  
  resultsPage: ResultsPage = <ResultsPage>{};
  pages: number[];
  currentPage: number = 1;

  constructor(
    private clienteService: ClienteService,
    private _modalService: NgbModal
  ) { }

  ngOnInit() {
    this.getClientes();
  }

  getClientes(): void {
    this.clienteService.byPage(this.currentPage, 10).subscribe((dataPackage) => {
      this.resultsPage = <ResultsPage>dataPackage.data;
      this.pages = Array.from(Array(this.resultsPage.last).keys());
    });
  }

  remove(numeroCliente: string): void {
    const modal = this._modalService.open(NgbdModalConfirm);
    const that = this;
    modal.result.then(
      function () {
        that.clienteService.remove(numeroCliente).subscribe((dataPackage) => that.getClientes());
      },
      function () { }
    );
    modal.componentInstance.title = "Eliminar cliente";
    modal.componentInstance.message = "¿Esta seguro de eliminar el cliente?";
    modal.componentInstance.description =
      "Si elimina el cliente no los podrá utilizar luego.";
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
    this.getClientes();
  };
}